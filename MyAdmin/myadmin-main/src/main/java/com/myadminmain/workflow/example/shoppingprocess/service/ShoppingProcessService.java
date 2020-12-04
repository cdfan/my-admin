package com.myadminmain.workflow.example.shoppingprocess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.workflow.example.shoppingprocess.entity.ShoppingProcess;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-17
 * @description: 购物流程表 service接口
 */
public interface ShoppingProcessService extends IService<ShoppingProcess> {

    /**
     * 功能描述: 分页查询购物流程
     *
     * @param page 分页对象
     * @param shopProcState 购物流程状态,具体参考业务字典
     * @param goodsCode 商品编码
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.example.shoppingprocess.entity.ShoppingProcess>
     * @author cdfan
     * @date 2020-09-17
     */
    IPage<ShoppingProcess> pageInfo(IPage<ShoppingProcess> page, String shopProcState, String goodsCode);

    /**
     * 功能描述: 新增购物流程
     *
     * @param shoppingProcess 购物流程实体对象
     * @author cdfan
     * @date 2020/9/17 16:30
     */
    void saveShoppingProcess(ShoppingProcess shoppingProcess);

    /**
     * 功能描述: 修改购物流程，对象中必须有主键
     *
     * @param shoppingProcess 购物流程实体对象
     * @author cdfan
     * @date 2020/9/17 17:37
     */
    void updateShoppingProcess(ShoppingProcess shoppingProcess);

    /**
     * 功能描述: 购物流程任务处理
     *
     * @param shoppingProcess 购物流程实体对象
     * @author cdfan
     * @date 2020/9/18 10:35
     */
    void handleTask(ShoppingProcess shoppingProcess);

    /**
     * 功能描述: 根据主键删除购物流程
     *
     * @param shopId 购物流程id
     * @author cdfan
     * @date 2020/9/21 10:34
     */
    void deleteShoppingProcess(Integer shopId);
}
