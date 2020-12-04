package com.myadminmain.sys.addr.area.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-16
 * @description: 地区地址表 实体类
 */
@TableName("sys_addr_area")
@ApiModel(description = "地区地址表")
public class AddrArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增列")
    @TableId(value = "AREA_ID", type = IdType.AUTO)
    private Integer areaId;

    @ApiModelProperty(value = "区代码")
    @TableField("AREA_CODE")
    private String areaCode;

    @ApiModelProperty(value = "区名称")
    @TableField("AREA_NAME")
    private String areaName;

    @ApiModelProperty(value = "父级市代码")
    @TableField("CITY_CODE")
    private String cityCode;

    @ApiModelProperty(value = "简称")
    @TableField("SHORT_NAME")
    private String shortName;

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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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
        return "AddrArea{" +
        "areaId=" + areaId +
        ", areaCode=" + areaCode +
        ", areaName=" + areaName +
        ", cityCode=" + cityCode +
        ", shortName=" + shortName +
        ", lng=" + lng +
        ", lat=" + lat +
        ", sort=" + sort +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", memo=" + memo +
        "}";
    }
}
