package com.laughing.help.biz.common;

/**
 * @author lifei.pan
 * @create 2018-09-17 下午4:01
 **/
public class Result<T> {
    private boolean success;
    private T data;
    private String resultMsg;

    public Result() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
