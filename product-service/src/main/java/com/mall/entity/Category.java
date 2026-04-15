package com.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
<<<<<<< HEAD:product-service/src/main/java/com/mall/entity/Category.java
@TableName("categories")
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer parentId;
=======
@TableName("users")
public class UserInfo {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态
     */
    private String status;
>>>>>>> f8aa785079fdf04af6b6b245a8dc915ab9afc632:user-service/src/main/java/org/example/entity/UserInfo.java
}
