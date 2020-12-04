package com.myadminmain.sys.user.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.core.shiro.ShiroUser;
import com.myadminmain.sys.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统用户表 Mapper 接口
 * @version: 1.0
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 功能描述: 通过用户id获取shiroUser对象
     * 
     * @param userId 用户id
     * @return com.myadminmain.core.shiro.ShiroUser
     * @author cdfan
     * @date 2020/3/2 22:42
     */
    ShiroUser getShiroUser(@Param("userId") Integer userId);

    /**
     * 功能描述: 根据条件查询用户及其相关数据
     * 
     * @param page 分页对象
     * @param userName 用户名
     * @param userCode 用户编码
     * @param deptId 部门id
     * @param roleId 角色id
     * @param userId 用户id
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.user.entity.User>
     * @author cdfan
     * @date 2020/4/28 15:52
     */
    IPage<User> queryUserAllInfo(@Param("page") IPage<User> page, @Param("userName") String userName,
        @Param("userCode") String userCode, @Param("deptId") Integer deptId, @Param("roleId") Integer roleId,
        @Param("userId") Integer userId);

    /**
     * 功能描述: 保存用户角色关联信息
     * 
     * @param userId 用户id
     * @param roleIds 关联的角色id集合
     * @author cdfan
     * @date 2020/4/29 22:59
     */
    void saveUserRole(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);

    /**
     * 功能描述: 保存用户部门关联信息
     * 
     * @param userId 用户id
     * @param deptIds 关联的部门id集合
     * @author cdfan
     * @date 2020/4/29 23:03
     */
    void saveUserDept(@Param("userId") Integer userId, @Param("deptIds") List<Integer> deptIds);

    /**
     * 功能描述: 删除用户关联的角色
     * 
     * @param userId 用户id
     * @param roleIds 角色id集合
     * @author cdfan
     * @date 2020/5/5 17:23
     */
    void deleteUserRole(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);

    /**
     * 功能描述: 删除用户关联的部门
     * 
     * @param userId 用户id
     * @param deptIds 部门id集合
     * @author cdfan
     * @date 2020/5/5 17:44
     */
    void deleteUserDept(@Param("userId") Integer userId, @Param("deptIds") List<Integer> deptIds);

    /**
     * 功能描述: 保存头像记录
     * 
     * @param userId userId
     * @param avatar 头像url
     * @author cdfan
     * @date 2020/6/10 12:15
     */
    void saveAvatarHistory(@Param("userId") Integer userId, @Param("avatar") String avatar);

    /**
     * 功能描述: 删除头像历史记录
     * 
     * @param userId 用户id
     * @author cdfan
     * @date 2020/6/10 12:21
     */
    void deleteAvatarHistory(@Param("userId") Integer userId);

    /**
     * 功能描述: 通过角色编码获取对应的用户
     *
     * @param roleCodes 角色编码集合
     * @return java.util.List<com.myadminmain.sys.user.entity.User>
     * @author cdfan
     * @date 2020/8/27 10:28
     */
    List<User> queryUserByRolecodes(ArrayList<String> roleCodes);
}
