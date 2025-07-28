package com.mall.controller;

import com.mall.api.R;
import com.mall.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orderToken")
public class OrderTokenController {

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public R<String> createOrderToken(@RequestParam(name = "userId") String userId) {
        Map<String, Object> claims = new HashMap<>();
        // String token = jwtUtils.createAccessToken(claims, userId);
        return R.ok(userId);
    }
}
