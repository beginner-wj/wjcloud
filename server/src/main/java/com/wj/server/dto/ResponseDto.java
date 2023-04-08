package com.wj.server.dto;

public class ResponseDto<T> {
    private boolean success = true;//业务上的成功或者失败
    private String code;//返回码
    private String message;//返回信息
    private T content;//返回泛型数据 自定义类型

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
