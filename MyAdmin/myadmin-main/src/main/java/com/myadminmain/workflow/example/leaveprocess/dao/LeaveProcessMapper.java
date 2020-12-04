package com.myadminmain.workflow.example.leaveprocess.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.workflow.example.leaveprocess.entity.LeaveProcess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-08-17
 * @description: 请假审批流程演示表 Mapper 接口
 */
public interface LeaveProcessMapper extends BaseMapper<LeaveProcess> {

    /**
     * 功能描述: 分页查询请假审批流演示
     *
     * @param page 分页对象
     * @param leaveType 请假类型
     * @param procState 流程状态
     * @param userId
     * @return com.baomidou.mybatisplus.core.metadata.IPage<LeaveProcess>
     * @author cdfan
     * @date 2020/8/19 10:31
     */
    IPage<LeaveProcess> pageInfo(@Param("page") IPage<LeaveProcess> page, @Param("leaveType") String leaveType, @Param("procState") String procState, @Param("userId") Integer userId);
}
