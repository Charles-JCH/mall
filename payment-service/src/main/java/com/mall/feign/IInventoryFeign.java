package com.mall.feign;

import com.mall.api.R;
import com.mall.feign.fallback.InventoryServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", fallback = InventoryServiceFeignFallback.class)
@Primary
public interface IInventoryFeign {

    @PutMapping("/inventory/reduce")
    R<String> reduceInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity);
}
