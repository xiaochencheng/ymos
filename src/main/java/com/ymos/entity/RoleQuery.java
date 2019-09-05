package com.ymos.entity;


public class RoleQuery extends Query {
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
        this.addQstrs("roleName", roleName);
    }

    @Override
    public String toString() {
        return "RoleQuery [roleName=" + roleName + "]";
    }
}
