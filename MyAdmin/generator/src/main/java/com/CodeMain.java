package com;

import com.config.CodeConfig;
import com.generator.CodeGenerator;

import java.util.ArrayList;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020/2/27
 * @description: 代码生成
 */
public class CodeMain {
    public static void main(String[] args) {
        CodeConfig config = new CodeConfig();
        // 表名
        config.setTable("sys_generate");
        // 表前缀,可以不设置
        config.setTablePrefix("sys_");
        // 模块名，能代表这个模块的名称。例如用户表的模块名就是用户
        config.setModelName("代码生成配置");
        // 作者
        config.setAuthor("cdfan");
        // 输出路径，通常为项目路径E:\\project\\temp\\"+System.currentTimeMillis() E:\project\MyAdmin\myadmin-main\src\main\java"
        config.setOutputDir("E:\\project\\MyAdmin\\myadmin-main\\src\\main\\java");
        // 包名，包路径 com.myadminmain.sys.logOperation
        config.setPackagePath("com.myadminmain.tools.generate");
        // 数据库url连接
        config.setDbUrl(
            "jdbc:mysql://localhost:3306/myadmin_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Hongkong");
        // 数据库用户名
        config.setDbUsername("root");
        // 数据库密码
        config.setDbPassword("xxxx");
        // 查询字段
        ArrayList<String> queryField = new ArrayList<>();
        queryField.add("tableName");
        queryField.add("author");
        queryField.add("modelName");
        config.setQueryFields(queryField);
        // 新增修改form表单是否每条数据单独占一行
        config.setFormLayout("1");
        CodeGenerator.generate(config);
    }
}
