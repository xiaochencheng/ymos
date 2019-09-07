package com.ymos.entity;

public class ProductQuery extends Query {

    private String pro_ch_name;
    private String pro_en_name;
    private String spu;
    private Integer size;
    private String dateTime;
    private String endTime;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        this.addQstrs("status",status);
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
        this.addQstrs("spu",spu);
    }

    public String getPro_en_name() {
        return pro_en_name;
    }

    public void setPro_en_name(String pro_en_name) {
        this.pro_en_name = pro_en_name;
        this.addQstrs("pro_en_name",pro_en_name);
    }

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

    public String getPro_ch_name() {
        return pro_ch_name;
    }

    public void setPro_ch_name(String pro_ch_name) {
        this.pro_ch_name = pro_ch_name;
        this.addQstrs("pro_ch_name",pro_ch_name);
    }
}
