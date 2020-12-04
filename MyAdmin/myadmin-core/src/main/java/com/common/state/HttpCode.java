package com.common.state;

/**
 * @author cdfan
 * @date 2020/3/5
 * @description: 自定义HTTP CODE，如果要使用已存在的httpCode，请使用org.springframework.http.HttpStatus中的code
 */
public enum HttpCode {
    /**
     * 登录异常，请重新登录
     */
    GO_LOGIN(4000, "登录异常，请重新登录"),
    /**
     * token超时
     */
    TOKEN_TIMEOUT(4001, "登录超时"),
    /**
     * token解析或验证失败
     */
    TOKEN_AUTHFAILED(4002, "登录异常"),
    /**
     * 用户未登录
     */
    NOT_LOGIN(4003, "用户未登陆"),
    /**
     * 用户已注销
     */
    UNSUBSCRIBE(4004, "当前用户已被注销"),
    /**
     * 用户被冻结
     */
    FROZEN_ACCOUNT(4005, "当前用户已被冻结"),;

    private final int value;

    private final String reasonPhrase;

    HttpCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
