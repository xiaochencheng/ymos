package com.ymos.entity;

public class ProductListQuery extends Query {

    private String name;
    private String adName;

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
        this.addQstrs("adName", adName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.addQstrs("name",name);
    }
}
