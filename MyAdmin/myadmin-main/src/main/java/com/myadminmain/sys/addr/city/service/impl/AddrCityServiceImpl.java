package com.myadminmain.sys.addr.city.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myadminmain.sys.addr.city.dao.AddrCityMapper;
import com.myadminmain.sys.addr.city.entity.AddrCity;
import com.myadminmain.sys.addr.city.service.AddrCityService;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 城市地址表 service实现类
 */
@Service
public class AddrCityServiceImpl extends ServiceImpl<AddrCityMapper, AddrCity> implements AddrCityService {

    @Override
    public IPage<AddrCity> pageInfo(IPage<AddrCity> page, String cityCode, String cityName, String provinceCode) {
        QueryWrapper<AddrCity> query = new QueryWrapper<AddrCity>();
        if (StringUtils.isNotEmpty(cityCode)) {
            query = query.eq("CITY_CODE", cityCode);
        }
        if (StringUtils.isNotEmpty(cityName)) {
            query = query.eq("CITY_NAME", cityName);
        }
        if (StringUtils.isNotEmpty(provinceCode)) {
            query = query.eq("PROVINCE_CODE", provinceCode);
        }
        return this.page(page, query);
    }

    @Override
    public List<Map<String, Object>> getCityCascader(String provinceCode) {
        List<AddrCity> addrCities = this.baseMapper.getCityCascader(provinceCode);
        List<Map<String, Object>> list = new ArrayList<>();
        for (AddrCity addrCity : addrCities) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", addrCity.getCityCode());
            map.put("label", addrCity.getCityName());
            map.put("type", "city");
            if (addrCity.getChildAddrCount() == 0) {
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
