package com.ymos.entity;

import java.util.Date;

/**
 * 产品分类列表
 */
public class ProductList extends Id {


    private int pid;           //
    private String name;       //产品列表名称
    private int creator;       //创建者
    private int seq;           //层级数
    private String adName;     //产品缩写

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "ProductList{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", creator=" + creator +
                ", seq=" + seq +
                ", adName=" + adName +
                '}';
    }
}
