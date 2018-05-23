package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class PHPReturnResBean {

	private int result;		   //结果
	
	private String desc;		//结果描述

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public PHPReturnResBean(int result, String desc) {
		super();
		this.result = result;
		this.desc = desc;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("result=").append(result);
		sb.append(",desc=").append(desc);
		return sb.toString();
	}
}
