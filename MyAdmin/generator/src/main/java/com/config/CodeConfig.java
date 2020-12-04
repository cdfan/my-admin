package com.config;

import java.util.List;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/2/27
 * @description: 代码生成配置类
 */
public class CodeConfig {

    /**
     * 输出路径，通常为项目路径
     */

    private String outputDir;

    /**
     * 作者
     */
    private String author;

    /**
     * 数据库url连接
     */
    private String dbUrl;

    /**
     * 数据库用户名
     */
    private String dbUsername;

    /**
     * 数据库密码
     */
    private String dbPassword;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 表名
     */
    private String table;

    /**
     * 包名，包路径
     */
    private String packagePath;

    /**
     * 模块名，能代表这个模块的名称。例如用户表的模块名就是用户
     */
    private String modelName;

    /**
     * 查询字段
     */
    private List<String> queryFields;

    /**
     * 新增修改的form弹出框，每条数据是否单独占一行 1，每条数据单独占一行 0，两条数据排列后占一行
     */
    private String formLayout = "1";

    /**
     * 路径名称，其实就是将实体类名大写转为-连接之后的名称
     */
    private String entityPathName;

    public String getEntityPathName() {
        return entityPathName;
    }

    public void setEntityPathName(String entityPathName) {
        this.entityPathName = entityPathName;
    }

    public String getFormLayout() {
        return formLayout;
    }

    public void setFormLayout(String formLayout) {
        this.formLayout = formLayout;
    }

    public List<String> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(List<String> queryFields) {
        this.queryFields = queryFields;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
