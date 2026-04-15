package org.example.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Map;

@Configuration
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成token
     *
     * @param username 用户名
     * @param claims   claims
     * @return token
     */
    public String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 解析token
     *
     * @param token token
     * @return claims
     */
    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token
     *
     * @param token token
     */
    public void validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            claims.getExpiration();
        } catch (ExpiredJwtException e) {
            Log.error("token expired");
            throw e;
        } catch (JwtException e) {
            Log.error("token is invalid");
            throw e;
        }
    }
}