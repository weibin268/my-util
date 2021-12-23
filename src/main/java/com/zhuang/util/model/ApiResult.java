package com.zhuang.util.model;

import java.io.Serializable;

public class ApiResult<T> implements Serializable {

    private int code = 0;
    private String message;
    private T data;

    public static ApiResult<Object> alert(String message) {
        ApiResult<Object> result = new ApiResult<>();
        result.setCode(-1);
        result.setMessage(message);
        return result;
    }

    public static ApiResult<Object> error(String message) {
        ApiResult<Object> result = new ApiResult<>();
        result.setCode(1);
        result.setMessage(message);
        return result;
    }

    public static ApiResult<Object> error(int code, String message) {
        ApiResult<Object> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> error(String message, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(1);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> error(int code, String message, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static ApiResult<Object> success() {
        return new ApiResult<Object>();
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setData(data);
        return result;
    }

    public boolean isSuccess() {
        return code == 0;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
