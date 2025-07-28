package com.mall.api;

import lombok.Getter;

@Getter
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "成功"),
    FAILED(500, "失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    VALIDATE_FAILED(404, "参数检验失败");


    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    private final long code;
    private final String message;
}
