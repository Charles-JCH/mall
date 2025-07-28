package com.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.api.R;
import com.mall.config.RequestContextTtl;
import com.mall.dto.ProductDto;
import com.mall.entiry.OrderInfo;
import com.mall.feign.IInventoryFeign;
import com.mall.feign.IProductFeign;
import com.mall.mapper.IOrderMapper;
import com.mall.service.IOrderService;
import com.mall.util.Log;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class OrderServiceImpl extends ServiceImpl<IOrderMapper, OrderInfo> implements IOrderService {

    @Autowired
    private IProductFeign productFeign;

    @Autowired
    private IInventoryFeign inventoryFeign;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ThreadPoolExecutor executor;

    @Transactional
    @Override
    public OrderInfo createOrder(String orderToken, Integer userId, Integer productId, Integer quantity) {
        String orderTokenStr = redisTemplate.opsForValue().get(orderToken);
        if (orderTokenStr == null) {
            Log.warn("请勿重复提交订单");
            return null;
        }

        OrderInfo order = new OrderInfo();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        ProductDto productDto = productFeign.getProductById(productId).getData();
        if (productDto == null) {
            Log.error("订单创建失败, 未查询到商品信息, 商品ID: {}", productId);
            return null;
        }
        order.setPrice(productDto.getPrice());
        order.setTotalPrice(productDto.getPrice().multiply(new BigDecimal(quantity)));
        // 0-待支付
        order.setStatus(0);
        this.save(order);
        redisTemplate.delete(orderToken);

        // 锁定库存
        R<String> result = inventoryFeign.lockInventory(productId, quantity);
        if (result.getCode() == 400 || result.getCode() == 500) {
            order.setStatus(2);
            this.updateById(order);
            Log.warn("库存锁定失败: 订单取消");
            return order;
        }

        // 保存当前线程的上下文交给 mq 的异步线程
        RequestContextTtl.set(RequestContextHolder.getRequestAttributes());
        RequestContextHolder.setRequestAttributes(RequestContextTtl.get());

        // 发送延时消息, 1 分钟后检查订单是否支付
        Message<String> message = MessageBuilder.withPayload(order.getId()).build();
        rocketMQTemplate.syncSend("order-timeout-topic", message, 3000, 5);
        Log.debug("订单超时处理完成, 订单ID: {}", order.getId());
        return order;
    }

    @Transactional
    @Override
    public void handleOrderTimeout(String orderId) {
        OrderInfo order = this.getById(orderId);
        if (order != null && order.getStatus() == 0) {
            order.setStatus(2); // 2-已取消
            this.updateById(order);

            // 调用库存服务回滚库存（RPC 调用库存服务）
            inventoryFeign.unlockInventory(order.getProductId(), order.getQuantity());
        }
    }
}
