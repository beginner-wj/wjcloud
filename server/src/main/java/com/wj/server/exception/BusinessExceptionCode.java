package com.wj.server.exception;

public enum BusinessExceptionCode {
    USER_LOGIN_NAME_EXIST("登录名已存在"),
    LOGINNAME_OR_PWD_ERROR("用户名或密码错误"),
    ;
    private String desc;
    BusinessExceptionCode(String desc){
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
