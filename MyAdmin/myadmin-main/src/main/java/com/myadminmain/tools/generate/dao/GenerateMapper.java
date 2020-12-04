package com.myadminmain.tools.generate.dao;

import com.myadminmain.tools.generate.entity.Generate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myadminmain.tools.generate.entity.TablesInfo;

import java.util.List;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-07-06
 * @description: 代码生成配置表 Mapper 接口
 */
public interface GenerateMapper extends BaseMapper<Generate> {

    /**
     * 功能描述: 查询数据库中的表信息
     * 
     * @return java.util.List<com.myadminmain.tools.generate.entity.TablesInfo>
     * @author cdfan
     * @date 2020/7/7 10:08
     */
    List<TablesInfo> getTablesInfo();

}
