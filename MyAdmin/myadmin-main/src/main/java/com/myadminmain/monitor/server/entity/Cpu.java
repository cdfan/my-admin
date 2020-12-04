package com.myadminmain.monitor.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import oshi.hardware.CentralProcessor;
import oshi.util.Util;

import java.io.Serializable;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/6/23
 * @description: cpu相关信息
 */
@ApiModel(description = "cpu相关信息")
public class Cpu implements Serializable {

    @ApiModelProperty(value = "cpu名称")
    private String cpuName;

    @ApiModelProperty(value = "物理cpu数量")
    private Integer physicalCpuPackage;

    @ApiModelProperty(value = "cpu核心数量")
    private Integer physicalCpuCore;

    @ApiModelProperty(value = "逻辑cpu数量")
    private Integer logicalCpu;

    @ApiModelProperty(value = "cpu使用率")
    private Double cpuRatio;

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public Integer getPhysicalCpuPackage() {
        return physicalCpuPackage;
    }

    public void setPhysicalCpuPackage(Integer physicalCpuPackage) {
        this.physicalCpuPackage = physicalCpuPackage;
    }

    public Integer getPhysicalCpuCore() {
        return physicalCpuCore;
    }

    public void setPhysicalCpuCore(Integer physicalCpuCore) {
        this.physicalCpuCore = physicalCpuCore;
    }

    public Integer getLogicalCpu() {
        return logicalCpu;
    }

    public void setLogicalCpu(Integer logicalCpu) {
        this.logicalCpu = logicalCpu;
    }

    public Double getCpuRatio() {
        return cpuRatio;
    }

    public void setCpuRatio(Double cpuRatio) {
        this.cpuRatio = cpuRatio;
    }

    /**
     * 功能描述: cpu信息初始化
     * 
     * @param processor CentralProcessor
     * @author cdfan
     * @date 2020/6/24 14:49
     */
    public void init(CentralProcessor processor) {
        this.cpuName = processor.getProcessorIdentifier().getName();
        this.physicalCpuPackage = processor.getPhysicalPackageCount();
        this.physicalCpuCore = processor.getPhysicalProcessorCount();
        this.logicalCpu = processor.getLogicalProcessorCount();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        this.cpuRatio =
            Double.parseDouble(String.format("%.2f", processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100));
    }

    @Override
    public String toString() {
        return "Cpu{" + "cpuName='" + cpuName + '\'' + ", physicalCpuPackage=" + physicalCpuPackage
            + ", physicalCpuCore=" + physicalCpuCore + ", logicalCpu=" + logicalCpu + ", cpuRatio=" + cpuRatio + '}';
    }
}
