package com.mall.mq;

import com.mall.config.RequestContextTtl;
import com.mall.service.IOrderService;
import com.mall.util.Log;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@RocketMQMessageListener(topic = "order-timeout-topic", consumerGroup = "order-consumer-group")
public class OrderTimeoutListener implements RocketMQListener<String> {

    @Autowired
    private IOrderService orderService;

    @Override
    public void onMessage(String orderId) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextTtl.set(requestAttributes);
        Log.debug("订单超时处理开始, 订单ID: {}", orderId);
        orderService.handleOrderTimeout(orderId);
    }
}
