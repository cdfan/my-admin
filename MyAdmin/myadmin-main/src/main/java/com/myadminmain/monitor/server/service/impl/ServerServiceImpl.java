package com.myadminmain.monitor.server.service.impl;

import com.myadminmain.monitor.server.entity.Cpu;
import com.myadminmain.monitor.server.entity.Disk;
import com.myadminmain.monitor.server.entity.Memory;
import com.myadminmain.monitor.server.entity.Server;
import com.myadminmain.monitor.server.entity.Sys;
import com.myadminmain.monitor.server.service.ServerService;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 获取服务器信息 service实现类
 * @version: 1.0
 */
@Service
public class ServerServiceImpl implements ServerService {

    @Override
    public Server getInfo() {
        Server server = new Server();
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        HardwareAbstractionLayer hal = si.getHardware();
        // 设置服务器系统相关信息
        Sys sys = new Sys();
        sys.init(os);
        server.setSys(sys);
        // 设置cpu相关信息
        Cpu cpu = new Cpu();
        cpu.init(hal.getProcessor());
        server.setCpu(cpu);
        // 设置内存相关信息
        Memory memory = new Memory();
        memory.init(hal.getMemory());
        server.setMemory(memory);
        // 设置磁盘相关信息
        Disk disk = new Disk();
        disk.init(os.getFileSystem());
        server.setDisk(disk);
        return server;
    }
}
