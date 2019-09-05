package com.ymos.entity;

import java.util.Date;

public class SkuQuery extends Query {

    private String  skuName;

    private String spu;

    private String name;

    private Integer size;

    private String dateTime;
    private String endTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
        this.addQstrs("dateTime",dateTime);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        this.addQstrs("endTime",endTime);
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
        this.addQstrs("size",size);
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
        this.addQstrs("skuName",skuName);
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
        this.addQstrs("spu",spu);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.addQstrs("name",name);
    }

    @Override
    public String toString() {
        return "SkuQuery{" +
                "skuName='" + skuName + '\'' +
                ", dateTime=" + dateTime +
                ", spu='" + spu + '\'' +
                ", name='" + name + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
