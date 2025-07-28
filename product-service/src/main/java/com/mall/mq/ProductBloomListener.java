package com.mall.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.util.Log;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RocketMQMessageListener(topic = "product-bloom-topic", consumerGroup = "product-consumer-group")
public class ProductBloomListener implements RocketMQListener<String> {

    private static final String BLOOM_FILTER_KEY = "product_bloom_filter";
    private static final String PRODUCT_DELETE_SET = "product_delete_set";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(String message) {
        try {
            Map<String, String> map = objectMapper.readValue(message, new TypeReference<>() {
            });
            String productId = map.get("productId");
            String productType = map.get("productType");
            RBloomFilter<Object> productBloom = redissonClient.getBloomFilter(BLOOM_FILTER_KEY);
            if ("ADD".equals(productType)) {
                productBloom.add(productId);
            } else if ("DELETE".equals(productType)) {
                redisTemplate.opsForSet().add(PRODUCT_DELETE_SET, productId);
            }
            Log.debug("🚀 消息消费成功");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
