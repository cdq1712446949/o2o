package com.cdq.o2o.dto;

public class Result<T> {

    //是否获取成功标识
    private boolean success;
    //错误信息
    private String errMsg;
    //错误码
    private int errorCode;
    //返回数据
    private T data;

    public Result(){}

    //成功时的构造器
    public Result(boolean success,T data){
        this.success=success;
        this.data=data;
    }

    //失败时的构造器
    public Result(boolean success,int errorCode,String errMsg){
        this.success=success;
        this.errMsg=errMsg;
        this.errorCode=errorCode;
    }

    public boolean isSuccess(){
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
