package com.myadminmain.monitor.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.common.resultdata.ResultData;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.exception.MyAdminException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.monitor.log.entity.OperationLog;
import com.myadminmain.monitor.log.service.OperationLogService;

import java.util.Arrays;
import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-06-17
 * @description: 操作日志管理 前端控制器
 */
@RestController
@RequestMapping("/operationLog")
@Api(tags = "OperationLogController", description = "操作日志操作api")
public class OperationLogController extends BaseController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 功能描述: 根据主键查询操作日志
     * 
     * @param operationLogId 操作日志id
     * @return com.common.resultdata.ResultData<com.myadminmain.monitor.log.entity.OperationLog>
     * @author cdfan
     * @date 2020-06-17
     */
    @Permission("operationLog_query")
    @BussinessLog("操作日志-单记录查询")
    @ApiOperation(value = "操作日志-单记录查询", notes = "根据主键查询操作日志")
    @ApiImplicitParam(name = "operationLogId", value = "操作日志id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/operationLogInfo/{operationLogId}", method = RequestMethod.GET)
    public ResultData<OperationLog> get(@PathVariable("operationLogId") Integer operationLogId) {
        return new ResultData<OperationLog>(operationLogService.getById(operationLogId));
    }

    /**
     * 功能描述: 查询所有操作日志
     * 
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.monitor.log.entity.OperationLog>>
     * @author cdfan
     * @date 2020-06-17
     */
    @Permission("operationLog_query")
    @BussinessLog("操作日志-列表查询")
    @ApiOperation(value = "操作日志-列表查询", notes = "查询所有操作日志")
    @RequestMapping(value = "/operationLogInfo", method = RequestMethod.GET)
    public ResultData<List<OperationLog>> list() {
        return new ResultData<List<OperationLog>>(operationLogService.list());
    }

    /**
     * 功能描述: 分页查询操作日志
     * 
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.monitor.log.entity.OperationLog>>
     * @author cdfan
     * @date 2020-06-17
     */
    @Permission("operationLog_query")
    @BussinessLog("操作日志-分页查询")
    @ApiOperation(value = "操作日志-分页查询", notes = "分页查询操作日志")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "userCode", value = "用户账号", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "logType", value = "日志类型：0[异常操作日志]、1[正常操作日志]", dataType = "String",
            paramType = "query"),
        @ApiImplicitParam(name = "businessName", value = "业务名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "createTime", value = "创建时间,操作时间，格式为yyyy-MM-dd,yyyy-MM-dd", dataType = "String",
            paramType = "query")})
    @RequestMapping(value = "/operationLogInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<OperationLog>> page(@RequestParam(value = "userCode", required = false) String userCode,
        @RequestParam(value = "logType", required = false) String logType,
        @RequestParam(value = "businessName", required = false) String businessName,
        @RequestParam(value = "createTime", required = false) String createTime) {
        IPage<OperationLog> page = this.defaultPage(OperationLog.class);
        IPage<OperationLog> list = operationLogService.pageInfo(page, userCode, logType, businessName, createTime);
        return new ResultData<IPage<OperationLog>>(list);
    }

    /**
     * 功能描述: 查询操作日志数据的数量
     * 
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-06-17
     */
    @Permission("operationLog_query")
    @BussinessLog("操作日志-数量查询")
    @ApiOperation(value = "操作日志-数量查询", notes = "查询操作日志数据的数量")
    @RequestMapping(value = "/operationLogInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count() {
        int count = operationLogService.count();
        return new ResultData<Integer>(count);
    }

    /**
     * 功能描述: 新增操作日志
     * 
     * @param operationLog 操作日志实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-06-17
     */
    @Permission("operationLog_add")
    @BussinessLog("操作日志-新增")
    @ApiOperation(value = "操作日志-新增", notes = "新增操作日志")
    @ApiImplicitParam(name = "operationLog", value = "操作日志实体对象", required = true, dataType = "OperationLog",
        paramType = "body")
    @RequestMapping(value = "/operationLogInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody OperationLog operationLog) {
        operationLogService.save(operationLog);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改操作日志，对象中必须有主键
     * 
     * @param operationLog 操作日志实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-06-17
     */
    @Permission("operationLog_edit")
    @BussinessLog("操作日志-修改")
    @ApiOperation(value = "操作日志-修改", notes = "修改操作日志，对象中必须有主键")
    @ApiImplicitParam(name = "operationLog", value = "操作日志实体对象", required = true, dataType = "OperationLog",
        paramType = "body")
    @RequestMapping(value = "/operationLogInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody OperationLog operationLog) {
        if(ObjectUtils.isEmpty(operationLog.getOperationLogId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        operationLogService.updateById(operationLog);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除操作日志
     * 
     * @param operationLogIds 操作日志id集合
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-06-17
     */
    @Permission("operationLog_delete")
    @BussinessLog("操作日志-删除")
    @ApiOperation(value = "操作日志-删除", notes = "根据主键删除操作日志")
    @ApiImplicitParam(name = "operationLogIds", value = "操作日志id集合", required = true, dataType = "int",
        paramType = "path")
    @RequestMapping(value = "/operationLogInfo/{operationLogIds}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("operationLogIds") Integer[] operationLogIds) {
        operationLogService.removeByIds(Arrays.asList(operationLogIds));
        return SUCCESS;
    }

}
