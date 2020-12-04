package com.myadminmain.sys.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exception.MyAdminException;
import com.myadminmain.sys.dict.entity.Dict;
import com.myadminmain.sys.dict.dao.DictMapper;
import com.myadminmain.sys.dict.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-05-25
 * @description: 系统业务字典表 service实现类
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public IPage<Dict> pageDict(IPage<Dict> page, String code, String name, String dictType, String state,
        String pcode) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(code)) {
            queryWrapper.like("code", code);
        }
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (StringUtils.isNotEmpty(dictType)) {
            queryWrapper.eq("dict_type", dictType);
        }
        if (StringUtils.isNotEmpty(state)) {
            queryWrapper.eq("state", state);
        }
        if (StringUtils.isNotEmpty(pcode)) {
            queryWrapper.eq("pcode", pcode);
        }
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDict(Integer dictId) {
        Dict dict = this.getById(dictId);
        // 删除业务字典
        this.removeById(dictId);
        // 删除对应的字典详情
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<Dict>().eq("pcode", dict.getCode());
        this.remove(dictQueryWrapper);
    }

    @Override
    public List<Dict> getDictDetails(String code) {
        List<Dict> dictList = this.list(new QueryWrapper<Dict>().eq("code", code).eq("state", "1"));
        if (dictList.size() > 0) {
            QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pcode", code).eq("state", "1").orderByAsc("order_num");
            return this.list(queryWrapper);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDict(Dict dict) {
        if (ObjectUtils.isEmpty(dict.getDictId())) {
            throw new MyAdminException("修改对象中必须存在主键");
        }
        Dict dictOld = this.getById(dict.getDictId());
        this.updateById(dict);
        // 如果字典编码修改了则一并修改字典详情
        if (ObjectUtils.isNotEmpty(dict.getCode())) {
            UpdateWrapper<Dict> updateWrapper =
                new UpdateWrapper<Dict>().set("pcode", dict.getCode()).eq("pcode", dictOld.getCode());
            this.update(updateWrapper);
        }
    }

}
