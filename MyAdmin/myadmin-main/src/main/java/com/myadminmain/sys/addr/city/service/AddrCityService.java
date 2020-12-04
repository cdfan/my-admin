package com.myadminmain.sys.addr.city.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.sys.addr.city.entity.AddrCity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 城市地址表 service接口
 */
public interface AddrCityService extends IService<AddrCity> {

    /**
     * 功能描述: 分页查询城市地址
     *
     * @param page 分页对象
     * @param cityCode 市代码
     * @param cityName 市名称
     * @param provinceCode 父级省代码
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.addr.city.entity.AddrCity>
     * @author cdfan
     * @date 2020-09-16
     */
    IPage<AddrCity> pageInfo(IPage<AddrCity> page, String cityCode, String cityName, String provinceCode);

    /**
     * 功能描述: 通过省份编码获取该省份下的所有城市用于级联选择
     *
     * @param provinceCode 父级省份编码
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/9/17 11:50
     */
    List<Map<String,Object>> getCityCascader(String provinceCode);
}
