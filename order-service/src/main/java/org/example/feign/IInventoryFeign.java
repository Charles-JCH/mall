package org.example.feign;

import org.example.dto.InventoryDto;
import org.example.fallback.InventoryServiceFeignFallback;
import org.example.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", fallback = InventoryServiceFeignFallback.class)
@Primary
public interface IInventoryFeign {

    @GetMapping("/inventory/{id}")
    R<InventoryDto> getInventoryById(@PathVariable("id") Integer id);

    @PutMapping("/inventory/lock")
    R<String> lockInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity);

    @PutMapping("/inventory/unlock")
    R<String> unlockInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity);

    @PutMapping("/inventory/reduce")
    R<String> reduceInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity);
}
