package com.wj.server.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public class LoginUserDto implements Serializable {
    /****
     ** ID
     **/
    private String id;
    /****
     ** 登录名
     **/
    private String loginName;
    /****
     ** 昵称
     **/
    private String name;


    private String token;//登录凭证、

    /**
     * 所有资源，用于前端界面控制
     */
    private List<ResourceDto> resources;

    /***
     * 所有资源中的请求，用于后端接口拦截
     */
    private HashSet<String> request;

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public HashSet<String> getRequest() {
        return request;
    }

    public void setRequest(HashSet<String> request) {
        this.request = request;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "LoginUserDto{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

}