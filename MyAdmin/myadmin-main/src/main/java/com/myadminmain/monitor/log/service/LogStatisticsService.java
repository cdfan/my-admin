package com.myadminmain.monitor.log.service;

import com.myadminmain.monitor.log.entity.LogStatistics;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020/10/12
 * @description: 日志统计 service接口
 */
public interface LogStatisticsService {

    /**
     * 功能描述: 统计本周和上周的日志
     * @return com.myadminmain.monitor.log.entity.LogStatistics
     * @author cdfan
     * @date 2020/10/12 14:55
     */
    LogStatistics logStatisticsInfo();
    
}
