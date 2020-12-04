package com.myadminmain.workflow.example.shoppingprocess.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-09-17
 * @description: 购物流程表 实体类
 */
@TableName("demo_shopping_process")
@ApiModel(description = "购物流程表")
public class ShoppingProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程业务key的前缀
     */
    public static final String BUSINESS_KEY_PREFIX = "shoppingProcess";

    @ApiModelProperty(value = "主键id")
    @TableId(value = "shop_id", type = IdType.AUTO)
    private Integer shopId;

    @ApiModelProperty(value = "流程实例id")
    @TableField("proc_inst_id")
    private String procInstId;

    @ApiModelProperty(value = "下单人员id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "购物流程状态,具体参考业务字典")
    @TableField("shop_proc_state")
    private String shopProcState;

    @ApiModelProperty(value = "商品编码")
    @TableField("goods_code")
    private String goodsCode;

    @ApiModelProperty(value = "商品数量")
    @TableField("goods_count")
    private Integer goodsCount;

    @ApiModelProperty(value = "收货地址，省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "收货地址，市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "收货地址，区")
    @TableField("area")
    private String area;

    @ApiModelProperty(value = "收货地址，街道")
    @TableField("street")
    private String street;

    @ApiModelProperty(value = "收货详细地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "运单号")
    @TableField("tracking_number")
    private String trackingNumber;

    @ApiModelProperty(value = "快递类型")
    @TableField("express_type")
    private String expressType;

    @ApiModelProperty(value = "快递员")
    @TableField("courier")
    private String courier;

    @ApiModelProperty(value = "快递员电话")
    @TableField("courier_phone")
    private String courierPhone;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "流程状态名称")
    @TableField(exist = false)
    private String shopProcStateName;

    @ApiModelProperty(value = "下单人员名称")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty(value = "商品名称")
    @TableField(exist = false)
    private String goodsName;

    @ApiModelProperty(value = "任务id")
    @TableField(exist = false)
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    @TableField(exist = false)
    private String taskName;

    @ApiModelProperty(value = "流程定义id")
    @TableField(exist = false)
    private String procDefId;

    @ApiModelProperty(value = "所属流程名称")
    @TableField(exist = false)
    private String procName;

    @ApiModelProperty(value = "流程业务key")
    @TableField(exist = false)
    private String businessKey;

    public String getShopProcStateName() {
        return shopProcStateName;
    }

    public void setShopProcStateName(String shopProcStateName) {
        this.shopProcStateName = shopProcStateName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getShopProcState() {
        return shopProcState;
    }

    public void setShopProcState(String shopProcState) {
        this.shopProcState = shopProcState;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ShoppingProcess{" +
        "shopId=" + shopId +
        ", procInstId=" + procInstId +
        ", userId=" + userId +
        ", shopProcState=" + shopProcState +
        ", goodsCode=" + goodsCode +
        ", goodsCount=" + goodsCount +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", street=" + street +
        ", address=" + address +
        ", trackingNumber=" + trackingNumber +
        ", expressType=" + expressType +
        ", courier=" + courier +
        ", courierPhone=" + courierPhone +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
