package com.ymos.entity;

public class MenuQuery extends Query {

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.addQstrs("name",name);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        this.addQstrs("url",url);
    }
}
