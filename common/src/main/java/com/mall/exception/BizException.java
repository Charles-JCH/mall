package com.mall.exception;

import com.mall.api.IErrorCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
    private IErrorCode errorCode;

    public BizException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BizException(String message) {
        super(message);
    }

}
