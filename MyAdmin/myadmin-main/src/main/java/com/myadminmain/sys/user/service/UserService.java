package com.myadminmain.sys.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.core.shiro.ShiroUser;
import com.myadminmain.sys.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统用户表 service接口
 * @version: 1.0
 */
public interface UserService extends IService<User> {

    /**
     * 功能描述: 通过用户id获取shiroUser对象
     * 
     * @param userId 用户id
     * @return com.myadminmain.core.shiro.ShiroUser
     * @author cdfan
     * @date 2020/3/22 18:46
     */
    ShiroUser getShiroUser(Integer userId);

    /**
     * 功能描述:分页查询用户信息
     * 
     * @param page 分页对象
     * @param userName 用户名
     * @param userCode 用户编码
     * @param deptId 部门id
     * @param roleId 角色id
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.user.entity.User>
     * @author cdfan
     * @date 2020/4/28 17:07
     */
    IPage<User> page(IPage<User> page, String userName, String userCode, Integer deptId, Integer roleId);

    /**
     * 功能描述: 保存用户及其相关信息
     * 
     * @param user 用户实体
     * @author cdfan
     * @date 2020/4/29 16:48
     */
    void saveUser(User user);

    /**
     * 功能描述: 根据User对象修改用户及其相关数据（用户所拥有部门、角色）对象中必须有主键
     * 
     * @param user 用户对象
     * @author cdfan
     * @date 2020/5/5 16:36
     */
    void updateUser(User user);

    /**
     * 功能描述: 删除用户及用户相关信息
     * 
     * @param userId 用户id
     * @author cdfan
     * @date 2020/5/13 17:41
     */
    void removeUser(Integer userId);

    /**
     * 功能描述: 给指定邮箱发送，验证码
     * 
     * @param codeId ，缓存使用的验证码id
     * @param mail 邮箱
     * @return java.lang.String
     * @author cdfan
     * @date 2020/5/31 16:20
     */
    String sendEmailCode(String codeId, String mail);

    /**
     * 功能描述: 修改用户密码
     * 
     * @param newPassword 新密码
     * @param oldPassword 老密码
     * @author cdfan
     * @date 2020/6/2 22:59
     */
    void updatePassword(String newPassword, String oldPassword);

    /**
     * 功能描述: 修改用户头像
     * 
     * @param userId 用户id
     * @param url 头像url
     * @author cdfan
     * @date 2020/6/10 14:27
     */
    void updateAvatar(Integer userId, String url);

    /**
     * 功能描述: 通过角色编码获取对应的用户
     *
     * @param roleCodes 角色编码集合
     * @return java.util.List<com.myadminmain.sys.user.entity.User>
     * @author cdfan
     * @date 2020/8/27 10:27
     */
    List<User> queryUserByRolecodes(ArrayList<String> roleCodes);
}
