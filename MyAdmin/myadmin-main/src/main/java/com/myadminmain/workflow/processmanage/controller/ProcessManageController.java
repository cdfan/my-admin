package com.myadminmain.workflow.processmanage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.myadminmain.workflow.processmanage.entity.ProcessManage;
import com.myadminmain.workflow.processmanage.service.ProcessManageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/9/4
 * @description: 工作流活动流程管理 前端控制器
 */
@RestController
@RequestMapping("/processManage")
@Api(tags = "ProcessManageController", description = "工作流活动流程管理api")
public class ProcessManageController extends BaseController {

    @Autowired
    ProcessManageService processManageService;

    /**
     * 功能描述: 分页查询运行中流程
     * @param procName 流程名称
     * @param procKey 流程标识
     * @param procSuspended 是否被挂起
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.processmanage.entity.ProcessManage>>
     * @author cdfan
     * @date 2020/9/8 22:44
     */
    @Permission("processRuning_query")
    @BussinessLog("运行中流程-分页查询")
    @ApiOperation(value = "运行中流程-分页查询", notes = "分页查询运行中流程")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "procName", value = "流程名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "procKey", value = "流程标识", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "procSuspended", value = "是否被挂起", dataType = "Boolean", paramType = "query")
    })
    @RequestMapping(value = "/processRuningInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<ProcessManage>> processRuningInfoPage(
            @RequestParam(value = "procName", required = false) String procName,
            @RequestParam(value = "procKey", required = false) String procKey,
            @RequestParam(value = "procSuspended", required = false) Boolean procSuspended) {
        Page<ProcessManage> page = this.defaultPage(ProcessManage.class);
        return new ResultData<IPage<ProcessManage>>(processManageService.processRuningInfoPage(page, procName, procKey, procSuspended));
    }
    
    /**
     * 功能描述: 修改运行中流程的状态
     *
     * @param procInstId 流程实例id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020/9/9 11:03
     */
    @Permission("processRuning_edit")
    @BussinessLog("运行中流程-状态修改")
    @ApiOperation(value = "运行中流程-状态修改", notes = "修改运行中流程的状态")
    @ApiImplicitParam(name = "procInstId", value = "流程实例id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/updateProcState/{procInstId}", method = RequestMethod.GET)
    public ResultData updateProcState(@PathVariable("procInstId") String procInstId) {
        processManageService.updateProcState(procInstId);
        return SUCCESS;
    }

    /**
     * 功能描述: 分页查询已结束流程
     * @param procName 流程名称
     * @param procKey 流程标识
     * @param procStartTime 流程开始时间
     * @param procEndTime 流程结束时间
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.processmanage.entity.ProcessManage>>
     * @author cdfan
     * @date 2020/9/9 14:34
     */
    @Permission("processFinish_query")
    @BussinessLog("已结束流程-分页查询")
    @ApiOperation(value = "已结束流程-分页查询", notes = "分页查询已结束流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "procName", value = "流程名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "procKey", value = "流程标识", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "procStartTime", value = "流程开始时间，格式为yyyy-MM-dd,yyyy-MM-dd", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "procEndTime", value = "流程结束时间，格式为yyyy-MM-dd,yyyy-MM-dd", dataType = "String",paramType = "query")
    })
    @RequestMapping(value = "/processFinishInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<ProcessManage>> processFinishInfoPage(
            @RequestParam(value = "procName", required = false) String procName,
            @RequestParam(value = "procKey", required = false) String procKey,
            @RequestParam(value = "procStartTime", required = false) String procStartTime,
            @RequestParam(value = "procEndTime", required = false) String procEndTime) {
        IPage<ProcessManage> page = this.defaultPage(ProcessManage.class);
        return new ResultData<IPage<ProcessManage>>(processManageService.processFinishInfoPage(page, procName, procKey, procStartTime, procEndTime));
    }

    
}
