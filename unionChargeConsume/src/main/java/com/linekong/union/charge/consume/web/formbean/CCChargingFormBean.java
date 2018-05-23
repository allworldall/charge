package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class CCChargingFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description = "虫虫支付订单号", nullable = false)
	private String transactionNo;
	@DataValidate(description = "商户订单号", nullable = false)
	private String partnerTransactionNo;
	@DataValidate(description = "订单状态", nullable = false)
	private String statusCode;
	@DataValidate(description = "支付商品的Id", nullable = false)
	private String productId;
	@DataValidate(description = "订单金额", nullable = false)
	private String orderPrice;
	@DataValidate(description = "游戏ID", nullable = false)
	private String packageId;
	@DataValidate(description = "回调签名", nullable = false)
	private String sign;
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
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getPartnerTransactionNo() {
		return partnerTransactionNo;
	}
	public void setPartnerTransactionNo(String partnerTransactionNo) {
		this.partnerTransactionNo = partnerTransactionNo;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",transactionNo=" + transactionNo
				+ ",partnerTransactionNo=" + partnerTransactionNo
				+ ",statusCode=" + statusCode + ",productId=" + productId
				+ ",orderPrice=" + orderPrice + ",packageId=" + packageId
				+ ",sign=" + sign;
	}
}
