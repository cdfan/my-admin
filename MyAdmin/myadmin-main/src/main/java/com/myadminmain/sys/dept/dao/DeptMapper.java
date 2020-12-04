package com.myadminmain.sys.dept.dao;

import com.myadminmain.sys.dept.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统部门表 Mapper 接口
 * @version: 1.0
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 功能描述: 根据部门名称模糊，查询当前及其父节点数据
     * 
     * @param deptName 部门名称
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/4/24 15:07
     */
    List<Map<String, Object>> queryParentDept(@Param("deptName") String deptName);

    /**
     * 功能描述: 根据部门id，获取部门下关联的用户
     * 
     * @param deptId 部门id
     * @return java.util.List<java.util.Map>
     * @author cdfan
     * @date 2020/4/27 11:47
     */
    List<Map> getUserDept(@Param("deptId") Integer deptId);
}
