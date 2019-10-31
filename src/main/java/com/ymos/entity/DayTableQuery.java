package com.ymos.entity;

public class DayTableQuery extends Query {

    private String dpzh;
    private String fksj;
    private String endTime;
    private Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
        this.addQstrs("size",size);
    }

    public String getDpzh() {
        return dpzh;
    }

    public void setDpzh(String dpzh) {
        this.dpzh = dpzh;
        this.addQstrs("dpzh",dpzh);
    }

    public String getFksj() {
        return fksj;
    }

    public void setFksj(String fksj) {
        this.fksj = fksj;
        this.addQstrs("fksj",fksj);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
         this.endTime = endTime;
         this.addQstrs("endTime",endTime);
    }
}
