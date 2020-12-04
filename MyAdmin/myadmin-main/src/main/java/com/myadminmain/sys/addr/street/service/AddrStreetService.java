package com.myadminmain.sys.addr.street.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.sys.addr.street.entity.AddrStreet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 街道地址表 service接口
 */
public interface AddrStreetService extends IService<AddrStreet> {

    /**
     * 功能描述: 分页查询街道地址
     *
     * @param page 分页对象
     * @param streetCode 街道代码
     * @param streetName 街道名称
     * @param areaCode 父级区代码
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.addr.street.entity.AddrStreet>
     * @author cdfan
     * @date 2020-09-16
     */
    IPage<AddrStreet> pageInfo(IPage<AddrStreet> page, String streetCode, String streetName, String areaCode);

    /**
     * 功能描述: 通过地区编码获取该地区下的所有街道用于级联选择
     *
     * @param areaCode 父级地区编码
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/9/17 12:00
     */
    List<Map<String,Object>> getStreetCascader(String areaCode);
}
