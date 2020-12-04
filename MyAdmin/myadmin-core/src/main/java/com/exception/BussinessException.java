package com.exception;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/3/17
 * @description: 运行时业务未知异常，统一返回操作失败
 */
public class BussinessException extends RuntimeException {
    public BussinessException(Throwable cause) {
        super(cause);
    }

    public BussinessException() {}

    public BussinessException(String message) {
        super(message);
    }
}
