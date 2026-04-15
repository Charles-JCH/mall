package com.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
<<<<<<< HEAD:product-service/src/main/java/com/mall/entity/Brand.java
@TableName("brands")
public class Brand {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String logo;
=======
@TableName("user_addresses")
public class UserAddress {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 收货人姓名
     */
    private String address;

    /**
     * 收货人邮编
     */
    private String postalCode;

    /**
     * 是否默认地址 0-非默认 1-默认
     */
    private Integer isDefault;
>>>>>>> f8aa785079fdf04af6b6b245a8dc915ab9afc632:user-service/src/main/java/org/example/entity/UserAddress.java
}
