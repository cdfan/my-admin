package com.myadminmain.tools.generate.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-07-06
 * @description: 数据库表信息 实体类
 */
@ApiModel(description = "数据库表信息")
public class TablesInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表名称")
    private String tableName;

    @ApiModelProperty(value = "表注释")
    private String tableComment;

    @ApiModelProperty(value = "表注释")
    private List<ColumnsInfo> columnsInfo;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<ColumnsInfo> getColumnsInfo() {
        return columnsInfo;
    }

    public void setColumnsInfo(List<ColumnsInfo> columnsInfo) {
        this.columnsInfo = columnsInfo;
    }
}
