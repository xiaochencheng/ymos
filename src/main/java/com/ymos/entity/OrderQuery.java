package com.ymos.entity;


public class OrderQuery extends Query {

    private String sku;
    private String orderId;
    private String   fksj;
    private String orderStatus;
    private String xdsj;
    private String bgh;
    private String ydh;
    private Integer size;
    private String dpzh;

    public String getDpzh() {
        return dpzh;
    }

    public void setDpzh(String dpzh) {
        this.dpzh = dpzh;
        this.addQstrs("dpzh",dpzh);
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
        this.addQstrs("size",size);
    }

    public String getYdh() {
        return ydh;
    }

    public void setYdh(String ydh) {
        this.ydh = ydh;
        this.addQstrs("ydh",ydh);
    }

    public String getBgh() {
        return bgh;
    }

    public void setBgh(String bgh) {
        this.bgh = bgh;
        this.addQstrs("bgh",bgh);
    }

    public String getXdsj() {
        return xdsj;
    }

    public void setXdsj(String xdsj) {
        this.xdsj = xdsj;
        this.addQstrs("xdsj",xdsj);
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
        this.addQstrs("sku",sku);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
        this.addQstrs("orderId",orderId);
    }

    public String getFksj() {
        return fksj;
    }

    public void setFksj(String fksj) {
        this.fksj = fksj;
        this.addQstrs("fksj",fksj);
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        this.addQstrs("orderStatus",orderStatus);
    }



}
