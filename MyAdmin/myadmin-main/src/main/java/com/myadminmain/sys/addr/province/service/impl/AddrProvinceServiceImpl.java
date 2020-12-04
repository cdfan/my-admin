package com.myadminmain.sys.addr.province.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myadminmain.sys.addr.province.dao.AddrProvinceMapper;
import com.myadminmain.sys.addr.province.entity.AddrProvince;
import com.myadminmain.sys.addr.province.service.AddrProvinceService;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 省份地址表 service实现类
 */
@Service
public class AddrProvinceServiceImpl extends ServiceImpl<AddrProvinceMapper, AddrProvince> implements AddrProvinceService {

    @Override
    public IPage<AddrProvince> pageInfo(IPage<AddrProvince> page, String provinceCode, String provinceName) {
        QueryWrapper<AddrProvince> query = new QueryWrapper<AddrProvince>();
        if (StringUtils.isNotEmpty(provinceCode)) {
            query = query.eq("PROVINCE_CODE", provinceCode);
        }
        if (StringUtils.isNotEmpty(provinceName)) {
            query = query.eq("PROVINCE_NAME", provinceName);
        }
        return this.page(page, query);
    }

    @Override
    public List<Map<String, Object>> getProvinceCascader() {
        List<AddrProvince> addrProvinces = this.list();
        List<Map<String, Object>> list = new ArrayList<>();
        for (AddrProvince addrProvince : addrProvinces) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", addrProvince.getProvinceCode());
            map.put("label", addrProvince.getProvinceName());
            map.put("type", "province");
            list.add(map);
        }
        return list;
    }
}
