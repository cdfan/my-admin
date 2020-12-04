package com.myadminmain.sys.addr.city.dao;

import com.myadminmain.sys.addr.city.entity.AddrCity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 城市地址表 Mapper 接口
 */
public interface AddrCityMapper extends BaseMapper<AddrCity> {

    /**
     * 功能描述: 通过省份编码获取该省份下的所有城市用于级联选择
     *
     * @param provinceCode 父级省份编码
     * @return java.util.List<com.myadminmain.sys.addr.city.entity.AddrCity>
     * @author cdfan
     * @date 2020/9/22 22:57
     */
    List<AddrCity> getCityCascader(@Param("provinceCode") String provinceCode);
}
