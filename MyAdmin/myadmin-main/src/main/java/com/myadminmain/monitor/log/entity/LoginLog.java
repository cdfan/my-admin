package com.myadminmain.monitor.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 登录日志记录表 实体类
 * @version: 1.0
 */
@TableName("sys_login_log")
@ApiModel(description = "登录日志记录表")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录记录主键id")
    @TableId(value = "login_log_id", type = IdType.AUTO)
    private Integer loginLogId;

    @ApiModelProperty(value = "用户账号")
    @TableField("user_code")
    private String userCode;

    @ApiModelProperty(value = "日志类型：0[登出]、1[登录]")
    @TableField("log_type")
    private String logType;

    @ApiModelProperty(value = "登录状态：0[失败]、1[成功]")
    @TableField("state")
    private String state;

    @ApiModelProperty(value = "登录ip")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "登录地址：格式：国家省份城市 网络运营商")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "浏览器类型")
    @TableField("browser")
    private String browser;

    @ApiModelProperty(value = "操作系统及版本")
    @TableField("os")
    private String os;

    @ApiModelProperty(value = "描述")
    @TableField("msg")
    private String msg;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    public Integer getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(Integer loginLogId) {
        this.loginLogId = loginLogId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LoginLog{" +
        "loginLogId=" + loginLogId +
        ", userCode=" + userCode +
        ", logType=" + logType +
        ", state=" + state +
        ", ip=" + ip +
        ", msg=" + msg +
        ", createTime=" + createTime +
        "}";
    }
}
