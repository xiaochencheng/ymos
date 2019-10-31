package com.ymos.entity;

import java.math.BigDecimal;
import java.util.List;

public class DayTable extends Id {

    //========================================================
    private String fksj;                       //日期
    private String dpzh;                       //店铺账号
    private int bgh;                           //订单金额
    private BigDecimal rate;                   //汇率
    private BigDecimal orderMoney;             //订单金额
    private BigDecimal salesMoney;             //销售金额
    private BigDecimal channelFeel;            //通道费
    private BigDecimal wuliuMoney;             //物流运费
    private BigDecimal wuliuChen;              //物流成本
    private BigDecimal caiguo;                 //采购合计
    private BigDecimal caiguoChen;             //采购成本占比
    private BigDecimal toufang;                //投放花费
    private BigDecimal toufangChen;            //投放成本占比
    private BigDecimal yugulirun;              //预估利润
    private BigDecimal chenBen;                //成本利润率
    private BigDecimal salesLirun;             //销售利润率

    //==========================================================



    public String getFksj() {
        return fksj;
    }

    public void setFksj(String fksj) {
        if(fksj==null||fksj==""){
            this.fksj = fksj;
        }else{
           this.fksj= fksj.substring(0,10);
        }

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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BigDecimal getSalesMoney() {
        return salesMoney;
    }

    public void setSalesMoney(BigDecimal salesMoney) {
        this.salesMoney = salesMoney;
    }

    public BigDecimal getChannelFeel() {
        return channelFeel;
    }

    public void setChannelFeel(BigDecimal channelFeel) {
        this.channelFeel = channelFeel;
    }

    public BigDecimal getWuliuMoney() {
        return wuliuMoney;
    }

    public void setWuliuMoney(BigDecimal wuliuMoney) {
        this.wuliuMoney = wuliuMoney;
    }

    public BigDecimal getWuliuChen() {
        return wuliuChen;
    }

    public void setWuliuChen(BigDecimal wuliuChen) {
        this.wuliuChen = wuliuChen;
    }

    public BigDecimal getCaiguo() {
        return caiguo;
    }

    public void setCaiguo(BigDecimal caiguo) {
        this.caiguo = caiguo;
    }

    public BigDecimal getCaiguoChen() {
        return caiguoChen;
    }

    public void setCaiguoChen(BigDecimal caiguoChen) {
        this.caiguoChen = caiguoChen;
    }

    public BigDecimal getToufang() {
        return toufang;
    }

    public void setToufang(BigDecimal toufang) {
        this.toufang = toufang;
    }

    public BigDecimal getToufangChen() {
        return toufangChen;
    }

    public void setToufangChen(BigDecimal toufangChen) {
        this.toufangChen = toufangChen;
    }

    public BigDecimal getYugulirun() {
        return yugulirun;
    }

    public void setYugulirun(BigDecimal yugulirun) {
        this.yugulirun = yugulirun;
    }

    public BigDecimal getChenBen() {
        return chenBen;
    }

    public void setChenBen(BigDecimal chenBen) {
        this.chenBen = chenBen;
    }

    public BigDecimal getSalesLirun() {
        return salesLirun;
    }

    public void setSalesLirun(BigDecimal salesLirun) {
        this.salesLirun = salesLirun;
    }

    @Override
    public String toString() {
        return "DayTable{" +
                "fksj='" + fksj + '\'' +
                ", dpzh='" + dpzh + '\'' +
                ", bgh=" + bgh +
                ", rate=" + rate +
                ", orderMoney=" + orderMoney +
                ", salesMoney=" + salesMoney +
                ", channelFeel=" + channelFeel +
                ", wuliuMoney=" + wuliuMoney +
                ", wuliuChen=" + wuliuChen +
                ", caiguo=" + caiguo +
                ", caiguoChen=" + caiguoChen +
                ", toufang=" + toufang +
                ", toufangChen=" + toufangChen +
                ", yugulirun=" + yugulirun +
                ", chenBen=" + chenBen +
                ", salesLirun=" + salesLirun +
                '}';
    }
}
