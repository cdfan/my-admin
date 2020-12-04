package com.myadminmain.monitor.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.common.resultdata.ResultData;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.exception.MyAdminException;
import com.myadminmain.core.shiro.ShiroUtil;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.monitor.log.entity.LoginLog;
import com.myadminmain.monitor.log.service.LoginLogService;
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

import java.util.Arrays;
import java.util.List;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 登录日志管理 前端控制器
 * @version: 1.0
 */
@RestController
@RequestMapping("/loginLog")
@Api(tags = "LoginLogController", description = "登录日志操作api")
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 功能描述: 根据主键查询登录日志
     * 
     * @param loginLogId 登录日志id
     * @return com.common.resultdata.ResultData<com.myadminmain.monitor.log.entity.LoginLog>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("loginLog_query")
    @BussinessLog("登录日志-单记录查询")
    @ApiOperation(value = "登录日志-单记录查询", notes = "根据主键查询登录日志")
    @ApiImplicitParam(name = "loginLogId", value = "登录日志id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/loginLogInfo/{loginLogId}", method = RequestMethod.GET)
    public ResultData<LoginLog> get(@PathVariable("loginLogId") Integer loginLogId) {
        return new ResultData<LoginLog>(loginLogService.getById(loginLogId));
    }

    /**
     * 功能描述: 查询所有登录日志
     * 
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.monitor.log.entity.LoginLog>>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("loginLog_query")
    @BussinessLog("登录日志-列表查询")
    @ApiOperation(value = "登录日志-列表查询", notes = "查询所有登录日志")
    @RequestMapping(value = "/loginLogInfo", method = RequestMethod.GET)
    public ResultData<List<LoginLog>> list() {
        return new ResultData<List<LoginLog>>(loginLogService.list());
    }

    /**
     * 功能描述: 分页查询登录日志
     * 
     * @param userCode 用户账号
     * @param logType 日志类型
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.monitor.log.entity.LoginLog>>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("loginLog_query")
    @BussinessLog("登录日志-分页查询")
    @ApiOperation(value = "登录日志-分页查询", notes = "分页查询登录日志")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "userCode", value = "用户账号", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "logType", value = "日志类型：0[登出]、1[登录]", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "state", value = "登录状态：0[失败]、1[成功]", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "createTime", value = "创建时间,登录时间，格式为yyyy-MM-dd,yyyy-MM-dd", dataType = "String",
            paramType = "query")})
    @RequestMapping(value = "/loginLogInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<LoginLog>> page(@RequestParam(value = "userCode", required = false) String userCode,
        @RequestParam(value = "logType", required = false) String logType,
        @RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "createTime", required = false) String createTime) {
        IPage<LoginLog> page = this.defaultPage(LoginLog.class);
        IPage<LoginLog> list = loginLogService.pageInfo(page, userCode, logType, state, createTime);
        return new ResultData<IPage<LoginLog>>(list);
    }

    /**
     * 功能描述: 查询当前登录用户近10次登录
     * 
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.monitor.log.entity.LoginLog>>
     * @author cdfan
     * @date 2020-03-22
     */
    @BussinessLog("当前用户登录日志查询")
    @ApiOperation(value = "当前用户登录日志查询", notes = "查询当前登录用户近10次登录")
    @RequestMapping(value = "/userLoginLog", method = RequestMethod.GET)
    public ResultData<IPage<LoginLog>> userLoginLog() {
        IPage<LoginLog> page = this.defaultPage(LoginLog.class);
        IPage<LoginLog> list = loginLogService.page(page, new QueryWrapper<LoginLog>().eq("log_type", "1")
            .eq("user_code", ShiroUtil.getUser().getUserCode()).orderByDesc("create_time"));
        return new ResultData<IPage<LoginLog>>(list);
    }

    /**
     * 功能描述: 新增登录日志
     * 
     * @param loginLog 登录日志实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("loginLog_add")
    @BussinessLog("登录日志-新增")
    @ApiOperation(value = "登录日志-新增", notes = "新增登录日志")
    @ApiImplicitParam(name = "loginLog", value = "登录日志实体对象", required = true, dataType = "LoginLog", paramType = "body")
    @RequestMapping(value = "/loginLogInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody LoginLog loginLog) {
        loginLogService.save(loginLog);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改登录日志，对象中必须有主键
     * 
     * @param loginLog 登录日志实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("loginLog_edit")
    @BussinessLog("登录日志-修改")
    @ApiOperation(value = "登录日志-修改", notes = "、修改登录日志，对象中必须有主键")
    @ApiImplicitParam(name = "loginLog", value = "登录日志实体对象", required = true, dataType = "LoginLog", paramType = "body")
    @RequestMapping(value = "/loginLogInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody LoginLog loginLog) {
        if(ObjectUtils.isEmpty(loginLog.getLoginLogId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        loginLogService.updateById(loginLog);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除登录日志
     * 
     * @param loginLogIds 登录日志id集合
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("loginLog_delete")
    @BussinessLog("登录日志-删除")
    @ApiOperation(value = "登录日志-删除", notes = "根据主键删除登录日志")
    @ApiImplicitParam(name = "loginLogIds", value = "登录日志id集合", required = true, dataType = "int[]", paramType = "path")
    @RequestMapping(value = "/loginLogInfo/{loginLogIds}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("loginLogIds") Integer[] loginLogIds) {
        loginLogService.removeByIds(Arrays.asList(loginLogIds));
        return SUCCESS;
    }

}
