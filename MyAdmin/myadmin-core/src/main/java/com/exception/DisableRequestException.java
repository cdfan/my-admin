package com.exception;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/11/30
 * @description: 请求拒绝异常
 */
public class DisableRequestException extends RuntimeException {
    /**
     * 错误编码
     */
    private Integer code;

    /**
     * 错误描述
     */
    private String message;

    public DisableRequestException() {
        super();
    }

    public DisableRequestException(String message) {
        this.message = message;
        this.code = 403;
    }

    public DisableRequestException(Integer code, String message) {
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
