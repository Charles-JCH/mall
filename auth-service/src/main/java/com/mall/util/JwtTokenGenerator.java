package com.mall.util;

import com.mall.config.RsaKeyProperties;
import com.mall.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenGenerator {

    private final RsaKeyProperties rsaKeyProperties;

    public JwtTokenGenerator(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    public String generateToken(UserDTO userDTO) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDTO.getId());
        claims.put("username", userDTO.getUsername());
        claims.put("authorities", userDTO.getRoles());

        return Jwts.builder()
                .setSubject(userDTO.getUsername())
                .setClaims(claims)
                .setId(userDTO.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + rsaKeyProperties.getExpiration() * 1000))
                .signWith(rsaKeyProperties.getPrivateKey(), SignatureAlgorithm.RS256) // 直接使用读取到的 PrivateKey 对象
                .compact();
    }
}
