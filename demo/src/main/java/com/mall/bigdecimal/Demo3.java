package com.mall.bigdecimal;

import java.math.BigDecimal;

public class Demo3 {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        BigDecimal bigDecimal1 = new BigDecimal("1000");
        BigDecimal bigDecimal2 = new BigDecimal("2000");

        stringBuilder.append(bigDecimal1).append("\n").append(bigDecimal2);

        System.out.println(stringBuilder);
    }
}
