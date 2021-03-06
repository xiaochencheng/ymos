package com.ymos.entity;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * sku
 */
public class Sku extends Id {


    //====================================================
    private String dangerDesBg;                //是否危险品
    private String hgbmBg;                  //海关编码
    private String imgUrl;                  //图片url
    private String name;                    //产品中文名
    private String nameCnBg;                //报关产品中文名
    private String nameEn;                  //英文名称
    private String nameEnBg;                //报关英文名称
    private BigDecimal price;                      //价格
    private BigDecimal priceBg;                    //默认采购价
    private String sbm;                        //是否液体
    private String skuName;                 //sku
    private String sku;
    private String sourceUrl;                  //是否普通货物
    private String weight;                  //重量
    private String weightBg;                //危险运输品
    private String spu;                     //spu
    private String create_date;             //创建时间
    private String creator;                 //创建人
    private String attributes;              //sku中文属性

//=======================================================


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDangerDesBg() {
        return dangerDesBg;
    }

    public void setDangerDesBg(String dangerDesBg) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceBg() {
        return priceBg;
    }

    public void setPriceBg(BigDecimal priceBg) {
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

    @Override
    public String toString() {
        return "Sku{" +
                "dangerDesBg=" + dangerDesBg +
                ", hgbmBg='" + hgbmBg + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", nameCnBg='" + nameCnBg + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", nameEnBg='" + nameEnBg + '\'' +
                ", price=" + price +
                ", priceBg=" + priceBg +
                ", sbm=" + sbm +
                ", skuName=" + skuName +
                ", sourceUrl=" + sourceUrl +
                ", weight='" + weight + '\'' +
                ", weightBg='" + weightBg + '\'' +
                ", spu='" + spu + '\'' +
                ", create_date='" + create_date + '\'' +
                ", creator='" + creator + '\'' +
                ", attributes='" + attributes + '\'' +
                '}';
    }
}
