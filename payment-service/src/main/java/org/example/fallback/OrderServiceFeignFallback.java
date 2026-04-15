package org.example.fallback;

import org.example.dto.OrderDto;
import org.example.feign.IOrderFeign;
import org.example.vo.R;
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
