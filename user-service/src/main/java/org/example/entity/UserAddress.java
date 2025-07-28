package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
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
}
