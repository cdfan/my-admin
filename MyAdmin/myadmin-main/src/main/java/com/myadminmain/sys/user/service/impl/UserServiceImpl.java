package com.myadminmain.sys.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exception.MyAdminException;
import com.myadminmain.config.properties.MyAdminProperties;
import com.myadminmain.core.cache.CacheSupport;
import com.myadminmain.core.mail.MailService;
import com.myadminmain.core.shiro.ShiroUser;
import com.myadminmain.core.shiro.ShiroUtil;
import com.myadminmain.sys.dept.entity.Dept;
import com.myadminmain.sys.role.entity.Role;
import com.myadminmain.sys.user.dao.UserMapper;
import com.myadminmain.sys.user.entity.User;
import com.myadminmain.sys.user.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统用户表 service实现类
 * @version: 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private MailService mailService;

    @Autowired
    private MyAdminProperties myAdminProperties;

    @Override
    public ShiroUser getShiroUser(Integer userId) {
        return baseMapper.getShiroUser(userId);
    }

    @Override
    public IPage<User> page(IPage<User> page, String userName, String userCode, Integer deptId, Integer roleId) {
        return this.baseMapper.queryUserAllInfo(page, userName, userCode, deptId, roleId, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        // 保存用户信息
        // 对密码进行加密
        user.setPassword(ShiroUtil.encrypt(user.getPassword(), user.getUserCode()));
        this.save(user);
        // 保存用户角色关联信息
        List<Integer> roleIds = user.getRoleIds();
        if (roleIds.size() > 0) {
            this.baseMapper.saveUserRole(user.getUserId(), user.getRoleIds());
        }
        // 保存用户部门管理信息
        List<Integer> deptIds = user.getDeptIds();
        if (deptIds.size() > 0) {
            this.baseMapper.saveUserDept(user.getUserId(), user.getDeptIds());
        }
        User saveUser = this.getById(user.getUserId());
        // 保存头像记录
        this.baseMapper.saveAvatarHistory(user.getUserId(), saveUser.getAvatar());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        // 获取未修改时的用户及其相关数据
        IPage<User> userList =
            this.baseMapper.queryUserAllInfo(new Page<User>(), null, null, null, null, user.getUserId());
        User oldUser = userList.getRecords().get(0);
        if (oldUser == null) {
            throw new MyAdminException("数据异常，操作失败");
        }
        // 修改用户信息
        this.updateById(user);
        // 修改用户所关联角色信息
        List<Integer> roleIds = user.getRoleIds();
        if (ObjectUtils.isNotEmpty(roleIds)) {
            // 用户原来所拥有的角色id
            List<Integer> oldRoleIds = new ArrayList<>();
            for (Role r : oldUser.getRoles()) {
                oldRoleIds.add(r.getRoleId());
            }
            distinctListItem(roleIds, oldRoleIds);
            // 新增用户关联的角色
            if (roleIds.size() > 0) {
                this.baseMapper.saveUserRole(user.getUserId(), roleIds);
            }
            // 删除用户关联的角色
            if (oldRoleIds.size() > 0) {
                this.baseMapper.deleteUserRole(user.getUserId(), oldRoleIds);
            }
        }
        // 修改用户所关联部门信息
        List<Integer> deptIds = user.getDeptIds();
        if (ObjectUtils.isNotEmpty(deptIds)) {
            // 用户原来所拥有的部门id
            List<Integer> oldDeptIds = new ArrayList<>();
            for (Dept d : oldUser.getDepts()) {
                oldDeptIds.add(d.getDeptId());
            }
            distinctListItem(deptIds, oldDeptIds);
            // 新增用户关联的部门
            if (deptIds.size() > 0) {
                this.baseMapper.saveUserDept(user.getUserId(), deptIds);
            }
            // 删除用户关联的部门
            if (oldDeptIds.size() > 0) {
                this.baseMapper.deleteUserDept(user.getUserId(), oldDeptIds);
            }
        }
        // 清除用户相关信息的缓存
        CacheSupport.clearAuthUserCache(user.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUser(Integer userId) {
        // 删除用户
        this.removeById(userId);
        // 删除用户的角色的关联信息
        this.baseMapper.deleteUserRole(userId, null);
        // 删除用户和部门的关联信息
        this.baseMapper.deleteUserDept(userId, null);
        // 删除头像历史记录
        this.baseMapper.deleteAvatarHistory(userId);
        // 清理用户缓存信息
        CacheSupport.clearAuthUserCache();
    }

    @Override
    @Cacheable(cacheNames = "temp_cache", key = "'mailCode:'+#root.args[0]", unless = "#result == null")
    public String sendEmailCode(String codeId, String mail) {
        if (StringUtils.isEmpty(mail)) {
            return null;
        }
        // 生成随机验证码
        String code = String.valueOf(RandomUtils.nextInt(100000, 999999));
        // 发送邮件
        Map<String, String> map = new HashMap<>();
        map.put("userName", ShiroUtil.getUser().getUserName());
        map.put("code", code);
        map.put("url", myAdminProperties.getWebUrl() + "/#/profile/index");
        try {
            mailService.sendHtmlMail(mail, "MyAdmin邮件验证码", map, "MailVerificationCode");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MyAdminException("邮件发送失败");
        }
        return code;
    }

    @Override
    public void updatePassword(String newPassword, String oldPassword) {
        // 获取用户信息
        User user = this.getOne(new QueryWrapper<User>().eq("user_code", ShiroUtil.getUser().getUserCode()));
        // 加密老密码
        String encryptOldPassword = ShiroUtil.encrypt(oldPassword, user.getUserCode());
        if (!encryptOldPassword.equals(user.getPassword())) {
            throw new MyAdminException("密码错误");
        }
        String encryptNewPassword = ShiroUtil.encrypt(newPassword, user.getUserCode());
        this.update(new UpdateWrapper<User>().set("password", encryptNewPassword).eq("user_code",
            ShiroUtil.getUser().getUserCode()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(Integer userId, String url) {
        this.update(new UpdateWrapper<User>().set("avatar", url).eq("user_id", userId));
        // 保存头像记录
        this.baseMapper.saveAvatarHistory(userId, url);
    }

    @Override
    public List<User> queryUserByRolecodes(ArrayList<String> roleCodes) {
        return this.baseMapper.queryUserByRolecodes(roleCodes);
    }

    private void distinctListItem(List<Integer> ids, List<Integer> oldIds) {
        if (oldIds.size() > 0) {
            Iterator<Integer> oldIdIterator = oldIds.iterator();
            // 如果修改的id集合中包含原来就有的id那么从集合中删除，这条记录不用修改
            while (oldIdIterator.hasNext()) {
                Integer oldRoleId = oldIdIterator.next();
                if (ids.contains(oldRoleId)) {
                    ids.remove(oldRoleId);
                    oldIdIterator.remove();
                }
            }
        }
    }
}
