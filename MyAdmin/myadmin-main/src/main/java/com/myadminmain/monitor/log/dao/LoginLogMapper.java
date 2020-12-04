package com.myadminmain.monitor.log.dao;

import com.myadminmain.monitor.log.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 登录记录 Mapper 接口
 * @version: 1.0
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 功能描述: 获取指定时间到当前时间每天的登录数量
     * @param time 指定时间
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Integer>>
     * @author cdfan
     * @date 2020/10/12 15:29
     */
    List<Map<String,Object>> daysLoginUserCount(@Param("time") LocalDateTime time);

    /**
     * 功能描述: 获取指定时间到当前时间每天的ip登录数量
     * @param time 指定时间
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Integer>>
     * @author cdfan
     * @date 2020/10/12 15:29
     */
    List<Map<String,Object>> daysLoginIpCount(@Param("time") LocalDateTime time);
}
