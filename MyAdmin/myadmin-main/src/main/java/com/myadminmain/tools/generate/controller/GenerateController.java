package com.myadminmain.tools.generate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.resultdata.ResultData;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.exception.MyAdminException;
import com.myadminmain.tools.generate.entity.TablesInfo;
import com.myadminmain.tools.generate.wrapper.GenerateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.tools.generate.entity.Generate;
import com.myadminmain.tools.generate.service.GenerateService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-07-06
 * @description: 代码生成配置管理 前端控制器
 */
@RestController
@RequestMapping("/generate")
@Api(tags = "GenerateController", description = "代码生成配置操作api")
public class GenerateController extends BaseController {

    @Autowired
    private GenerateService generateService;

    /**
     * 功能描述: 根据主键查询代码生成配置
     * 
     * @param generateId 代码生成配置id
     * @return com.common.resultdata.ResultData<com.myadminmain.tools.generate.entity.Generate>
     * @author cdfan
     * @date 2020-07-06
     */
    @Permission("generate_query")
    @BussinessLog("代码生成配置-单记录查询")
    @ApiOperation(value = "代码生成配置-单记录查询", notes = "根据主键查询代码生成配置")
    @ApiImplicitParam(name = "generateId", value = "代码生成配置id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/generateInfo/{generateId}", method = RequestMethod.GET)
    public ResultData<Generate> get(@PathVariable("generateId") Integer generateId) {
        return new ResultData<Generate>(generateService.getById(generateId));
    }

    /**
     * 功能描述: 查询所有代码生成配置
     * 
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.tools.generate.entity.Generate>>
     * @author cdfan
     * @date 2020-07-06
     */
    @Permission("generate_query")
    @BussinessLog("代码生成配置-列表查询")
    @ApiOperation(value = "代码生成配置-列表查询", notes = "查询所有代码生成配置")
    @RequestMapping(value = "/generateInfo", method = RequestMethod.GET)
    public ResultData<List<Generate>> list() {
        return new ResultData<List<Generate>>(generateService.list());
    }

    /**
     * 功能描述: 分页查询代码生成配置
     * 
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.tools.generate.entity.Generate>>
     * @author cdfan
     * @date 2020-07-06
     */
    @Permission("generate_query")
    @BussinessLog("代码生成配置-分页查询")
    @ApiOperation(value = "代码生成配置-分页查询", notes = "分页查询代码生成配置")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "tableName", value = "表名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "modelName", value = "模块名，能代表这个模块的名称。例如用户表的模块名就是用户", dataType = "String",
            paramType = "query"),
        @ApiImplicitParam(name = "author", value = "作者", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/generateInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<Generate>> page(@RequestParam(value = "tableName", required = false) String tableName,
        @RequestParam(value = "modelName", required = false) String modelName,
        @RequestParam(value = "author", required = false) String author) {
        Page<Generate> page = this.defaultPage(Generate.class);
        IPage<Generate> generateIPage = generateService.pageInfo(page, tableName, modelName, author);
        this.wrap(new GenerateWrapper(generateIPage.getRecords()));
        return new ResultData<IPage<Generate>>(generateIPage);
    }

    /**
     * 功能描述: 查询代码生成配置数据的数量
     * 
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-07-06
     */
    @Permission("generate_query")
    @BussinessLog("代码生成配置-数量查询")
    @ApiOperation(value = "代码生成配置-数量查询", notes = "查询代码生成配置数据的数量")
    @RequestMapping(value = "/generateInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count() {
        return new ResultData<Integer>(generateService.count());
    }

    /**
     * 功能描述: 新增代码生成配置
     * 
     * @param generate 代码生成配置实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-07-06
     */
    @Permission("generate_add")
    @BussinessLog("代码生成配置-新增")
    @ApiOperation(value = "代码生成配置-新增", notes = "新增代码生成配置")
    @ApiImplicitParam(name = "generate", value = "代码生成配置实体对象", required = true, dataType = "Generate",
        paramType = "body")
    @RequestMapping(value = "/generateInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody Generate generate) {
        generateService.save(generate);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改代码生成配置，对象中必须有主键
     * 
     * @param generate 代码生成配置实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-07-06
     */
    @Permission("generate_edit")
    @BussinessLog("代码生成配置-修改")
    @ApiOperation(value = "代码生成配置-修改", notes = "修改代码生成配置，对象中必须有主键")
    @ApiImplicitParam(name = "generate", value = "代码生成配置实体对象", required = true, dataType = "Generate",
        paramType = "body")
    @RequestMapping(value = "/generateInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody Generate generate) {
        if (ObjectUtils.isEmpty(generate.getGenerateId())) {
            throw new MyAdminException("修改对象中必须存在主键");
        }
        generateService.updateById(generate);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除代码生成配置
     * 
     * @param generateId 代码生成配置id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-07-06
     */
    @Permission("generate_delete")
    @BussinessLog("代码生成配置-删除")
    @ApiOperation(value = "代码生成配置-删除", notes = "根据主键删除代码生成配置")
    @ApiImplicitParam(name = "generateId", value = "代码生成配置id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/generateInfo/{generateId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("generateId") Integer generateId) {
        generateService.removeById(generateId);
        return SUCCESS;
    }

    /**
     * 功能描述: 查询数据库中的表信息
     * 
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.tools.generate.entity.Generate>>
     * @author cdfan
     * @date 2020-07-06
     */
    @Permission("generate_query")
    @BussinessLog("查询数据库中的表信息")
    @ApiOperation(value = "查询数据库中的表信息", notes = "查询数据库中的表信息")
    @RequestMapping(value = "/getTablesInfo", method = RequestMethod.GET)
    public ResultData<List<TablesInfo>> getTablesInfo() {
        return new ResultData<List<TablesInfo>>(generateService.getTablesInfo());
    }

    /**
     * 功能描述: 下载以及生成代码
     * 
     * @param generateId 代码生成配置id
     * @author cdfan
     * @date 2020-07-06
     */
    @Permission("generate_download")
    @BussinessLog("下载以及生成代码")
    @ApiOperation(value = "下载以及生成代码", notes = "下载以及生成代码")
    @ApiImplicitParam(name = "generateId", value = "代码生成配置id", dataType = "int", paramType = "query")
    @RequestMapping(value = "/downloadCode", method = RequestMethod.GET)
    public void downloadCode(@RequestParam(value = "generateId") Integer generateId) {
        generateService.downloadCode(generateId);
        // return SUCCESS;
    }

    /**
     * 功能描述: 预览生成代码
     * 
     * @param generateId 代码生成配置id
     * @return com.common.resultdata.ResultData<java.util.Map<java.lang.String,java.lang.String>>
     * @author cdfan
     * @date 2020/7/9 22:51
     */
    @Permission("generate_preview")
    @BussinessLog("预览生成代码")
    @ApiOperation(value = "预览生成代码", notes = "预览生成代码")
    @ApiImplicitParam(name = "generateId", value = "代码生成配置id", dataType = "int", paramType = "query")
    @RequestMapping(value = "/previewCode", method = RequestMethod.GET)
    public ResultData<Map<String, String>> previewCode(@RequestParam(value = "generateId") Integer generateId) {
        return new ResultData<Map<String, String>>(generateService.previewCode(generateId));
    }
}
