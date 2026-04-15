package org.example.controller;

import org.example.util.JwtUtil;
import org.example.vo.R;
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
    private JwtUtil jwtUtil;

    @GetMapping
    public R<String> createOrderToken(@RequestParam(name = "userId") String userId) {
        Map<String, Object> claims = new HashMap<>();
        String token = jwtUtil.generateToken(userId, claims);
        return R.ok(token);
    }
}
