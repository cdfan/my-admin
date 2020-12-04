package com.myadminmain.monitor.log.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/10/12
 * @description: 日志统计对象
 */ 
@ApiModel(description = "日志统计对象")
public class LogStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户登录量")
    private Map<String,List<Integer>> loginUser;

    @ApiModelProperty(value = "接口请求量")
    private Map<String,List<Integer>> request;

    @ApiModelProperty(value = "ip登录量")
    private Map<String,List<Integer>> loginIp;

    public Map<String, List<Integer>> getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Map<String, List<Integer>> loginUser) {
        this.loginUser = loginUser;
    }

    public Map<String, List<Integer>> getRequest() {
        return request;
    }

    public void setRequest(Map<String, List<Integer>> request) {
        this.request = request;
    }

    public Map<String, List<Integer>> getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(Map<String, List<Integer>> loginIp) {
        this.loginIp = loginIp;
    }
}
