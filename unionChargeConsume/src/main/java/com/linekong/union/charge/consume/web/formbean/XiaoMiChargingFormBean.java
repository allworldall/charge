package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class XiaoMiChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "appId", nullable = false)
	private String appId;
	@DataValidate(description = "开发商订单ID", nullable = false)
	private String cpOrderId;
	@DataValidate(description = "开发商透传信息", nullable = true)
	private String cpUserInfo;
	@DataValidate(description = "用户ID", nullable = false)
	private String uid;
	@DataValidate(description = "游戏平台订单ID", nullable = false)
	private String orderId;
	@DataValidate(description = "订单状态 TRADE_SUCCESS 代表成功", nullable = false)
	private String orderStatus;
	@DataValidate(description = "支付金额，单位为分，即0.01米币", nullable = false)
	private String payFee;
	@DataValidate(description = "商品代码", nullable = false)
	private String productCode;
	@DataValidate(description = "商品名称", nullable = false)
	private String productName;
	@DataValidate(description = "商品数量", nullable = false)
	private String productCount;
	@DataValidate(description = "支付时间，格式yyyy-MM-dd HH:mm:ss", nullable = false)
	private String payTime;
	@DataValidate(description = "签名，签名方法详见下节(HmacSHA1算法)", nullable = false)
	private String signature;
	//订单类型
	private String orderConsumeType;
	//使用优惠券特有参数
	private String partnerGiftConsume;
	
	public String getOrderConsumeType() {
		return orderConsumeType;
	}
	public void setOrderConsumeType(String orderConsumeType) {
		this.orderConsumeType = orderConsumeType;
	}
	public String getPartnerGiftConsume() {
		return partnerGiftConsume;
	}
	public void setPartnerGiftConsume(String partnerGiftConsume) {
		this.partnerGiftConsume = partnerGiftConsume;
	}
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
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCpOrderId() {
		return cpOrderId;
	}
	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}
	public String getCpUserInfo() {
		return cpUserInfo;
	}
	public void setCpUserInfo(String cpUserInfo) {
		this.cpUserInfo = cpUserInfo;
	}
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayFee() {
		return payFee;
	}
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCount() {
		return productCount;
	}
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature.replace(" ", "+").replace("\\", "");
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",appId=" + appId + ",cpOrderId=" + cpOrderId
				+ ",cpUserInfo=" + cpUserInfo + ",uid=" + uid + ",orderId="
				+ orderId + ",orderStatus=" + orderStatus + ",payFee="
				+ payFee + ",productCode=" + productCode + ",productName="
				+ productName + ",productCount=" + productCount + ",payTime="
				+ payTime + ",signature=" + signature + ",orderConsumeType="
				+ orderConsumeType + ",partnerGiftConsume="
				+ partnerGiftConsume;
	}

}
