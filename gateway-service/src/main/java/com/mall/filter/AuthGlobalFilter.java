package com.mall.filter;

import com.alibaba.fastjson2.JSON;
import com.mall.api.R;
import com.mall.api.ResultCode;
import com.mall.config.PublicKeyManager;
import com.mall.constant.HttpHeaderConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "secure.ignore")
@Data
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private List<String> urls;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private PublicKeyManager publicKeyManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 1. 白名单放行
        if (checkWhitelist(path)) {
            return chain.filter(exchange);
        }

        // 2. 获取 Token
        String token = request.getHeaders().getFirst("Authorization");
        if (!StringUtils.hasText(token)) {
            return unauthorized(exchange, "未登录，请携带 Token");
        }

        // 3. 移除 "Bearer " 前缀
        if (token.startsWith("Bearer ") || token.startsWith("bearer ")) {
            token = token.substring(7);
        }

        // 4. 校验 Token
        Claims claims;
        try {
            PublicKey publicKey = publicKeyManager.getPublicKey();
            if (publicKey == null) {
                return unauthorized(exchange, "网关未获取到公钥，服务暂不可用");
            }

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);

            claims = claimsJws.getBody();

        } catch (ExpiredJwtException e) {
            return unauthorized(exchange, "Token 已过期，请重新登录");
        } catch (Exception e) {
            return unauthorized(exchange, "Token 无效或被篡改");
        }

        // 5. 提取用户信息并透传 (Request Mutation)
        // 注意：WebFlux 中 request 是不可变的，必须用 mutate 构建新的 request
        String userId = claims.get("userId").toString();
        // auth 服务生成的 token 里 key 叫 "authorities"
        Object authorities = claims.get("authorities");

        ServerHttpRequest mutatedRequest = request.mutate()
                .header(HttpHeaderConstants.HEADER_USER_ID, userId)
                .header(HttpHeaderConstants.HEADER_ROLES, JSON.toJSONString(authorities))
                // 可以移除 Authorization 头，防止透传到下游（可选，看安全策略）
                // .headers(httpHeaders -> httpHeaders.remove("Authorization"))
                .build();

        // 6. 放行给下游
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    /**
     * 校验白名单
     */
    private boolean checkWhitelist(String path) {
        if (urls == null) return false;
        for (String url : urls) {
            if (pathMatcher.match(url, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回 401 JSON 响应
     */
    private Mono<Void> unauthorized(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        R<Void> result = R.failed(ResultCode.UNAUTHORIZED, msg);
        String json = JSON.toJSONString(result);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
