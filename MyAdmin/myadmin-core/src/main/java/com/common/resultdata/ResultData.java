package com.common.resultdata;

/**
 * 功能描述: 返回给前台的提示（最终转化为json形式）
 * 
 * @author cdfan
 * @date 2020/5/22 16:16
 */
public class ResultData<T> {

    protected int code;
    protected String message;
    protected T result;

    public ResultData(T result) {
        this(200, "操作成功", result);
    }

    public ResultData(int code, String message) {
        this(code, message, null);
    }

    public ResultData(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
