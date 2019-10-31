package com.ymos.entity;

import javax.swing.plaf.basic.BasicHTML;
import java.math.BigDecimal;

public class DayTableForm extends IdForm<DayTable> {

    private String fksj;
    private String dpzh;
    private int bgh;
    private BigDecimal rate;
    private BigDecimal orderMoney;
    private BigDecimal salesMoney;
    private BigDecimal channelFeel;
    private BigDecimal wuliuMoney;
    private BigDecimal wuliuChen;
    private BigDecimal caiguo;
    private BigDecimal caiguoChen;
    private BigDecimal toufang;
    private BigDecimal toufangChen;
    private BigDecimal yugulirun;
    private BigDecimal chenBen;
    private BigDecimal salesLirun;

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

    public String getFksj() {
        return fksj;
    }

    public void setFksj(String fksj) {
        this.fksj = fksj;
    }

    @Override
    public String toString() {
        return "DayTableForm{" +
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

    @Override
    public DayTable newObj() {
        return null;
    }

    @Override
    public void formObj(DayTable dayTable) {
         dayTable.setFksj(fksj);
         dayTable.setDpzh(dpzh);
         dayTable.setBgh(bgh);
         dayTable.setRate(rate);
         dayTable.setOrderMoney(orderMoney);
         dayTable.setSalesMoney(salesMoney);
         dayTable.setChannelFeel(channelFeel);
         dayTable.setWuliuMoney(wuliuMoney);
         dayTable.setWuliuChen(wuliuChen);
         dayTable.setCaiguo(caiguo);
         dayTable.setCaiguoChen(caiguoChen);
         dayTable.setToufang(toufang);
         dayTable.setToufangChen(toufangChen);
         dayTable.setYugulirun(yugulirun);
         dayTable.setSalesLirun(salesLirun);
    }
}
