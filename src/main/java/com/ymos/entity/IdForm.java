package com.ymos.entity;

import java.io.Serializable;

public abstract class IdForm<T extends Id> implements Serializable{

	protected String id;
	
	public abstract T newObj();
	public abstract void formObj(T t);
	public T toObj(){
		T t= newObj();
		t.setId(this.id);
		this.formObj(t);
		return t;
	}
    public boolean isModified(){
    	return this.id!=null&&!"0".equals(this.id);
    }
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
