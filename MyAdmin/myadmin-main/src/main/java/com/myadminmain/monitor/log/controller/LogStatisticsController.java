package com.myadminmain.monitor.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.annotion.BussinessLog;
import com.common.resultdata.ResultData;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.monitor.log.entity.LogStatistics;
import com.myadminmain.monitor.log.service.LogStatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cdfan
 * @date 2020/10/12
 * @description: 日志统计 前端控制器
 * @version: 1.0
 */
@RestController
@RequestMapping("/logStatistics")
@Api(tags = "LogStatisticsController", description = "日志统计操作api")
public class LogStatisticsController extends BaseController {

    @Autowired
    private LogStatisticsService logStatisticsService;

    /**
     * 功能描述: 统计本周和上周的日志
     * @return com.common.resultdata.ResultData<com.myadminmain.monitor.log.entity.LogStatistics>
     * @author cdfan
     * @date 2020/10/12 14:54
     */
    @BussinessLog("日志统计")
    @ApiOperation(value = "日志统计", notes = "统计本周和上周的日志")
    @RequestMapping(value = "/logStatisticsInfo", method = RequestMethod.GET)
    public ResultData<LogStatistics> logStatisticsInfo() {
        return new ResultData<LogStatistics>(logStatisticsService.logStatisticsInfo());
    }
}
