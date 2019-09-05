package com.ymos.entity;

import java.util.Date;

public class RoleForm extends IdForm<Role> {
    private String name;
    private String sql;
    private String description;
    private Date createDate;
    private int creator;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSql() {
        return sql;
    }
    public void setSql(String sql) {
        this.sql = sql;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public int getCreator() {
        return creator;
    }
    public void setCreator(int creator) {
        this.creator = creator;
    }
    @Override
    public Role newObj() {
        return new Role();
    }

    @Override
    public String toString() {
        return "RoleForm [name=" + name + ", sql=" + sql + ", description=" + description + ", createDate="
                + createDate + ", creator=" + creator + "]";
    }
    @Override
    public void formObj(Role t) {
        t.setName(name);
        t.setSql(sql);
        t.setDescription(description);
        t.setCreateDate(createDate);
        t.setCreator(creator);
    }
}
