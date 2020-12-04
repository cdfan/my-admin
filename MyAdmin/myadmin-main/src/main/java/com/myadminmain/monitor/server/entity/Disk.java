package com.myadminmain.monitor.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/6/23
 * @description: 磁盘相关信息
 */
@ApiModel(description = "磁盘相关信息")
public class Disk implements Serializable {

    @ApiModelProperty(value = "磁盘总量")
    private String total;

    @ApiModelProperty(value = "磁盘可用")
    private String usableSpace;

    @ApiModelProperty(value = "磁盘已使用")
    private String used;

    @ApiModelProperty(value = "磁盘使用比例")
    private Double useRatio;

    @ApiModelProperty(value = "磁盘中分区及文件系统的相关信息")
    private List<FileSystemInfo> fileSystems;

    public List<FileSystemInfo> getFileSystems() {
        return fileSystems;
    }

    public void setFileSystems(List<FileSystemInfo> fileSystems) {
        this.fileSystems = fileSystems;
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

    public Double getUseRatio() {
        return useRatio;
    }

    public void setUseRatio(Double useRatio) {
        this.useRatio = useRatio;
    }

    /**
     * 功能描述: 初始化磁盘相关信息
     * 
     * @param fileSystem FileSystemInfo
     * @author cdfan
     * @date 2020/6/24 15:05
     */
    public void init(FileSystem fileSystem) {
        long totalSum = 0;
        long usableSum = 0;
        ArrayList<FileSystemInfo> fileSystems = new ArrayList<>();
        for (OSFileStore fs : fileSystem.getFileStores()) {
            FileSystemInfo fileSystemInfo = new FileSystemInfo();
            fileSystemInfo.setName(fs.getName());
            fileSystemInfo.setMountpoint(fs.getMount());
            fileSystemInfo.setType(fs.getType());
            fileSystemInfo.setTotal(FormatUtil.formatBytes(fs.getTotalSpace()));
            fileSystemInfo.setUsableSpace(FormatUtil.formatBytes(fs.getUsableSpace()));
            fileSystemInfo.setUsed(FormatUtil.formatBytes(fs.getTotalSpace() - fs.getUsableSpace()));
            fileSystemInfo.setUseRatio(
                String.format("%.2f%%", (fs.getTotalSpace() - fs.getUsableSpace()) / (double)fs.getTotalSpace() * 100));
            fileSystems.add(fileSystemInfo);
            totalSum += fs.getTotalSpace();
            usableSum += fs.getUsableSpace();
        }
        this.fileSystems = fileSystems;
        this.total = FormatUtil.formatBytes(totalSum);
        this.usableSpace = FormatUtil.formatBytes(usableSum);
        this.used = FormatUtil.formatBytes(totalSum - usableSum);
        this.useRatio = Double.parseDouble(String.format("%.2f", (totalSum - usableSum) / (double)totalSum * 100));
    }

    @Override
    public String toString() {
        return "Disk{" + "total='" + total + '\'' + ", usableSpace='" + usableSpace + '\'' + ", used='" + used + '\''
            + ", useRatio=" + useRatio + '}';
    }
}
