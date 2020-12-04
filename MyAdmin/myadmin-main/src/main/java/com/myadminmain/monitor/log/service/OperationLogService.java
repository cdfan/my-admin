package com.myadminmain.monitor.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.monitor.log.entity.OperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-06-17
 * @description: 操作日志 service接口
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 功能描述: 分页查询操作日志
     * 
     * @param page 分页对象
     * @param userCode 用户账号
     * @param logType 日志类型：0[异常操作日志]、1[正常操作日志]
     * @param businessName 业务名称
     * @param createTime 创建时间,操作时间，格式为yyyy-MM-dd,yyyy-MM-dd
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.monitor.log.entity.OperationLog>
     * @author cdfan
     * @date 2020/6/18 16:15
     */
    IPage<OperationLog> pageInfo(IPage<OperationLog> page, String userCode, String logType, String businessName,
        String createTime);

    /**
     * 功能描述: 获取指定时间到当前时间每天的请求数量
     * @param time 指定时间
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/10/12 16:54
     */
    List<Map<String,Object>> daysRequestCount(LocalDateTime time);
}
