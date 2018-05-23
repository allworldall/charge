package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class QihuResbean {

	
	private String status;     //ok or error
	
	private String delivery;   //发货状态success ,mismatch(用户Id不匹配) ，other
	
	private String msg;        //delivery非success时描述

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public QihuResbean(String status, String delivery, String msg) {
		super();
		this.status = status;
		this.delivery = delivery;
		this.msg = msg;
	}

	public QihuResbean() {
		super();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("status=").append(status);
		sb.append(",delivery=").append(delivery);
		sb.append(",msg=").append(msg);
		return sb.toString();
	}
}
