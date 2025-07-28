package com.mall.stringformatTest;

public class StringFormatDemo1 {

    public static void main(String[] args) {
        String name = "张三";
        int age = 18;
        String result = "";
        result += String.format("姓名：%s，年龄：%d \n", name, age);
        System.out.printf(result);
    }
}
