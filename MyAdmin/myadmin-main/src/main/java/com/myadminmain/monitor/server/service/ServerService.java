package com.myadminmain.monitor.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myadminmain.monitor.server.entity.Server;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 获取服务器信息 service接口
 * @version: 1.0
 */
public interface ServerService {

    /**
     * 功能描述: 获取服务器相关信息
     * 
     * @return com.myadminmain.monitor.server.entity.Server
     * @author cdfan
     * @date 2020/6/24 14:02
     */
    Server getInfo();
}
