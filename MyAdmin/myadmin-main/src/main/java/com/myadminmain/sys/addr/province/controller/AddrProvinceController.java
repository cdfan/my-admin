package com.myadminmain.sys.addr.province.controller;

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
import com.myadminmain.sys.addr.province.entity.AddrProvince;
import com.myadminmain.sys.addr.province.service.AddrProvinceService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-09-16
 * @description: 省份地址管理 前端控制器
 */
@RestController
@RequestMapping("/addrProvince")
@Api(tags = "AddrProvinceController", description = "省份地址操作api")
public class AddrProvinceController extends BaseController {

    @Autowired
    private AddrProvinceService addrProvinceService;

    /**
     * 功能描述: 根据主键查询省份地址
     *
     * @param provinceId 省份地址id
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.addr.province.entity.AddrProvince>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("省份地址-单记录查询")
    @ApiOperation(value = "省份地址-单记录查询", notes = "根据主键查询省份地址")
    @ApiImplicitParam(name = "provinceId", value = "省份地址id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/addrProvinceInfo/{provinceId}", method = RequestMethod.GET)
    public ResultData<AddrProvince> get(@PathVariable("provinceId") Integer provinceId) {
        return new ResultData<AddrProvince>(addrProvinceService.getById(provinceId));
    }

    /**
     * 功能描述: 查询所有省份地址
     *
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.sys.addr.province.entity.AddrProvince>>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("省份地址-列表查询")
    @ApiOperation(value = "省份地址-列表查询", notes = "查询所有省份地址")
    @RequestMapping(value = "/addrProvinceInfo", method = RequestMethod.GET)
    public ResultData<List<AddrProvince>> list() {
        return new ResultData<List<AddrProvince>>(addrProvinceService.list());
    }

    /**
     * 功能描述: 分页查询省份地址
     *
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.addr.province.entity.AddrProvince>>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("省份地址-分页查询")
    @ApiOperation(value = "省份地址-分页查询", notes = "分页查询省份地址")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "provinceCode", value = "省份代码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "provinceName", value = "省份名称", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/addrProvinceInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<AddrProvince>> page(
            @RequestParam(value = "provinceCode", required = false) String provinceCode,
            @RequestParam(value = "provinceName", required = false) String provinceName) {
        Page<AddrProvince> page = this.defaultPage(AddrProvince.class);
        return new ResultData<IPage<AddrProvince>>(addrProvinceService.pageInfo(page, provinceCode, provinceName));
    }

    /**
     * 功能描述: 查询省份地址数据的数量
     *
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("省份地址-数量查询")
    @ApiOperation(value = "省份地址-数量查询", notes = "查询省份地址数据的数量")
    @RequestMapping(value = "/addrProvinceInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count() {
        return new ResultData<Integer>(addrProvinceService.count());
    }

    /**
     * 功能描述: 新增省份地址
     *
     * @param addrProvince 省份地址实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("省份地址-新增")
    @ApiOperation(value = "省份地址-新增", notes = "新增省份地址")
    @ApiImplicitParam(name = "addrProvince", value = "省份地址实体对象", required = true, dataType = "AddrProvince", paramType = "body")
    @RequestMapping(value = "/addrProvinceInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody AddrProvince addrProvince) {
        addrProvinceService.save(addrProvince);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改省份地址，对象中必须有主键
     *
     * @param addrProvince 省份地址实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("省份地址-修改")
    @ApiOperation(value = "省份地址-修改", notes = "修改省份地址，对象中必须有主键")
    @ApiImplicitParam(name = "addrProvince", value = "省份地址实体对象", required = true, dataType = "AddrProvince", paramType = "body")
    @RequestMapping(value = "/addrProvinceInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody AddrProvince addrProvince) {
        if(ObjectUtils.isEmpty(addrProvince.getProvinceId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        addrProvinceService.updateById(addrProvince);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除省份地址
     *
     * @param provinceId 省份地址id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("省份地址-删除")
    @ApiOperation(value = "省份地址-删除", notes = "根据主键删除省份地址")
    @ApiImplicitParam(name = "provinceId", value = "省份地址id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/addrProvinceInfo/{provinceId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("provinceId") Integer provinceId) {
        addrProvinceService.removeById(provinceId);
        return SUCCESS;
    }


    /**
     * 功能描述: 获取用于级联选择的全国省份
     * @return com.common.resultdata.ResultData<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @author cdfan
     * @date 2020/9/16 11:34
     */
    @BussinessLog("省份地址-获取用于级联选择的全国省份")
    @ApiOperation(value = "省份地址-列表查询", notes = "获取用于级联选择的全国省份")
    @RequestMapping(value = "/getProvinceCascader", method = RequestMethod.GET)
    public ResultData<List<Map<String, Object>>> getProvinceCascader() {
        return new ResultData<List<Map<String, Object>>>(addrProvinceService.getProvinceCascader());
    }

}
