package com.kmd.remoterdp.model;


/**
 * Created by FARHAN on 2/1/2015.
 */
public class BaseModel{
    private String errorMsg;
    private int errorCode;
    private int action;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
