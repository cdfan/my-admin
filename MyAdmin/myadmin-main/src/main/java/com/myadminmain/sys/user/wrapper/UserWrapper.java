package com.myadminmain.sys.user.wrapper;

import com.common.wrapper.BaseControllerWrapper;
import com.myadminmain.sys.dept.entity.Dept;
import com.myadminmain.sys.role.entity.Role;
import com.myadminmain.sys.user.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/4/27
 * @description: user 对象包装类
 */
public class UserWrapper extends BaseControllerWrapper<User> {

    public UserWrapper(List<User> list) {
        super(list);
    }

    public UserWrapper(User obj) {
        super(obj);
    }

    @Override
    protected void wrapper(User data) {
        // 将user中的密码删除，在用户管理前端列表中，并不需要出现用户的密码
        data.setPassword(null);
        // 通过用户所拥有的角色，设置角色id集合
        List<Role> roles = data.getRoles();
        List<Integer> roleIds = new ArrayList<Integer>();
        for (Role r : roles) {
            roleIds.add(r.getRoleId());
        }
        data.setRoleIds(roleIds);
        // 通过用户所拥有的部门，设置部门id集合
        List<Dept> depts = data.getDepts();
        List<Integer> deptIds = new ArrayList<Integer>();
        for (Dept d : depts) {
            deptIds.add(d.getDeptId());
        }
        data.setDeptIds(deptIds);
    }
}
