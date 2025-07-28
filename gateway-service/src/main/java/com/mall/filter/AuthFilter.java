package com.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.api.R;
import com.mall.api.ResultCode;
import com.mall.util.JwtUtils;
import com.mall.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String[] WHITE_LIST = {
            "/auth/**"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // OPTIONS请求放行
        // if (request.getMethod() == HttpMethod.OPTIONS) {
        //     response.setStatusCode(HttpStatus.OK);
        //     response.getHeaders().add("Access-Control-Allow-Headers", "*");
        //     response.getHeaders().add("Access-Control-Allow-Methods", "*");
        //     response.getHeaders().add("Access-Control-Allow-Origin", "*");
        //     return response.setComplete();
        // }

        // 白名单放行
        if (Arrays.stream(WHITE_LIST).anyMatch(path -> request.getURI().getPath().startsWith(path))) {
            return chain.filter(exchange);
        }

        // 获取token
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            R<String> error = R.failed(ResultCode.UNAUTHORIZED);
            String body;
            try {
                body = objectMapper.writeValueAsString(error);
                DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                Log.error("序列化失败", e);
                return response.setComplete();
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}