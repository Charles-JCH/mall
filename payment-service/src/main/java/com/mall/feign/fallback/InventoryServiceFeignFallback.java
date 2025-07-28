package com.mall.feign.fallback;

import com.mall.api.R;
import com.mall.feign.IInventoryFeign;
import org.springframework.stereotype.Component;

@Component
public class InventoryServiceFeignFallback implements IInventoryFeign {
    @Override
    public R<String> reduceInventory(Integer productId, Integer quantity) {
        return R.error(500, "Inventory service error");
    }
}
