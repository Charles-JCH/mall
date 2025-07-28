package com.mall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "thread.pool")
@Component
@Data
public class ThreadPoolConfigProperties {
    private int coreSize;
    private int maxSize;
    private int keepAliveTime;
}
