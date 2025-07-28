package com.mall.controller;

import com.mall.api.R;
import com.mall.entity.InventoryInfo;
import com.mall.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController extends BaseController<IInventoryService, InventoryInfo> {

    @Autowired
    private IInventoryService inventoryService;

    protected InventoryController(IInventoryService service) {
        super(service);
    }

    @PutMapping("/lock")
    public R<String> lockInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        boolean result = inventoryService.lockInventory(productId, quantity);
        if (!result) {
            return R.error(400, "Error locking inventory");
        }
        return R.ok("Inventory locked");
    }

    @PutMapping("/unlock")
    public R<String> unlockInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        boolean result = inventoryService.unlockInventory(productId, quantity);
        if (!result) {
            return R.error(400, "Error unlocking inventory");
        }
        return R.ok("Inventory unlocked");
    }

    @PutMapping("/reduce")
    public R<String> reduceInventory(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        boolean result = inventoryService.reduceInventory(productId, quantity);
        if (!result) {
            return R.error(400, "Error reducing inventory");
        }
        return R.ok("Inventory reduced");
    }
}
