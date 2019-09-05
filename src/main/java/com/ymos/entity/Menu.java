package com.ymos.entity;

import java.sql.Timestamp;
import java.util.List;

public class Menu  extends Id{

    private static final long serialVersionUID = 1L;

    private String pid;
    private String name;
    private String url;
    private Timestamp create_time;
    private int creator;
    private int seq;
    private List<Menu> children;//layui tree需要的children节点
    private Boolean spread;//layui tree需要的spread节点

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp createTime) {
        create_time = createTime;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Menu [pid=" + pid + ", name=" + name + ", url=" + url + ", create_time=" + create_time + ", creator="
                + creator + ", seq=" + seq + "]";
    }

}
