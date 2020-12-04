package com.myadminmain.sys.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.myadminmain.sys.dept.entity.Dept;
import com.myadminmain.sys.role.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cdfan
 * @date 2020-03-22
 * @description: 系统用户表 实体类
 * @version: 1.0
 */
@TableName("sys_user")
@ApiModel(description = "系统用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "账号")
    @TableField("user_code")
    private String userCode;

    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "性别:0[女]，1[男]")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "用户头像url")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "状态:0[禁用]，1[启用]")
    @TableField("state")
    private String state;

    @ApiModelProperty(value = "主题颜色")
    @TableField("theme_color")
    private String themeColor;

    @ApiModelProperty(value = "主题大小")
    @TableField("theme_size")
    private String themeSize;

    @ApiModelProperty(value = "是否开启标签页：0[否]，1[是]")
    @TableField("tags_view")
    private String tagsView;

    @ApiModelProperty(value = "是否显示侧边栏logo：0[否]，1[是]")
    @TableField("sidebar_logo")
    private String sidebarLogo;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "用户所拥有的角色")
    @TableField(exist = false)
    private List<Role> roles;

    @ApiModelProperty(value = "用户所拥有的部门")
    @TableField(exist = false)
    private List<Dept> depts;

    @ApiModelProperty(value = "用户所拥有角色的角色id集合")
    @TableField(exist = false)
    private List<Integer> roleIds;

    @ApiModelProperty(value = "用户所拥有部门的部门id集合")
    @TableField(exist = false)
    private List<Integer> deptIds;

    @ApiModelProperty(value = "修改邮箱时的验证码id，缓存中的key")
    @TableField(exist = false)
    private String emailCodeId;

    @ApiModelProperty(value = "用户历史头像")
    @TableField(exist = false)
    private List<String> avatarHistory;


    public String getSidebarLogo() {
        return sidebarLogo;
    }

    public void setSidebarLogo(String sidebarLogo) {
        this.sidebarLogo = sidebarLogo;
    }

    public List<String> getAvatarHistory() {
        return avatarHistory;
    }

    public void setAvatarHistory(List<String> avatarHistory) {
        this.avatarHistory = avatarHistory;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getThemeSize() {
        return themeSize;
    }

    public void setThemeSize(String themeSize) {
        this.themeSize = themeSize;
    }

    public String getTagsView() {
        return tagsView;
    }

    public void setTagsView(String tagsView) {
        this.tagsView = tagsView;
    }

    public String getEmailCodeId() {
        return emailCodeId;
    }

    public void setEmailCodeId(String emailCodeId) {
        this.emailCodeId = emailCodeId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Dept> getDepts() {
        return depts;
    }

    public void setDepts(List<Dept> depts) {
        this.depts = depts;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", state='" + state + '\'' +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
