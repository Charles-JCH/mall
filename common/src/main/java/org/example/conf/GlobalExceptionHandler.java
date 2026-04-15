package org.example.conf;

import org.example.vo.R;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     */
    @ExceptionHandler(Exception.class)
    public R<?> handleException() {
        return R.error(500, "Internal server error");
    }
}
