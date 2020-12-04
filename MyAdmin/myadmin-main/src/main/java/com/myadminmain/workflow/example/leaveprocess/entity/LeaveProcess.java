package com.myadminmain.workflow.example.leaveprocess.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.util.LocalDateTimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.activiti.engine.task.Task;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-08-17
 * @description: 请假审批流程演示表 实体类
 */
@TableName("demo_leave_process")
@ApiModel(description = "请假审批流程演示表")
public class LeaveProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程业务key的前缀
     */
    public static final String BUSINESS_KEY_PREFIX = "leaveProcess";

    @ApiModelProperty(value = "请假主键id")
    @TableId(value = "leave_id", type = IdType.AUTO)
    private Integer leaveId;

    @ApiModelProperty(value = "流程实例id")
    @TableField("proc_inst_id")
    private String procInstId;

    @ApiModelProperty(value = "流程状态，具体参考业务字典")
    @TableField("proc_state")
    private String procState;

    @ApiModelProperty(value = "请假类型")
    @TableField("leave_type")
    private String leaveType;

    @ApiModelProperty(value = "请假人员id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "请假开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "请假结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "请假原因")
    @TableField("leave_reason")
    private String leaveReason;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "流程状态名称")
    @TableField(exist = false)
    private String procStateName;

    @ApiModelProperty(value = "申请人员名称")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty(value = "请假类型名称")
    @TableField(exist = false)
    private String leaveTypeName;

    @ApiModelProperty(value = "任务id")
    @TableField(exist = false)
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    @TableField(exist = false)
    private String taskName;

    @ApiModelProperty(value = "下一任务处理用户")
    @TableField(exist = false)
    private List<String> nextTaskUser;

    @ApiModelProperty(value = "流程定义id")
    @TableField(exist = false)
    private String procDefId;

    @ApiModelProperty(value = "所属流程名称")
    @TableField(exist = false)
    private String procName;

    public Double getLeaveDay() {
        long ms = LocalDateTimeUtil.getMilliByTime(this.getEndTime()) - LocalDateTimeUtil.getMilliByTime(this.getStartTime());
        int hours = Math.round(ms / 1000 / 60 / 60) % 24;
        Double day = (double) (ms / 1000 / 60 / 60 / 24);
        if(hours >= 9){
            day += 1;
        }else{
            day = Double.parseDouble((ms / 1000 / 60 / 60 / 24) + "." + hours % 24);
        }
        return day;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public List<String> getNextTaskUser() {
        return nextTaskUser;
    }

    public void setNextTaskUser(List<String> nextTaskUser) {
        this.nextTaskUser = nextTaskUser;
    }


    public String getProcStateName() {
        return procStateName;
    }

    public void setProcStateName(String procStateName) {
        this.procStateName = procStateName;
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

    public String getProcState() {
        return procState;
    }

    public void setProcState(String procState) {
        this.procState = procState;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }


    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "LeaveProcess{" +
                "leaveId=" + leaveId +
                ", procInstId='" + procInstId + '\'' +
                ", procState='" + procState + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", userId=" + userId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", leaveReason='" + leaveReason + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", userName='" + userName + '\'' +
                ", leaveTypeName='" + leaveTypeName + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                '}';
    }
}
