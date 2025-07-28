package com.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product_attributes")
public class ProductAttribute {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer productId;
    private String attributeName;
    private String attributeValue;
}
