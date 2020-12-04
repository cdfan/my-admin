package com.myadminmain.monitor.log.service.impl;

import com.myadminmain.monitor.log.entity.LoginLog;
import com.myadminmain.monitor.log.dao.LoginLogMapper;
import com.myadminmain.monitor.log.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.util.ToolUtil;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 登录记录 service实现类
 * @version: 1.0
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Override
    public IPage<LoginLog> pageInfo(IPage<LoginLog> page, String userCode, String logType, String state,
        String createTime) {
        QueryWrapper<LoginLog> query = new QueryWrapper<LoginLog>();
        if (StringUtils.isNotEmpty(userCode)) {
            query = query.eq("user_code", userCode);
        }
        if (StringUtils.isNotEmpty(logType)) {
            query = query.eq("log_type", logType);
        }
        if (StringUtils.isNotEmpty(state)) {
            query = query.eq("state", state);
        }
        if (StringUtils.isNotEmpty(createTime)) {
            query = ToolUtil.addTimeQuery(query, createTime, "create_time");
        }
        return this.page(page, query);
    }

    @Override
    public List<Map<String, Object>> daysLoginUserCount(LocalDateTime time) {
        return this.baseMapper.daysLoginUserCount(time);
    }

    @Override
    public List<Map<String, Object>> daysLoginIpCount(LocalDateTime time) {
        return this.baseMapper.daysLoginIpCount(time);
    }

}
