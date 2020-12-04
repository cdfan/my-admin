package com.myadminmain.sys.dept.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.common.resultdata.ResultData;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.exception.MyAdminException;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.sys.dept.entity.Dept;
import com.myadminmain.sys.dept.service.DeptService;
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

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 部门管理 前端控制器
 * @version: 1.0
 */
@RestController
@RequestMapping("/dept")
@Api(tags = "DeptController", description = "部门操作api")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    /**
     * 功能描述: 根据主键查询部门
     * 
     * @param deptId 部门id
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.dept.entity.Dept>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("dept_query")
    @BussinessLog("部门-单记录查询")
    @ApiOperation(value = "部门-单记录查询", notes = "根据主键查询部门")
    @ApiImplicitParam(name = "deptId", value = "部门id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/deptInfo/{deptId}", method = RequestMethod.GET)
    public ResultData<Dept> get(@PathVariable("deptId") Integer deptId) {
        return new ResultData<Dept>(deptService.getById(deptId));
    }

    /**
     * 功能描述: 查询部门列表，返回树结构
     * 
     * @param deptName 部门名称
     * @return com.common.resultdata.ResultData<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("dept_query")
    @BussinessLog("部门-列表查询，返回树结构")
    @ApiOperation(value = "部门-列表查询，返回树结构", notes = "查询部门列表，返回树结构")
    @ApiImplicitParams({@ApiImplicitParam(name = "deptName", value = "部门简称", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/deptInfo", method = RequestMethod.GET)
    public ResultData<List<Map<String, Object>>>
        list(@RequestParam(value = "deptName", required = false) String deptName) {
        return new ResultData<List<Map<String, Object>>>(deptService.deptTreeList(deptName));
    }

    /**
     * 功能描述: 分页查询部门
     * 
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.dept.entity.Dept>>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("dept_query")
    @BussinessLog("部门-分页查询")
    @ApiOperation(value = "部门-分页查询", notes = "分页查询部门")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/deptInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<Dept>> page() {
        IPage<Dept> page = this.defaultPage(Dept.class);
        return new ResultData<IPage<Dept>>(deptService.page(page));
    }

    /**
     * 功能描述: 新增部门
     * 
     * @param dept 部门实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("dept_add")
    @BussinessLog("部门-新增")
    @ApiOperation(value = "部门-新增", notes = "新增部门")
    @ApiImplicitParam(name = "dept", value = "部门实体对象", required = true, dataType = "Dept", paramType = "body")
    @RequestMapping(value = "/deptInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody Dept dept) {
        deptService.save(dept);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改部门，对象中必须有主键
     * 
     * @param dept 部门实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("dept_edit")
    @BussinessLog("部门-修改")
    @ApiOperation(value = "部门-修改", notes = "修改部门，对象中必须有主键")
    @ApiImplicitParam(name = "dept", value = "部门实体对象", required = true, dataType = "Dept", paramType = "body")
    @RequestMapping(value = "/deptInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody Dept dept) {
        if(ObjectUtils.isEmpty(dept.getDeptId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        deptService.updateById(dept);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除部门，如果当前部门下存在用户则删除失败
     * 
     * @param deptId 部门id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("dept_delete")
    @BussinessLog("部门-删除")
    @ApiOperation(value = "部门-删除", notes = "根据主键删除部门，如果当前部门下存在用户则删除失败")
    @ApiImplicitParam(name = "deptId", value = "部门id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/deptInfo/{deptId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("deptId") Integer deptId) {
        deptService.removeDept(deptId);
        return SUCCESS;
    }

}
