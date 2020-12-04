package com.myadminmain.monitor.server.entity;

import com.util.DateUtil;
import com.util.HttpUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import oshi.software.os.OperatingSystem;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/6/23
 * @description: 服务器系统相关信息
 */
@ApiModel(description = "服务器系统相关信息")
public class Sys implements Serializable {

    @ApiModelProperty(value = "系统名称")
    private String sysName;

    @ApiModelProperty(value = "系统架构")
    private String sysArch;

    @ApiModelProperty(value = "jvm名称")
    private String jvmName;

    @ApiModelProperty(value = "java版本")
    private String javaVersion;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "运行时间")
    private String runTimed;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysArch() {
        return sysArch;
    }

    public void setSysArch(String sysArch) {
        this.sysArch = sysArch;
    }

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRunTimed() {
        return runTimed;
    }

    public void setRunTimed(String runTimed) {
        this.runTimed = runTimed;
    }

    /**
     * 功能描述: 服务器系统相关信息
     * 
     * @author cdfan
     * @date 2020/6/24 14:09
     * @param os OperatingSystem对象
     */
    public void init(OperatingSystem os) {
        this.sysName = os.toString();
        OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean();
        this.sysArch = system.getArch();
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        this.jvmName = runtime.getVmName();
        this.javaVersion = System.getProperty("java.version");
        this.ip = HttpUtil.getLocalIp();
        this.runTimed = DateUtil.secondToTime(runtime.getUptime() / 1000);
    }

    @Override
    public String toString() {
        return "Sys{" + "sysName='" + sysName + '\'' + ", sysArch='" + sysArch + '\'' + ", jvmName='" + jvmName + '\''
            + ", javaVersion='" + javaVersion + '\'' + ", ip='" + ip + '\'' + ", runTimed='" + runTimed + '\'' + '}';
    }
}