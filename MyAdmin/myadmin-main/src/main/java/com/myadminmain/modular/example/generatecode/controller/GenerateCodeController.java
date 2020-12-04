package com.myadminmain.modular.example.generatecode.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.resultdata.ResultData;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.exception.MyAdminException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.modular.example.generatecode.entity.GenerateCode;
import com.myadminmain.modular.example.generatecode.service.GenerateCodeService;

import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-08-16
 * @description: 代码生成演示管理 前端控制器
 */
@RestController
@RequestMapping("/generateCode")
@Api(tags = "GenerateCodeController", description = "代码生成演示操作api")
public class GenerateCodeController extends BaseController {

    @Autowired
    private GenerateCodeService generateCodeService;

    /**
     * 功能描述: 根据主键查询代码生成演示
     *
     * @param id 代码生成演示id
     * @return com.common.resultdata.ResultData<com.myadminmain.modular.example.generatecode.entity.GenerateCode>
     * @author cdfan
     * @date 2020-08-16
     */
    @Permission("generateCode_query")
    @BussinessLog("代码生成演示-单记录查询")
    @ApiOperation(value = "代码生成演示-单记录查询", notes = "根据主键查询代码生成演示")
    @ApiImplicitParam(name = "id", value = "代码生成演示id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/generateCodeInfo/{id}", method = RequestMethod.GET)
    public ResultData<GenerateCode> get(@PathVariable("id") Integer id) {
        return new ResultData<GenerateCode>(generateCodeService.getById(id));
    }

    /**
     * 功能描述: 查询所有代码生成演示
     *
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.modular.example.generatecode.entity.GenerateCode>>
     * @author cdfan
     * @date 2020-08-16
     */
    @Permission("generateCode_query")
    @BussinessLog("代码生成演示-列表查询")
    @ApiOperation(value = "代码生成演示-列表查询", notes = "查询所有代码生成演示")
    @RequestMapping(value = "/generateCodeInfo", method = RequestMethod.GET)
    public ResultData<List<GenerateCode>> list() {
        return new ResultData<List<GenerateCode>>(generateCodeService.list());
    }

    /**
     * 功能描述: 分页查询代码生成演示
     *
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.modular.example.generatecode.entity.GenerateCode>>
     * @author cdfan
     * @date 2020-08-16
     */
    @Permission("generateCode_query")
    @BussinessLog("代码生成演示-分页查询")
    @ApiOperation(value = "代码生成演示-分页查询", notes = "分页查询代码生成演示")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "name", value = "名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "code", value = "编码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "day", value = "日期，格式为yyyy-MM-dd，范围日期用,号分割", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/generateCodeInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<GenerateCode>> page(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "day", required = false) String day) {
        Page<GenerateCode> page = this.defaultPage(GenerateCode.class);
        return new ResultData<IPage<GenerateCode>>(generateCodeService.pageInfo(page, name, code, day));
    }

    /**
     * 功能描述: 查询代码生成演示数据的数量
     *
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-08-16
     */
    @Permission("generateCode_query")
    @BussinessLog("代码生成演示-数量查询")
    @ApiOperation(value = "代码生成演示-数量查询", notes = "查询代码生成演示数据的数量")
    @RequestMapping(value = "/generateCodeInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count() {
        return new ResultData<Integer>(generateCodeService.count());
    }

    /**
     * 功能描述: 新增代码生成演示
     *
     * @param generateCode 代码生成演示实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-16
     */
    @Permission("generateCode_add")
    @BussinessLog("代码生成演示-新增")
    @ApiOperation(value = "代码生成演示-新增", notes = "新增代码生成演示")
    @ApiImplicitParam(name = "generateCode", value = "代码生成演示实体对象", required = true, dataType = "GenerateCode", paramType = "body")
    @RequestMapping(value = "/generateCodeInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody GenerateCode generateCode) {
        generateCodeService.save(generateCode);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改代码生成演示，对象中必须有主键
     *
     * @param generateCode 代码生成演示实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-16
     */
    @Permission("generateCode_edit")
    @BussinessLog("代码生成演示-修改")
    @ApiOperation(value = "代码生成演示-修改", notes = "修改代码生成演示，对象中必须有主键")
    @ApiImplicitParam(name = "generateCode", value = "代码生成演示实体对象", required = true, dataType = "GenerateCode", paramType = "body")
    @RequestMapping(value = "/generateCodeInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody GenerateCode generateCode) {
        if(ObjectUtils.isEmpty(generateCode.getId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        generateCodeService.updateById(generateCode);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除代码生成演示
     *
     * @param id 代码生成演示id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-08-16
     */
    @Permission("generateCode_delete")
    @BussinessLog("代码生成演示-删除")
    @ApiOperation(value = "代码生成演示-删除", notes = "根据主键删除代码生成演示")
    @ApiImplicitParam(name = "id", value = "代码生成演示id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/generateCodeInfo/{id}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("id") Integer id) {
        generateCodeService.removeById(id);
        return SUCCESS;
    }

}
