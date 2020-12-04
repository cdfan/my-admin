package com.common.resultdata;

/**
 * 功能描述: 错误提示结果
 * 
 * @author cdfan
 * @date 2020/3/13 11:37
 */
public class ErrorResultData extends ResultData<Object> {

    public ErrorResultData() {
        this(500, "操作失败");
    }

    public ErrorResultData(String message) {
        this(500, message);
    }

    public ErrorResultData(int code, String message) {
        super(code, message);
    }
}
