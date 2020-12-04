package com.exception;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/6/15
 * @description: 登录相关异常
 */
public class LoginException extends RuntimeException {
    /**
     * 用户账号
     */
    private String userCode;
    /**
     * 错误描述
     */
    private String message;

    public LoginException() {
        super();
    }

    public String getUserCode() {
        return userCode;
    }

    public LoginException(String userCode, String message) {
        this.userCode = userCode;
        this.message = message;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
