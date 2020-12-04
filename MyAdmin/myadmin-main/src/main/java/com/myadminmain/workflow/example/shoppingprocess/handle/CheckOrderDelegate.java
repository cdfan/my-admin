package com.myadminmain.workflow.example.shoppingprocess.handle;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.myadminmain.workflow.example.shoppingprocess.entity.ShoppingProcess;
import com.myadminmain.workflow.example.shoppingprocess.service.ShoppingProcessService;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/9/17
 * @description: 购物流程，订单校验任务处理类
 */
@Component
public class CheckOrderDelegate implements JavaDelegate {

    /**
     * 业务服务实例对象
     */
    @Autowired
    private ShoppingProcessService shoppingProcessService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // 这里直接从流程中获取商品数量进行判断，如果数量超过10那么就超过库存，直接流转到下单失败，否则验证通过
        Integer goodsCount = (Integer) execution.getVariable("goodsCount");
        String message;
        Boolean result;
        if (ObjectUtils.isEmpty(goodsCount) || goodsCount==0){
            result = false;
            message = "未获取到商品数量";
        }else if(goodsCount <= 10){
            result = true;
            message = "商品校验通过";
        }else{
            result = false;
            message = "商品校验失败，购买数量超过库存数量";
        }
        // 设置流程变量
        execution.setVariable("$result", result);
        // 设置任务结果说明
        Task task = execution.getEngineServices().getTaskService().createTaskQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
        execution.getEngineServices().getTaskService().addComment(task.getId(), execution.getProcessInstanceId(),message);
        // 如果校验不通过则更新业务状态，将状态改为下单失败
        if(!result){
            String businessId = execution.getProcessBusinessKey().replace(ShoppingProcess.BUSINESS_KEY_PREFIX + ":", "");
            shoppingProcessService.update(new UpdateWrapper<ShoppingProcess>().set("shop_proc_state","6").eq("shop_id", businessId));
        }
    }
}
