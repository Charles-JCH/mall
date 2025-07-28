package com.mall.filter;

import com.alibaba.fastjson2.JSON;
import com.mall.constant.HttpHeaderConstants;
import com.mall.entity.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestHeaderAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, javax.servlet.ServletException {

        String userId = request.getHeader(HttpHeaderConstants.HEADER_USER_ID);
        String username = request.getHeader(HttpHeaderConstants.HEADER_USERNAME);
        String rolesJson = request.getHeader(HttpHeaderConstants.HEADER_ROLES);
        String permissionsJson = request.getHeader(HttpHeaderConstants.HEADER_PERMISSIONS);
        String tenantId = request.getHeader(HttpHeaderConstants.HEADER_TENANT_ID);
        String traceId = request.getHeader(HttpHeaderConstants.HEADER_TRACE_ID);

        if (!StringUtils.hasText(userId)) {
            filterChain.doFilter(request, response);
            return;
        }

        List<String> roles = new ArrayList<>();
        if (StringUtils.hasText(rolesJson)) {
            roles = JSON.parseArray(rolesJson, String.class);
        }

        List<String> permissions = new ArrayList<>();
        if (StringUtils.hasText(permissionsJson)) {
            permissions = JSON.parseArray(permissionsJson, String.class);
        }

        UserPrincipal principal = new UserPrincipal(userId, username, roles, permissions, tenantId, traceId);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
