package com.myadminmain.monitor.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import oshi.hardware.GlobalMemory;
import oshi.util.FormatUtil;

import java.io.Serializable;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/6/23
 * @description: 内存相关信息
 */
@ApiModel(description = "内存相关信息")
public class Memory implements Serializable {

    @ApiModelProperty(value = "内存总量")
    private String total;

    @ApiModelProperty(value = "空闲内存")
    private String available;

    @ApiModelProperty(value = "使用内存")
    private String used;

    @ApiModelProperty(value = "内存使用比例")
    private Double useRatio;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public Double getUseRatio() {
        return useRatio;
    }

    public void setUseRatio(Double useRatio) {
        this.useRatio = useRatio;
    }

    /**
     * 功能描述: 初始化内存相关信息
     * 
     * @param memory GlobalMemory
     * @author cdfan
     * @date 2020/6/24 15:01
     */
    public void init(GlobalMemory memory) {
        this.total = FormatUtil.formatBytes(memory.getTotal());
        this.available = FormatUtil.formatBytes(memory.getAvailable());
        this.used = FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable());
        this.useRatio = Double.parseDouble(
            String.format("%.2f", (memory.getTotal() - memory.getAvailable()) / (double)memory.getTotal() * 100));
    }

    @Override
    public String toString() {
        return "Memory{" + "total='" + total + '\'' + ", available='" + available + '\'' + ", used='" + used + '\''
            + ", useRatio=" + useRatio + '}';
    }
}
