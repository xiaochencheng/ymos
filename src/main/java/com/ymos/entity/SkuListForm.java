package com.ymos.entity;

public class SkuListForm extends IdForm<SkuList> {

    //=================================================================
    private String skuName;                        //sku编码
    private String pro_ch_name;                //产品中文名
    private String pro_en_name;                //产品英文名
    private String cus_ch_name;                //报关中文名
    private String cus_en_name;                //报关英文名
    private String cus_price;                  //报关价格
    private String url;                        //来源URL
    private String cus_code;                   //海关编码
    private String pro_purchase_price;         //采购价
    private String presale_price;              //报关金额
    private String weidth;                     //重量
    private String identifier;                 //识别码
    private String dan_goods;                  //危险运输品种类
    private String length;                     //长
    private String height;                     //高
    private String width;                      //宽
    //=================================================================


    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCus_code() {
        return cus_code;
    }

    public void setCus_code(String cus_code) {
        this.cus_code = cus_code;
    }

    public String getPro_purchase_price() {
        return pro_purchase_price;
    }

    public void setPro_purchase_price(String pro_purchase_price) {
        this.pro_purchase_price = pro_purchase_price;
    }

    public String getPresale_price() {
        return presale_price;
    }

    public void setPresale_price(String presale_price) {
        this.presale_price = presale_price;
    }

    public String getWeidth() {
        return weidth;
    }

    public void setWeidth(String weidth) {
        this.weidth = weidth;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        identifier = identifier;
    }

    public String getDan_goods() {
        return dan_goods;
    }

    public void setDan_goods(String dan_goods) {
        this.dan_goods = dan_goods;
    }

    @Override
    public String toString() {
        return "SkuList{" +
                "skuName='" + skuName + '\'' +
                ", pro_ch_name='" + pro_ch_name + '\'' +
                ", pro_en_name='" + pro_en_name + '\'' +
                ", cus_ch_name='" + cus_ch_name + '\'' +
                ", cus_en_name='" + cus_en_name + '\'' +
                ", cus_price='" + cus_price + '\'' +
                ", url='" + url + '\'' +
                ", cus_code='" + cus_code + '\'' +
                ", pro_purchase_price='" + pro_purchase_price + '\'' +
                ", presale_price='" + presale_price + '\'' +
                ", weidth='" + weidth + '\'' +
                ", identifier='" + identifier + '\'' +
                ", dan_goods='" + dan_goods + '\'' +
                ", length='" + length + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                '}';
    }


    @Override
    public SkuList newObj() {
        return new SkuList();
    }

    @Override
    public void formObj(SkuList skuList) {
        skuList.setCus_ch_name(cus_ch_name);
        skuList.setCus_en_name(cus_en_name);
        skuList.setPro_ch_name(pro_ch_name);
        skuList.setPro_en_name(pro_en_name);
        skuList.setCus_code(cus_code);
        skuList.setCus_price(cus_price);
        skuList.setDan_goods(dan_goods);
        skuList.setIdentifier(identifier);
        skuList.setPresale_price(presale_price);
        skuList.setPro_purchase_price(pro_purchase_price);
        skuList.setSkuName(skuName);
        skuList.setUrl(url);
        skuList.setLength(length);
        skuList.setHeight(height);
        skuList.setWidth(width);

    }
}
