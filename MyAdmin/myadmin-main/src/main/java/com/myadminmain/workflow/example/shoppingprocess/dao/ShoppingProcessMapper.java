package com.myadminmain.workflow.example.shoppingprocess.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.workflow.example.leaveprocess.entity.LeaveProcess;
import com.myadminmain.workflow.example.shoppingprocess.entity.ShoppingProcess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-17
 * @description: 购物流程表 Mapper 接口
 */
public interface ShoppingProcessMapper extends BaseMapper<ShoppingProcess> {

    /**
     * 功能描述: 分页查询购物流程数据
     *
     * @param page 分页对象
     * @param shopProcState 流程状态
     * @param goodsCode 商品编码
     * @param userId 下单人id
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.example.leaveprocess.entity.LeaveProcess>
     * @author cdfan
     * @date 2020/9/17 17:00
     */
    IPage<ShoppingProcess> pageInfo(@Param("page") IPage<ShoppingProcess> page, @Param("shopProcState") String shopProcState, @Param("goodsCode") String goodsCode, @Param("userId") Integer userId);
}
