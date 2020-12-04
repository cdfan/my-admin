package com.myadminmain.sys.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.sys.role.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统角色表 service接口
 * @version: 1.0
 */
public interface RoleService extends IService<Role> {

    /**
     * 功能描述: 通过userid查询用户所拥有的所有角色
     * 
     * @param userId 用户id
     * @return java.util.List<com.myadminmain.sys.role.entity.Role>
     * @author cdfan
     * @date 2020/3/23 17:36
     */
    List<Role> findRoleByUserId(Integer userId);

    /**
     * 功能描述: 分页查询角色信息
     * 
     * @param page 分页对象
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.role.entity.Role>
     * @author cdfan
     * @date 2020/4/8 17:33
     */
    IPage<Role> page(IPage<Role> page, String roleName, String roleCode);

    /**
     * 功能描述: 保存角色及相关信息
     * 
     * @param role 角色实体对象
     * @author cdfan
     * @date 2020/4/15 14:52
     */
    void saveRole(Role role);

    /**
     * 功能描述: 根据Role对象修改角色及权限
     * 
     * @param role 角色实体对象
     * @author cdfan
     * @date 2020/4/15 16:23
     */
    void updateRole(Role role);

    /**
     * 功能描述: 根据角色修改权限,角色对象中必须有主键
     * 
     * @param role 角色实体对象
     * @return boolean
     * @author cdfan
     * @date 2020/4/15 16:51
     */
    boolean updateRoleMenu(Role role);

    /**
     * 功能描述: 根据主键删除-角色及相关权限，如果当前角色被用户所引用则删除失败
     * 
     * @param roleId 角色id
     * @author cdfan
     * @date 2020/4/15 17:50
     */
    void removeRole(Integer roleId);

    /**
     * 功能描述: 查询角色所拥有的的菜单id，只包含最底层子节点
     * 
     * @param roleId 角色id
     * @return java.util.List<java.lang.Integer>
     * @author cdfan
     * @date 2020/4/20 22:59
     */
    List<Integer> getHasMenuId(Integer roleId);
}
