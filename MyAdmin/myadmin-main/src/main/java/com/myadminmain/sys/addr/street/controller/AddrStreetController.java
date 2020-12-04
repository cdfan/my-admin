package com.myadminmain.sys.addr.street.controller;

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
import com.myadminmain.sys.addr.street.entity.AddrStreet;
import com.myadminmain.sys.addr.street.service.AddrStreetService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-09-16
 * @description: 街道地址管理 前端控制器
 */
@RestController
@RequestMapping("/addrStreet")
@Api(tags = "AddrStreetController", description = "街道地址操作api")
public class AddrStreetController extends BaseController {

    @Autowired
    private AddrStreetService addrStreetService;

    /**
     * 功能描述: 根据主键查询街道地址
     *
     * @param streetId 街道地址id
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.addr.street.entity.AddrStreet>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("街道地址-单记录查询")
    @ApiOperation(value = "街道地址-单记录查询", notes = "根据主键查询街道地址")
    @ApiImplicitParam(name = "streetId", value = "街道地址id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/addrStreetInfo/{streetId}", method = RequestMethod.GET)
    public ResultData<AddrStreet> get(@PathVariable("streetId") Integer streetId) {
        return new ResultData<AddrStreet>(addrStreetService.getById(streetId));
    }

    /**
     * 功能描述: 查询所有街道地址
     *
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.sys.addr.street.entity.AddrStreet>>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("街道地址-列表查询")
    @ApiOperation(value = "街道地址-列表查询", notes = "查询所有街道地址")
    @RequestMapping(value = "/addrStreetInfo", method = RequestMethod.GET)
    public ResultData<List<AddrStreet>> list() {
        return new ResultData<List<AddrStreet>>(addrStreetService.list());
    }

    /**
     * 功能描述: 分页查询街道地址
     *
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.addr.street.entity.AddrStreet>>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("街道地址-分页查询")
    @ApiOperation(value = "街道地址-分页查询", notes = "分页查询街道地址")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "streetCode", value = "街道代码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "streetName", value = "街道名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "areaCode", value = "父级区代码", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/addrStreetInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<AddrStreet>> page(
            @RequestParam(value = "streetCode", required = false) String streetCode,
            @RequestParam(value = "streetName", required = false) String streetName,
            @RequestParam(value = "areaCode", required = false) String areaCode) {
        Page<AddrStreet> page = this.defaultPage(AddrStreet.class);
        return new ResultData<IPage<AddrStreet>>(addrStreetService.pageInfo(page, streetCode, streetName, areaCode));
    }

    /**
     * 功能描述: 查询街道地址数据的数量
     *
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("街道地址-数量查询")
    @ApiOperation(value = "街道地址-数量查询", notes = "查询街道地址数据的数量")
    @RequestMapping(value = "/addrStreetInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count() {
        return new ResultData<Integer>(addrStreetService.count());
    }

    /**
     * 功能描述: 新增街道地址
     *
     * @param addrStreet 街道地址实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("街道地址-新增")
    @ApiOperation(value = "街道地址-新增", notes = "新增街道地址")
    @ApiImplicitParam(name = "addrStreet", value = "街道地址实体对象", required = true, dataType = "AddrStreet", paramType = "body")
    @RequestMapping(value = "/addrStreetInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody AddrStreet addrStreet) {
        addrStreetService.save(addrStreet);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改街道地址，对象中必须有主键
     *
     * @param addrStreet 街道地址实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("街道地址-修改")
    @ApiOperation(value = "街道地址-修改", notes = "修改街道地址，对象中必须有主键")
    @ApiImplicitParam(name = "addrStreet", value = "街道地址实体对象", required = true, dataType = "AddrStreet", paramType = "body")
    @RequestMapping(value = "/addrStreetInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody AddrStreet addrStreet) {
        if(ObjectUtils.isEmpty(addrStreet.getStreetId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        addrStreetService.updateById(addrStreet);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除街道地址
     *
     * @param streetId 街道地址id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("街道地址-删除")
    @ApiOperation(value = "街道地址-删除", notes = "根据主键删除街道地址")
    @ApiImplicitParam(name = "streetId", value = "街道地址id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/addrStreetInfo/{streetId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("streetId") Integer streetId) {
        addrStreetService.removeById(streetId);
        return SUCCESS;
    }

    /**
     * 功能描述: 通过地区编码获取该地区下的所有街道用于级联选择
     *
     * @param areaCode 父级地区编码
     * @return com.common.resultdata.ResultData<java.util.List < java.util.Map < java.lang.String , java.lang.Object>>>
     * @author cdfan
     * @date 2020/9/17 12:00
     */
    @BussinessLog("街道地址-通过地区编码获取该地区下的所有街道用于级联选择")
    @ApiOperation(value = "街道地址-列表查询", notes = "通过地区编码获取该地区下的所有街道用于级联选择")
    @ApiImplicitParam(name = "areaCode", value = "父级地区编码", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getStreetCascader/{areaCode}", method = RequestMethod.GET)
    public ResultData<List<Map<String, Object>>> getStreetCascader(@PathVariable("areaCode") String areaCode) {
        return new ResultData<List<Map<String, Object>>>(addrStreetService.getStreetCascader(areaCode));
    }
}
