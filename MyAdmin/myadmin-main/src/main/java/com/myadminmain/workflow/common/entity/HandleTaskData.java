package com.myadminmain.workflow.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/28
 * @description: 任务处理数据
 */
@ApiModel(description = "任务处理数据对象")
public class HandleTaskData  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "处理结果")
    private Boolean result;

    @ApiModelProperty(value = "下一任务处理用户")
    private List<String> user;

    @ApiModelProperty(value = "处理备注")
    private String comment;

    @ApiModelProperty(value = "流程中的业务key")
    private String businessKey;

    @ApiModelProperty(value = "业务id")
    private Integer businessId;

    @ApiModelProperty(value = "任务id")
    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<String> getUser() {
        return user;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
}
