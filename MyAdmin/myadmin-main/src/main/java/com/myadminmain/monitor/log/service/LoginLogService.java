package com.myadminmain.monitor.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.monitor.log.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 登录记录 service接口
 * @version: 1.0
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 功能描述: 分页查询登录日志
     * 
     * @param page 分页对象
     * @param userCode 用户账号
     * @param logType 日志类型：0[登出]、1[登录]
     * @param state 登录状态：0[失败]、1[成功]
     * @param createTime 创建时间,登录时间，格式为yyyy-MM-dd,yyyy-MM-dd
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.monitor.log.entity.LoginLog>
     * @author cdfan
     * @date 2020/6/4 17:45
     */
    IPage<LoginLog> pageInfo(IPage<LoginLog> page, String userCode, String logType, String state, String createTime);

    /**
     * 功能描述: 获取指定时间到当前时间每天的登录数量
     * @param time 指定时间
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Integer>>
     * @author cdfan
     * @date 2020/10/12 15:28
     */
    List<Map<String,Object>> daysLoginUserCount(LocalDateTime time);

    /**
     * 功能描述: 获取指定时间到当前时间每天的ip登录数量
     * @param time 指定时间
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Integer>>
     * @author cdfan
     * @date 2020/10/12 15:28
     */
    List<Map<String,Object>> daysLoginIpCount(LocalDateTime time);
}
