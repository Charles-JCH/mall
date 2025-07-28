package com.mall.config;

import com.alibaba.fastjson2.JSON;
import com.mall.api.R;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有权限处理
 */
@Deprecated
public class AccessDeniedHandlerConfig implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        R<Object> result = R.failed("没有权限");
        String json = JSON.toJSONString(result);
        response.getWriter().print(json);
    }
}
