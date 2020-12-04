package com.myadminmain.tools.generate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-07-06
 * @description: 代码生成配置表 实体类
 */
@TableName("sys_generate")
@ApiModel(description = "代码生成配置表")
public class Generate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "generate_id", type = IdType.AUTO)
    private Integer generateId;

    @ApiModelProperty(value = "表名称")
    @TableField("table_name")
    private String tableName;

    @ApiModelProperty(value = "表前缀")
    @TableField("table_prefix")
    private String tablePrefix;

    @ApiModelProperty(value = "模块名，能代表这个模块的名称。例如用户表的模块名就是用户")
    @TableField("model_name")
    private String modelName;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "输出路径，通常为项目路径")
    @TableField("output_dir")
    private String outputDir;

    @ApiModelProperty(value = "包名，包路径")
    @TableField("package_path")
    private String packagePath;

    @ApiModelProperty(value = "查询字段，多个用逗号分隔")
    @TableField("query_field")
    private String queryField;

    @ApiModelProperty(value = "表单布局1[一列]、2[两列]")
    @TableField("form_layout")
    private String formLayout;

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

    @ApiModelProperty(value = "查询字段集合")
    @TableField(exist = false)
    private List<String> queryFields;

    public List<String> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(List<String> queryFields) {
        this.queryFields = queryFields;
    }

    public Integer getGenerateId() {
        return generateId;
    }

    public void setGenerateId(Integer generateId) {
        this.generateId = generateId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getQueryField() {
        return queryField;
    }

    public void setQueryField(String queryField) {
        this.queryField = queryField;
    }

    public String getFormLayout() {
        return formLayout;
    }

    public void setFormLayout(String formLayout) {
        this.formLayout = formLayout;
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
        return "Generate{" +
        "generateId=" + generateId +
        ", tableName=" + tableName +
        ", tablePrefix=" + tablePrefix +
        ", modelName=" + modelName +
        ", author=" + author +
        ", outputDir=" + outputDir +
        ", packagePath=" + packagePath +
        ", queryField=" + queryField +
        ", formLayout=" + formLayout +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
