package com.mall.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    /**
     * 商品id
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品品牌id
     */
    private Integer brandId;

    /**
     * 商品分类id
     */
    private Integer categoryId;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品状态
     */
    private Integer status;

    /**
     * 商品类型
     */
    private String type;
}
