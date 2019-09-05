package com.ymos.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tree implements Serializable{
	private String id;
	private String title;
	private String value;
	private boolean checked;
	private List<Tree> data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Tree> getData() {
		if(data==null){
			data=new ArrayList<Tree>();
		}
		return data;
	}
	public void setData(List<Tree> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Tree [title=" + title + ", value=" + value + ", checked=" + checked + ", data=" + data + "]";
	}
	
}
