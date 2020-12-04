package com.myadminmain.workflow.example.shoppingprocess.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
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
import com.myadminmain.workflow.example.shoppingprocess.dao.ShoppingProcessMapper;
import com.myadminmain.workflow.example.shoppingprocess.entity.ShoppingProcess;
import com.myadminmain.workflow.example.shoppingprocess.service.ShoppingProcessService;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-17
 * @description: 购物流程表 service实现类
 */
@Service
public class ShoppingProcessServiceImpl extends ServiceImpl<ShoppingProcessMapper, ShoppingProcess>
    implements ShoppingProcessService {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    WorkflowService workflowService;

    @Override
    public IPage<ShoppingProcess> pageInfo(IPage<ShoppingProcess> page, String shopProcState, String goodsCode) {
        IPage<ShoppingProcess> shoppingPage =
            this.baseMapper.pageInfo(page, shopProcState, goodsCode, ShiroUtil.getUser().getUserId());
        List<ShoppingProcess> shoppingList = shoppingPage.getRecords();

        List<String> procInstIds =
            shoppingList.stream().map(ShoppingProcess::getProcInstId).collect(Collectors.toList());
        if (procInstIds.size() > 0) {
            // 获取当前正在执行的任务
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
            for (ShoppingProcess shoppingProcess : shoppingList) {
                // 设置当前任务
                if (ObjectUtils.isNotEmpty(taskHashMap.get(shoppingProcess.getProcInstId()))) {
                    shoppingProcess.setTaskId(taskHashMap.get(shoppingProcess.getProcInstId()).getId());
                    shoppingProcess.setTaskName(taskHashMap.get(shoppingProcess.getProcInstId()).getName());
                } else {
                    shoppingProcess.setTaskName(shoppingProcess.getShopProcStateName());
                }
                shoppingProcess.setProcName(procNameMap.get(shoppingProcess.getProcInstId()));
            }

        }
        return shoppingPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveShoppingProcess(ShoppingProcess shoppingProcess) {
        shoppingProcess.setUserId(ShiroUtil.getUser().getUserId());
        // 新增时默认为未下单状态
        shoppingProcess.setShopProcState("1");
        this.save(shoppingProcess);
        // 获取当前业务关联的流程定义id
        if (ObjectUtils.isEmpty(shoppingProcess.getProcDefId())) {
            throw new MyAdminException("当前业务为关联流程，请关联后重试！");
        }

        // -------------启动流程-----------
        Map<String, Object> variables = new HashMap<>();
        // 设置流程启动人员，及下一节点办理人
        Authentication.setAuthenticatedUserId(ShiroUtil.getUser().getUserCode());
        variables.put("submitUser", ShiroUtil.getUser().getUserCode());
        // 购买物品的数量，校验任务节点需要通过该节点校验
        variables.put("goodsCount", shoppingProcess.getGoodsCount());
        // 设置业务id
        String businessKey = ShoppingProcess.BUSINESS_KEY_PREFIX + ":" + shoppingProcess.getShopId();
        ProcessInstance processInstance =
            runtimeService.startProcessInstanceById(shoppingProcess.getProcDefId(), businessKey, variables);
        // 更新业务对象
        UpdateWrapper<ShoppingProcess> updateWrapper = new UpdateWrapper<ShoppingProcess>()
            .set("proc_inst_id", processInstance.getId()).eq("shop_id", shoppingProcess.getShopId());
        this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateShoppingProcess(ShoppingProcess shoppingProcess) {
        if (ObjectUtils.isEmpty(shoppingProcess.getShopId())) {
            throw new MyAdminException("修改对象中必须存在主键");
        }
        ShoppingProcess oldShoppingProcess = this.getById(shoppingProcess.getShopId());
        // 修改数据对象
        this.updateById(shoppingProcess);
        if (ObjectUtils.isNotEmpty(oldShoppingProcess.getGoodsCount())
            && !oldShoppingProcess.getGoodsCount().equals(shoppingProcess.getGoodsCount())) {
            // 修改流程中的变量
            Execution execution =
                runtimeService.createExecutionQuery().processInstanceId(shoppingProcess.getProcInstId()).singleResult();
            Map<String, Object> variables = new HashMap<>();
            variables.put("goodsCount", shoppingProcess.getGoodsCount());
            runtimeService.setVariables(execution.getId(), variables);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleTask(ShoppingProcess shoppingProcess) {
        // 推动任务所需对象
        HandleTaskData handleTaskData = new HandleTaskData();
        ShoppingProcess newShoppingProcess;
        // 判断是新增还是修改
        if (ObjectUtils.isEmpty(shoppingProcess.getShopId())) {
            // 新增
            this.saveShoppingProcess(shoppingProcess);
            // 获取任务
            newShoppingProcess = this.getById(shoppingProcess.getShopId());
            Task activeTask = taskService.createTaskQuery().active().processInstanceId(newShoppingProcess.getProcInstId())
                    .singleResult();
            handleTaskData.setTaskId(activeTask.getId());
        } else {
            if(ObjectUtils.isEmpty(shoppingProcess.getTaskId())){
                throw new MyAdminException("办理失败，未获取到任务");
            }
            // 修改
            this.updateShoppingProcess(shoppingProcess);
            handleTaskData.setTaskId(shoppingProcess.getTaskId());
            newShoppingProcess = shoppingProcess;
        }
        //更新业务流程状态, 这里由于不存在回退和撤回，直接根据正常流程走就行
        int shopProcState = Integer.parseInt(newShoppingProcess.getShopProcState()) + 1;
        this.update(new UpdateWrapper<ShoppingProcess>().set("shop_proc_state", shopProcState).eq("shop_id", newShoppingProcess.getShopId()));

        // 推动任务
        workflowService.handleTask(handleTaskData, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteShoppingProcess(Integer shopId) {
        ShoppingProcess shoppingProcess = this.getById(shopId);
        // 删除业务数据
        this.removeById(shopId);
        // 删除流程数据
        workflowService.deleteProcessInfo(shoppingProcess.getProcInstId());
    }
}
