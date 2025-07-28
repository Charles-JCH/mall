package com.mall.feign.fallback;

import com.mall.api.R;
import com.mall.dto.InventoryDto;
import com.mall.feign.IInventoryFeign;
import org.springframework.stereotype.Component;

@Component
public class InventoryServiceFeignFallback implements IInventoryFeign {
    @Override
    public R<InventoryDto> getInventoryById(Integer id) {
        return R.error(500, "Inventory service error");
    }

    @Override
    public R<String> lockInventory(Integer productId, Integer quantity) {
        return R.error(500, "Inventory service error");
    }

    @Override
    public R<String> unlockInventory(Integer productId, Integer quantity) {
        return R.error(500, "Inventory service error");
    }

    @Override
    public R<String> reduceInventory(Integer productId, Integer quantity) {
        return R.error(500, "Inventory service error");
    }
}
