package com.myadminmain.sys.role.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.common.resultdata.ResultData;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.exception.MyAdminException;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.sys.role.entity.Role;
import com.myadminmain.sys.role.service.RoleService;
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

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 角色管理 前端控制器
 * @version: 1.0
 */
@RestController
@RequestMapping("/role")
@Api(tags = "RoleController", description = "角色操作api")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 功能描述: 根据主键查询角色
     * 
     * @param roleId 角色id
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.role.entity.Role>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("role_query")
    @BussinessLog("角色-单记录查询")
    @ApiOperation(value = "角色-单记录查询", notes = "根据主键查询角色")
    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/roleInfo/{roleId}", method = RequestMethod.GET)
    public ResultData<Role> get(@PathVariable("roleId") Integer roleId) {
        Role list = roleService.getById(roleId);
        return new ResultData<Role>(list);
    }

    /**
     * 功能描述: 查询所有角色
     * 
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.sys.role.entity.Role>>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("role_query")
    @BussinessLog("角色-列表查询")
    @ApiOperation(value = "角色-列表查询", notes = "查询所有角色")
    @RequestMapping(value = "/roleInfo", method = RequestMethod.GET)
    public ResultData<List<Role>> list() {
        return new ResultData<List<Role>>(roleService.list());
    }

    /**
     * 功能描述: 分页查询角色
     * 
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.role.entity.Role>>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("role_query")
    @BussinessLog("角色-分页查询")
    @ApiOperation(value = "角色-分页查询", notes = "分页查询角色")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "roleCode", value = "角色编码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/roleInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<Role>> page(@RequestParam(value = "roleName", required = false) String roleName,
        @RequestParam(value = "roleCode", required = false) String roleCode) {
        IPage<Role> page = this.defaultPage(Role.class);
        IPage<Role> list = roleService.page(page, roleName, roleCode);
        return new ResultData<IPage<Role>>(list);
    }

    /**
     * 功能描述: 查询角色数据的数量
     * 
     * @param roleCode 角色编码
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020/4/29 17:29
     */
    @Permission("role_query")
    @BussinessLog("角色-数量查询")
    @ApiOperation(value = "角色-数量查询", notes = "查询角色数据的数量")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "roleCode", value = "角色编码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "int", paramType = "query")})
    @RequestMapping(value = "/roleInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count(@RequestParam(value = "roleCode", required = false) String roleCode,
        @RequestParam(value = "roleId", required = false) Integer roleId) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>().eq("role_code", roleCode);
        if (ObjectUtils.isNotEmpty(roleId)) {
            queryWrapper.ne("role_id", roleId);
        }
        int count = roleService.count(queryWrapper);
        return new ResultData<Integer>(count);
    }

    /**
     * 功能描述: 新增角色及权限
     * 
     * @param role 角色实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("role_add")
    @BussinessLog("角色-新增")
    @ApiOperation(value = "角色-新增", notes = "新增角色及权限")
    @ApiImplicitParam(name = "role", value = "角色实体对象", required = true, dataType = "Role", paramType = "body")
    @RequestMapping(value = "/roleInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody Role role) {
        roleService.saveRole(role);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改角色及权限，对象中必须有主键
     * 
     * @param role 角色实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("role_edit|role_permission")
    @BussinessLog("角色-修改")
    @ApiOperation(value = "角色-修改", notes = "修改角色及权限，对象中必须有主键")
    @ApiImplicitParam(name = "role", value = "角色实体对象", required = true, dataType = "Role", paramType = "body")
    @RequestMapping(value = "/roleInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody Role role) {
        if(ObjectUtils.isEmpty(role.getRoleId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        roleService.updateRole(role);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除角色及相关权限，如果当前角色被用户所引用则删除失败
     * 
     * @param roleId 角色id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("role_delete")
    @BussinessLog("角色-删除")
    @ApiOperation(value = "角色-删除", notes = "根据主键删除角色及相关权限，如果当前角色被用户所引用则删除失败")
    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/roleInfo/{roleId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("roleId") Integer roleId) {
        roleService.removeRole(roleId);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据角色修改权限，角色对象中必须有主键
     * 
     * @param role 角色实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("role_edit")
    @BussinessLog("角色-权限修改")
    @ApiOperation(value = "角色-权限修改", notes = "根据角色修改权限，角色对象中必须有主键")
    @ApiImplicitParam(name = "role", value = "角色实体对象", required = true, dataType = "Role", paramType = "body")
    @RequestMapping(value = "/updateRoleMenu", method = RequestMethod.PUT)
    public ResultData updateRoleMenu(@RequestBody Role role) {
        if(ObjectUtils.isEmpty(role.getRoleId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        roleService.updateRoleMenu(role);
        return SUCCESS;
    }

    /**
     * 功能描述: 查询角色所拥有的菜单id
     * 
     * @param roleId 角色id
     * @return com.common.resultdata.ResultData<java.util.List<java.lang.Integer>>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("role_query")
    @BussinessLog("查询角色所拥有的菜单id")
    @ApiOperation(value = "查询角色所拥有的菜单id", notes = "查询角色所拥有的菜单id")
    @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/getHasMenuId/{roleId}", method = RequestMethod.GET)
    public ResultData<List<Integer>> getHasMenuId(@PathVariable("roleId") Integer roleId) {
        return new ResultData<List<Integer>>(roleService.getHasMenuId(roleId));
    }
}
