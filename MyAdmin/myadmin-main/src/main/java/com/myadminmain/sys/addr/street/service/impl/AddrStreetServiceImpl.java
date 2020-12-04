package com.myadminmain.sys.addr.street.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myadminmain.sys.addr.street.dao.AddrStreetMapper;
import com.myadminmain.sys.addr.street.entity.AddrStreet;
import com.myadminmain.sys.addr.street.service.AddrStreetService;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 街道地址表 service实现类
 */
@Service
public class AddrStreetServiceImpl extends ServiceImpl<AddrStreetMapper, AddrStreet> implements AddrStreetService {

    @Override
    public IPage<AddrStreet> pageInfo(IPage<AddrStreet> page, String streetCode, String streetName, String areaCode) {
        QueryWrapper<AddrStreet> query = new QueryWrapper<AddrStreet>();
        if (StringUtils.isNotEmpty(streetCode)) {
            query = query.eq("STREET_CODE", streetCode);
        }
        if (StringUtils.isNotEmpty(streetName)) {
            query = query.eq("STREET_NAME", streetName);
        }
        if (StringUtils.isNotEmpty(areaCode)) {
            query = query.eq("AREA_CODE", areaCode);
        }
        return this.page(page, query);
    }

    @Override
    public List<Map<String, Object>> getStreetCascader(String areaCode) {
        List<AddrStreet> addrStreets = this.list(new QueryWrapper<AddrStreet>().eq("AREA_CODE", areaCode));
        List<Map<String, Object>> list = new ArrayList<>();
        for (AddrStreet addrStreet : addrStreets) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", addrStreet.getStreetCode());
            map.put("label", addrStreet.getStreetName());
            map.put("type", "street");
            // 最下级子节点
            map.put("leaf", true);
            list.add(map);
        }
        return list;
    }
}
