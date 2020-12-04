package com.myadminmain.workflow.processmanage.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myadminmain.workflow.processmanage.entity.ProcessManage; /**
 * @author cdfan
 * @version 1.0
 * @date 2020/9/8
 * @description: 工作流活动流程管理 service接口
 */
public interface ProcessManageService {

    /**
     * 功能描述: 分页查询运行中流程
     *
     * @param page 分页对象
     * @param procName 流程名称
     * @param procKey 流程标识
     * @param procSuspended 是否被挂起
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.processmanage.entity.ProcessManage>
     * @author cdfan
     * @date 2020/9/8 22:47
     */
    IPage<ProcessManage> processRuningInfoPage(Page<ProcessManage> page, String procName, String procKey, Boolean procSuspended);

    /**
     * 功能描述: 修改运行中流程的状态
     *
     * @param procInstId 流程实例id
     * @author cdfan
     * @date 2020/9/9 11:04
     */
    void updateProcState(String procInstId);

    /**
     * 功能描述: 分页查询已结束流程
     *
     * @param page 分页对象
     * @param procName 流程名称
     * @param procKey 流程标识
     * @param procStartTime 流程开始时间
     * @param procEndTime 流程结束时间
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.processmanage.entity.ProcessManage>
     * @author cdfan
     * @date 2020/9/9 14:35
     */
    IPage<ProcessManage> processFinishInfoPage(IPage<ProcessManage> page, String procName, String procKey, String procStartTime, String procEndTime);
}
