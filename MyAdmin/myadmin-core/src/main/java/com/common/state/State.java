package com.common.state;

/**
 * @author cdfan
 * @date 2020/3/4
 * @description: 状态，是否成功，统一采用，0表示失败，1表示成功
 */
public enum State {

    /**
     * 成功
     */
    SUCCESS("1"),
    /**
     * 失败
     */
    ERROR("0");

    private String state;

    State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
