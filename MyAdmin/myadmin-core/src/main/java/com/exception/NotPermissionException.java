package com.exception;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/3/17
 * @description: 权限不足异常
 */
public class NotPermissionException extends RuntimeException {
    public NotPermissionException(Throwable cause) {
        super(cause);
    }

    public NotPermissionException() {}

    public NotPermissionException(String message) {
        super(message);
    }

    public NotPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
