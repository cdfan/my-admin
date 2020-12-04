package com.myadminmain.sys.addr.area.controller;

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
import com.myadminmain.sys.addr.area.entity.AddrArea;
import com.myadminmain.sys.addr.area.service.AddrAreaService;

import java.util.List;
import java.util.Map;

/**
 * @author cdfan
 * @version 1.0
 * @date 2020-09-16
 * @description: 地区地址管理 前端控制器
 */
@RestController
@RequestMapping("/addrArea")
@Api(tags = "AddrAreaController", description = "地区地址操作api")
public class AddrAreaController extends BaseController {

    @Autowired
    private AddrAreaService addrAreaService;

    /**
     * 功能描述: 根据主键查询地区地址
     *
     * @param areaId 地区地址id
     * @return com.common.resultdata.ResultData<com.myadminmain.sys.addr.area.entity.AddrArea>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("地区地址-单记录查询")
    @ApiOperation(value = "地区地址-单记录查询", notes = "根据主键查询地区地址")
    @ApiImplicitParam(name = "areaId", value = "地区地址id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/addrAreaInfo/{areaId}", method = RequestMethod.GET)
    public ResultData<AddrArea> get(@PathVariable("areaId") Integer areaId) {
        return new ResultData<AddrArea>(addrAreaService.getById(areaId));
    }

    /**
     * 功能描述: 查询所有地区地址
     *
     * @return com.common.resultdata.ResultData<java.util.List<com.myadminmain.sys.addr.area.entity.AddrArea>>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("地区地址-列表查询")
    @ApiOperation(value = "地区地址-列表查询", notes = "查询所有地区地址")
    @RequestMapping(value = "/addrAreaInfo", method = RequestMethod.GET)
    public ResultData<List<AddrArea>> list() {
        return new ResultData<List<AddrArea>>(addrAreaService.list());
    }

    /**
     * 功能描述: 分页查询地区地址
     *
     * @return com.common.resultdata.ResultData<com.baomidou.mybatisplus.core.metadata.IPage<com.myadminmain.sys.addr.area.entity.AddrArea>>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("地区地址-分页查询")
    @ApiOperation(value = "地区地址-分页查询", notes = "分页查询地区地址")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "currentPage", value = "当前页，页码，默认为1", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据，默认为10", dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)，默认为升序", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "areaCode", value = "区代码", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "areaName", value = "区名称", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "cityCode", value = "父级市代码", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/addrAreaInfoPage", method = RequestMethod.GET)
    public ResultData<IPage<AddrArea>> page(
            @RequestParam(value = "areaCode", required = false) String areaCode,
            @RequestParam(value = "areaName", required = false) String areaName,
            @RequestParam(value = "cityCode", required = false) String cityCode) {
        Page<AddrArea> page = this.defaultPage(AddrArea.class);
        return new ResultData<IPage<AddrArea>>(addrAreaService.pageInfo(page, areaCode, areaName, cityCode));
    }

    /**
     * 功能描述: 查询地区地址数据的数量
     *
     * @return com.common.resultdata.ResultData<java.lang.Integer>
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("地区地址-数量查询")
    @ApiOperation(value = "地区地址-数量查询", notes = "查询地区地址数据的数量")
    @RequestMapping(value = "/addrAreaInfoCount", method = RequestMethod.GET)
    public ResultData<Integer> count() {
        return new ResultData<Integer>(addrAreaService.count());
    }

    /**
     * 功能描述: 新增地区地址
     *
     * @param addrArea 地区地址实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("地区地址-新增")
    @ApiOperation(value = "地区地址-新增", notes = "新增地区地址")
    @ApiImplicitParam(name = "addrArea", value = "地区地址实体对象", required = true, dataType = "AddrArea", paramType = "body")
    @RequestMapping(value = "/addrAreaInfo", method = RequestMethod.POST)
    public ResultData add(@RequestBody AddrArea addrArea) {
        addrAreaService.save(addrArea);
        return SUCCESS;
    }

    /**
     * 功能描述: 修改地区地址，对象中必须有主键
     *
     * @param addrArea 地区地址实体对象
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("地区地址-修改")
    @ApiOperation(value = "地区地址-修改", notes = "修改地区地址，对象中必须有主键")
    @ApiImplicitParam(name = "addrArea", value = "地区地址实体对象", required = true, dataType = "AddrArea", paramType = "body")
    @RequestMapping(value = "/addrAreaInfo", method = RequestMethod.PUT)
    public ResultData update(@RequestBody AddrArea addrArea) {
        if(ObjectUtils.isEmpty(addrArea.getAreaId())){
            throw new MyAdminException("修改对象中必须存在主键");
        }
        addrAreaService.updateById(addrArea);
        return SUCCESS;
    }

    /**
     * 功能描述: 根据主键删除地区地址
     *
     * @param areaId 地区地址id
     * @return com.common.resultdata.ResultData
     * @author cdfan
     * @date 2020-09-16
     */
    @BussinessLog("地区地址-删除")
    @ApiOperation(value = "地区地址-删除", notes = "根据主键删除地区地址")
    @ApiImplicitParam(name = "areaId", value = "地区地址id", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/addrAreaInfo/{areaId}", method = RequestMethod.DELETE)
    public ResultData delete(@PathVariable("areaId") Integer areaId) {
        addrAreaService.removeById(areaId);
        return SUCCESS;
    }

    /**
     * 功能描述: 通过城市编码获取该城市下的所有地区用于级联选择
     *
     * @param cityCode 父级城市编码
     * @return com.common.resultdata.ResultData<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @author cdfan
     * @date 2020/9/17 11:52
     */
    @BussinessLog("地区地址-通过城市编码获取该城市下的所有地区用于级联选择")
    @ApiOperation(value = "地区地址-列表查询", notes = "通过城市编码获取该城市下的所有地区用于级联选择")
    @ApiImplicitParam(name = "cityCode", value = "父级城市编码", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getAreaCascader/{cityCode}", method = RequestMethod.GET)
    public ResultData<List<Map<String, Object>>> getAreaCascader(@PathVariable("cityCode") String cityCode) {
        return new ResultData<List<Map<String, Object>>>(addrAreaService.getAreaCascader(cityCode));
    }
}
