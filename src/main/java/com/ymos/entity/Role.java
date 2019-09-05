package com.ymos.entity;

public class Role extends Id {

	private static final long serialVersionUID = 1L;

	private String name;
	private String sql;
	private String description;
	private int creator;
	
	//t_user里的用户名
	private String username;
	
	
	public Role(String id, String name) {
		this.id=id;
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role(){}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public String getSql() { 
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", sql=" + sql + ", description=" + description + ", creator=" + creator
				+ ", username=" + username + "]";
	}

}
