package com.myadminmain.monitor.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;

import java.io.Serializable;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/6/23
 * @description: 磁盘中文件系统的相关信息
 */
@ApiModel(description = "磁盘中分区及文件系统的相关信息")
public class FileSystemInfo implements Serializable {

    @ApiModelProperty(value = "文件系统名称")
    private String name;

    @ApiModelProperty(value = "挂载点或盘符")
    private String mountpoint;

    @ApiModelProperty(value = "文件系统类型")
    private String type;

    @ApiModelProperty(value = "磁盘分区总量")
    private String total;

    @ApiModelProperty(value = "磁盘分区可用")
    private String usableSpace;

    @ApiModelProperty(value = "磁盘分区已使用")
    private String used;

    @ApiModelProperty(value = "磁盘分区使用比例")
    private String useRatio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMountpoint() {
        return mountpoint;
    }

    public void setMountpoint(String mountpoint) {
        this.mountpoint = mountpoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsableSpace() {
        return usableSpace;
    }

    public void setUsableSpace(String usableSpace) {
        this.usableSpace = usableSpace;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getUseRatio() {
        return useRatio;
    }

    public void setUseRatio(String useRatio) {
        this.useRatio = useRatio;
    }

    @Override
    public String toString() {
        return "FileSystemInfo{" + "name='" + name + '\'' + ", mountpoint='" + mountpoint + '\'' + ", type='" + type
            + '\'' + ", total='" + total + '\'' + ", usableSpace='" + usableSpace + '\'' + ", used='" + used + '\''
            + ", useRatio=" + useRatio + '}';
    }
}
