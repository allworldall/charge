package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class VivoChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description = "响应码,例如200")
	private String respCode;
	
	@DataValidate(description = "响应信息，例如交易完成")
	private String respMsg;
	
	@DataValidate(description = "签名方法，例如MD5", nullable = false)
	private String signMethod;
	
	@DataValidate(description = "签名信息", nullable = false)
	private String signature;
	
	@DataValidate(description = "交易种类，固定为01", nullable = false)
	private String tradeType;
	
	@DataValidate(description = "交易状态，0000代表成功", nullable = false)
	private String tradeStatus;
	
	@DataValidate(description = "定长20位数字，由vivo分发的唯一识别码", nullable = false)
	private String cpId;
	
	@DataValidate(description = "应用ID", nullable = false)
	private String appId;
	
	@DataValidate(description = "用户在vivo这边的唯一标识", nullable = false)
	private String uid;
	
	@DataValidate(description = "商户自定义订单号，最长 64 位字母、数字和下划线组成", nullable = false)
	private String cpOrderNumber;
	
	@DataValidate(description = "vivo订单号", nullable = false)
	private String orderNumber;
	
	@DataValidate(description = "单位：分，币种：人民币，为长整型，如：101，10000", nullable = false)
	private String orderAmount;

	@DataValidate(description = "商户透传信息，64位", nullable = true)
	private String extInfo;
	
	@DataValidate(description = "交易时间：yyyyMMddHHmmss", nullable = false)
	private String payTime;

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

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature.replace(" ", "+").replace("\\", "");
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCpOrderNumber() {
		return cpOrderNumber;
	}

	public void setCpOrderNumber(String cpOrderNumber) {
		this.cpOrderNumber = cpOrderNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",respCode=" + respCode + ",respMsg=" + respMsg
				+ ",signMethod=" + signMethod + ",signature=" + signature
				+ ",tradeType=" + tradeType + ",tradeStatus=" + tradeStatus
				+ ",cpId=" + cpId + ",appId=" + appId + ",uid=" + uid
				+ ",cpOrderNumber=" + cpOrderNumber + ",orderNumber="
				+ orderNumber + ",orderAmount=" + orderAmount + ",extInfo="
				+ extInfo + ",payTime=" + payTime;
	}

}
