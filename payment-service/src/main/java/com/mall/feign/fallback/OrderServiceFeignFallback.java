package com.mall.feign.fallback;

import com.mall.api.R;
import com.mall.dto.OrderDto;
import com.mall.feign.IOrderFeign;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFeignFallback implements IOrderFeign {
    @Override
    public R<OrderDto> getOrderById(String id) {
        return R.error(404, "Order not found");
    }

    @Override
    public R<String> updateOrderById(String id, OrderDto orderInfo) {
        return R.error(500, "Order service error");
    }
}
