package com.myadminmain.workflow.process.dao;

import com.myadminmain.workflow.process.entity.ActProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/10
 * @description: com.myadminmain.sys.workflow.process.dao
 */
@Repository
public interface ActProcessMapper {

    /**
     * 功能描述: 获取流程业务关联数据
     *
     * @return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @author cdfan
     * @date 2020/8/10 14:22
     */
    List<Map<String,Object>> queryProcessBusinessInfo();

    /**
     * 功能描述: 获取流程关联数据
     *
     * @param paramMap 流程关联参数
     * @return java.util.Map<java.lang.String , java.lang.Object>
     * @author cdfan
     * @date 2020/8/11 14:40
     */
    List<Map<String,Object>> queryBusiness(Map<String, String> paramMap);

    /**
     * 功能描述: 流程关联数据
     *
     * @param paramMap 流程关联参数
     * @author cdfan
     * @date 2020/8/11 15:12
     */
    void deleteBusiness(Map<String, String> paramMap);

    /**
     * 功能描述: 新增流程关联数据
     *
     * @param procId 流程定义id
     * @param businessId 业务id（菜单id）
     * @author cdfan
     * @date 2020/8/11 15:17
     */
    void addBusiness(@Param("procId") String procId, @Param("businessId") Integer businessId);

    /**
     * 功能描述: 根据菜单编码获取当前业务关联的流程定义id
     *
     * @param menuCode 菜单编码
     * @return java.lang.String
     * @author cdfan
     * @date 2020/8/19 15:14
     */
    Set<String> getBusinessRelation(@Param("menuCode") String menuCode);

    /**
     * 功能描述: 批量新增流程关联业务数据
     *
     * @param actProcess 流程管理对象
     * @author cdfan
     * @date 2020/9/11 10:02
     */
    void addBusinessBatch(@Param("actProcess") ActProcess actProcess);

    /**
     * 功能描述: 根据关联业务id删除关联业务数据
     *
     * @param businessList 关联业务数据map
     * @author cdfan
     * @date 2020/9/11 11:07
     */
    void deleteBusinessById(@Param("businessList") List<Map<String,Object>> businessList);
}
