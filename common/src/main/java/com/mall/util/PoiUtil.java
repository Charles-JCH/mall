package com.mall.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PoiUtil {

    @Async("exportTaskExecutor")
    public boolean exportExcel() {
        return true;
    }
}
