package com.myadminmain.modular.example.generatecode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.modular.example.generatecode.entity.GenerateCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-08-16
 * @description: 代码生成演示表 service接口
 */
public interface GenerateCodeService extends IService<GenerateCode> {

    /**
     * 功能描述: 分页查询代码生成演示
     *
     * @param page 分页对象
     * @param name 名称
     * @param code 编码
     * @param day 日期，格式为yyyy-MM-dd，范围日期用,号分割
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.modular.example.generatecode.entity.GenerateCode>
     * @author cdfan
     * @date 2020-08-16
     */
    IPage<GenerateCode> pageInfo(IPage<GenerateCode> page, String name, String code, String day);
}
