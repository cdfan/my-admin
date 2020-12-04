package com.exception;

/**
 * 功能描述: 封装MyAdmin的异常,业务异常，不合法操作
 * 
 * @author cdfan
 * @date 2020/5/22 16:23
 */
public class MyAdminException extends RuntimeException {

    /**
     * 错误编码
     */
    private Integer code;

    /**
     * 错误描述
     */
    private String message;

    public MyAdminException() {
        super();
    }

    public MyAdminException(String message) {
        this.message = message;
        this.code = 500;
    }

    public MyAdminException(String message, Integer code) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
