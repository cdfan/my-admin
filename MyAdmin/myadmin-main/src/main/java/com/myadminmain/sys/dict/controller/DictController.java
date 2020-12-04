package com.myadminmain.sys.dict.controller;

import java.util.List;

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
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.common.resultdata.ResultData;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.sys.dict.entity.Dict;
import com.myadminmain.sys.dict.service.DictService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-05-25
 * @description: 业务字典管理 前端控制器
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "DictController", description = "业务字典操作api")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    /**
     * 功能描述: 根据主键查询业务字典
     * 
     * @param dictId 业务字典id
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.dict.entity.Dict>
     * @author cdfan
     * @date 2020-05-25
     */
    @Permission("dict_query")
    @BussinessLog("业务字典-单记录查询")
    @ApiOperation(value = "业务字典-单记录查询", notes = "根据主键查询业务字典")
    @ApiImplicitParam(name = "dictId", value = "业务字典id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/dictInfo/{dictId}", method = RequestMethod.GET)
    public ResultData<Dict> get(@PathVariable("dictId") Integer dictId) {
        return new ResultData<Dict>(dictService.getById(dictId));
    }

    /**
     * 功能描述: 查询所有业务字典
     * 
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.sys.dict.entity.Dict>>
     * @author cdfan
     * @date 2020-05-25
     */
    @Permission("dict_query")
    @BussinessLog("业务字典-列表查询")
    @ApiOperation(value = "业务字典-列表查询", notes = "查询所有业务字典")
    @RequestMapping(value = "/dictInfo", method = RequestMethod.GET)
    public ResultData<List<Dict>> list() {
        return new ResultData<List<Dict>>(dictService.list());
    }

    /**
     * 功能描述: 分页查询业务字典
     * 
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.dict.entity.Dict>>
     * @author cdfan
     * @date 2020-05-25
     */
    @Permission("dict_query")
    @BussinessLog("业务字典-分页查询")
    @ApiOperation(value = "业务字典-分页查询", notes = "分页查询业务字典")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "code", value = "字典编码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "name", value = "字典名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "dictType", value = "字典类型:0为父节点字典类型，1为子节点字典", dataType = "String",
            paramType = "query"),
        @ApiImplicitParam(name = "state", value = "状态:0[禁用]，1[启用]", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "pcode", value = "父级字典编码", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/dictInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<Dict>> page(@RequestParam(value = "code", required = false) String code,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "dictType", required = false) String dictType,
        @RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "pcode", required = false) String pcode) {
        IPage<Dict> page = this.defaultPage(Dict.class);
        return new ResultData<IPage<Dict>>(dictService.pageDict(page, code, name, dictType, state, pcode));
    }

    /**
     * 功能描述: 查询业务字典数据的数量
     * 
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-05-25
     */
    @Permission("dict_query")
    @BussinessLog("业务字典-数量查询")
    @ApiOperation(value = "业务字典-数量查询", notes = "查询业务字典数据的数量")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "字典编码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "dictId", value = "字典id", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pcode", value = "父级字典编码", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/dictInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count(@RequestParam(value = "code", required = false) String code,
        @RequestParam(value = "dictId", required = false) Integer dictId,
        @RequestParam(value = "pcode", required = false) String pcode) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().eq("code", code);
        if (ObjectUtils.isNotEmpty(dictId)) {
            queryWrapper.ne("dict_id", dictId);
        }
        if (ObjectUtils.isNotEmpty(pcode)) {
            queryWrapper.eq("pcode", pcode);
        }
        int count = dictService.count(queryWrapper);
        return new ResultData<Integer>(count);
    }

    /**
     * 功能描述: 新增业务字典
     * 
     * @param dict 业务字典实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-05-25
     */
    @Permission("dict_add")
    @BussinessLog("业务字典-新增")
    @ApiOperation(value = "业务字典-新增", notes = "新增业务字典")
    @ApiImplicitParam(name = "dict", value = "业务字典实体对象", required = true, dataType = "Dict", paramType = "body")
    @RequestMapping(value = "/dictInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody Dict dict) {
        dictService.save(dict);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改业务字典，对象中必须有主键
     * 
     * @param dict 业务字典实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-05-25
     */
    @Permission("dict_edit")
    @BussinessLog("业务字典-修改")
    @ApiOperation(value = "业务字典-修改", notes = "修改业务字典，对象中必须有主键")
    @ApiImplicitParam(name = "dict", value = "业务字典实体对象", required = true, dataType = "Dict", paramType = "body")
    @RequestMapping(value = "/dictInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody Dict dict) {
        dictService.updateDict(dict);
        return SUCCESS;
    }

    /**
     * 功能描述: 删除业务字典，及其字典详情
     * 
     * @param dictId 业务字典id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-05-25
     */
    @Permission("dict_delete")
    @BussinessLog("业务字典-删除")
    @ApiOperation(value = "业务字典-删除", notes = "删除业务字典，及其字典详情")
    @ApiImplicitParam(name = "dictId", value = "业务字典id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/dictInfo/{dictId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("dictId") Integer dictId) {
        dictService.deleteDict(dictId);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据字典编码查询字典详情
     *
     * @param code 字典编码
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.dict.entity.Dict>
     * @author cdfan
     * @date 2020-05-25
     */
    @BussinessLog("业务字典-根据字典编码查询字典详情")
    @ApiOperation(value = "业务字典-根据字典编码查询字典详情", notes = "根据字典编码查询字典详情")
    @ApiImplicitParam(name = "code", value = "字典编码", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getDictDetails/{code}", method = RequestMethod.GET)
    public ResultData<List<Dict>> getDictDetails(@PathVariable("code") String code) {
        return new ResultData<List<Dict>>(dictService.getDictDetails(code));
    }
}
