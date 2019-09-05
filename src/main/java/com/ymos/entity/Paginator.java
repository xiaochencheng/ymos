package com.ymos.entity;

import java.io.Serializable;

/**
 * 分页工具类
 */
public class Paginator implements Serializable{
    public static final int DEFAULT_SIZE = 12;
    private int start;
    private int size;
    private int page;
    private int total;
    private int totalPage;
    private boolean needTotal;
    private boolean hasNextPage;
    private boolean hasPrePage;
    private boolean hasData;
    private boolean pageless;
    private String path;
    private Query query;
    private Object attachment;

    public Paginator() {
    }

    public Paginator(int page) {
        this.page = page;
        this.init(); 
    }

    public Paginator(int page, int size) {
        this.page = page;
        this.size = size;
        this.init();
    }

    private void init() {
        if(this.size < 1) {
            this.size = 12;
        }

        if(this.page < 1) {
            this.page = 1;
        }

        this.start = (this.page - 1) * this.size;
        this.hasPrePage = this.page > 1;
    }

    public boolean isPageless() {
        return this.pageless;
    }

    public void setPageless(boolean pageless) {
        this.pageless = pageless;
    }

    public boolean isNeedTotal() {
        return this.needTotal;
    }

    public void setNeedTotal(boolean needTotal) {
        this.needTotal = needTotal;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Query getQuery() {
        return this.query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public boolean isHasPrePage() {
        return this.hasPrePage;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public boolean isHasData() {
        return this.hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public void setPage(int page) {
        this.init();
    }

    public int getPage() {
        return this.page;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return this.size;
    }

    public int getSqlSize() {
        return this.size + 1;
    }

    public void setSize(int size) {
        this.size = size;
        this.init();
    }

    public Object getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

	public int getTotalPage() {
		return (int) Math.ceil(this.total*1.0/this.size);
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public String toString() {
		return "Paginator [start=" + start + ", size=" + size + ", page=" + page + ", total=" + total + ", totalPage="
				+ totalPage + ", needTotal=" + needTotal + ", hasNextPage=" + hasNextPage + ", hasPrePage=" + hasPrePage
				+ ", hasData=" + hasData + ", pageless=" + pageless + ", path=" + path + ", query=" + query
				+ ", attachment=" + attachment + "]";
	}

}
