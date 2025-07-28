package com.mall.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            requestTemplate.header("Authorization", request.getHeader("Authorization"));
        }
    }

    // @Bean("requestInterceptor")
    // public RequestInterceptor requestInterceptor() {
    //     return requestTemplate -> {
    //         ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    //         if (requestAttributes != null) {
    //             HttpServletRequest request = requestAttributes.getRequest();
    //             String authorization = request.getHeader("Authorization");
    //             requestTemplate.header("Authorization", authorization);
    //         }
    //     };
    // }
}
