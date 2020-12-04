package com.myadminmain.workflow.process.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/9
 * @description: 流程管理对象
 */
@ApiModel(description = "流程管理对象")
public class ActProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程定义id")
    private String procId;

    @ApiModelProperty(value = "流程部署id")
    private String deploymentId;

    @ApiModelProperty(value = "流程标识")
    private String procKey;

    @ApiModelProperty(value = "流程名称")
    private String procName;

    @ApiModelProperty(value = "流程版本")
    private Integer version;

    @ApiModelProperty(value = "流程状态，是否为挂起状态，true表示挂起")
    private boolean suspended;

    @ApiModelProperty(value = "bpmn.xml资源名称")
    private String resourceName;

    @ApiModelProperty(value = "流程图片资源名称")
    private String diagramResourceName;

    @ApiModelProperty(value = "流程描述")
    private String description;

    @ApiModelProperty(value = "部署时间")
    private LocalDateTime deploymentTime;

    @ApiModelProperty(value = "关联业务id集合")
    private List<Integer> businessIds;

    @ApiModelProperty(value = "关联业务名称集合")
    private List<String> businessNames;

    public List<Integer> getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(List<Integer> businessIds) {
        this.businessIds = businessIds;
    }

    public List<String> getBusinessNames() {
        return businessNames;
    }

    public void setBusinessNames(List<String> businessNames) {
        this.businessNames = businessNames;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getProcKey() {
        return procKey;
    }

    public void setProcKey(String procKey) {
        this.procKey = procKey;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(LocalDateTime deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

}
