package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.entity.ProductInfo;
import com.mall.mapper.IProductMapper;
import com.mall.service.IProductService;
import com.mall.util.Log;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl extends ServiceImpl<IProductMapper, ProductInfo> implements IProductService {

    private static final String PRODUCT_BLOOM_FILTER = "product_bloom_filter";
    private static final Long EXPECTED_INSERTIONS = 100000L;
    private static final Double FALSE_PROBABILITY = 0.01;
    private static final String PRODUCT_DELETE_SET = "product_delete_set";
    private static final String PRODUCT_ID_PREFIX = "product_";
    private static final String PRODUCT_LOCK_PREFIX = "product_lock_";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IProductMapper productMapper;

    @PostConstruct
    public void initBloomFilter() {
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(PRODUCT_BLOOM_FILTER);
        if (bloomFilter.isExists()) {
            bloomFilter.delete();
        }
        bloomFilter.tryInit(EXPECTED_INSERTIONS, FALSE_PROBABILITY);
        List<Integer> productIds = this.list().stream().map(ProductInfo::getId).collect(Collectors.toList());
        for (Integer productId : productIds) {
            bloomFilter.add(productId);
        }
    }

    @Override
    @Cacheable(value = "product", key = "'products_page_' + #page + '_size_' + #size")
    public Page<ProductInfo> getAllProduct(Integer page, Integer size) {
        return this.page(new Page<>(page, size));
    }

    @Override
    public ProductInfo getProductById(Integer id) {
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(PRODUCT_BLOOM_FILTER);
        if (!bloomFilter.contains(id)) {
            Log.warn("bloom filter not found");
            return null;
        }
        Boolean isDeleted = redisTemplate.opsForSet().isMember(PRODUCT_DELETE_SET, String.valueOf(id));
        if (Boolean.TRUE.equals(isDeleted)) {
            Log.warn("product is deleted");
            return null;
        }
        String cacheKey = PRODUCT_ID_PREFIX + id;
        String productJson = redisTemplate.opsForValue().get(cacheKey);
        if (productJson != null) {
            if ("null".equals(productJson)) {
                return null;
            }
            try {
                return objectMapper.readValue(productJson, ProductInfo.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        RLock lock = redissonClient.getLock(PRODUCT_LOCK_PREFIX + id);
        try {
            boolean isLock = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (isLock) {
                productJson = redisTemplate.opsForValue().get(cacheKey);
                if (productJson != null) {
                    if ("null".equals(productJson)) {
                        return null;
                    }
                    try {
                        return objectMapper.readValue(productJson, ProductInfo.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
                ProductInfo product = this.getById(id);
                if (product != null) {
                    try {
                        String json = objectMapper.writeValueAsString(product);
                        redisTemplate.opsForValue().set(cacheKey, json, 3600, TimeUnit.SECONDS);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    redisTemplate.opsForValue().set(cacheKey, "null", 600, TimeUnit.SECONDS);
                }
                return product;
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }

    @Override
    public List<ProductInfo> getProductByName(String name) {
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return productMapper.selectList(queryWrapper);
    }

    @Override
    @CacheEvict(value = "product", allEntries = true)
    public boolean addProduct(ProductInfo productInfo) {
        boolean added = this.save(productInfo);
        if (added) {
            sendProductUpdateMessage(productInfo.getId(), "ADD");
        }
        return added;
    }

    @Override
    @CacheEvict(value = "product", allEntries = true)
    public boolean updateProductById(Integer id, ProductInfo product) {
        return this.updateById(product);
    }

    @Override
    @CacheEvict(value = "product", allEntries = true)
    public boolean deleteProductById(Integer id) {
        boolean removed = this.removeById(id);
        Log.debug(String.valueOf(removed));
        if (removed) {
            sendProductUpdateMessage(id, "DELETE");
        }
        return removed;
    }

    private void sendProductUpdateMessage(Integer productId, String productType) {
        Map<String, String> map = new HashMap<>();
        map.put("productId", String.valueOf(productId));
        map.put("productType", productType);
        try {
            String json = objectMapper.writeValueAsString(map);
            rocketMQTemplate.convertAndSend("product-bloom-topic", json);
            Log.info("send product update message: " + json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
