package com.ymos.entity;

import java.io.Serializable;

public class Result<T> implements Serializable{

	/**
	 * 数据正文
	 */
	private T data = null;
	/**
	 * 操作状态
	 */
	private boolean flag = false;
	/**
	 * 提示信息
	 */
	private String prompt = null;
	private int status = 0;

	public int getStatus() {
		return this.status;
	}

	public Result<T> setStatus(int status) {
		this.status = status;
		return this;
	}

	public T getData() {
		return this.data;
	}

	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}

	public boolean isFlag() {
		return this.flag;
	}

	public Result<T> setFlag(boolean flag) {
		this.flag = flag;
		return this;
	}

	public String getPrompt() {
		return this.prompt;
	}

	public Result<T> setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
	}
}
