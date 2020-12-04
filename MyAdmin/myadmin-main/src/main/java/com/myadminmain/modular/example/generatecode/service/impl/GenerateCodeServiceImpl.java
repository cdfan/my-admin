package com.myadminmain.modular.example.generatecode.service.impl;

import com.myadminmain.modular.example.generatecode.entity.GenerateCode;
import com.myadminmain.modular.example.generatecode.dao.GenerateCodeMapper;
import com.myadminmain.modular.example.generatecode.service.GenerateCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.util.ToolUtil;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-08-16
 * @description: 代码生成演示表 service实现类
 */
@Service
public class GenerateCodeServiceImpl extends ServiceImpl<GenerateCodeMapper, GenerateCode> implements GenerateCodeService {

    @Override
    public IPage<GenerateCode> pageInfo(IPage<GenerateCode> page, String name, String code, String day) {
        QueryWrapper<GenerateCode> query = new QueryWrapper<GenerateCode>();
        if (StringUtils.isNotEmpty(name)) {
            query = query.eq("name", name);
        }
        if (StringUtils.isNotEmpty(code)) {
            query = query.eq("code", code);
        }
        if (StringUtils.isNotEmpty(day)) {
            query = ToolUtil.addTimeQuery(query, day, "day");
        }
        return this.page(page, query);
    }
}
