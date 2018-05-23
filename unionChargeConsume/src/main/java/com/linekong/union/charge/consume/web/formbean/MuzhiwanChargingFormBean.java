package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class MuzhiwanChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "游戏唯一标记", nullable = false)
	private String appkey;
	@DataValidate(description = "订单唯一标记", nullable = false)
	private String orderID;
	@DataValidate(description = "商品名称", nullable = false)
	private String productName;
	@DataValidate(description = "商品描述", nullable = false)
	private String productDesc;
	@DataValidate(description = "商品ID", nullable = false)
	private String productID;
	@DataValidate(description = "金额，元为单位", nullable = false)
	private String money;
	@DataValidate(description = "充值用户ID", nullable = false)
	private String uid;
	@DataValidate(description = "扩展域", nullable = false)
	// 蓝港预支付ID
	private String extern;
	@DataValidate(description = "签名，签名规则详见“签名说明”", nullable = false)
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
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
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
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getExtern() {
		return extern;
	}
	public void setExtern(String extern) {
		this.extern = extern;
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
				+ cpName + ",appkey=" + appkey + ",orderID=" + orderID
				+ ",productName=" + productName + ",productDesc="
				+ productDesc + ",productID=" + productID + ",money=" + money
				+ ",uid=" + uid + ",extern=" + extern + ",sign=" + sign;
	}
	
}
