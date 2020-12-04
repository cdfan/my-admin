package com.myadminmain.monitor.server.controller;

import com.common.resultdata.ResultData;
import com.common.annotion.Permission;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.monitor.server.entity.Server;
import com.myadminmain.monitor.server.service.ServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 获取服务器信息 前端控制器
 * @version: 1.0
 */
@RestController
@RequestMapping("/server")
@Api(tags = "ServerController", description = "服务器信息操作api")
public class ServerController extends BaseController {

    @Autowired
    private ServerService loginLogService;

    /**
     * 功能描述: 获取服务器相关信息
     * 
     * @return com.common.resultdata.ResultData<com.myadminmain.monitor.server.entity.Server>
     * @author cdfan
     * @date 2020/6/24 11:42
     */
    @Permission("server_query")
    @ApiOperation(value = "获取服务器相关信息", notes = "获取服务器相关信息")
    @RequestMapping(value = "/serverInfo", method = RequestMethod.GET)
    public ResultData<Server> get() {
        return new ResultData<Server>(loginLogService.getInfo());
    }

}
