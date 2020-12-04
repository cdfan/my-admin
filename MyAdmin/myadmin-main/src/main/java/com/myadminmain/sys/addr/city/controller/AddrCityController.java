package com.myadminmain.sys.addr.city.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.resultdata.ResultData;
import com.common.annotion.BussinessLog;
import com.common.annotion.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.exception.MyAdminException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.myadminmain.core.common.controller.BaseController;
import com.myadminmain.sys.addr.city.entity.AddrCity;
import com.myadminmain.sys.addr.city.service.AddrCityService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-09-16
 * @description: 城市地址管理 前端控制器
 */
@RestController
@RequestMapping("/addrCity")
@Api(tags = "AddrCityController", description = "城市地址操作api")
public class AddrCityController extends BaseController {

    @Autowired
    private AddrCityService addrCityService;

    /**
     * 功能描述: 根据主键查询城市地址
     *
     * @param cityId 城市地址id
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.addr.city.entity.AddrCity>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("城市地址-单记录查询")
    @ApiOperation(value = "城市地址-单记录查询", notes = "根据主键查询城市地址")
    @ApiImplicitParam(name = "cityId", value = "城市地址id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/addrCityInfo/{cityId}", method = RequestMethod.GET)
    public ResultData<AddrCity> get(@PathVariable("cityId") Integer cityId) {
        return new ResultData<AddrCity>(addrCityService.getById(cityId));
    }

    /**
     * 功能描述: 查询所有城市地址
     *
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.sys.addr.city.entity.AddrCity>>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("城市地址-列表查询")
    @ApiOperation(value = "城市地址-列表查询", notes = "查询所有城市地址")
    @RequestMapping(value = "/addrCityInfo", method = RequestMethod.GET)
    public ResultData<List<AddrCity>> list() {
        return new ResultData<List<AddrCity>>(addrCityService.list());
    }

    /**
     * 功能描述: 分页查询城市地址
     *
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.addr.city.entity.AddrCity>>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("城市地址-分页查询")
    @ApiOperation(value = "城市地址-分页查询", notes = "分页查询城市地址")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "cityCode", value = "市代码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "cityName", value = "市名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "provinceCode", value = "父级省代码", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/addrCityInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<AddrCity>> page(
            @RequestParam(value = "cityCode", required = false) String cityCode,
            @RequestParam(value = "cityName", required = false) String cityName,
            @RequestParam(value = "provinceCode", required = false) String provinceCode) {
        Page<AddrCity> page = this.defaultPage(AddrCity.class);
        return new ResultData<IPage<AddrCity>>(addrCityService.pageInfo(page, cityCode, cityName, provinceCode));
    }

    /**
     * 功能描述: 查询城市地址数据的数量
     *
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("城市地址-数量查询")
    @ApiOperation(value = "城市地址-数量查询", notes = "查询城市地址数据的数量")
    @RequestMapping(value = "/addrCityInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count() {
        return new ResultData<Integer>(addrCityService.count());
    }

    /**
     * 功能描述: 新增城市地址
     *
     * @param addrCity 城市地址实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("城市地址-新增")
    @ApiOperation(value = "城市地址-新增", notes = "新增城市地址")
    @ApiImplicitParam(name = "addrCity", value = "城市地址实体对象", required = true, dataType = "AddrCity", paramType = "body")
    @RequestMapping(value = "/addrCityInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody AddrCity addrCity) {
        addrCityService.save(addrCity);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改城市地址，对象中必须有主键
     *
     * @param addrCity 城市地址实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("城市地址-修改")
    @ApiOperation(value = "城市地址-修改", notes = "修改城市地址，对象中必须有主键")
    @ApiImplicitParam(name = "addrCity", value = "城市地址实体对象", required = true, dataType = "AddrCity", paramType = "body")
    @RequestMapping(value = "/addrCityInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody AddrCity addrCity) {
        if(ObjectUtils.isEmpty(addrCity.getCityId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        addrCityService.updateById(addrCity);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除城市地址
     *
     * @param cityId 城市地址id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("城市地址-删除")
    @ApiOperation(value = "城市地址-删除", notes = "根据主键删除城市地址")
    @ApiImplicitParam(name = "cityId", value = "城市地址id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/addrCityInfo/{cityId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("cityId") Integer cityId) {
        addrCityService.removeById(cityId);
        return SUCCESS;
    }


    /**
     * 功能描述: 通过省份编码获取该省份下的所有城市用于级联选择
     * @param provinceCode 父级省份编码
     * @return com.common.resultdata.ResultData<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @author cdfan
     * @date 2020/9/17 11:50
     */
    @BussinessLog("城市地址-通过省份编码获取该省份下的所有城市用于级联选择")
    @ApiOperation(value = "城市地址-列表查询", notes = "通过省份编码获取该省份下的所有城市用于级联选择")
    @ApiImplicitParam(name = "provinceCode", value = "父级省份编码", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getCityCascader/{provinceCode}", method = RequestMethod.GET)
    public ResultData<List<Map<String, Object>>> getCityCascader(@PathVariable("provinceCode") String provinceCode) {
        return new ResultData<List<Map<String, Object>>>(addrCityService.getCityCascader(provinceCode));
    }

}
