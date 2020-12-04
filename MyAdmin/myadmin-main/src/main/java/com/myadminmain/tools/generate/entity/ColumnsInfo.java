package com.myadminmain.tools.generate.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author cdfan
 * @version: 1.0
 * @date 2020-07-06
 * @description: 数据库表字段信息 实体类
 */
@ApiModel(description = "数据库表字段信息")
public class ColumnsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字段名称")
    private String columnName;

    @ApiModelProperty(value = "字段注释")
    private String columnComment;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
