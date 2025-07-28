package org.example.service.impl;

import org.example.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private RestTemplate restTemplate;

    public void getForObject() {
        Map<String, String> param = new HashMap<>();
        param.put("name", "lily");
        String response = restTemplate.getForObject("http://localhost:8080/demo", String.class, param);
    }

    public void getForEntity() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/demo", String.class);
    }
}
