package com.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
<<<<<<< HEAD:product-service/src/main/java/com/mall/entity/ProductInfo.java
@TableName("products")
public class ProductInfo {
=======
public class ProductDto {
    /**
     * 商品id
     */
>>>>>>> f8aa785079fdf04af6b6b245a8dc915ab9afc632:common/src/main/java/org/example/dto/ProductDto.java
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
<<<<<<< HEAD:product-service/src/main/java/com/mall/entity/ProductInfo.java
=======

    /**
     * 商品类型
     */
    private String type;
>>>>>>> f8aa785079fdf04af6b6b245a8dc915ab9afc632:common/src/main/java/org/example/dto/ProductDto.java
}
