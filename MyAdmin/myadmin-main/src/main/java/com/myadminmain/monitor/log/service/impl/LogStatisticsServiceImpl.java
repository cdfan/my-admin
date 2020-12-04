package com.myadminmain.monitor.log.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myadminmain.monitor.log.entity.LogStatistics;
import com.myadminmain.monitor.log.service.LogStatisticsService;
import com.myadminmain.monitor.log.service.LoginLogService;
import com.myadminmain.monitor.log.service.OperationLogService;
import com.util.LocalDateTimeUtil;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-10-12
 * @description: 日志统计 service实现类
 */
@Service
public class LogStatisticsServiceImpl implements LogStatisticsService {

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private OperationLogService operationLogService;

    @Override
    public LogStatistics logStatisticsInfo() {
        LogStatistics logStatistics = new LogStatistics();
        //获取两个星期之前
        LocalDateTime time = LocalDateTimeUtil.getDayStart(LocalDateTimeUtil.minus(LocalDateTime.now(), 13, ChronoUnit.DAYS));
        //获取两个星期之前到当前时间每天的登录数量
        List<Map<String,Object>> loginUserCount = loginLogService.daysLoginUserCount(time);
        logStatistics.setLoginUser(this.buildLogStatistics(loginUserCount));
        //获取两个星期之前到当前时间每天的请求数量
        List<Map<String,Object>> requestCount = operationLogService.daysRequestCount(time);
        logStatistics.setRequest(this.buildLogStatistics(requestCount));
        //获取两个星期之前到当前时间每天的登录ip数量
        List<Map<String,Object>> loginIpCount = loginLogService.daysLoginIpCount(time);
        logStatistics.setLoginIp(this.buildLogStatistics(loginIpCount));
        return logStatistics;
    }

    private Map<String,List<Integer>> buildLogStatistics(List<Map<String,Object>> data){
        List<Integer> list = new ArrayList<>();
        Map<String,List<Integer>> buildMap = new HashMap<>();
        Map<String, Integer> dataMap = new LinkedHashMap<>();
        for (Map<String, Object> item : data) {
            dataMap.put(item.get("time").toString(), Integer.parseInt(item.get("count").toString()));
        }
        LocalDateTime time = LocalDateTimeUtil.getDayStart(LocalDateTimeUtil.minus(LocalDateTime.now(), 13, ChronoUnit.DAYS));
        for (int i = 0; i < 14; i++) {
            list.add(dataMap.getOrDefault(LocalDateTimeUtil.formatTime(time, "yyyy-MM-dd"), 0));
            if(i==6){
                buildMap.put("lastWeek", list);
                list = new ArrayList<>();
            }
            time = LocalDateTimeUtil.plus(time,1,ChronoUnit.DAYS);
        }
        buildMap.put("thisWeek", list);
        return buildMap;
    }
}
