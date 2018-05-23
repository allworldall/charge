package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class YoukuChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "订单号（最长不超过 64 位）", nullable = false)
	private String apporderID;
	@DataValidate(description = "用户 id", nullable = false)
	private String uid;
	@DataValidate(description = "价格(单位为“分”)", nullable = false)
	private String price;
	@DataValidate(description = "数字签名", nullable = false)
	private String sign;
	@DataValidate(description = "透传参数（最长 128 位）", nullable = false)
	private String passthrough;
	@DataValidate(description = "计费结果 0:计费失败 1:计费成功 2:计费部分成功（短代支付独有的参数，其他支付方式没有这个参数）", nullable = true)
	private String result;
	@DataValidate(description = "成功支付金额（短代支付独有的参数，其他支付方式没有这个参数）", nullable = true)
	private String success_amount;
	
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
	public String getApporderID() {
		return apporderID;
	}
	public void setApporderID(String apporderID) {
		this.apporderID = apporderID;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	public String getPassthrough() {
		return passthrough;
	}
	public void setPassthrough(String passthrough) {
		this.passthrough = passthrough;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getSuccess_amount() {
		return success_amount;
	}
	public void setSuccess_amount(String success_amount) {
		this.success_amount = success_amount;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",apporderID=" + apporderID + ",uid=" + uid
				+ ",price=" + price + ",sign=" + sign + ",passthrough="
				+ passthrough + ",result=" + result + ",success_amount="
				+ success_amount;
	}

	
}
