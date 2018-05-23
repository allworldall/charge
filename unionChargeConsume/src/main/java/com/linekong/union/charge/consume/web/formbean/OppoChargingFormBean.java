package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class OppoChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "回调通知 ID,该值使用系统为这次支付生成的订单号",maxLength=50,nullable = false)
	private String notifyId;
	@DataValidate(description = "开发者订单号（客户端上传）",maxLength=100,nullable = false)
	private String partnerOrder;
	@DataValidate(description = "商品名称（客户端上传）",maxLength=50,nullable = false)
	private String productName;
	@DataValidate(description = "商品描述（客户端上传）",maxLength=100,nullable = false)
	private String productDesc;
	@DataValidate(description = "商品价格(以分为单位)",nullable = false)
	private int price;
	@DataValidate(description = "商品数量（一般为 1）",nullable = false)
	private int count;
	@DataValidate(description = "请求支付时上传的附加参数（客户端上传）",maxLength=200)
	private String attach;
	@DataValidate(description = "签名",nullable = false)
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
	public String getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}
	public String getPartnerOrder() {
		return partnerOrder;
	}
	public void setPartnerOrder(String partnerOrder) {
		this.partnerOrder = partnerOrder;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
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
				+ cpName + ",notifyId=" + notifyId + ",partnerOrder="
				+ partnerOrder + ",productName=" + productName
				+ ",productDesc=" + productDesc + ",price=" + price
				+ ",count=" + count + ",attach=" + attach + ",sign=" + sign;
	}
     
}
