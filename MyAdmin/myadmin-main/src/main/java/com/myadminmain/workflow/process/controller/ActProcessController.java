package com.myadminmain.workflow.process.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.common.resultdata.ResultData;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.workflow.process.entity.ActProcess;
import com.myadminmain.workflow.process.service.ActProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-08-04
 * @description: 流程管理（流程定义） 前端控制器
 */
@RestController
@RequestMapping("/actProcess")
@Api(tags = "ActProcessController", description = "流程管理操作api")
public class ActProcessController extends BaseController {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ActProcessService actProcessService;

    /**
     * 功能描述:分页查询流程管理
     *
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<ActProcess>>
     * @author cdfan
     * @date 2020/8/10 11:58
     */
    @Permission("actProcess_query")
    @BussinessLog("流程管理-分页查询")
    @ApiOperation(value = "流程管理-分页查询", notes = "分页查询流程管理")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "procKey", value = "流程标识", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "procName", value = "流程名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "businessId", value = "关联业务id", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/actProcessInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<ActProcess>> page(@RequestParam(value = "procKey", required = false) String procKey,
                                              @RequestParam(value = "procName", required = false) String procName,
                                              @RequestParam(value = "businessId", required = false) Integer businessId) {
        Page<ActProcess> page = this.defaultPage(ActProcess.class);
        return new ResultData<IPage<ActProcess>>(actProcessService.pageInfo(page, procKey, procName, businessId));
    }

    /**
     * 功能描述: 查询所有流程定义
     * @return com.common.resultdata.ResultData<java.util.List<java.util.Map<java.lang.String,java.lang.String>>>
     * @author cdfan
     * @date 2020/9/8 14:40
     */
    @BussinessLog("流程管理-流程定义查询")
    @ApiOperation(value = "流程管理-流程定义查询", notes = "查询所有流程定义")
    @RequestMapping(value = "/getProcDefList", method = RequestMethod.GET)
    public ResultData<List<Map<String, String>>> getProcDefList() {
        return new ResultData<List<Map<String, String>>>(actProcessService.getProcDefList());
    }

    /**
     * 功能描述: 流程关联业务
     *
     * @param actProcess 流程管理实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020/8/11 11:05
     */
    @Permission("actProcess_edit")
    @BussinessLog("流程管理-流程关联业务")
    @ApiOperation(value = "流程管理-流程关联业务", notes = "流程关联业务，传入对象中必须包含流程定义id")
    @ApiImplicitParam(name = "actProcess", value = "流程管理实体对象", required = true, dataType = "ActProcess", paramType = "body")
    @RequestMapping(value = "/businessRelation", method = RequestMethod.PUT)
    public ResultData businessRelation(@RequestBody ActProcess actProcess) {
        actProcessService.businessRelation(actProcess);
        return SUCCESS;
    }

    /**
     * 功能描述: 通过流程定义获取对应的bpmn.xml
     *
     * @param procId 流程定义id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actProcess_query")
    @BussinessLog("流程管理-通过流程定义获取对应的bpmn.xml")
    @ApiOperation(value = "流程管理-获取资源文件", notes = "通过流程定义获取对应的bpmn.xml")
    @ApiImplicitParam(name = "procId", value = "流程定义d", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getProcessFile/{procId}", method = RequestMethod.GET)
    public ResultData<String> getProcessFile(@PathVariable("procId") String procId) throws IOException {
        return new ResultData<String>(actProcessService.getProcessFile(procId));
    }

    /**
     * 功能描述: 通过流程定义获取对应的资源图片
     *
     * @param procId 流程定义id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actProcess_query")
    @BussinessLog("流程管理-通过流程定义获取对应的资源图片")
    @ApiOperation(value = "流程管理-获取资源图片", notes = "通过流程定义获取对应的资源图片")
    @ApiImplicitParam(name = "procId", value = "流程定义d", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getProcessImage/{procId}", method = RequestMethod.GET)
    public ResultData<String> getProcessImage(@PathVariable("procId") String procId) throws IOException {
        return new ResultData<String>(actProcessService.getProcessImage(procId));
    }

    /**
     * 功能描述: 修改流程状态
     *
     * @param actProcess 流程实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actProcess_edit")
    @BussinessLog("流程管理-状态修改")
    @ApiOperation(value = "流程管理-状态修改", notes = "修改流程状态，对象中必须有流程定义id")
    @ApiImplicitParam(name = "actProcess", value = "流程管理实体对象", required = true, dataType = "ActProcess", paramType = "body")
    @RequestMapping(value = "/updateProcessState", method = RequestMethod.PUT)
    public ResultData updateProcessState(@RequestBody ActProcess actProcess) {
        actProcessService.updateProcessState(actProcess);
        return SUCCESS;
    }

    /**
     * 功能描述: 下载流程资源
     *
     * @param procId 流程定义id
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actProcess_download")
    @BussinessLog("流程流程-下载流程资源")
    @ApiOperation(value = "流程流程-下载流程资源", notes = "下载流程资源")
    @ApiImplicitParam(name = "procId", value = "流程定义id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/downloadResource/{procId}", method = RequestMethod.GET)
    public void downloadResource(@PathVariable("procId") String procId) throws IOException {
        actProcessService.downloadResource(procId);
    }

    /**
     * 功能描述: 根据流程部署id删除部署的流程
     *
     * @param deploymentId 流程部署id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actProcess_delete")
    @BussinessLog("流程管理-流程删除")
    @ApiOperation(value = "流程管理-流程删除", notes = "根据流程部署id删除部署的流程")
    @ApiImplicitParam(name = "deploymentId", value = "流程部署id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/actProcessInfo/{deploymentId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("deploymentId") String deploymentId) {
        actProcessService.deleteActProcess(deploymentId);
        return SUCCESS;
    }

    /**
     * 功能描述: 上传流程文件并部署
     *
     * @param bpmnFile 上传的流程文件
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020/5/27 10:48
     */
    @BussinessLog("流程管理-上传流程文件并部署")
    @ApiOperation(value = "流程管理-上传流程文件并部署", notes = "上传流程文件并部署")
    @ApiImplicitParam(name = "bpmnFile", value = "上传的流程文件", required = true, dataType = "MultipartFile",
            paramType = "body")
    @RequestMapping(value = "/uploadDeploymentFile", method = RequestMethod.POST)
    public ResultData uploadDeploymentFile(@RequestParam("bpmnFile") MultipartFile bpmnFile) throws IOException {
        actProcessService.uploadDeploymentFile(bpmnFile);
        return SUCCESS;
    }

    /**
     * 功能描述: 转模型，根据部署的流程创建流程模型
     *
     * @param procId 流程定义id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actProcess_edit")
    @BussinessLog("流程管理-转模型，根据部署的流程创建流程模型")
    @ApiOperation(value = "流程管理-转模型", notes = "转模型，根据部署的流程创建流程模型")
    @ApiImplicitParam(name = "procId", value = "流程定义d", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/convertModel/{procId}", method = RequestMethod.GET)
    public ResultData convertModel(@PathVariable("procId") String procId){
        actProcessService.convertModel(procId);
        return SUCCESS;
    }



    /**
     * 功能描述: 根据菜单编码获取关联的流程定义
     * @param menuCode 菜单编码
     * @return com.common.resultdata.ResultData<java.util.List < java.util.Map < java.lang.String , java.lang.String>>>
     * @author cdfan
     * @date 2020/9/11 12:15
     */
    @BussinessLog("流程管理-获取关联的流程定义")
    @ApiOperation(value = "流程管理-获取关联的流程定义", notes = "根据菜单编码获取关联的流程定义")
    @ApiImplicitParam(name = "menuCode", value = "菜单编码", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getRelationProcDef/{menuCode}", method = RequestMethod.GET)
    public ResultData<List<Map<String, String>>> getRelationProcDef(@PathVariable("menuCode") String menuCode) {
        return new ResultData<List<Map<String, String>>>(actProcessService.getRelationProcDef(menuCode));
    }
}
