package com.myadminmain.monitor.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.monitor.log.entity.OperationLog;
import com.myadminmain.monitor.log.dao.OperationLogMapper;
import com.myadminmain.monitor.log.service.OperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.util.ToolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-06-17
 * @description: 操作日志 service实现类
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
    implements OperationLogService {

    @Override
    public IPage<OperationLog> pageInfo(IPage<OperationLog> page, String userCode, String logType, String businessName,
        String createTime) {
        QueryWrapper<OperationLog> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(userCode)) {
            query = query.eq("user_code", userCode);
        }
        if (StringUtils.isNotEmpty(logType)) {
            query = query.eq("log_type", logType);
        }
        if (StringUtils.isNotEmpty(businessName)) {
            query = query.eq("business_name", businessName);
        }
        if (StringUtils.isNotEmpty(createTime)) {
            query = ToolUtil.addTimeQuery(query, createTime, "create_time");
        }
        return this.page(page, query);
    }

    @Override
    public List<Map<String, Object>> daysRequestCount(LocalDateTime time) {
        return this.baseMapper.daysRequestCount(time);
    }
}
