package com.myadminmain.sys.addr.area.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myadminmain.sys.addr.area.dao.AddrAreaMapper;
import com.myadminmain.sys.addr.area.entity.AddrArea;
import com.myadminmain.sys.addr.area.service.AddrAreaService;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 地区地址表 service实现类
 */
@Service
public class AddrAreaServiceImpl extends ServiceImpl<AddrAreaMapper, AddrArea> implements AddrAreaService {

    @Override
    public IPage<AddrArea> pageInfo(IPage<AddrArea> page, String areaCode, String areaName, String cityCode) {
        QueryWrapper<AddrArea> query = new QueryWrapper<AddrArea>();
        if (StringUtils.isNotEmpty(areaCode)) {
            query = query.eq("AREA_CODE", areaCode);
        }
        if (StringUtils.isNotEmpty(areaName)) {
            query = query.eq("AREA_NAME", areaName);
        }
        if (StringUtils.isNotEmpty(cityCode)) {
            query = query.eq("CITY_CODE", cityCode);
        }
        return this.page(page, query);
    }

    @Override
    public List<Map<String, Object>> getAreaCascader(String cityCode) {
        List<AddrArea> addrAreas = this.baseMapper.getAreaCascader(cityCode);
        List<Map<String, Object>> list = new ArrayList<>();
        for (AddrArea addrArea : addrAreas) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", addrArea.getAreaCode());
            map.put("label", addrArea.getAreaName());
            map.put("type", "area");
            if (addrArea.getChildAddrCount() == 0) {
                // 没有下级地区
                map.put("leaf", true);
            } else {
                map.put("leaf", false);
            }
            list.add(map);
        }
        return list;
    }
}
