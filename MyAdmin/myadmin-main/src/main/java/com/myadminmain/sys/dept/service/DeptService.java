package com.myadminmain.sys.dept.service;

import com.myadminmain.sys.dept.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统部门表 service接口
 * @version: 1.0
 */
public interface DeptService extends IService<Dept> {

    /**
     * 功能描述: 根据部门名称模糊查询部门列表，返回树结构
     * 
     * @param deptName 部门名称
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/4/24 15:03
     */
    List<Map<String, Object>> deptTreeList(String deptName);

    /**
     * 功能描述: 根据主键删除-部门，如果当前部门下存在用户则删除失败
     * 
     * @param deptId 部门id
     * @author cdfan
     * @date 2020/4/27 11:45
     */
    void removeDept(Integer deptId);

}
