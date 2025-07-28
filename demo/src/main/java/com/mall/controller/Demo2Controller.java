package com.mall.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo2")
public class Demo2Controller {

    @RequestMapping("/test")
    public String test() {
        Map<String, Object> map = new HashMap<>();
        map.put("file", "");
        map.put("type", null);
        map.put("token", null);

        // 创建 RestTemplate 实例
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // 创建HttpEntity（包含请求头和请求体）
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(map, headers);

        // 发送POST请求并获取响应
        return restTemplate.postForObject("https://www.baidu.com", requestEntity, String.class);
    }
}
