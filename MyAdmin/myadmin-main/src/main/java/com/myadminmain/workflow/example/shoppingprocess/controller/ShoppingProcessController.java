package com.myadminmain.workflow.example.shoppingprocess.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.common.resultdata.ResultData;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.workflow.example.shoppingprocess.entity.ShoppingProcess;
import com.myadminmain.workflow.example.shoppingprocess.service.ShoppingProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-09-17
 * @description: 购物流程管理 前端控制器
 */
@RestController
@RequestMapping("/shoppingProcess")
@Api(tags = "ShoppingProcessController", description = "购物流程操作api")
public class ShoppingProcessController extends BaseController {

    @Autowired
    private ShoppingProcessService shoppingProcessService;

    /**
     * 功能描述: 根据主键查询购物流程
     *
     * @param shopId 购物流程id
     * @return com.common.resultdata.ResultData<com.myadminmain.workflow.example.shoppingprocess.entity.ShoppingProcess>
     * @author cdfan
     * @date 2020-09-17
     */
    @BussinessLog("购物流程-单记录查询")
    @ApiOperation(value = "购物流程-单记录查询", notes = "根据主键查询购物流程")
    @ApiImplicitParam(name = "shopId", value = "购物流程id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/shoppingProcessInfo/{shopId}", method = RequestMethod.GET)
    public ResultData<ShoppingProcess> get(@PathVariable("shopId") Integer shopId) {
        return new ResultData<ShoppingProcess>(shoppingProcessService.getById(shopId));
    }

    /**
     * 功能描述: 查询所有购物流程
     *
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.workflow.example.shoppingprocess.entity.ShoppingProcess>>
     * @author cdfan
     * @date 2020-09-17
     */
    @Permission("shoppingProcess_query")
    @BussinessLog("购物流程-列表查询")
    @ApiOperation(value = "购物流程-列表查询", notes = "查询所有购物流程")
    @RequestMapping(value = "/shoppingProcessInfo", method = RequestMethod.GET)
    public ResultData<List<ShoppingProcess>> list() {
        return new ResultData<List<ShoppingProcess>>(shoppingProcessService.list());
    }

    /**
     * 功能描述: 分页查询购物流程数据
     *
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.workflow.example.shoppingprocess.entity.ShoppingProcess>>
     * @author cdfan
     * @date 2020-09-17
     */
    @Permission("shoppingProcess_query")
    @BussinessLog("购物流程-分页查询")
    @ApiOperation(value = "购物流程-分页查询", notes = "分页查询购物流程")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "shopProcState", value = "购物流程状态,具体参考业务字典", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "goodsCode", value = "商品编码", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/shoppingProcessInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<ShoppingProcess>> page(
            @RequestParam(value = "shopProcState", required = false) String shopProcState,
            @RequestParam(value = "goodsCode", required = false) String goodsCode) {
        Page<ShoppingProcess> page = this.defaultPage(ShoppingProcess.class);
        return new ResultData<IPage<ShoppingProcess>>(shoppingProcessService.pageInfo(page, shopProcState, goodsCode));
    }

    /**
     * 功能描述: 查询购物流程数据的数量
     *
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-09-17
     */
    @Permission("shoppingProcess_query")
    @BussinessLog("购物流程-数量查询")
    @ApiOperation(value = "购物流程-数量查询", notes = "查询购物流程数据的数量")
    @RequestMapping(value = "/shoppingProcessInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count() {
        return new ResultData<Integer>(shoppingProcessService.count());
    }

    /**
     * 功能描述: 新增购物流程
     *
     * @param shoppingProcess 购物流程实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-17
     */
    @Permission("shoppingProcess_add")
    @BussinessLog("购物流程-新增")
    @ApiOperation(value = "购物流程-新增", notes = "新增购物流程")
    @ApiImplicitParam(name = "shoppingProcess", value = "购物流程实体对象", required = true, dataType = "ShoppingProcess", paramType = "body")
    @RequestMapping(value = "/shoppingProcessInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody ShoppingProcess shoppingProcess) {
        shoppingProcessService.saveShoppingProcess(shoppingProcess);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改购物流程，对象中必须有主键
     *
     * @param shoppingProcess 购物流程实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-17
     */
    @Permission("shoppingProcess_edit")
    @BussinessLog("购物流程-修改")
    @ApiOperation(value = "购物流程-修改", notes = "修改购物流程，对象中必须有主键")
    @ApiImplicitParam(name = "shoppingProcess", value = "购物流程实体对象", required = true, dataType = "ShoppingProcess", paramType = "body")
    @RequestMapping(value = "/shoppingProcessInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody ShoppingProcess shoppingProcess) {
        shoppingProcessService.updateShoppingProcess(shoppingProcess);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除购物流程
     *
     * @param shopId 购物流程id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-17
     */
    @Permission("shoppingProcess_delete")
    @BussinessLog("购物流程-删除")
    @ApiOperation(value = "购物流程-删除", notes = "根据主键删除购物流程")
    @ApiImplicitParam(name = "shopId", value = "购物流程id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/shoppingProcessInfo/{shopId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("shopId") Integer shopId) {
        shoppingProcessService.deleteShoppingProcess(shopId);
        return SUCCESS;
    }

    /**
     * 功能描述: 购物流程任务处理
     *
     * @param shoppingProcess 购物流程实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020/9/18 10:35
     */
    @BussinessLog("购物流程-任务处理")
    @ApiOperation(value = "购物流程-任务处理", notes = "购物流程任务处理")
    @ApiImplicitParam(name = "shoppingProcess", value = "购物流程实体对象", required = true, dataType = "ShoppingProcess", paramType = "body")
    @RequestMapping(value = "/handleTask", method = RequestMethod.POST)
    public ResultData handleTask(@RequestBody ShoppingProcess shoppingProcess) {
        shoppingProcessService.handleTask(shoppingProcess);
        return SUCCESS;
    }

}
