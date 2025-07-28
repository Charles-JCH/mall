package com.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("products")
public class ProductInfo {
    private Integer id;
    private String name;
    private String description;
    private Integer brandId;
    private Integer categoryId;
    private BigDecimal price;
    private Integer status;
}
