package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dao.IInventoryMapper;
import org.example.entity.InventoryInfo;
import org.example.service.IInventoryService;
import org.springframework.stereotype.Service;

@Service
public class InventoryImpl extends ServiceImpl<IInventoryMapper, InventoryInfo> implements IInventoryService {
    @Override
    public boolean lockInventory(Integer productId, Integer quantity) {
        int rowsAffected = baseMapper.lockInventory(productId, quantity);
        return rowsAffected > 0;
    }

    @Override
    public boolean unlockInventory(Integer productId, Integer quantity) {
        return this.update(new LambdaUpdateWrapper<InventoryInfo>()
                .eq(InventoryInfo::getProductId, productId)
                .ge(InventoryInfo::getReserveStock, quantity)
                .setSql("reserve_stock = reserve_stock - " + quantity));
    }

    @Override
    public boolean reduceInventory(Integer productId, Integer quantity) {
        return this.update(new LambdaUpdateWrapper<InventoryInfo>()
                .eq(InventoryInfo::getProductId, productId)
                .setSql("reserve_stock = reserve_stock - " + quantity + ", stock = stock - " + quantity));
    }
}
