package com.myadminmain.sys.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.common.resultdata.ErrorResultData;
import com.common.resultdata.ResultData;
import com.exception.MyAdminException;
import com.myadminmain.config.properties.MyAdminProperties;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import com.myadminmain.core.cache.CacheSupport;
import com.myadminmain.core.shiro.ShiroUtil;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.sys.user.entity.User;
import com.myadminmain.sys.user.service.UserService;
import com.myadminmain.sys.user.wrapper.UserWrapper;
import com.util.FileUtil;
import com.util.LocalDateTimeUtil;
import com.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 用户管理 前端控制器
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
@Api(tags = "UserController", description = "用户操作api")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private MyAdminProperties myAdminProperties;

    /**
     * 功能描述: 根据主键查询用户
     * 
     * @param userId 用户id
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.user.entity.User>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("user_query")
    @BussinessLog("用户-单记录查询")
    @ApiOperation(value = "用户-单记录查询", notes = "根据主键查询用户")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/userInfo/{userId}", method = RequestMethod.GET)
    public ResultData<User> get(@PathVariable("userId") Integer userId) {
        return new ResultData<User>(userService.getById(userId));
    }

    /**
     * 功能描述: 查询所有用户
     * 
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.sys.user.entity.User>>
     * @author cdfan
     * @date 2020-03-22
     */
    @BussinessLog("用户-列表查询")
    @ApiOperation(value = "用户-列表查询", notes = "查询所有用户")
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ResultData<List<User>> list() {
        return new ResultData<List<User>>(userService.list());
    }

    /**
     * 功能描述: 分页查询用户
     * 
     * @param userName 用户名
     * @param userCode 用户账号
     * @param deptId 部门id
     * @param roleId 角色id
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.user.entity.User>>
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("user_query")
    @BussinessLog("用户-分页查询")
    @ApiOperation(value = "用户-分页查询", notes = "分页查询用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "userCode", value = "用户账号", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "deptId", value = "部门id", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "int", paramType = "query")})
    @RequestMapping(value = "/userInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<User>> page(@RequestParam(value = "userName", required = false) String userName,
        @RequestParam(value = "userCode", required = false) String userCode,
        @RequestParam(value = "deptId", required = false) Integer deptId,
        @RequestParam(value = "roleId", required = false) Integer roleId) {
        IPage<User> page = this.defaultPage(User.class);
        IPage<User> pageList = userService.page(page, userName, userCode, deptId, roleId);
        this.wrap(new UserWrapper(pageList.getRecords()));
        return new ResultData<IPage<User>>(pageList);
    }

    /**
     * 功能描述: 根据用户账号获取当前用户相关信息，所有用户都有权限
     * 
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.sys.user.entity.User>>
     * @author cdfan
     * @date 2020-03-22
     */
    @BussinessLog("用户信息查询")
    @ApiOperation(value = "用户信息查询", notes = "根据用户编码获取当前用户相关信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "userCode", value = "用户账号", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/userData", method = RequestMethod.GET)
    public ResultData<IPage<User>> userData(@RequestParam(value = "userCode", required = false) String userCode) {
        IPage<User> page = this.defaultPage(User.class);
        IPage<User> pageList = userService.page(page, null, userCode, null, null);
        this.wrap(new UserWrapper(pageList.getRecords()));
        return new ResultData<IPage<User>>(pageList);
    }

    /**
     * 功能描述: 查询用户数据的数量
     * 
     * @param userCode 用户编码
     * @param userId 用户id
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020/4/29 17:29
     */
    @Permission("user_query")
    @BussinessLog("用户-数量查询")
    @ApiOperation(value = "用户-数量查询", notes = "查询用户数据的数量")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userCode", value = "用户编码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "userId", value = "用户id", dataType = "int", paramType = "query")})
    @RequestMapping(value = "/userInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count(@RequestParam(value = "userCode", required = false) String userCode,
        @RequestParam(value = "userId", required = false) Integer userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("user_code", userCode);
        if (ObjectUtils.isNotEmpty(userId)) {
            queryWrapper.ne("user_id", userId);
        }
        int count = userService.count(queryWrapper);
        return new ResultData<Integer>(count);
    }

    /**
     * 功能描述: 新增用户
     * 
     * @param user 用户实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("user_add")
    @BussinessLog("用户-新增")
    @ApiOperation(value = "用户-新增", notes = "新增用户")
    @ApiImplicitParam(name = "user", value = "用户实体对象", required = true, dataType = "User", paramType = "body")
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody User user) {
        userService.saveUser(user);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改用户及其相关数据（用户所拥有部门、角色），对象中必须有主键
     * 
     * @param user 用户实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("user_edit")
    @BussinessLog("用户-修改")
    @ApiOperation(value = "用户-修改", notes = "修改用户及其相关数据（用户所拥有部门、角色），对象中必须有主键")
    @ApiImplicitParam(name = "user", value = "用户实体对象", required = true, dataType = "User", paramType = "body")
    @RequestMapping(value = "/userInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody User user) {
        if(ObjectUtils.isEmpty(user.getUserId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        userService.updateUser(user);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改用户基本信息，对象中必须有主键，所有用户都有权限
     * 
     * @param user 用户实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @BussinessLog("用户-修改")
    @ApiOperation(value = "用户-修改", notes = "修改用户基本信息")
    @ApiImplicitParam(name = "user", value = "用户实体对象", required = true, dataType = "User", paramType = "body")
    @RequestMapping(value = "/updateUserBaseInfo", method = RequestMethod.PUT)
    public ResultData updateUserBaseInfo(@RequestBody User user) {
        if (ObjectUtils.isEmpty(user.getUserId())) {
            user.setUserId(ShiroUtil.getUser().getUserId());
        }
        userService.updateById(user);
        if (StringUtils.isNotEmpty(user.getEmailCodeId())) {
            // 清理邮箱验证码
            CacheSupport.me().clearEmailCode(user.getEmailCodeId());
        }
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除用户
     * 
     * @param userId 用户id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-03-22
     */
    @Permission("user_delete")
    @BussinessLog("用户-删除")
    @ApiOperation(value = "用户-删除", notes = "根据主键删除用户")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/userInfo/{userId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("userId") Integer userId) {
        userService.removeUser(userId);
        return SUCCESS;
    }

    /**
     * 功能描述: 上传用户头像
     * 
     * @param imgFile 头像图片
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020/5/27 10:48
     */
    @BussinessLog("用户-上传头像")
    @ApiOperation(value = "用户-上传头像", notes = "上传用户头像图片")
    @ApiImplicitParam(name = "imgFile", value = "用户头像数据", required = true, dataType = "MultipartFile",
        paramType = "body")
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public ResultData<Map<String, String>> uploadAvatar(@RequestParam("img") MultipartFile imgFile) {
        if (imgFile.isEmpty()) {
            throw new MyAdminException("上传失败，请选择文件!");
        }
        String fileName = ShiroUtil.getUser().getUserCode() + LocalDateTimeUtil.getMilliByTime(LocalDateTime.now());
        Map<String, String> result = FileUtil.uploadFile(imgFile, myAdminProperties.getResourcePath(),
            myAdminProperties.getUserAvatarPath(), fileName);

        if (Boolean.valueOf(result.get("state"))) {
            userService.updateAvatar(ShiroUtil.getUser().getUserId(), result.get("url"));
            return new ResultData<Map<String, String>>(result);
        } else {
            throw new MyAdminException("上传失败");
        }
    }

    /**
     * 功能描述: 给指定邮箱，发送邮箱验证码
     * 
     * @param email 邮箱
     * @return com.common.resultdata.ResultData<java.lang.String>
     * @author cdfan
     * @date 2020/5/31 15:49
     */
    @BussinessLog("发送邮箱验证码")
    @ApiOperation(value = "发送邮箱验证码", notes = "给指定邮箱，发送邮箱验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "邮箱", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/sendEmailCode", method = RequestMethod.GET)
    public ResultData<String> sendEmailCode(@RequestParam(value = "email", required = true) String email) {
        // 生成验证码id
        String codeId = UUID.randomUUID().toString().replace("-", "");
        userService.sendEmailCode(codeId, email);
        return new ResultData<String>(codeId);
    }

    /**
     * 功能描述: 验证邮箱验证码
     * 
     * @param paramMap 验证码、验证码id集合
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020/5/31 17:42
     */
    @BussinessLog("用户-邮箱验证")
    @ApiOperation(value = "用户-邮箱验证", notes = "验证邮箱验证码")
    @ApiImplicitParam(name = "paramMap", value = "邮箱、验证码、验证码id集合", required = true, dataType = "Map",
        paramType = "body")
    @RequestMapping(value = "/userEmailValidate", method = RequestMethod.POST)
    public ResultData userEmailValidate(@RequestBody Map<String, Object> paramMap) {
        String codeId = ToolUtil.toStr(paramMap.get("codeId"));
        String code = ToolUtil.toStr(paramMap.get("code"));
        if (StringUtils.isEmpty(codeId) || StringUtils.isEmpty(code)) {
            return new ErrorResultData("验证码错误");
        }
        String cacheCode = userService.sendEmailCode(codeId, null);
        if (StringUtils.isEmpty(cacheCode)) {
            return new ErrorResultData("验证码已过期");
        } else if (!cacheCode.toLowerCase().equals(code)) {
            return new ErrorResultData("验证码错误");
        }
        return SUCCESS;
    }

    /**
     * 功能描述: 验证用户密码是否正确
     * 
     * @param paramMap 密码map
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020/6/2 22:32
     */
    @BussinessLog("用户-密码验证")
    @ApiOperation(value = "用户-密码验证", notes = "验证用户密码是否正确")
    @ApiImplicitParam(name = "paramMap", value = "密码", required = true, dataType = "Map", paramType = "body")
    @RequestMapping(value = "/validatePassword", method = RequestMethod.POST)
    public ResultData validatePassword(@RequestBody Map<String, Object> paramMap) {
        String password = ToolUtil.toStr(paramMap.get("password"));
        if (StringUtils.isEmpty(password)) {
            return new ErrorResultData("密码错误");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("user_code", ShiroUtil.getUser().getUserCode()));
        // 加密密码
        String encryptPassword = ShiroUtil.encrypt(password, user.getUserCode());
        if (!encryptPassword.equals(user.getPassword())) {
            return new ErrorResultData("密码错误");
        }
        return SUCCESS;
    }

    /**
     * 功能描述: 修改用户密码
     * 
     * @param paramMap 新密码和老密码集合
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020/6/22 14:27
     */
    @BussinessLog("用户-密码修改")
    @ApiOperation(value = "用户-密码修改", notes = "修改用户密码")
    @ApiImplicitParam(name = "paramMap", value = "新密码和老密码集合", required = true, dataType = "Map", paramType = "body")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public ResultData updatePassword(@RequestBody Map<String, Object> paramMap) {
        String newPassword = ToolUtil.toStr(paramMap.get("newPassword"));
        String oldPassword = ToolUtil.toStr(paramMap.get("oldPassword"));
        if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(oldPassword)) {
            throw new MyAdminException("密码修改失败");
        }
        userService.updatePassword(newPassword, oldPassword);
        return SUCCESS;
    }
}
