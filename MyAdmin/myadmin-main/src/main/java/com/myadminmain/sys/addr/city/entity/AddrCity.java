package com.myadminmain.sys.addr.city.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 城市地址表 实体类
 */
@TableName("sys_addr_city")
@ApiModel(description = "城市地址表")
public class AddrCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增列")
    @TableId(value = "CITY_ID", type = IdType.AUTO)
    private Integer cityId;

    @ApiModelProperty(value = "市代码")
    @TableField("CITY_CODE")
    private String cityCode;

    @ApiModelProperty(value = "市名称")
    @TableField("CITY_NAME")
    private String cityName;

    @ApiModelProperty(value = "简称")
    @TableField("SHORT_NAME")
    private String shortName;

    @ApiModelProperty(value = "父级省代码")
    @TableField("PROVINCE_CODE")
    private String provinceCode;

    @ApiModelProperty(value = "经度")
    @TableField("LNG")
    private String lng;

    @ApiModelProperty(value = "纬度")
    @TableField("LAT")
    private String lat;

    @ApiModelProperty(value = "排序")
    @TableField("SORT")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    @TableField("MEMO")
    private String memo;

    @ApiModelProperty(value = "下级地区数量，如果没有下级地区则为0")
    @TableField(exist = false)
    private Integer childAddrCount;

    public Integer getChildAddrCount() {
        return childAddrCount;
    }

    public void setChildAddrCount(Integer childAddrCount) {
        this.childAddrCount = childAddrCount;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "AddrCity{" +
        "cityId=" + cityId +
        ", cityCode=" + cityCode +
        ", cityName=" + cityName +
        ", shortName=" + shortName +
        ", provinceCode=" + provinceCode +
        ", lng=" + lng +
        ", lat=" + lat +
        ", sort=" + sort +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", memo=" + memo +
        "}";
    }
}
