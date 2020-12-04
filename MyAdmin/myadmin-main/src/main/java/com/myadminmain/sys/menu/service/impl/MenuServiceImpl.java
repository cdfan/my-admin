package com.myadminmain.sys.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exception.MyAdminException;
import com.myadminmain.core.cache.CacheSupport;
import com.myadminmain.sys.menu.entity.Menu;
import com.myadminmain.sys.menu.dao.MenuMapper;
import com.myadminmain.sys.menu.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myadminmain.sys.user.service.UserService;
import com.util.ToolUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统菜单表 service实现类
 * @version: 1.0
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private UserService userService;

    @Override
    public List<Menu> findMenuByUserId(Integer userId) {
        return baseMapper.findMenuByUserId(userId);
    }

    @Override
    public Map<String, Object> getUserRoutes(Integer userId) {
        List<Menu> menuList = baseMapper.findMenuByUserId(userId);
        Map<String, Object> resultMap = new HashMap<>(3);
        if (!ObjectUtils.isEmpty(menuList)) {
            List<String> bntList = new ArrayList<>();
            Iterator<Menu> it = menuList.iterator();
            Menu menu;
            while (it.hasNext()) {
                menu = it.next();
                // 去掉按钮，并单独存起来。因为路由不需要加载按钮
                if ("0".equals(menu.getIsmenu())) {
                    bntList.add(menu.getMenuCode());
                    it.remove();
                }
            }
            resultMap.put("menuList", menuList);
            resultMap.put("bntList", bntList);
            resultMap.put("user", userService.getById(userId));
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> menuTreeList(String menuName, String menuCode, String state) {
        List<Map<String, Object>> maps;
        // 存储过程用到的类型，如果仅仅是menuCode有值为eq精确匹配，
        // 如果仅仅是menuName有值为like模糊匹配，两个都有值为空串两个都匹配
        String type = "";
        if (ObjectUtils.isEmpty(menuCode) && ObjectUtils.isEmpty(menuName)) {
            QueryWrapper<Menu> query = new QueryWrapper<>();
            if (!ObjectUtils.isEmpty(state)) {
                query.eq("state", state);
            }
            query.orderByAsc("order_num", "menu_code");
            maps = this.listMaps(query);
        } else {
            if (!ObjectUtils.isEmpty(menuCode) && ObjectUtils.isEmpty(menuName)) {
                type = "eq";
            } else if (ObjectUtils.isEmpty(menuCode) && !ObjectUtils.isEmpty(menuName)) {
                type = "like";
            }
            maps = this.baseMapper.queryParentMenu(menuName, menuCode, type);
        }
        // 构建树结构
        return ToolUtil.treeBuilder(maps, "menuId", "pid", false);
    }

    @Override
    public List<Map<String, Object>> getMenuNodes(boolean disabledPNode) {
        QueryWrapper<Menu> query = new QueryWrapper<Menu>().eq("ismenu", "1").eq("state", "1");
        query.orderByAsc("order_num", "menu_code");
        List<Map<String, Object>> maps = this.listMaps(query);
        // 构建树结构
        return ToolUtil.treeBuilder(maps, "menuId", "pid", disabledPNode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeMenu(Integer menuId) {
        QueryWrapper<Menu> query = new QueryWrapper<Menu>().eq("pid", menuId);
        int count = this.count(query);
        // 有下级节点不能删除
        if (count > 0) {
            throw new MyAdminException("当前节点下包含子节点，不能删除");
        } else {
            // 删除菜单
            this.removeById(menuId);
            // 删除角色菜单对应关系
            this.baseMapper.deleteRoleMenuByMenuId(menuId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(Menu menu) {
        // 修改菜单
        this.updateById(menu);
        // 清理拥有当前菜单的用户缓存信息
        List<Map> userMenu = this.baseMapper.getUserMenu(menu.getMenuId());
        CacheSupport.clearAuthCache(userMenu, "userId");
    }

}
