package com.ymos.entity;


public class DayTableReport extends AbstractReport {

    //========================================================
    private String fksj;                       //日期
    private String dpzh;                       //店铺账号
    private int bgh;                           //订单金额
    private String rate;                   //汇率
    private String orderMoney;             //订单金额
    private String salesMoney;             //销售金额
    private String channelFeel;            //通道费
    private String wuliuMoney;             //物流运费
    private String wuliuChen;              //物流成本
    private String caiguo;                 //采购合计
    private String caiguoChen;             //采购成本占比
    private String toufang;                //投放花费
    private String toufangChen;            //投放成本占比
    private String yugulirun;              //预估利润
    private String chenBen;                //成本利润率
    private String salesLirun;             //销售利润率
    private String endTime;
    //==========================================================


    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFksj() {
        return fksj;
    }

    public void setFksj(String fksj) {
        this.fksj = fksj;
    }

    public String getDpzh() {
        return dpzh;
    }

    public void setDpzh(String dpzh) {
        this.dpzh = dpzh;
    }

    public int getBgh() {
        return bgh;
    }

    public void setBgh(int bgh) {
        this.bgh = bgh;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getSalesMoney() {
        return salesMoney;
    }

    public void setSalesMoney(String salesMoney) {
        this.salesMoney = salesMoney;
    }

    public String getChannelFeel() {
        return channelFeel;
    }

    public void setChannelFeel(String channelFeel) {
        this.channelFeel = channelFeel;
    }

    public String getWuliuMoney() {
        return wuliuMoney;
    }

    public void setWuliuMoney(String wuliuMoney) {
        this.wuliuMoney = wuliuMoney;
    }

    public String getWuliuChen() {
        return wuliuChen;
    }

    public void setWuliuChen(String wuliuChen) {
        this.wuliuChen = wuliuChen;
    }

    public String getCaiguo() {
        return caiguo;
    }

    public void setCaiguo(String caiguo) {
        this.caiguo = caiguo;
    }

    public String getCaiguoChen() {
        return caiguoChen;
    }

    public void setCaiguoChen(String caiguoChen) {
        this.caiguoChen = caiguoChen;
    }

    public String getToufang() {
        return toufang;
    }

    public void setToufang(String toufang) {
        this.toufang = toufang;
    }

    public String getToufangChen() {
        return toufangChen;
    }

    public void setToufangChen(String toufangChen) {
        this.toufangChen = toufangChen;
    }

    public String getYugulirun() {
        return yugulirun;
    }

    public void setYugulirun(String yugulirun) {
        this.yugulirun = yugulirun;
    }

    public String getChenBen() {
        return chenBen;
    }

    public void setChenBen(String chenBen) {
        this.chenBen = chenBen;
    }

    public String getSalesLirun() {
        return salesLirun;
    }

    public void setSalesLirun(String salesLirun) {
        this.salesLirun = salesLirun;
    }
}
