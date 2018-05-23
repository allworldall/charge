package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class ZhangyueChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "商户ID，合作方标识。", nullable = false)
	private String merId;
	@DataValidate(description = "应用id，应用标识", nullable = false)
	private String appid;
	@DataValidate(description = "掌阅订单号，用于记录订单", nullable = false)
	private String orderId;
	@DataValidate(description = "商户订单", nullable = false)
	private String meOrderId;
	@DataValidate(description = "订单金额，为浮点", nullable = false)
	private String payAmt;
	@DataValidate(description = "日期格式:yyyyMMddHHmmss", nullable = false)
	private String transTime;
	@DataValidate(description = "订单状态，1为成功，其他失败", nullable = false)
	private String orderStatus;
	@DataValidate(description = "错误码", nullable = false)
	private String errorCode;
	@DataValidate(description = "错误信息", nullable = false)
	private String errorMsg;
	@DataValidate(description = "充值类型", nullable = false)
	private String rechargeType;
	@DataValidate(description = "MD5 签名,详细方法见下", nullable = false)
	private String md5SignValue;
	
	
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMeOrderId() {
		return meOrderId;
	}
	public void setMeOrderId(String meOrderId) {
		this.meOrderId = meOrderId;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}
	public String getMd5SignValue() {
		return md5SignValue;
	}
	public void setMd5SignValue(String md5SignValue) {
		this.md5SignValue = md5SignValue;
	}
	@Override
	public String toString() {
		return "ZhangyueChargingFormBean [gameName=" + gameName + ", cpName="
				+ cpName + ", merId=" + merId + ", appid=" + appid
				+ ", orderId=" + orderId + ", meOrderId=" + meOrderId
				+ ", payAmt=" + payAmt + ", transTime=" + transTime
				+ ", orderStatus=" + orderStatus + ", errorCode=" + errorCode
				+ ", errorMsg=" + errorMsg + ", rechargeType=" + rechargeType
				+ ", md5SignValue=" + md5SignValue + "]";
	}
	
}
