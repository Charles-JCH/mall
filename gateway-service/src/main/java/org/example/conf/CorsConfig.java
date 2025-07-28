package org.example.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有源访问
        config.addAllowedOrigin("*");
        // 允许的请求头
        config.addAllowedHeader("*");
        // 允许的请求方式
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 允许跨域的路径
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
