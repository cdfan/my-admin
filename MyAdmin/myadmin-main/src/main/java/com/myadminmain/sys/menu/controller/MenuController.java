package com.myadminmain.sys.menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.common.resultdata.ResultData;
import com.exception.MyAdminException;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.myadminmain.core.shiro.ShiroUser;
import com.myadminmain.core.shiro.ShiroUtil;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.sys.menu.entity.Menu;
import com.myadminmain.sys.menu.service.MenuService;
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
 * @description: 菜单管理 前端控制器
 * @version: 1.0
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "MenuController", description = "菜单操作api")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * 功能描述: 根据主键查询菜单
     * 
     * @param menuId 菜单id
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.menu.entity.Menu>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("menu_query")
    @BussinessLog("菜单-单记录查询")
    @ApiOperation(value = "菜单-单记录查询", notes = "根据主键查询菜单")
    @ApiImplicitParam(name = "menuId", value = "菜单id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/menuInfo/{menuId}", method = RequestMethod.GET)
    public ResultData<Menu> get(@PathVariable("menuId") Integer menuId) {
        return new ResultData<Menu>(menuService.getById(menuId));
    }

    /**
     * 功能描述: 查询所有菜单，并返回树结构
     * 
     * @param menuName 菜单名称
     * @param menuCode 菜单编码
     * @param state 状态:0[禁用]，1[启用]
     * @return com.common.resultdata.ResultData<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @author cdfan
     * @date 2020/3/30 15:12
     */
    @Permission("menu_query")
    @BussinessLog("菜单-列表查询，返回树结构")
    @ApiOperation(value = "菜单-列表查询，返回树结构", notes = "查询所有菜单，返回树结构")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "menuName", value = "菜单名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "menuCode", value = "菜单编码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "state", value = "状态:0[禁用]，1[启用]", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/menuInfo", method = RequestMethod.GET)
    public ResultData<List<Map<String, Object>>> list(
        @RequestParam(value = "menuName", required = false) String menuName,
        @RequestParam(value = "menuCode", required = false) String menuCode,
        @RequestParam(value = "state", required = false) String state) {
        return new ResultData<List<Map<String, Object>>>(menuService.menuTreeList(menuName, menuCode, state));
    }

    /**
     * 功能描述: 获取所有菜单节点，并返回树结构,
     *
     * @param disabledPNode 是否禁用父节点，如果为true则会在生成的树的父节点中添加disabled: true
     * @return com.common.resultdata.ResultData<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @author cdfan
     * @date 2020/3/30 15:12
     */
    @BussinessLog("菜单-获取所有菜单节点，并返回树结构")
    @ApiOperation(value = "菜单-获取所有菜单节点，并返回树结构", notes = "获取所有菜单节点，并返回树结构")
    @ApiImplicitParam(name = "disabledPNode", value = "是否禁用父级节点", required = false, dataType = "boolean", paramType = "query")
    @RequestMapping(value = "/getMenuNodes", method = RequestMethod.GET)
    public ResultData<List<Map<String, Object>>> getMenuNodes(@RequestParam(value = "disabledPNode", required = false) boolean disabledPNode) {
        return new ResultData<List<Map<String, Object>>>(menuService.getMenuNodes(disabledPNode));
    }

    /**
     * 功能描述: 分页查询菜单
     * 
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.menu.entity.Menu>>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("menu_query")
    @BussinessLog("菜单-分页查询")
    @ApiOperation(value = "菜单-分页查询", notes = "分页查询菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),})
    @RequestMapping(value = "/menuInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<Menu>> page() {
        IPage<Menu> page = this.defaultPage(Menu.class);
        return new ResultData<IPage<Menu>>(menuService.page(page));
    }

    /**
     * 功能描述: 查询菜单数据的数量
     * 
     * @param menuCode 菜单编码
     * @param menuId 菜单id
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020/4/29 17:29
     */
    @Permission("menu_query")
    @BussinessLog("菜单-数量查询")
    @ApiOperation(value = "菜单-数量查询", notes = "查询菜单数据的数量")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "menuCode", value = "菜单编码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "menuId", value = "菜单id", dataType = "int", paramType = "query")})
    @RequestMapping(value = "/menuInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count(@RequestParam(value = "menuCode", required = false) String menuCode,
        @RequestParam(value = "menuId", required = false) Integer menuId) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<Menu>().eq("menu_code", menuCode);
        if (ObjectUtils.isNotEmpty(menuId)) {
            queryWrapper.ne("menu_id", menuId);
        }
        int count = menuService.count(queryWrapper);
        return new ResultData<Integer>(count);
    }

    /**
     * 功能描述: 新增菜单
     * 
     * @param menu 菜单实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("menu_add")
    @BussinessLog("菜单-新增")
    @ApiOperation(value = "菜单-新增", notes = "新增菜单")
    @ApiImplicitParam(name = "menu", value = "菜单实体对象", required = true, dataType = "Menu", paramType = "body")
    @RequestMapping(value = "/menuInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody Menu menu) {
        menuService.save(menu);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改菜单，对象中必须有主键
     * 
     * @param menu 菜单实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("menu_edit")
    @BussinessLog("菜单-修改")
    @ApiOperation(value = "菜单-修改", notes = "修改菜单，对象中必须有主键")
    @ApiImplicitParam(name = "menu", value = "菜单实体对象", required = true, dataType = "Menu", paramType = "body")
    @RequestMapping(value = "/menuInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody Menu menu) {
        if (ObjectUtils.isEmpty(menu.getMenuId())) {
            throw new MyAdminException("修改对象中必须存在主键");
        }
        menuService.updateMenu(menu);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除菜单，当菜单中包含子节点则需要先删除子节点
     * 
     * @param menuId 菜单id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("menu_delete")
    @BussinessLog("菜单-删除")
    @ApiOperation(value = "菜单-删除", notes = "根据主键删除菜单")
    @ApiImplicitParam(name = "menuId", value = "菜单id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/menuInfo/{menuId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("menuId") Integer menuId) {
        menuService.removeMenu(menuId);
        return SUCCESS;
    }

    /**
     * 功能描述: 获取当前登录用户及其所拥有的菜单
     * 
     * @return com.common.resultdata.ResultData<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/3/25 14:16
     */
    @BussinessLog("获取当前登录用户及其所拥有的菜单")
    @ApiOperation(value = "获取当前登录用户及其所拥有的菜单", notes = "获取当前登录用户及其所拥有的菜单")
    @RequestMapping(value = "/getUserRoutes", method = RequestMethod.GET)
    public ResultData<Map<String, Object>> getUserRoutes() {
        ShiroUser user = ShiroUtil.getUser();
        if (ObjectUtils.isEmpty(user)) {
            throw new MyAdminException("未获取到登录用户");
        } else {
            return new ResultData<Map<String, Object>>(menuService.getUserRoutes(user.getUserId()));
        }
    }
}
