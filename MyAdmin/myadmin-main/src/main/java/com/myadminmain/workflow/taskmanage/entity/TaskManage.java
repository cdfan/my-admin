package com.myadminmain.workflow.taskmanage.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/9/4
 * @description: 工作流任务管理对象
 */
@ApiModel(description = "工作流任务管理对象")
public class TaskManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程实例id")
    private String procInstId;

    @ApiModelProperty(value = "所属流程名称")
    private String procName;

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

    @ApiModelProperty(value = "任务创建时间")
    private LocalDateTime taskStartTime;

    @ApiModelProperty(value = "任务完成时间")
    private LocalDateTime taskEndTime;

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

    public LocalDateTime getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(LocalDateTime taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public LocalDateTime getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(LocalDateTime taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
