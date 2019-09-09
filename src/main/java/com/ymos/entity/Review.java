package com.ymos.entity;

import java.util.Date;

public class Review extends Id{

    private String soureId;                 //资源id
    private String pro_ch_name;             //产品中文名
    private String pro_en_name;             //产品英文名
    private String cus_ch_name;             //报关中文名
    private String cus_en_name;             //报关英文名
    private String cus_price;               //报关价格（usd）
    private String cus_weight;              //报关重量（g）
    private String pro_list;                //产品分类
    private String pro_url;                 //文件（图片）
    private String pro_purchase_price;      //建议采购价
    private String weight;                  //重量
    private String url;                     //采购网址
    private String url2;                    //采购网址2
    private String url3;                    //采购网址3
    private String presale_price;           //预售价
    private String    creator;                 //创建者
    private int    status;                  //审核状态
    private String remark;                  //备注
    private Date dateTime;
    private String spu;                     //标识产品唯一性id
    private String freight;                 //运费


    public String getSoureId() {
        return soureId;
    }

    public void setSoureId(String soureId) {
        this.soureId = soureId;
    }

    public String getPro_ch_name() {
        return pro_ch_name;
    }

    public void setPro_ch_name(String pro_ch_name) {
        this.pro_ch_name = pro_ch_name;
    }

    public String getPro_en_name() {
        return pro_en_name;
    }

    public void setPro_en_name(String pro_en_name) {
        this.pro_en_name = pro_en_name;
    }

    public String getCus_ch_name() {
        return cus_ch_name;
    }

    public void setCus_ch_name(String cus_ch_name) {
        this.cus_ch_name = cus_ch_name;
    }

    public String getCus_en_name() {
        return cus_en_name;
    }

    public void setCus_en_name(String cus_en_name) {
        this.cus_en_name = cus_en_name;
    }

    public String getCus_price() {
        return cus_price;
    }

    public void setCus_price(String cus_price) {
        this.cus_price = cus_price;
    }

    public String getCus_weight() {
        return cus_weight;
    }

    public void setCus_weight(String cus_weight) {
        this.cus_weight = cus_weight;
    }

    public String getPro_list() {
        return pro_list;
    }

    public void setPro_list(String pro_list) {
        this.pro_list = pro_list;
    }

    public String getPro_url() {
        return pro_url;
    }

    public void setPro_url(String pro_url) {
        this.pro_url = pro_url;
    }

    public String getPro_purchase_price() {
        return pro_purchase_price;
    }

    public void setPro_purchase_price(String pro_purchase_price) {
        this.pro_purchase_price = pro_purchase_price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public String getPresale_price() {
        return presale_price;
    }

    public void setPresale_price(String presale_price) {
        this.presale_price = presale_price;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    @Override
    public String toString() {
        return "Review{" +
                "soureId='" + soureId + '\'' +
                ", pro_ch_name='" + pro_ch_name + '\'' +
                ", pro_en_name='" + pro_en_name + '\'' +
                ", cus_ch_name='" + cus_ch_name + '\'' +
                ", cus_en_name='" + cus_en_name + '\'' +
                ", cus_price='" + cus_price + '\'' +
                ", cus_weight='" + cus_weight + '\'' +
                ", pro_list='" + pro_list + '\'' +
                ", pro_url='" + pro_url + '\'' +
                ", pro_purchase_price='" + pro_purchase_price + '\'' +
                ", weight='" + weight + '\'' +
                ", url='" + url + '\'' +
                ", url2='" + url2 + '\'' +
                ", url3='" + url3 + '\'' +
                ", presale_price='" + presale_price + '\'' +
                ", creator='" + creator + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", dateTime=" + dateTime +
                ", spu='" + spu + '\'' +
                ", freight='" + freight + '\'' +
                '}';
    }
}
