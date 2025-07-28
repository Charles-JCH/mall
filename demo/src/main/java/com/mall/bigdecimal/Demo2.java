package com.mall.bigdecimal;

import java.time.LocalDate;

public class Demo2 {

    public static void main(String[] args) {
        String result = getPreviousQuarter(LocalDate.now());
        System.out.println(result);
    }

    public static String getPreviousQuarter(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();

        // 计算当前季度（1-4）
        int currentQuarter = (month - 1) / 3 + 1;

        // 计算上一个季度
        int previousQuarter = currentQuarter - 1;
        if (previousQuarter == 0) {
            previousQuarter = 4;
            year--; // 跨年处理（如1月属于Q1，上一个季度是去年的Q4）
        }

        // 提取年份的后两位（如2025 → 25）
        String shortYear = String.format("%02d", year % 100);

        return shortYear + "Q" + previousQuarter;
    }
}
