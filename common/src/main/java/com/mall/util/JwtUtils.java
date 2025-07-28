package com.mall.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {

    private String privateKeyPem;
    private String publicKeyPem;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public JwtUtils() {
    }

    public JwtUtils(String privateKeyPem, String publicKeyPem) {
        this.privateKeyPem = privateKeyPem;
        this.publicKeyPem = publicKeyPem;
        init();
    }

    @PostConstruct
    private void init() {
        if (publicKeyPem != null && !publicKeyPem.isEmpty()) {
            this.publicKey = RsaUtils.getPublicKey(publicKeyPem);
        }
        if (privateKeyPem != null && !privateKeyPem.isEmpty()) {
            this.privateKey = RsaUtils.getPrivateKey(privateKeyPem);
        }
    }

    public String generateToken(Map<String, Object> claims, long expireMillis) {
        if (privateKey == null) throw new IllegalStateException("privateKey missing");
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expireMillis))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        if (publicKey == null) throw new IllegalStateException("publicKey missing");
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            return null;
        }
    }
}