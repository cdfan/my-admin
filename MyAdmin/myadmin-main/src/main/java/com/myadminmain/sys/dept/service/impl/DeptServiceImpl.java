package com.myadminmain.sys.dept.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exception.MyAdminException;
import com.myadminmain.sys.dept.dao.DeptMapper;
import com.myadminmain.sys.dept.entity.Dept;
import com.myadminmain.sys.dept.service.DeptService;
import com.util.ToolUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统部门表 service实现类
 * @version: 1.0
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public List<Map<String, Object>> deptTreeList(String deptName) {
        List<Map<String, Object>> maps;
        if (ObjectUtils.isEmpty(deptName)) {
            QueryWrapper<Dept> query = new QueryWrapper<>();
            query.orderByAsc("order_num");
            maps = this.listMaps(query);
        } else {
            maps = this.baseMapper.queryParentDept(deptName);
        }
        // 构建树结构
        return ToolUtil.treeBuilder(maps, "deptId", "pid", false);
    }

    @Override
    public void removeDept(Integer deptId) {
        // 当前部门下是否存在用户
        List<Map> list = this.baseMapper.getUserDept(deptId);
        if (list.size() > 0) {
            throw new MyAdminException("当前部门下存在用户，请先解除关系");
        }
        // 判断当前部门下是否存在子部门
        QueryWrapper<Dept> query = new QueryWrapper<Dept>().eq("pid", deptId);
        int count = this.count(query);
        if (count > 0) {
            throw new MyAdminException("当前节点下包含子节点，不能删除");
        }
        // 删除角色信息
        this.removeById(deptId);
    }

}
