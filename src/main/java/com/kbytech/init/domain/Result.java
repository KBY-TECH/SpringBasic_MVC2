package com.kbytech.init.domain;

public class Result {
    private boolean vailid;
    private  String errorMsg;

    public Result() {
        this(true,null);
    }

    public Result(boolean vailid, String errorMsg) {
        this.vailid = vailid;
        this.errorMsg = errorMsg;
    }
    public boolean isValid()
    {
        return vailid;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static Result ok()
    {
        return new Result(true,null);
    }
    public static Result fail(String erroMsg)
    {
        return new Result(false,erroMsg);
    }
}
