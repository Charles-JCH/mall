package com.mall.feign;

import com.mall.api.R;
import com.mall.dto.OrderDto;
import com.mall.feign.fallback.OrderServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", fallback = OrderServiceFeignFallback.class)
@Primary
public interface IOrderFeign {

    @GetMapping("/order/{id}")
    R<OrderDto> getOrderById(@PathVariable String id);

    @PutMapping("/order/{id}")
    R<String> updateOrderById(@PathVariable String id, @RequestBody OrderDto orderInfo);
}
