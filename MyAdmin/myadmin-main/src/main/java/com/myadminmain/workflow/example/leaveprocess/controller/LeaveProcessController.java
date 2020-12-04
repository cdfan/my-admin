package com.myadminmain.workflow.example.leaveprocess.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.myadminmain.workflow.common.entity.HandleTaskData;
import com.myadminmain.workflow.example.leaveprocess.entity.LeaveProcess;
import com.myadminmain.workflow.example.leaveprocess.service.LeaveProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-08-17
 * @description: 请假审批流演示管理 前端控制器
 */
@RestController
@RequestMapping("/leaveProcess")
@Api(tags = "LeaveProcessController", description = "请假审批流演示操作api")
public class LeaveProcessController extends BaseController {

    @Autowired
    private LeaveProcessService leaveProcessService;

    /**
     * 功能描述: 根据主键查询请假审批流演示
     *
     * @param leaveId 请假审批流演示id
     * @return com.common.resultdata.ResultData<LeaveProcess>
     * @author cdfan
     * @date 2020-08-17
     */
    @BussinessLog("请假审批流演示-单记录查询")
    @ApiOperation(value = "请假审批流演示-单记录查询", notes = "根据主键查询请假审批流演示")
    @ApiImplicitParam(name = "leaveId", value = "请假审批流演示id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/leaveProcessInfo/{leaveId}", method = RequestMethod.GET)
    public ResultData<LeaveProcess> get(@PathVariable("leaveId") Integer leaveId) {
        return new ResultData<LeaveProcess>(leaveProcessService.getLeaveProcessById(leaveId));
    }

    /**
     * 功能描述: 查询所有请假审批流演示
     *
     * @return com.common.resultdata.ResultData<java.util.List<LeaveProcess>>
     * @author cdfan
     * @date 2020-08-17
     */
    @Permission("leaveProcess_query")
    @BussinessLog("请假审批流演示-列表查询")
    @ApiOperation(value = "请假审批流演示-列表查询", notes = "查询所有请假审批流演示")
    @RequestMapping(value = "/leaveProcessInfo", method = RequestMethod.GET)
    public ResultData<List<LeaveProcess>> list() {
        return new ResultData<List<LeaveProcess>>(leaveProcessService.list());
    }

    /**
     * 功能描述: 分页查询请假审批流演示
     *
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<LeaveProcess>>
     * @author cdfan
     * @date 2020-08-17
     */
    @Permission("leaveProcess_query")
    @BussinessLog("请假审批流演示-分页查询")
    @ApiOperation(value = "请假审批流演示-分页查询", notes = "分页查询请假审批流演示")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "leaveType", value = "请假类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "procState", value = "流程状态", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/leaveProcessInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<LeaveProcess>> page(
            @RequestParam(value = "leaveType", required = false) String leaveType,
            @RequestParam(value = "procState", required = false) String procState) {
        Page<LeaveProcess> page = this.defaultPage(LeaveProcess.class);
        return new ResultData<IPage<LeaveProcess>>(leaveProcessService.pageInfo(page, leaveType, procState));
    }

    /**
     * 功能描述: 查询请假审批流演示数据的数量
     *
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-08-17
     */
    @Permission("leaveProcess_query")
    @BussinessLog("请假审批流演示-数量查询")
    @ApiOperation(value = "请假审批流演示-数量查询", notes = "查询请假审批流演示数据的数量")
    @RequestMapping(value = "/leaveProcessInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count() {
        return new ResultData<Integer>(leaveProcessService.count());
    }

    /**
     * 功能描述: 新增请假审批流演示
     *
     * @param leaveProcess 请假审批流演示实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-17
     */
    @Permission("leaveProcess_add")
    @BussinessLog("请假审批流演示-新增")
    @ApiOperation(value = "请假审批流演示-新增", notes = "新增请假审批流演示")
    @ApiImplicitParam(name = "leaveProcess", value = "请假审批流演示实体对象", required = true, dataType = "LeaveProcess", paramType = "body")
    @RequestMapping(value = "/leaveProcessInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody LeaveProcess leaveProcess) {
        leaveProcessService.saveLeaveProcess(leaveProcess);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改请假审批流演示，对象中必须有主键
     *
     * @param leaveProcess 请假审批流演示实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-17
     */
    @Permission("leaveProcess_edit")
    @BussinessLog("请假审批流演示-修改")
    @ApiOperation(value = "请假审批流演示-修改", notes = "修改请假审批流演示，对象中必须有主键")
    @ApiImplicitParam(name = "leaveProcess", value = "请假审批流演示实体对象", required = true, dataType = "LeaveProcess", paramType = "body")
    @RequestMapping(value = "/leaveProcessInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody LeaveProcess leaveProcess) {
        leaveProcessService.updateLeaveProcess(leaveProcess);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除请假审批流演示
     *
     * @param leaveId 请假审批流演示id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-17
     */
    @Permission("leaveProcess_delete")
    @BussinessLog("请假审批流演示-删除")
    @ApiOperation(value = "请假审批流演示-删除", notes = "根据主键删除请假审批流演示")
    @ApiImplicitParam(name = "leaveId", value = "请假审批流演示id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/leaveProcessInfo/{leaveId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("leaveId") Integer leaveId) {
        leaveProcessService.deleteLeaveProcess(leaveId);
        return SUCCESS;
    }

    /**
     * 功能描述: 任务处理
     *
     * @param handleTaskData 任务处理数据对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-17
     */
    // @Permission("leaveProcess_edit")
    @BussinessLog("请假审批流演示-任务处理")
    @ApiOperation(value = "请假审批流演示-任务处理", notes = "请假审批流演示任务处理")
    @ApiImplicitParam(name = "handleTaskData", value = "任务处理数据对象", required = true, dataType = "HandleTaskData", paramType = "body")
    @RequestMapping(value = "/handleTask", method = RequestMethod.POST)
    public ResultData handleTask(@RequestBody HandleTaskData handleTaskData) {
        leaveProcessService.handleTask(handleTaskData);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键撤销请假审批流演示
     *
     * @param leaveId 请假审批流演示id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-17
     */
    @Permission("leaveProcess_revoke")
    @BussinessLog("请假审批流演示-撤销")
    @ApiOperation(value = "请假审批流演示-撤销", notes = "根据主键撤销请假审批流演示")
    @ApiImplicitParam(name = "leaveId", value = "请假审批流演示id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/revokeLeaveProcess/{leaveId}", method = RequestMethod.DELETE)
    public ResultData revokeLeaveProcess(@PathVariable("leaveId") Integer leaveId) {
        leaveProcessService.revokeLeaveProcess(leaveId);
        return SUCCESS;
    }


    /**
     * 功能描述: 提交请假审批流演示
     *
     * @param leaveProcess 请假审批流演示实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-17
     */
    @Permission("leaveProcess_add")
    @BussinessLog("请假审批流演示-提交")
    @ApiOperation(value = "请假审批流演示-提交", notes = "提交请假审批流演示")
    @ApiImplicitParam(name = "leaveProcess", value = "请假审批流演示实体对象", required = true, dataType = "LeaveProcess", paramType = "body")
    @RequestMapping(value = "/commitLeaveProcess", method = RequestMethod.POST)
    public ResultData commitLeaveProcess(@RequestBody LeaveProcess leaveProcess) {
        leaveProcessService.commitLeaveProcess(leaveProcess);
        return SUCCESS;
    }
}
