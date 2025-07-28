package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entiry.OrderInfo;

public interface IOrderService extends IService<OrderInfo> {
    OrderInfo createOrder(String orderToken, Integer userId, Integer productId, Integer quantity);

    void handleOrderTimeout(String orderId);
}
