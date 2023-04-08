package com.wj.server.dto;

import java.io.Serializable;
import java.util.List;

public class RoleDto implements Serializable {
    /****
     ** ID
     **/
    private String id;
    /****
     ** 角色
     **/
    private String name;
    /****
     ** 描述
     **/
    private String roledesc;


    List<String> resourceIds;

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    private List<String> userIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(" Hash = ").append(hashCode());
        sb.append(", id = ").append(id);
        sb.append(", name = ").append(name);
        sb.append(", roledesc = ").append(roledesc);
        sb.append("]");
        return sb.toString();
    }
}