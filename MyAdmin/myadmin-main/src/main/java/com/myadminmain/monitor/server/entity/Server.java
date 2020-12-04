package com.myadminmain.monitor.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/6/23
 * @description: 服务器信息对象
 */
@ApiModel(description = "服务器信息对象")
public class Server implements Serializable {

    @ApiModelProperty(value = "服务器系统相关信息")
    private Sys sys;

    @ApiModelProperty(value = "cpu相关信息")
    private Cpu cpu;

    @ApiModelProperty(value = "内存相关信息")
    private Memory memory;

    @ApiModelProperty(value = "磁盘相关信息")
    private Disk disk;

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    @Override
    public String toString() {
        return "Server{" + "sys=" + sys + ", cpu=" + cpu + ", memory=" + memory + ", disk=" + disk + '}';
    }
}
