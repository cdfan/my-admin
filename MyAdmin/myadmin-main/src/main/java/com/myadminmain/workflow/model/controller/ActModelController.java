package com.myadminmain.workflow.model.controller;

import java.io.IOException;
import java.util.List;

import com.myadminmain.workflow.model.entity.ActModel;
import com.myadminmain.workflow.model.service.ActModelService;
import org.activiti.engine.RepositoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.common.resultdata.ResultData;
import com.exception.MyAdminException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myadminmain.core.common.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-08-04
 * @description: 流程模型管理 前端控制器
 */
@RestController
@RequestMapping("/actModel")
@Api(tags = "ActModelController", description = "流程模型操作api")
public class ActModelController extends BaseController {

    @Autowired
    private ActModelService actModelService;

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ObjectMapper objectMapper;

    /**
     * 功能描述: 根据主键查询流程模型
     *
     * @param id 流程模型id
     * @return com.common.resultdata.ResultData<ActModel>
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_query")
    @BussinessLog("流程模型-单记录查询")
    @ApiOperation(value = "流程模型-单记录查询", notes = "根据主键查询流程模型")
    @ApiImplicitParam(name = "id", value = "流程模型id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/actModelInfo/{id}", method = RequestMethod.GET)
    public ResultData<ActModel> get(@PathVariable("id") Integer id) {
        return new ResultData<ActModel>(actModelService.getById(id));
    }

    /**
     * 功能描述: 查询所有流程模型
     *
     * @return com.common.resultdata.ResultData<java.util.List<ActModel>>
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_query")
    @BussinessLog("流程模型-列表查询")
    @ApiOperation(value = "流程模型-列表查询", notes = "查询所有流程模型")
    @RequestMapping(value = "/actModelInfo", method = RequestMethod.GET)
    public ResultData<List<ActModel>> list() {
        return new ResultData<List<ActModel>>(actModelService.list());
    }

    /**
     * 功能描述: 分页查询流程模型
     *
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<ActModel>>
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_query")
    @BussinessLog("流程模型-分页查询")
    @ApiOperation(value = "流程模型-分页查询", notes = "分页查询流程模型")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "modelKey", value = "模型标识key", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "modelName", value = "模型名称", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/actModelInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<ActModel>> page(@RequestParam(value = "modelKey", required = false) String modelKey,
        @RequestParam(value = "modelName", required = false) String modelName) {
        Page<ActModel> page = this.defaultPage(ActModel.class);
        return new ResultData<IPage<ActModel>>(actModelService.pageInfo(page, modelKey, modelName));
    }

    /**
     * 功能描述: 查询流程模型数据的数量
     *
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_query")
    @BussinessLog("流程模型-数量查询")
    @ApiOperation(value = "流程模型-数量查询", notes = "查询流程模型数据的数量")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "modelKey", value = "模型标识", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "id", value = "模型id", dataType = "int", paramType = "query")})
    @RequestMapping(value = "/actModelInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count(@RequestParam(value = "modelKey", required = false) String modelKey,
        @RequestParam(value = "id", required = false) Integer id) {
        QueryWrapper<ActModel> queryWrapper = new QueryWrapper<ActModel>().eq("model_key", modelKey);
        if (ObjectUtils.isNotEmpty(id)) {
            queryWrapper.ne("id", id);
        }
        int count = actModelService.count(queryWrapper);
        return new ResultData<Integer>(count);
    }

    /**
     * 功能描述: 新增流程模型
     *
     * @param actModel 流程模型实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_add")
    @BussinessLog("流程模型-新增")
    @ApiOperation(value = "流程模型-新增", notes = "新增流程模型")
    @ApiImplicitParam(name = "actModel", value = "流程模型实体对象", required = true, dataType = "ActModel", paramType = "body")
    @RequestMapping(value = "/actModelInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody ActModel actModel) {
        actModelService.save(actModel);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改流程模型，对象中必须有主键
     *
     * @param actModel 流程模型实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_edit")
    @BussinessLog("流程模型-修改")
    @ApiOperation(value = "流程模型-修改", notes = "修改流程模型，对象中必须有主键")
    @ApiImplicitParam(name = "actModel", value = "流程模型实体对象", required = true, dataType = "ActModel", paramType = "body")
    @RequestMapping(value = "/actModelInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody ActModel actModel) {
        if(ObjectUtils.isEmpty(actModel.getId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        actModelService.updateById(actModel);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除流程模型
     *
     * @param id 流程模型id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_delete")
    @BussinessLog("流程模型-删除")
    @ApiOperation(value = "流程模型-删除", notes = "根据主键删除流程模型")
    @ApiImplicitParam(name = "id", value = "流程模型id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/actModelInfo/{id}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("id") Integer id) {
        actModelService.deleteActModel(id);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据模型id生成并获取对应的bpmn.xml
     *
     * @param id 流程模型id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_query")
    @BussinessLog("流程模型-生成并获取对应的bpmn.xml")
    @ApiOperation(value = "流程模型-生成并获取对应的bpmn.xml", notes = "根据模型id生成并获取对应的bpmn.xml")
    @ApiImplicitParam(name = "id", value = "流程模型id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/getModelFile/{id}", method = RequestMethod.GET)
    public ResultData<String> getModelFile(@PathVariable("id") Integer id) throws IOException {
        return new ResultData<String>(actModelService.getModelFile(id));
    }

    /**
     * 功能描述: 根据模型id生成并获取对应的流程图
     *
     * @param id 流程模型id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_query")
    @BussinessLog("流程模型-生成并获取对应的流程图")
    @ApiOperation(value = "流程模型-生成并获取对应的流程图", notes = "根据模型id生成并获取对应的流程图")
    @ApiImplicitParam(name = "id", value = "流程模型id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/getModelImage/{id}", method = RequestMethod.GET)
    public ResultData<String> getModelImage(@PathVariable("id") Integer id) throws IOException {
        return new ResultData<String>(actModelService.getModelImage(id));
    }

    /**
     * 功能描述: 下载模型资源
     *
     * @param id 流程模型id
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_download")
    @BussinessLog("流程模型-下载模型资源")
    @ApiOperation(value = "流程模型-下载模型资源", notes = "下载模型资源")
    @ApiImplicitParam(name = "id", value = "流程模型id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/downloadResource/{id}", method = RequestMethod.GET)
    public void downloadResource(@PathVariable("id") Integer id) throws IOException {
        actModelService.downloadResource(id);
    }

    /**
     * 功能描述: 流程部署
     *
     * @param id 流程模型id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-04
     */
    @Permission("actModel_deployment")
    @BussinessLog("流程模型-流程部署")
    @ApiOperation(value = "流程模型-流程部署", notes = "流程部署")
    @ApiImplicitParam(name = "id", value = "流程模型id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/deploymentModel/{id}", method = RequestMethod.GET)
    public ResultData deploymentModel(@PathVariable("id") Integer id) throws IOException {
        actModelService.deploymentModel(id);
        return SUCCESS;
    }
}
