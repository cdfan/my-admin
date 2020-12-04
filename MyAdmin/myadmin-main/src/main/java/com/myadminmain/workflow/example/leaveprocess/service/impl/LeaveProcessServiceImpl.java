package com.myadminmain.workflow.example.leaveprocess.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myadminmain.core.activiti.constant.ActConstant;
import com.myadminmain.sys.user.entity.User;
import com.myadminmain.sys.user.service.UserService;
import com.myadminmain.workflow.common.entity.NextTaskUser;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exception.MyAdminException;
import com.myadminmain.core.shiro.ShiroUtil;
import com.myadminmain.workflow.common.entity.HandleTaskData;
import com.myadminmain.workflow.common.service.WorkflowService;
import com.myadminmain.workflow.common.service.impl.WorkflowServiceImpl;
import com.myadminmain.workflow.example.leaveprocess.dao.LeaveProcessMapper;
import com.myadminmain.workflow.example.leaveprocess.entity.LeaveProcess;
import com.myadminmain.workflow.example.leaveprocess.service.LeaveProcessService;
import com.myadminmain.workflow.process.service.ActProcessService;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-08-17
 * @description: 请假审批流程演示表 service实现类
 */
@Service
public class LeaveProcessServiceImpl extends ServiceImpl<LeaveProcessMapper, LeaveProcess>
    implements LeaveProcessService {

    @Autowired
    ActProcessService actProcessService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    UserService userService;

    @Override
    public IPage<LeaveProcess> pageInfo(IPage<LeaveProcess> page, String leaveType, String procState) {
        IPage<LeaveProcess> levaePage =
            this.baseMapper.pageInfo(page, leaveType, procState, ShiroUtil.getUser().getUserId());
        List<LeaveProcess> levaeList = levaePage.getRecords();
        List<String> procInstIds = levaeList.stream().map(LeaveProcess::getProcInstId).collect(Collectors.toList());
        if (procInstIds.size() > 0) {
            // 获取当前申请人正在执行的任务
            List<Task> activeTask = taskService.createTaskQuery().active().processInstanceIdIn(procInstIds).list();
            Map<String, Task> taskHashMap = new HashMap<>();
            for (Task task : activeTask) {
                taskHashMap.put(task.getProcessInstanceId(), task);
            }
            // 设置所属流程名称
            List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
                .processInstanceIds(new HashSet<String>(procInstIds)).list();
            Map<String, String> procNameMap = new HashMap<>();
            for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
                procNameMap.put(historicProcessInstance.getId(), historicProcessInstance.getProcessDefinitionName()
                    + ".v." + historicProcessInstance.getProcessDefinitionVersion());
            }
            for (LeaveProcess leaveProcess : levaeList) {
                // 设置当前任务
                if (ObjectUtils.isNotEmpty(taskHashMap.get(leaveProcess.getProcInstId()))) {
                    leaveProcess.setTaskId(taskHashMap.get(leaveProcess.getProcInstId()).getId());
                    leaveProcess.setTaskName(taskHashMap.get(leaveProcess.getProcInstId()).getName());
                } else {
                    leaveProcess.setTaskName(leaveProcess.getProcStateName());
                }
                leaveProcess.setProcName(procNameMap.get(leaveProcess.getProcInstId()));
            }

        }
        return levaePage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLeaveProcess(LeaveProcess leaveProcess) {
        leaveProcess.setUserId(ShiroUtil.getUser().getUserId());
        // 新增时默认为为提交状态
        leaveProcess.setProcState("1");
        this.save(leaveProcess);
        // 获取当前业务关联的流程定义id
        if (ObjectUtils.isEmpty(leaveProcess.getProcDefId())) {
            throw new MyAdminException("当前业务为关联流程，请关联后重试！");
        }

        // -------------启动流程-----------
        Map<String, Object> variables = new HashMap<>();
        // 设置流程启动人员，及下一节点办理人
        Authentication.setAuthenticatedUserId(ShiroUtil.getUser().getUserCode());
        variables.put("applyUser", ShiroUtil.getUser().getUserCode());
        // 设置流程默认走向条件
        variables.put(ActConstant.RESULT_PREFIX + ActConstant.TEMP_RESULT_SUFFIX, true);
        // 请假天数
        variables.put("leaveDay", leaveProcess.getLeaveDay());
        // 设置业务id
        String businessKey = LeaveProcess.BUSINESS_KEY_PREFIX + ":" + leaveProcess.getLeaveId();
        ProcessInstance processInstance =
            runtimeService.startProcessInstanceById(leaveProcess.getProcDefId(), businessKey, variables);
        // 更新业务对象
        UpdateWrapper<LeaveProcess> updateWrapper = new UpdateWrapper<LeaveProcess>()
            .set("proc_inst_id", processInstance.getId()).eq("leave_id", leaveProcess.getLeaveId());
        this.update(updateWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLeaveProcess(LeaveProcess leaveProcess) {
        if (ObjectUtils.isEmpty(leaveProcess.getLeaveId())) {
            throw new MyAdminException("修改对象中必须存在主键");
        }
        LeaveProcess oldLeaveProcess = this.getById(leaveProcess.getLeaveId());
        // 修改数据对象
        this.updateById(leaveProcess);
        if (ObjectUtils.isNotEmpty(oldLeaveProcess.getLeaveDay())
            && !oldLeaveProcess.getLeaveDay().equals(leaveProcess.getLeaveDay())) {
            // 修改流程中的变量
            Execution execution =
                runtimeService.createExecutionQuery().processInstanceId(leaveProcess.getProcInstId()).singleResult();
            Map<String, Object> variables = new HashMap<>();
            variables.put("leaveDay", leaveProcess.getLeaveDay());
            runtimeService.setVariables(execution.getId(), variables);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLeaveProcess(Integer leaveId) {
        LeaveProcess leaveProcess = this.getById(leaveId);
        // 删除业务数据
        this.removeById(leaveId);
        // 删除流程实例
        workflowService.deleteProcessInfo(leaveProcess.getProcInstId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleTask(HandleTaskData handleTaskData) {
        if (ObjectUtils.isEmpty(handleTaskData.getBusinessId())
            && ObjectUtils.isEmpty(handleTaskData.getBusinessKey())) {
            throw new MyAdminException("办理失败，未获取业务标识");
        }
        if (ObjectUtils.isEmpty(handleTaskData.getTaskId())) {
            throw new MyAdminException("办理失败，未获取到任务");
        }
        // 由于需要支持并行请假流程，所以需要对并行节点进行对应操作（获取并行节点的结果流程变量并设置对应的值）
        Map<String,Object> variable = this.getTaskVariable(handleTaskData);
        // 办理任务
        workflowService.handleTask(handleTaskData, variable);
        String businessKey;
        Integer businessId;
        if (ObjectUtils.isNotEmpty(handleTaskData.getBusinessKey())) {
            businessKey = handleTaskData.getBusinessKey();
            if (ObjectUtils.isEmpty(handleTaskData.getBusinessId())) {
                businessId = Integer.parseInt(businessKey.replace(LeaveProcess.BUSINESS_KEY_PREFIX + ":", ""));
            } else {
                businessId = handleTaskData.getBusinessId();
            }
        } else {
            businessId = handleTaskData.getBusinessId();
            businessKey = LeaveProcess.BUSINESS_KEY_PREFIX + ":" + businessId;
        }
        // 判断流程是否结束
        ProcessInstance pi =
            runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        LeaveProcess leaveProcess = this.getById(businessId);
        UpdateWrapper<LeaveProcess> updateWrapper;
        if (ObjectUtils.isEmpty(pi)) {
            // 流程结束了，修改业务状态
            // 判断是正常结束还是作废结束
            if (handleTaskData.getResult()) {
                // 正常结束
                updateWrapper = new UpdateWrapper<LeaveProcess>().set("proc_state", "4").eq("leave_id", businessId);
            } else {
                // 作废结束
                updateWrapper = new UpdateWrapper<LeaveProcess>().set("proc_state", "6").eq("leave_id", businessId);
            }
            this.update(updateWrapper);
        } else {
            // 判断当前业务的状态,如果是通过
            if (handleTaskData.getResult() && !"2".equals(leaveProcess.getProcState())) {
                updateWrapper = new UpdateWrapper<LeaveProcess>().set("proc_state", "2").eq("leave_id", businessId);
                this.update(updateWrapper);
            }
            // 如果审核不通过
            if (!handleTaskData.getResult()) {
                updateWrapper = new UpdateWrapper<LeaveProcess>().set("proc_state", "3").eq("leave_id", businessId);
                this.update(updateWrapper);
            }
        }

    }

    /**
     * 功能描述: 获取并行节点需要设置的流程变量
     *
     * @param handleTaskData HandleTaskData
     * @return java.util.Map<java.lang.String , java.lang.Object>
     * @author cdfan
     * @date 2020/9/11 16:55
     */
    private Map<String, Object> getTaskVariable(HandleTaskData handleTaskData) {
        Map<String, Object> variable = null;
        Task task = taskService.createTaskQuery().taskId(handleTaskData.getTaskId()).singleResult();
        if ("leaveParallel_managerApproval".equals(task.getTaskDefinitionKey())){
            // 并行请假流程，经理审批节点
            variable = new HashMap<>();
            variable.put("$result_manager", handleTaskData.getResult());
        }else if("leaveParallel_personnelApproval".equals(task.getTaskDefinitionKey())){
            // 并行请假流程，人事审批节点
            variable = new HashMap<>();
            variable.put("$result_personnel", handleTaskData.getResult());
        }else{
            // 其他流程节点,当结果变量不为空时
            if(handleTaskData.getResult() != null){
                variable = new HashMap<>();
                variable.put(ActConstant.RESULT_PREFIX, handleTaskData.getResult());
            }
        }
        return variable;
    }

    @Override
    public void revokeLeaveProcess(Integer leaveId) {
        LeaveProcess leaveProcess = this.getById(leaveId);
        // 删除流程实例
        ProcessInstance processInstance =
            runtimeService.createProcessInstanceQuery().processInstanceId(leaveProcess.getProcInstId()).singleResult();
        if (ObjectUtils.isNotEmpty(processInstance)) {
            runtimeService.deleteProcessInstance(leaveProcess.getProcInstId(), "请假审批流撤销");
        }
        // 修改业务状态
        UpdateWrapper<LeaveProcess> updateWrapper =
            new UpdateWrapper<LeaveProcess>().set("proc_state", "5").eq("leave_id", leaveId);
        this.update(updateWrapper);
    }

    @Override
    public LeaveProcess getLeaveProcessById(Integer leaveId) {
        LeaveProcess leaveProcess = this.getById(leaveId);
        User user = userService.getById(leaveProcess.getUserId());
        leaveProcess.setUserName(user.getUserName());
        return leaveProcess;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void commitLeaveProcess(LeaveProcess leaveProcess) {
        // 判断是新增还是修改
        if (ObjectUtils.isEmpty(leaveProcess.getLeaveId())) {
            // 新增
            this.saveLeaveProcess(leaveProcess);
        } else {
            // 修改
            this.updateLeaveProcess(leaveProcess);
        }
        LeaveProcess newLeaveProcess = this.getById(leaveProcess.getLeaveId());
        // 构建推动任务所需对象
        HandleTaskData handleTaskData = new HandleTaskData();
        if (ObjectUtils.isEmpty((leaveProcess.getTaskId()))) {
            // 在新增或修改的时候提交任务节点流程实例中只能有一个任务
            Task activeTask = taskService.createTaskQuery().active().processInstanceId(newLeaveProcess.getProcInstId())
                .singleResult();
            if (ObjectUtils.isEmpty(activeTask)) {
                throw new MyAdminException("未获取到活动的任务");
            }
            handleTaskData.setTaskId(activeTask.getId());
        } else {
            handleTaskData.setTaskId(leaveProcess.getTaskId());
        }
        handleTaskData.setResult(true);
        // 获取下一任务办理人
        NextTaskUser nextTaskUser = workflowService.nextTaskUser(newLeaveProcess.getProcInstId());
        if (nextTaskUser.getHasNextUser()) {
            if(ObjectUtils.isEmpty(leaveProcess.getNextTaskUser())){
                // 设置默认下一任务办理人
                handleTaskData.setUser(nextTaskUser.getUserCodes());
            }else{
                // 设置指定下一任务办理人
                handleTaskData.setUser(leaveProcess.getNextTaskUser());
            }
        }
        handleTaskData.setComment(null);
        handleTaskData.setBusinessKey(null);
        handleTaskData.setBusinessId(newLeaveProcess.getLeaveId());
        // 推动任务
        this.handleTask(handleTaskData);
    }

}
