<<<<<<< HEAD:common/src/main/java/com/mall/config/MyBatisPlusConfig.java
package com.mall.config;
=======
package org.example.conf;
>>>>>>> f8aa785079fdf04af6b6b245a8dc915ab9afc632:common/src/main/java/org/example/conf/MyBatisPlusConfig.java

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
