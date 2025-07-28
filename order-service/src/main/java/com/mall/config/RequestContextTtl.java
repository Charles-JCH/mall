package com.mall.config;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.web.context.request.RequestAttributes;

public class RequestContextTtl {
    private static final TransmittableThreadLocal<RequestAttributes> REQUEST_ATTRIBUTES = new TransmittableThreadLocal<>();

    public static void set(RequestAttributes requestAttributes) {
        REQUEST_ATTRIBUTES.set(requestAttributes);
    }

    public static RequestAttributes get() {
        return REQUEST_ATTRIBUTES.get();
    }

    public static void clear() {
        REQUEST_ATTRIBUTES.remove();
    }
}
