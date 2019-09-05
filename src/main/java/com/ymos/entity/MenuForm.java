package com.ymos.entity;

import java.sql.Timestamp;
import java.util.List;

public class MenuForm extends IdForm<Menu> {

    private String pid;
    private String name;
    private String url;
    private Timestamp create_time;
    private int creator;
    private int seq;
    private List<Menu> children;//layui tree需要的children节点
    private Boolean spread;//layui tree需要的spread节点

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

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    @Override
    public Menu newObj() {
        return new Menu();
    }

    @Override
    public void formObj(Menu menu) {
       menu.setId(id);
       menu.setChildren(children);
       menu.setCreate_time(create_time);
       menu.setCreator(creator);
       menu.setName(name);
       menu.setSeq(seq);
       menu.setPid(pid);
       menu.setSpread(spread);
       menu.setUrl(url);
    }
}
