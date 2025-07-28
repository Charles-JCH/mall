package com.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.entity.InventoryInfo;
import org.apache.ibatis.annotations.Param;

public interface IInventoryMapper extends BaseMapper<InventoryInfo> {
    int lockInventory(@Param("productId") Integer productId, @Param("quantity") Integer quantity);
}
