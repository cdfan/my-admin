package com.myadminmain.workflow.taskmanage.controller;

import com.myadminmain.workflow.taskmanage.entity.TaskManage;
import com.myadminmain.workflow.taskmanage.service.TaskManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.common.resultdata.ResultData;
import com.myadminmain.core.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/9/4
 * @description: 工作流任务管理 前端控制器
 */
@RestController
@RequestMapping("/taskManage")
@Api(tags = "TaskManageController", description = "工作流任务管理api")
public class TaskManageController extends BaseController {

    @Autowired
    TaskManageService taskManageService;

    /**
     * 功能描述: 分页查询待办任务
     * 
     * @param taskName 任务名称
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.taskmanage.entity.TaskManage>>
     * @author cdfan
     * @date 2020/9/8 16:01
     */
    @Permission("taskTodo_query")
    @BussinessLog("待办任务-分页查询")
    @ApiOperation(value = "待办任务-分页查询", notes = "分页查询待办任务")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "taskName", value = "任务名称", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/taskTodoInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<TaskManage>>
        taskTodoInfoPage(@RequestParam(value = "taskName", required = false) String taskName) {
        Page<TaskManage> page = this.defaultPage(TaskManage.class);
        return new ResultData<IPage<TaskManage>>(taskManageService.taskTodoInfoPage(page, taskName));
    }

    /**
     * 功能描述: 分页查询已办任务
     * 
     * @param taskName 任务名称
     * @param procDefId 流程定义id
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage <
     *         com.myadminmain.workflow.taskmanage.entity.TaskManage>>
     * @author cdfan
     * @date 2020/9/8 16:10
     */
    @Permission("taskDone_query")
    @BussinessLog("已办任务-分页查询")
    @ApiOperation(value = "已办任务-分页查询", notes = "分页查询已办任务")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "taskName", value = "任务名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "procDefId", value = "流程定义id", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/taskDoneInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<TaskManage>> taskDoneInfoPage(
        @RequestParam(value = "taskName", required = false) String taskName,
        @RequestParam(value = "procDefId", required = false) String procDefId) {
        Page<TaskManage> page = this.defaultPage(TaskManage.class);
        return new ResultData<IPage<TaskManage>>(taskManageService.taskDoneInfoPage(page, taskName, procDefId));
    }


    /**
     * 功能描述: 查询待办任务的数量
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020/9/14 16:42
     */
    @BussinessLog("待办任务-数量查询")
    @ApiOperation(value = "待办任务-数量查询", notes = "查询待办任务的数量")
    @RequestMapping(value = "/countTaskTodo", method = RequestMethod.GET)
    public ResultData<Integer> countTaskTodo() {
        return new ResultData<Integer>(taskManageService.countTaskTodo());
    }
}
