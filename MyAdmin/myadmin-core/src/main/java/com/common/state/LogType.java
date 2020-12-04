package com.common.state;

/**
 * @author cdfan
 * @date 2020/3/4
 * @description: 登录日志类型
 */
public enum LogType {
    /**
     * 登录
     */
    LOGIN("1"),
    /**
     * 登出
     */
    LOGOUT("0");

    private String logType;

    LogType(String logType) {
        this.logType = logType;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
