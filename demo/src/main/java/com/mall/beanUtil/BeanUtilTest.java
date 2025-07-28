package com.mall.beanUtil;

import lombok.Data;
import org.springframework.beans.BeanUtils;

public class BeanUtilTest {
    public static void main(String[] args) {
        User user = new User();
        user.setName("张三");
        user.setAge(18);
        user.setSex("男");
        user.setAddress("上海");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        System.out.println(userDTO);
    }

}

@Data
class User {
    private String name;
    private int age;
    private String sex;
    private String address;
}

@Data
class UserDTO {
    private String userName;
    private int age;
    private String sex;
    private String phone;
}