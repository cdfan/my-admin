package com.myadminmain.workflow.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myadminmain.workflow.process.entity.ActProcess;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-08-04
 * @description: 流程管理 service接口
 */
public interface ActProcessService {
    /**
     * 功能描述: 分页查询流程管理
     *
     * @param page 分页对象
     * @param procKey 流程标识
     * @param procName 流程名称
     * @param businessId 关联业务id
     * @return com.baomidou.mybatisplus.core.metadata.IPage<java.lang.ActProcess>
     * @author cdfan
     * @date 2020/8/10 10:52
     */
    IPage<ActProcess> pageInfo(Page<ActProcess> page, String procKey, String procName, Integer businessId);

    /**
     * 功能描述: 流程关联业务
     *
     * @param actProcess 流程管理对象
     * @author cdfan
     * @date 2020/8/11 12:15
     */
    void businessRelation(ActProcess actProcess);

    /**
     * 功能描述: 通过流程定义获取对应的bpmn.xml
     *
     * @param procId 流程定义id
     * @return java.lang.String
     * @author cdfan
     * @date 2020/8/12 11:49
     */
    String getProcessFile(String procId) throws UnsupportedEncodingException, IOException;

    /**
     * 功能描述: 通过流程定义获取对应的资源图片
     *
     * @param procId 流程定义id
     * @return java.lang.String
     * @author cdfan
     * @date 2020/8/12 14:50
     */
    String getProcessImage(String procId);

    /**
     * 功能描述: 修改流程状态
     *
     * @param actProcess 流程管理对象
     * @author cdfan
     * @date 2020/8/12 15:57
     */
    void updateProcessState(ActProcess actProcess);

    /**
     * 功能描述: 下载流程资源
     *
     * @param procId 流程定义id
     * @author cdfan
     * @date 2020/8/12 17:30
     */
    void downloadResource(String procId);

    /**
     * 功能描述: 根据流程部署id删除部署的流程
     *
     * @param deploymentId 流程部署id
     * @author cdfan
     * @date 2020/8/12 17:40
     */
    void deleteActProcess(String deploymentId);

    /**
     * 功能描述: 上传流程文件并部署
     *
     * @param bpmnFile 上传的流程文件
     * @author cdfan
     * @date 2020/8/13 16:34
     */
    void uploadDeploymentFile(MultipartFile bpmnFile) throws IOException;

    /**
     * 功能描述: 转模型，根据部署的流程创建流程模型
     *
     * @param procId 流程定义id
     * @author cdfan
     * @date 2020/8/14 14:47
     */
    void convertModel(String procId);

    /**
     * 功能描述: 根据菜单编码获取当前业务关联的流程定义id
     *
     * @param menuCode 菜单编码
     * @return java.lang.String
     * @author cdfan
     * @date 2020/8/19 15:11
     */
    Set<String> getBusinessRelation(String menuCode);

    /**
     * 功能描述: 查询所有流程定义
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @author cdfan
     * @date 2020/9/8 14:41
     */ 
    List<Map<String, String>> getProcDefList();

    /**
     * 功能描述: 根据业务菜单编码获取关联的流程定义
     *
     * @param menuCode 菜单编码
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @author cdfan
     * @date 2020/9/11 12:15
     */
    List<Map<String,String>> getRelationProcDef(String menuCode);
}
