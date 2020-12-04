package com.myadminmain.sys.role.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.sys.role.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统角色表 Mapper 接口
 * @version: 1.0
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 功能描述: 通过userid查询用户所拥有的所有角色
     * 
     * @param userId 用户id
     * @return java.util.List<com.myadminmain.sys.role.entity.Role>
     * @author cdfan
     * @date 2020/3/23 17:37
     */
    List<Role> findRoleByUserId(@Param("userId") Integer userId);

    /**
     * 功能描述: 查询角色及其所拥有的菜单
     * 
     * @param page 分页对象
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.role.entity.Role>
     * @author cdfan
     * @date 2020/4/8 17:58
     */
    IPage<Role> queryRoleAndMenus(@Param("page") IPage<Role> page, @Param("roleName") String roleName,
        @Param("roleCode") String roleCode);

    /**
     * 功能描述: 查询角色所拥有的菜单id
     * 
     * @param roleId 角色id
     * @return java.util.List<java.lang.Integer>
     * @author cdfan
     * @date 2020/4/15 15:12
     */
    List<Map> getRoleMenu(@Param("roleId") Integer roleId);

    /**
     * 功能描述: 新增关联菜单
     * 
     * @param roleId 角色id
     * @param newMenuIds 菜单id集合
     * @author cdfan
     * @date 2020/4/15 15:44
     */
    void saveRoleMenu(@Param("roleId") Integer roleId, @Param("newMenuIds") List<Integer> newMenuIds);

    /**
     * 功能描述: 删除关联菜单
     * 
     * @param roleMenu 角色菜单关联信息
     * @author cdfan
     * @date 2020/4/15 15:52
     */
    void deleteRoleMenu(@Param("roleMenu") List<Map> roleMenu);

    /**
     * 功能描述: 查询角色用户关联信息
     * 
     * @param roleId 角色id
     * @return java.util.List<java.util.Map>
     * @author cdfan
     * @date 2020/4/15 17:58
     */
    List<Map> getUserRole(@Param("roleId") Integer roleId);

    /**
     * 功能描述: 根据角色id，删除角色菜单关联信息
     * 
     * @param roleId 角色id
     * @author cdfan
     * @date 2020/4/15 18:04
     */
    void deleteRoleMenuByRoleId(@Param("roleId") Integer roleId);

    /**
     * 功能描述: 查询角色所拥有的菜单id
     *
     * @param roleId 角色id
     * @return java.util.List<java.lang.Integer>
     * @author cdfan
     * @date 2020/4/20 23:00
     */
    List<Integer> getHasMenuId(@Param("roleId") Integer roleId);
}
