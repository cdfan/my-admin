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
 * @version: 1.0
 * @date 2020-06-17
 * @description: 操作日志记录表 实体类
 */
@TableName("sys_operation_log")
@ApiModel(description = "操作日志记录表")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "操作日志id")
    @TableId(value = "operation_log_id", type = IdType.AUTO)
    private Integer operationLogId;

    @ApiModelProperty(value = "用户账号")
    @TableField("user_code")
    private String userCode;

    @ApiModelProperty(value = "日志类型：0[异常操作日志]、1[正常操作日志]")
    @TableField("log_type")
    private String logType;

    @ApiModelProperty(value = "业务名称")
    @TableField("business_name")
    private String businessName;

    @ApiModelProperty(value = "类名称")
    @TableField("class_name")
    private String className;

    @ApiModelProperty(value = "方法名称")
    @TableField("method_name")
    private String methodName;

    @ApiModelProperty(value = "参数信息")
    @TableField("arg_msg")
    private String argMsg;

    @ApiModelProperty(value = "错误信息，错误日志时才存在")
    @TableField("exception_msg")
    private String exceptionMsg;

    @ApiModelProperty(value = "请求地址")
    @TableField("uri")
    private String uri;

    @ApiModelProperty(value = "请求类型")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "请求耗时，单位毫秒")
    @TableField("elapsed_time")
    private Integer elapsedTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

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


    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Integer getOperationLogId() {
        return operationLogId;
    }

    public void setOperationLogId(Integer operationLogId) {
        this.operationLogId = operationLogId;
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArgMsg() {
        return argMsg;
    }

    public void setArgMsg(String argMsg) {
        this.argMsg = argMsg;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    @Override
    public String toString() {
        return "OperationLog{" +
        "operationLogId=" + operationLogId +
        ", userCode=" + userCode +
        ", logType=" + logType +
        ", businessName=" + businessName +
        ", className=" + className +
        ", methodName=" + methodName +
        ", argMsg=" + argMsg +
        ", exceptionMsg=" + exceptionMsg +
        ", uri=" + uri +
        ", method=" + method +
        ", elapsedTime=" + elapsedTime +
        ", createTime=" + createTime +
        ", ip=" + ip +
        ", address=" + address +
        ", browser=" + browser +
        ", os=" + os +
        "}";
    }
}
