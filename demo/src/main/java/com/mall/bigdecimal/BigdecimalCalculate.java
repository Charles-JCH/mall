package com.mall.bigdecimal;

import java.math.BigDecimal;

public class BigdecimalCalculate {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("20.00000");
        BigDecimal b = new BigDecimal("100");
        BigDecimal c = a.divide(b);

        a.add(b);

        System.out.println(a);

        // if (c.compareTo(new BigDecimal("0.3")) > 0) {
        //     System.out.println("合格");
        // } else {
        //     System.out.println("不合格");
        //     System.out.println(a);
        // }
    }
}
