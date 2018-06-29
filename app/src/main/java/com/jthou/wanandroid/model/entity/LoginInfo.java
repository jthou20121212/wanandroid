package com.jthou.wanandroid.model.entity;

import java.util.Arrays;

public class LoginInfo {

    private String[] collectIds;
    private String email;
    private String icon;
    private int id;
    private String password;
    private int type;
    private String username;

    public String[] getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(String[] collectIds) {
        this.collectIds = collectIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "collectIds=" + Arrays.toString(collectIds) +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", username='" + username + '\'' +
                '}';
    }
}
