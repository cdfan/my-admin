package com.myadminmain.workflow.taskmanage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myadminmain.workflow.taskmanage.entity.TaskManage;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/9/4
 * @description: 工作流任务管理 service接口
 */
public interface TaskManageService {

    /**
     * 功能描述: 分页查询待办任务
     * 
     * @param page 分页对象
     * @param taskName 任务名称
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.taskmanage.entity.TaskManage>
     * @author cdfan
     * @date 2020/9/8 16:02
     */
    IPage<TaskManage> taskTodoInfoPage(Page<TaskManage> page, String taskName);

    /**
     * 功能描述: 分页查询已办任务
     * 
     * @param page 分页对象
     * @param taskName 任务名称
     * @param procDefId 流程定义id
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.taskmanage.entity.TaskManage>
     * @author cdfan
     * @date 2020/9/8 16:07
     */
    IPage<TaskManage> taskDoneInfoPage(Page<TaskManage> page, String taskName, String procDefId);

    /**
     * 功能描述: 查询待办任务的数量
     *
     * @return java.lang.Integer
     * @author cdfan
     * @date 2020/9/14 16:42
     */
    Integer countTaskTodo();

}
