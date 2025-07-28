package com.mall.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "security")
public class SecurityIgnoreUrlProperties {
    private List<String> ignoreUrls = new ArrayList<>();
}
