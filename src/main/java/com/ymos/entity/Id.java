package com.ymos.entity;

import java.io.Serializable;
import java.util.Date;


public abstract class Id implements Serializable{
	
	protected String id;
	protected Date createDate;
	protected Date modifyDate;
	public String getId() {
		return id;
	} 
	public boolean modified(){
		//return !StringUtils.isNullOrEmpty(this.id);
		return this.id!=null&&!"0".equals(this.id)&&!"".equals(this.id);
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
