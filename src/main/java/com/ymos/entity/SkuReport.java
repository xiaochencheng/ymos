package com.ymos.entity;

public class SkuReport extends AbstractReport {

    private int dangerDesBg;                //是否危险品
    private String hgbmBg;                  //海关编码
    private String imgUrl;                  //图片url
    private String name;                    //产品中文名
    private String nameCnBg;                //报关产品中文名
    private String nameEn;                  //英文名称
    private String nameEnBg;                //报关英文名称
    private int price;                      //价格
    private int priceBg;                    //默认采购价
    private String sbm;                        //是否液体
    private String skuName;                 //sku
    private String sourceUrl;                  //是否普通货物
    private String weight;                  //重量
    private String weightBg;                //危险运输品
    private String spu;                     //spu
    private String create_date;             //创建时间
    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDangerDesBg() {
        return dangerDesBg;
    }

    public void setDangerDesBg(int dangerDesBg) {
        this.dangerDesBg = dangerDesBg;
    }

    public String getHgbmBg() {
        return hgbmBg;
    }

    public void setHgbmBg(String hgbmBg) {
        this.hgbmBg = hgbmBg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCnBg() {
        return nameCnBg;
    }

    public void setNameCnBg(String nameCnBg) {
        this.nameCnBg = nameCnBg;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEnBg() {
        return nameEnBg;
    }

    public void setNameEnBg(String nameEnBg) {
        this.nameEnBg = nameEnBg;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceBg() {
        return priceBg;
    }

    public void setPriceBg(int priceBg) {
        this.priceBg = priceBg;
    }

    public String getSbm() {
        return sbm;
    }

    public void setSbm(String sbm) {
        this.sbm = sbm;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightBg() {
        return weightBg;
    }

    public void setWeightBg(String weightBg) {
        this.weightBg = weightBg;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
