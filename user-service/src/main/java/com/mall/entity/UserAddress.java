package com.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_address")
public class UserAddress {
    /**
     * 主键(雪花算法)
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 收货人姓名
     */
    @TableField("username")
    private String username;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 收货地址
     */
    @TableField("address")
    private String address;

    /**
     * 收货邮编
     */
    @TableField("postal_code")
    private String postalCode;

    /**
     * 是否默认地址 0-非默认 1-默认
     */
    @TableField("is_default")
    private Integer isDefault;
}
