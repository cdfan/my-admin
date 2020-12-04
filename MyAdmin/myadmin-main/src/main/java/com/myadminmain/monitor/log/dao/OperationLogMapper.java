package com.myadminmain.monitor.log.dao;

import com.myadminmain.monitor.log.entity.OperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-06-17
 * @description: 操作日志 Mapper 接口
 */
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 功能描述: 获取指定时间到当前时间每天的请求数量
     * @param time 指定时间
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/10/12 16:55
     */
    List<Map<String,Object>> daysRequestCount(@Param("time") LocalDateTime time);
    
}
