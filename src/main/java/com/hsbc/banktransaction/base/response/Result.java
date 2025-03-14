package com.hsbc.banktransaction.base.response;

public class Result<T> {
    private int code;
    private String message;
    private boolean success;
    private T data;

    // 添加默认无参构造函数
    public Result() {
    }

    public Result(int code, String message,boolean success, T data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),true, data);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),true, null);
    }

    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(),false, null);
    }
    // 修改 error 方法，使其可以接受额外的错误信息
    public static <T> Result<T> error(ResultCode resultCode, String errorMessage) {
        return new Result<>(resultCode.getCode(), errorMessage,false, null);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}