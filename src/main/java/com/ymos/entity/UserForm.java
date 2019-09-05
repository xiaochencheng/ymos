package com.ymos.entity;

import java.util.Date;
import java.util.List;

public class UserForm extends IdForm<User> {
    private String username;
    private String passwd;
    private String gender;
    private String mobile;
    private String email;
    private String lastLoginIp;
    private String status;
    private Date create_date;
    private List<Role> roles;
    private String remark;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLastLoginIp() {
        return lastLoginIp;
    }
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getCreate_date() {
        return create_date;
    }
    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }


    @Override
    public String toString() {
        return "UserForm [username=" + username + ", passwd=" + passwd + ", gender=" + gender + ", mobile=" + mobile
                + ", email=" + email + ", lastLoginIp=" + lastLoginIp + ", status=" + status + ", create_date="
                + create_date + ", roles=" + roles + ", remark=" + remark + "]";
    }
    @Override
    public User newObj() {

        return new User();
    }

    @Override
    public void formObj(User t) {
        t.setUsername(username);
        t.setPasswd(passwd);
        t.setGender(gender);
        t.setMobile(mobile);
        t.setEmail(email);
        t.setLastLoginIp(lastLoginIp);
        t.setStatus(status);
        t.setRoles(roles);
        t.setRemark(remark);
        t.setCreateDate(create_date);
    }
}
