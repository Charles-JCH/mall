package org.example.fallback;

import org.example.feign.IInventoryFeign;
import org.example.vo.R;
import org.springframework.stereotype.Component;

@Component
public class InventoryServiceFeignFallback implements IInventoryFeign {
    @Override
    public R<String> reduceInventory(Integer productId, Integer quantity) {
        return R.error(500, "Inventory service error");
    }
}
