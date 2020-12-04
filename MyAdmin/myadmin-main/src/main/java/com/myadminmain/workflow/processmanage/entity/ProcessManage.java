package com.myadminmain.workflow.processmanage.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/9/4
 * @description: 工作流活动流程管理对象
 */
@ApiModel(description = "工作流活动流程管理对象")
public class ProcessManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程实例id")
    private String procInstId;

    @ApiModelProperty(value = "流程名称")
    private String procName;

    @ApiModelProperty(value = "流程定义标识key")
    private String procKey;

    @ApiModelProperty(value = "流程定义版本")
    private Integer procVersion;

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务表单key，对应form表单组件名称")
    private String formKey;

    @ApiModelProperty(value = "流程业务key")
    private String businessKey;

    @ApiModelProperty(value = "流程发起人编码")
    private String startUserCode;

    @ApiModelProperty(value = "流程发起人名称")
    private String startUserName;

    @ApiModelProperty(value = "流程状态，是否被挂起，如果是则为true，否则为false")
    private Boolean procSuspended;

    @ApiModelProperty(value = "流程开始时间")
    private LocalDateTime procStartTime;

    @ApiModelProperty(value = "流程结束时间")
    private LocalDateTime procEndTime;

    @ApiModelProperty(value = "耗时")
    private String elapsedTime;


    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getProcKey() {
        return procKey;
    }

    public void setProcKey(String procKey) {
        this.procKey = procKey;
    }

    public Integer getProcVersion() {
        return procVersion;
    }

    public void setProcVersion(Integer procVersion) {
        this.procVersion = procVersion;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getStartUserCode() {
        return startUserCode;
    }

    public void setStartUserCode(String startUserCode) {
        this.startUserCode = startUserCode;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public Boolean getProcSuspended() {
        return procSuspended;
    }

    public void setProcSuspended(Boolean procSuspended) {
        this.procSuspended = procSuspended;
    }

    public LocalDateTime getProcStartTime() {
        return procStartTime;
    }

    public void setProcStartTime(LocalDateTime procStartTime) {
        this.procStartTime = procStartTime;
    }

    public LocalDateTime getProcEndTime() {
        return procEndTime;
    }

    public void setProcEndTime(LocalDateTime procEndTime) {
        this.procEndTime = procEndTime;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
