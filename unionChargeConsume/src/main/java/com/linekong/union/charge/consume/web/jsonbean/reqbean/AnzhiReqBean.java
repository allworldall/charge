package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class AnzhiReqBean {
	private String uid;
	private String orderId;
	private String orderAmount;
	private String orderTime;
	private int code;
	private String msg;
	private String payAmount;
	private String cpInfo;
	private int notifyTime;
	private String memo;
	private String redBagMoney;
	private String cpCustomInfo;
	private String orderAccount;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getCpInfo() {
		return cpInfo;
	}

	public void setCpInfo(String cpInfo) {
		this.cpInfo = cpInfo;
	}

	public int getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(int notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRedBagMoney() {
		return redBagMoney;
	}

	public void setRedBagMoney(String redBagMoney) {
		this.redBagMoney = redBagMoney;
	}

	public String getCpCustomInfo() {
		return cpCustomInfo;
	}

	public void setCpCustomInfo(String cpCustomInfo) {
		this.cpCustomInfo = cpCustomInfo;
	}

	public String getOrderAccount() {
		return orderAccount;
	}

	public void setOrderAccount(String orderAccount) {
		this.orderAccount = orderAccount;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("uid=").append(uid);
		sb.append(",orderId=").append(orderId);
		sb.append(",orderAmount=").append(orderAmount);
		sb.append(",orderTime=").append(orderTime);
		sb.append(",code=").append(code);
		sb.append(",msg=").append(msg);
		sb.append(",payAmount=").append(payAmount);
		sb.append(",cpInfo=").append(cpInfo);
		sb.append(",notifyTime=").append(notifyTime);
		sb.append(",memo=").append(memo);
		sb.append(",redBagMoney=").append(redBagMoney);
		sb.append(",cpCustomInfo=").append(cpCustomInfo);
		sb.append(",orderAccount=").append(orderAccount);
		return sb.toString();
	}
}
