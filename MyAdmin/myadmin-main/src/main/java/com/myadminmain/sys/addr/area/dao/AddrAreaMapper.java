package com.myadminmain.sys.addr.area.dao;

import com.myadminmain.sys.addr.area.entity.AddrArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 地区地址表 Mapper 接口
 */
public interface AddrAreaMapper extends BaseMapper<AddrArea> {

    /**
     * 功能描述: 通过城市编码获取该城市下的所有地区用于级联选择
     *
     * @param cityCode 父级城市编码
     * @return java.util.List<com.myadminmain.sys.addr.area.entity.AddrArea>
     * @author cdfan
     * @date 2020/9/23 11:07
     */
    List<AddrArea> getAreaCascader(@Param("cityCode") String cityCode);
}
