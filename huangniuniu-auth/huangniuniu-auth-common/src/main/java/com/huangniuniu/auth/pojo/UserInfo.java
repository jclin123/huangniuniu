package com.huangniuniu.auth.pojo;

/**
 * 载荷对象
 */
public class UserInfo {

    private Long id;

    private Integer roleType;

    private String username;

    public UserInfo() {
    }

    public UserInfo(Long id, Integer roleType, String username) {
        this.id = id;
        this.roleType = roleType;
        this.username = username;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}