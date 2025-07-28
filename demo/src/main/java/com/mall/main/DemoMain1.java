package com.mall.main;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;

public class DemoMain1 {
    public static void main(String[] args) {
        JSONArray jsonObject = JSON.parseArray("[{\"tom\":\"jack\"}]");
        System.out.println(jsonObject);

    }
}
