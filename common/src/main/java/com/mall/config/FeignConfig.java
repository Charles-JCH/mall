package com.mall.config;

import com.mall.constant.HttpHeaderConstants;
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
            String token = request.getHeader(HttpHeaderConstants.HEADER_AUTHORIZATION);
            if (token != null) {
                requestTemplate.header(HttpHeaderConstants.HEADER_AUTHORIZATION, token);
            }
        }
    }
}
