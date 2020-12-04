package com.myadminmain.sys.addr.province.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.sys.addr.province.entity.AddrProvince;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 省份地址表 service接口
 */
public interface AddrProvinceService extends IService<AddrProvince> {

    /**
     * 功能描述: 分页查询省份地址
     *
     * @param page 分页对象
     * @param provinceCode 省份代码
     * @param provinceName 省份名称
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.addr.province.entity.AddrProvince>
     * @author cdfan
     * @date 2020-09-16
     */
    IPage<AddrProvince> pageInfo(IPage<AddrProvince> page, String provinceCode, String provinceName);

    /**
     * 功能描述: 获取用于级联选择的全国省份
     *
     * @return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @author cdfan
     * @date 2020/9/16 11:34
     */
    List<Map<String,Object>> getProvinceCascader();

}
