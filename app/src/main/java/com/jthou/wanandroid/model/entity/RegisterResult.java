package com.jthou.wanandroid.model.entity;

/**
 * Created by user on 2018/7/24.
 */

public class RegisterResult {

    private LoginInfo data;
    private int errorCode;
    private String errorMsg;

    public LoginInfo getData() {
        return data;
    }

    public void setData(LoginInfo data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
