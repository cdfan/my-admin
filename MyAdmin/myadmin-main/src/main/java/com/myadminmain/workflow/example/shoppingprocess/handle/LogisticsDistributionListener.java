package com.myadminmain.workflow.example.shoppingprocess.handle;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/9/17
 * @description: 购物流程，物流配送监听类，设置物流配送任务的办理人
 */
@Component
public class LogisticsDistributionListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        // 设置办理人为快递配送角色
        delegateTask.addCandidateGroup("express");
        // 额外添加user22为办理人
        delegateTask.addCandidateUser("user22");
    }
}
