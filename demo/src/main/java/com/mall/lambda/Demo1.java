package com.mall.lambda;

import lombok.Data;

import java.util.List;

public class Demo1 {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("tom", 20),
                new User("tom", 21)
        );

        System.out.println(users.stream()
                .filter(user -> user.getName().equals("tom"))
                .map(user -> user.getAge())
                .findFirst().orElse(0));


    }


}

@Data
class User {
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}


