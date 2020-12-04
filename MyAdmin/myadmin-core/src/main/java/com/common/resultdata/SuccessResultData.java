package com.common.resultdata;

/**
 * 功能描述: 成功结果
 * 
 * @author cdfan
 * @date 2020/5/22 16:17
 */
public class SuccessResultData extends ResultData<Object> {

    public SuccessResultData() {
        this(200, "操作成功");
    }

    public SuccessResultData(String message) {
        this(200, message);
    }

    public SuccessResultData(int code, String message) {
        super(code, message);
    }
}
