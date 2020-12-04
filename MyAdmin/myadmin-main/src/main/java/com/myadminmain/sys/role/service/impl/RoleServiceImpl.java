package com.myadminmain.sys.role.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exception.MyAdminException;
import com.myadminmain.core.cache.CacheSupport;
import com.myadminmain.sys.role.dao.RoleMapper;
import com.myadminmain.sys.role.entity.Role;
import com.myadminmain.sys.role.service.RoleService;
import com.util.ToolUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统角色表 service实现类
 * @version: 1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Override
    public List<Role> findRoleByUserId(Integer userId) {
        return baseMapper.findRoleByUserId(userId);
    }

    @Override
    public IPage<Role> page(IPage<Role> page, String roleName, String roleCode) {
        IPage<Role> pageList = this.baseMapper.queryRoleAndMenus(page, roleName, roleCode);
        List<Role> list = pageList.getRecords();
        for (Role role : list) {
            List<Map<String, Object>> menuList = role.getMenuTree();
            List<Map<String, Object>> menuTree = ToolUtil.treeBuilder(menuList, "menuId", "pid", false);
            role.setMenuTree(menuTree);
        }
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(Role role) {
        // 保存角色信息
        this.save(role);
        // 保存角色授权信息
        List<Integer> newMenuIds = role.getAllMenuIds();
        if (newMenuIds.size() > 0) {
            this.baseMapper.saveRoleMenu(role.getRoleId(), newMenuIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Role role) {
        // 修改角色信息
        this.updateById(role);
        // 修改角色授权信息
        this.updateRoleMenu(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleMenu(Role role) {
        // 查询角色所拥有的菜单id
        List<Map> roleMenu = this.baseMapper.getRoleMenu(role.getRoleId());
        // 修改菜单id
        List<Integer> newMenuIds = role.getAllMenuIds();
        // 如果关联的菜单为空时，删除角色所关联的所有菜单
        if (ObjectUtils.isEmpty(newMenuIds)) {
            this.baseMapper.deleteRoleMenuByRoleId(role.getRoleId());
            return true;
        }
        // 原有菜单id
        Iterator<Map> oldMenuIds = roleMenu.iterator();
        Integer oldMenuId;
        while (oldMenuIds.hasNext()) {
            oldMenuId = (Integer)oldMenuIds.next().get("menuId");
            if (newMenuIds.contains(oldMenuId)) {
                newMenuIds.remove(oldMenuId);
                oldMenuIds.remove();
            }
        }
        if (newMenuIds.size() > 0) {
            // 新增关联菜单
            this.baseMapper.saveRoleMenu(role.getRoleId(), newMenuIds);
        }
        if (roleMenu.size() > 0) {
            // 删除关联菜单
            this.baseMapper.deleteRoleMenu(roleMenu);
        }
        // 清除拥有当前角色的所有用户的权限缓存
        // 获取角色所属用户
        List<Map> userRole = this.baseMapper.getUserRole(role.getRoleId());
        CacheSupport.clearAuthCache(userRole, "userId");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRole(Integer roleId) {
        // 当前角色是否被用户引用
        List<Map> list = this.baseMapper.getUserRole(roleId);
        if (list.size() > 0) {
            throw new MyAdminException("当前角色已被用户引用，请先解除关系");
        }
        // 删除角色信息
        this.removeById(roleId);
        // 删除角色菜单关联信息
        this.baseMapper.deleteRoleMenuByRoleId(roleId);
    }

    @Override
    public List<Integer> getHasMenuId(Integer roleId) {
        return this.baseMapper.getHasMenuId(roleId);
    }
}
