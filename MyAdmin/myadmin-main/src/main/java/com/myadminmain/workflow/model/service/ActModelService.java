package com.myadminmain.workflow.model.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.workflow.model.entity.ActModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-08-04
 * @description: 模型管理表 service接口
 */
public interface ActModelService extends IService<ActModel> {

    /**
     * 功能描述: 分页查询流程模型
     *
     * @param page 分页对象
     * @param modelKey 模型标识key
     * @param modelName 模型名称
     * @return com.baomidou.mybatisplus.core.metadata.IPage<ActModel>
     * @author cdfan
     * @date 2020-08-04
     */
    IPage<ActModel> pageInfo(IPage<ActModel> page, String modelKey, String modelName);

    /**
     * 功能描述: 根据模型id生成并获取对应的bpmn.xml
     *
     * @param id 模型id
     * @return java.lang.String
     * @author cdfan
     * @date 2020/8/6 16:20
     */
    String getModelFile(Integer id) throws IOException;

    /**
     * 功能描述: 根据模型id生成并获取对应的bpmn.xml
     *
     * @param id 模型id
     * @return java.lang.String
     * @author cdfan
     * @date 2020/8/6 17:12
     */
    String getModelImage(Integer id);

    /**
     * 功能描述: 删除流程模型
     *
     * @param id 模型id
     * @author cdfan
     * @date 2020/8/7 11:16
     */
    void deleteActModel(Integer id);

    /**
     * 功能描述: 下载模型资源
     *
     * @param id 模型id
     * @author cdfan
     * @date 2020/8/7 12:01
     */ 
    void downloadResource(Integer id) throws IOException;

    /**
     * 功能描述: 流程部署
     *
     * @param id 模型id
     * @author cdfan
     * @date 2020/8/7 17:18
     */
    void deploymentModel(Integer id) throws IOException;
}
