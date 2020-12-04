package com.common.state;

/**
 * 功能描述: 排序规则
 * 
 * @author cdfan
 * @date 2020/4/22 10:16
 */
public enum Order {
    /**
     * 升序
     */
    ASC("asc"),
    /**
     * 降序
     */
    DESC("desc");

    private String des;

    Order(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
