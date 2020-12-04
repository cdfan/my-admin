package com.myadminmain.tools.generate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.tools.generate.entity.Generate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myadminmain.tools.generate.entity.TablesInfo;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-07-06
 * @description: 代码生成配置表 service接口
 */
public interface GenerateService extends IService<Generate> {

    /**
     * 功能描述: 分页查询代码生成配置
     * 
     * @param page 分页对象
     * @param tableName 表名称
     * @param modelName 模块名，能代表这个模块的名称。例如用户表的模块名就是用户
     * @param author 作者
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.tools.generate.entity.Generate>
     * @author cdfan
     * @date 2020-07-06
     */
    IPage<Generate> pageInfo(IPage<Generate> page, String tableName, String modelName, String author);

    /**
     * 功能描述: 查询数据库中的表信息
     * 
     * @return java.util.List<com.myadminmain.tools.generate.entity.TablesInfo>
     * @author cdfan
     * @date 2020/7/7 10:07
     */
    List<TablesInfo> getTablesInfo();

    /**
     * 功能描述: 下载以及生成代码
     * 
     * @param generateId 代码配置id
     * @author cdfan
     * @date 2020/7/7 23:00
     */
    void downloadCode(Integer generateId);

    /**
     * 功能描述: 预览生成代码
     * 
     * @param generateId 代码生成配置id
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @author cdfan
     * @date 2020/7/9 22:38
     */
    Map<String, String> previewCode(Integer generateId);
}
