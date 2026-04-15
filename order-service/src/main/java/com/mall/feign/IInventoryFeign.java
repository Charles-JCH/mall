package com.mall.feign;

<<<<<<< HEAD:order-service/src/main/java/com/mall/feign/IInventoryFeign.java
import com.mall.api.R;
import com.mall.dto.InventoryDto;
import com.mall.feign.fallback.InventoryServiceFeignFallback;
=======
import org.example.dto.InventoryDto;
import org.example.fallback.InventoryServiceFeignFallback;
import org.example.vo.R;
>>>>>>> f8aa785079fdf04af6b6b245a8dc915ab9afc632:order-service/src/main/java/org/example/feign/IInventoryFeign.java
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
