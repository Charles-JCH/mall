package com.mall.config;

import com.alibaba.fastjson2.JSON;
import com.mall.api.R;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理
 */
@Deprecated
public class AuthenticationSuccessHandlerConfig implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Object principal = authentication.getPrincipal();
        R<Object> result = R.success(principal);
        String json = JSON.toJSONString(result);
        response.getWriter().print(json);
    }
}
