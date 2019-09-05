package com.ymos.entity;

public class UserQuery extends Query {

    private String username;
    private String status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.addQstrs("username",username);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.addQstrs("status",status);
    }
}
