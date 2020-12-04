package com.myadminmain.workflow.common.controller;

import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.common.resultdata.ResultData;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.workflow.common.entity.HistoryTask;
import com.myadminmain.workflow.common.entity.NextTaskUser;
import com.myadminmain.workflow.common.service.WorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/27
 * @description: 工作流公共操作
 */
@RestController
@RequestMapping("/workflowHandle")
@Api(tags = "WorkflowController", description = "工作流公共操作")
public class WorkflowController extends BaseController {

    @Autowired
    WorkflowService workflowService;

    /**
     * 功能描述: 根据流程实例id获取当前任务的下一任务办理人
     *
     * @param procInstId 流程实例id
     * @return com.common.resultdata.ResultData<com.myadminmain.workflow.common.entity.NextTaskUser>
     * @author cdfan
     * @date 2020-08-04
     */ 
    @BussinessLog("根据流程实例id获取当前任务的下一任务办理人")
    @ApiOperation(value = "获取当前任务的下一任务办理人", notes = "根据流程实例id获取当前任务的下一任务办理人")
    @ApiImplicitParam(name = "procInstId", value = "流程实例id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/nextTaskUser/{procInstId}", method = RequestMethod.GET)
    public ResultData<NextTaskUser> nextTaskUser(@PathVariable("procInstId") String procInstId){
        return new ResultData<NextTaskUser>(workflowService.nextTaskUser(procInstId));
    }


    /**
     * 功能描述: 根据流程实例id获取当前流程执行任务记录
     *
     * @param procInstId 流程实例id
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.workflow.common.entity.HistoryTask>>
     * @author cdfan
     * @date 2020-08-04
     */ 
    @BussinessLog("根据流程实例id获取当前流程执行任务记录")
    @ApiOperation(value = "获取当前流程执行任务记录", notes = "根据流程实例id获取当前流程执行任务记录")
    @ApiImplicitParam(name = "procInstId", value = "流程实例id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/historyTask/{procInstId}", method = RequestMethod.GET)
    public ResultData<List<HistoryTask>> historyTask(@PathVariable("procInstId") String procInstId){
        return new ResultData<List<HistoryTask>>(workflowService.historyTask(procInstId));
    }

    /**
     * 功能描述: 根据流程实例id获取流转轨迹高亮流程图
     * @param procInstId 流程实例id
     * @return com.common.resultdata.ResultData<java.lang.String>
     * @author cdfan
     * @date 2020/9/2 22:37
     */
    @BussinessLog("根据流程实例id获取流转轨迹高亮流程图")
    @ApiOperation(value = "获取流转轨迹高亮流程图", notes = "根据流程实例id获取流转轨迹高亮流程图")
    @ApiImplicitParam(name = "procInstId", value = "流程实例id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getHighlightTrackImage/{procInstId}", method = RequestMethod.GET)
    public ResultData<String> getHighlightTrackImage(@PathVariable("procInstId") String procInstId) throws IOException {
        return new ResultData<String>(workflowService.getHighlightTrackImage(procInstId));
    }

}
