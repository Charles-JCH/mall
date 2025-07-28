package com.mall.listTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTest {
    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < 2; i++) {
            map.put("name", "name" + i);
            map.put("age", "age" + i);
            list.add(map);
        }

        System.out.println(list);
    }
}
