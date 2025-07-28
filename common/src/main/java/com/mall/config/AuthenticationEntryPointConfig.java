package com.mall.config;

import com.alibaba.fastjson2.JSON;
import com.mall.api.R;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求未认证处理
 */
@Deprecated
public class AuthenticationEntryPointConfig implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        R<Object> result = R.failed(authException.getLocalizedMessage());
        String json = JSON.toJSONString(result);
        response.getWriter().print(json);
    }
}
