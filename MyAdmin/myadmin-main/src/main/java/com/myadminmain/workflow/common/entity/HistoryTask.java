package com.myadminmain.workflow.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/31
 * @description: 流程历史任务
 */
@ApiModel(description = "流程历史任务对象")
public class HistoryTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "form表单的key")
    private String formKey;

    @ApiModelProperty(value = "业务key")
    private String businessKey;

    @ApiModelProperty(value = "办理人")
    private String userName;

    @ApiModelProperty(value = "办理结果")
    private String result;

    @ApiModelProperty(value = "办理意见")
    private String comment;

    @ApiModelProperty(value = "耗时")
    private String elapsedTime;

    @ApiModelProperty(value = "任务创建时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "任务完成时间")
    private LocalDateTime endTime;

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "HistoryTask{" +
                "taskName='" + taskName + '\'' +
                ", userName='" + userName + '\'' +
                ", result='" + result + '\'' +
                ", comment='" + comment + '\'' +
                ", elapsedTime='" + elapsedTime + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
