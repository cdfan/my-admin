package com.myadminmain.sys.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myadminmain.sys.menu.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统菜单表 Mapper 接口
 * @version: 1.0
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 功能描述: 根据userId，获取所拥有的菜单
     * 
     * @param userId 菜单id
     * @return java.util.List<com.myadminmain.sys.menu.entity.Menu>
     * @author cdfan
     * @date 2020/3/23 16:08
     */
    List<Menu> findMenuByUserId(@Param("userId") Integer userId);

    /**
     * 功能描述: 通过查询条件查询匹配菜单及所有父菜单，其中type为： 如果仅仅是menuCode有值type为eq精确匹配，如果仅仅是menuName有值type为like模糊匹配，两个都有值为空串两个都匹配
     * 
     * @param menuName 菜单名称
     * @param menuCode 菜单编码
     * @param type 匹配类型
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/4/1 16:03
     */
    List<Map<String, Object>> queryParentMenu(@Param("menuName") String menuName, @Param("menuCode") String menuCode,
        @Param("type") String type);

    /**
     * 功能描述: 根据菜单id，删除角色菜单对应关系
     * 
     * @param menuId 菜单id
     * @author cdfan
     * @date 2020/6/5 11:30
     */
    void deleteRoleMenuByMenuId(@Param("menuId") Integer menuId);

    /**
     * 功能描述:根据菜单id获取拥有当前菜单的用户
     * 
     * @param menuId 菜单id
     * @return java.util.List<java.util.Map>
     * @author cdfan
     * @date 2020/6/15 16:56
     */
    List<Map> getUserMenu(@Param("menuId") Integer menuId);
}
