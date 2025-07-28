package com.mall.dto;

import lombok.Data;

@Data
public class InventoryDto {
    /**
     * 库存id
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 锁定库存数量
     */
    private Integer reservedStock;
}
