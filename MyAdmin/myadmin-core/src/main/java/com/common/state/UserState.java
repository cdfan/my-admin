package com.common.state;

/**
 * 功能描述: 用户状态
 * 
 * @author cdfan
 * @date 2020/5/22 15:52
 */
public enum UserState {
    /**
     * 正常
     */
    OK("1", "启用"),
    /**
     * 冻结
     */
    FREEZED("0", "冻结");

    String code;
    String message;

    UserState(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOfs(String value) {
        if (value == null) {
            return "";
        } else {
            for (UserState ms : UserState.values()) {
                if (ms.getCode().equals(value)) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
