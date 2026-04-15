package com.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
<<<<<<< HEAD:gateway-service/src/main/java/com/mall/config/CorsConfig.java
    public CorsWebFilter corsWebFilter() {
=======
    public CorsWebFilter corsFilter() {
>>>>>>> f8aa785079fdf04af6b6b245a8dc915ab9afc632:gateway-service/src/main/java/org/example/conf/CorsConfig.java
        CorsConfiguration config = new CorsConfiguration();
        // 允许的请求头
        config.addAllowedHeader("*");
        // 允许的请求方法
        config.addAllowedMethod("*");
<<<<<<< HEAD:gateway-service/src/main/java/com/mall/config/CorsConfig.java
        // 允许的域
        config.addAllowedOriginPattern("*");
        // 允许携带cookie
        // config.setAllowCredentials(true);

=======
>>>>>>> f8aa785079fdf04af6b6b245a8dc915ab9afc632:gateway-service/src/main/java/org/example/conf/CorsConfig.java
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 允许跨域的路径
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
