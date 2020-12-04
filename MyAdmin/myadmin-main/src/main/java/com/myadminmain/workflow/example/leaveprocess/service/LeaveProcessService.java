package com.myadminmain.workflow.example.leaveprocess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.workflow.common.entity.HandleTaskData;
import com.myadminmain.workflow.example.leaveprocess.entity.LeaveProcess;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-08-17
 * @description: 请假审批流程演示表 service接口
 */
public interface LeaveProcessService extends IService<LeaveProcess> {

    /**
     * 功能描述: 分页查询请假审批流演示
     *
     * @param page 分页对象
     * @param leaveType 请假类型
     * @param procState 流程状态
     * @return com.baomidou.mybatisplus.core.metadata.IPage<LeaveProcess>
     * @author cdfan
     * @date 2020-08-17
     */
    IPage<LeaveProcess> pageInfo(IPage<LeaveProcess> page, String leaveType, String procState);


    /**
     * 功能描述: 新增请假审批流演示
     *
     * @param leaveProcess 请假审批流演示实体对象
     * @author cdfan
     * @date 2020/8/24 11:52
     */
    void saveLeaveProcess(LeaveProcess leaveProcess);

    /**
     * 功能描述: 修改请假审批流演示
     * @param leaveProcess 请假审批流对象
     * @author cdfan
     * @date 2020/8/26 17:30
     */
    void updateLeaveProcess(LeaveProcess leaveProcess);

    /**
     * 功能描述: 根据主键删除请假审批流演示
     *
     * @param leaveId 请假审批流演示id
     * @author cdfan
     * @date 2020/8/27 14:22
     */
    void deleteLeaveProcess(Integer leaveId);

    /**
     * 功能描述: 任务处理
     *
     * @param handleTaskData 任务处理数据对象
     * @author cdfan
     * @date 2020/8/28 14:10
     */
    void handleTask(HandleTaskData handleTaskData);

    /**
     * 功能描述: 根据主键撤销请假审批流演示
     * @param leaveId 请假审批流演示id
     * @author cdfan
     * @date 2020/9/4 10:22
     */
    void revokeLeaveProcess(Integer leaveId);

    /**
     * 功能描述: 根据主键查询请假审批流演示
     * @param leaveId 请假审批流演示id
     * @return com.myadminmain.workflow.example.leaveprocess.entity.LeaveProcess
     * @author cdfan
     * @date 2020/9/6 16:19
     */
    LeaveProcess getLeaveProcessById(Integer leaveId);

    /**
     * 功能描述: 提交请假审批流演示
     * @param leaveProcess 请假审批流演示对象
     * @author cdfan
     * @date 2020/9/7 11:20
     */
    void commitLeaveProcess(LeaveProcess leaveProcess);
    
}
