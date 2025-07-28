package com.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entiry.OrderInfo;

public interface IOrderService extends IService<OrderInfo> {
    OrderInfo createOrder(String orderToken, Integer userId, Integer productId, Integer quantity);

    void handleOrderTimeout(String orderId);
}
