package com.myadminmain.sys.menu.service;

import com.myadminmain.sys.menu.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统菜单表 service接口
 * @version: 1.0
 */
public interface MenuService extends IService<Menu> {

    /**
     * 功能描述: 根据userId，获取所拥有的菜单
     * 
     * @param userId 用户id
     * @return java.util.List<java.lang.String>
     * @author cdfan
     * @date 2020/3/23 16:03
     */
    List<Menu> findMenuByUserId(Integer userId);

    /**
     * 功能描述: 根据用户id，获取用户及其所拥有的菜单
     * 
     * @param userId 用户id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author cdfan
     * @date 2020/3/25 14:18
     */
    Map<String, Object> getUserRoutes(Integer userId);

    /**
     * 功能描述: 列表查询所有菜单,并返回树结构
     * 
     * @param menuName 菜单名称
     * @param menuCode 菜单编码
     * @param state 状态
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/3/30 15:11
     */
    List<Map<String, Object>> menuTreeList(String menuName, String menuCode, String state);

    /**
     * 功能描述: 获取所有菜单节点,并返回树结构
     *
     * @param disabledPNode 是否禁用父节点，如果为true则会在生成的树的父节点中添加disabled: true
     * @return java.util.List<java.util.Map>
     * @author cdfan
     * @date 2020/4/2 16:40
     */
    List<Map<String, Object>> getMenuNodes(boolean disabledPNode);

    /**
     * 功能描述: 删除菜单，当菜单中包含子节点则需要先删除子节点
     * 
     * @param menuId 菜单id
     * @author cdfan
     * @date 2020/4/13 16:49
     */
    void removeMenu(Integer menuId);

    /**
     * 功能描述: 根据Menu对象修改菜单
     * 
     * @param menu 菜单对象
     * @author cdfan
     * @date 2020/6/15 16:52
     */
    void updateMenu(Menu menu);

}
