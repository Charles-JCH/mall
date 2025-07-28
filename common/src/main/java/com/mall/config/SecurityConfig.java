package com.mall.config;

import com.mall.filter.RequestHeaderAuthenticationFilter;
import com.mall.properties.SecurityIgnoreUrlProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ConditionalOnProperty(prefix = "security", name = "enable", havingValue = "true")
@EnableConfigurationProperties(SecurityIgnoreUrlProperties.class)
public class SecurityConfig {
    private final SecurityIgnoreUrlProperties securityIgnoreUrlProperties;
    private final RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter;

    public SecurityConfig(SecurityIgnoreUrlProperties securityIgnoreUrlProperties, RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter) {
        this.securityIgnoreUrlProperties = securityIgnoreUrlProperties;
        this.requestHeaderAuthenticationFilter = requestHeaderAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> urls = securityIgnoreUrlProperties.getIgnoreUrls();
        String[] ignoreUrls = urls.toArray(new String[0]);

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(ignoreUrls).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(requestHeaderAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
