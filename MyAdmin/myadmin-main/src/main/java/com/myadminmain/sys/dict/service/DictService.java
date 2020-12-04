package com.myadminmain.sys.dict.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.sys.dict.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-05-25
 * @description: 系统业务字典表 service接口
 */
public interface DictService extends IService<Dict> {

    /**
     * 功能描述: 分页查询字典数据
     * 
     * @param page 分页对象
     * @param code 字典编码
     * @param name 字典名称
     * @param dictType 字典类型:0为父节点字典类型，1为子节点字典
     * @param state 状态:0[禁用]，1[启用]
     * @param pcode 父级字典编码
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.dict.entity.Dict>
     * @author cdfan
     * @date 2020/6/12 15:31
     */
    IPage<Dict> pageDict(IPage<Dict> page, String code, String name, String dictType, String state, String pcode);

    /**
     * 功能描述: 删除业务字典，及其字典详情
     *
     * @param dictId 业务字典id
     * @author cdfan
     * @date 2020/8/17 14:21
     */
    void deleteDict(Integer dictId);

    /**
     * 功能描述: 根据字典编码查询字典详情
     *
     * @param code 字典编码
     * @return java.util.List<com.myadminmain.sys.dict.entity.Dict>
     * @author cdfan
     * @date 2020/8/17 14:53
     */
    List<Dict> getDictDetails(String code);

    /**
     * 功能描述: 修改业务字典
     *
     * @param dict 业务字典对象
     * @author cdfan
     * @date 2020/8/19 11:14
     */
    void updateDict(Dict dict);
}
