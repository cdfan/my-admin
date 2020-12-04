package com.myadminmain.sys.addr.area.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myadminmain.sys.addr.area.entity.AddrArea;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 地区地址表 service接口
 */
public interface AddrAreaService extends IService<AddrArea> {

    /**
     * 功能描述: 分页查询地区地址
     *
     * @param page 分页对象
     * @param areaCode 区代码
     * @param areaName 区名称
     * @param cityCode 父级市代码
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.addr.area.entity.AddrArea>
     * @author cdfan
     * @date 2020-09-16
     */
    IPage<AddrArea> pageInfo(IPage<AddrArea> page, String areaCode, String areaName, String cityCode);

    /**
     * 功能描述: 通过城市编码获取该城市下的所有地区用于级联选择
     *
     * @param cityCode 父级城市编码
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author cdfan
     * @date 2020/9/17 11:52
     */
    List<Map<String,Object>> getAreaCascader(String cityCode);
}
