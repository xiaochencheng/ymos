package com.ymos.entity;

public class SkuListQuery extends Query {

    private String skuName;

    private String cus_ch_name;

    private String cus_en_name;

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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
        this.addQstrs("skuName",skuName);
    }

    public String getCus_ch_name() {
        return cus_ch_name;
    }

    public void setCus_ch_name(String cus_ch_name) {
        this.cus_ch_name = cus_ch_name;
        this.addQstrs("cus_ch_name",cus_ch_name);
    }

    public String getCus_en_name() {
        return cus_en_name;
    }

    public void setCus_en_name(String cus_en_name) {
        this.cus_en_name = cus_en_name;
        this.addQstrs("cus_en_name",cus_en_name);
    }

    @Override
    public String toString() {
        return "SkuListQuery{" +
                "skuName='" + skuName + '\'' +
                ", cus_ch_name='" + cus_ch_name + '\'' +
                ", cus_en_name='" + cus_en_name + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
