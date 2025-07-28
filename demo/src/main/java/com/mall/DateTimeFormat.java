package com.mall;

import java.time.format.DateTimeFormatter;

public class DateTimeFormat {

    public static void main(String[] args) {
        String format = "yyyy-MM-dd";

        String startData = "2021-01-01";
        String endData = "2022-01-02 12:00:00";

        DateTimeFormatter.ofPattern(format).parse(startData);

        System.out.println(startData + " " + endData);
    }
}
