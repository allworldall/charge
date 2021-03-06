package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class UCData {
    private String orderId;
    private String gameId;
    private String accountId;
    private String creator;
    private String payWay;
    private String amount;
    private String callbackInfo;
    private String orderStatus;
    private String failedDesc;
    private String cpOrderId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCallbackInfo() {
		return callbackInfo;
	}
	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getFailedDesc() {
		return failedDesc;
	}
	public void setFailedDesc(String failedDesc) {
		this.failedDesc = failedDesc;
	}
	public String getCpOrderId() {
		return cpOrderId;
	}
	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("orderId=").append(orderId);
		sb.append(",gameId=").append(gameId);
		sb.append(",accountId=").append(accountId);
		sb.append(",creator=").append(creator);
		sb.append(",payWay=").append(payWay);
		sb.append(",amount=").append(amount);
		sb.append(",callbackInfo=").append(callbackInfo);
		sb.append(",orderStatus=").append(orderStatus);
		sb.append(",failedDesc=").append(failedDesc);
		sb.append(",cpOrderId=").append(cpOrderId);
		return sb.toString();
	}
}
