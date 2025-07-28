package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.InventoryInfo;

public interface IInventoryService extends IService<InventoryInfo> {
    boolean lockInventory(Integer productId, Integer quantity);

    boolean unlockInventory(Integer productId, Integer quantity);

    boolean reduceInventory(Integer productId, Integer quantity);
}
