package com.myadminmain.workflow.common.service;

import com.myadminmain.workflow.common.entity.HandleTaskData;
import com.myadminmain.workflow.common.entity.HistoryTask;
import com.myadminmain.workflow.common.entity.NextTaskUser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/8/28
 * @description: 工作流公共操作Service
 */
public interface WorkflowService {

    /**
     * 功能描述: 根据流程实例id获取当前任务的下一任务办理人
     *
     * @param procInstId 流程实例id
     * @return com.myadminmain.workflow.common.entity.NextTaskUser
     * @author cdfan
     * @date 2020/8/26 22:31
     */ 
    NextTaskUser nextTaskUser(String procInstId);

    /**
     * 功能描述: 办理任务
     *
     * @param handleTaskData 任务处理数据
     * @param variable 办理任务时设置的变量
     * @author cdfan
     * @date 2020/8/28 14:47
     */
    void handleTask(HandleTaskData handleTaskData, Map<String, Object> variable);

    /**
     * 功能描述: 根据流程实例id获取当前流程执行任务记录
     * 
     * @param procInstId 流程实例id
     * @return java.util.List<com.myadminmain.workflow.common.entity.HistoryTask>
     * @author cdfan
     * @date 2020/9/1 17:59
     */
    List<HistoryTask> historyTask(String procInstId);

    /**
     * 功能描述: 根据流程实例id获取流转轨迹高亮流程图
     *
     * @param procInstId 流程实例id
     * @return java.lang.String
     * @author cdfan
     * @date 2020/9/2 22:41
     */
    String getHighlightTrackImage(String procInstId);

    /**
     * 功能描述: 根据流程实例id，删除流程中的数据
     *
     * @param procInstId 流程实例id
     * @author cdfan
     * @date 2020/9/9 22:48
     */
    void deleteProcessInfo(String procInstId);
}
