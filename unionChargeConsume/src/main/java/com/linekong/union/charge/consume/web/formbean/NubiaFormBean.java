package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class NubiaFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description = "游戏订单号", nullable = false)
	public String order_no;		//蓝港的预支付订单号
	@DataValidate(description = "支付通知时间", nullable = false)
	public String data_timestamp;
	@DataValidate(description = "支付成功与否(成功为1)", nullable = false)
	public String pay_success;
	
	public String sign;			//支付通知签名（旧）
	@DataValidate(description = "应用id", nullable = false)
	public String app_id;		
	@DataValidate(description = "登录时获取的uid", nullable = false)
	public String uid;			//登录时获取的uid，该uid必须和nubia账号相关联
	@DataValidate(description = "支付金额（精确到分）", nullable = false)
	public String amount;		//单位是元，精确到分
	@DataValidate(description = "商品名称", nullable = false)
	public String product_name;
	@DataValidate(description = "商品描述", nullable = false)
	public String product_des;
	@DataValidate(description = "商品数量", nullable = false)
	public String number;
	
	public String order_serial;		//Nubia支付的订单号
	@DataValidate(description = "支付通知签名(新)", nullable = false)
	public String order_sign;
	
	
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
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getData_timestamp() {
		return data_timestamp;
	}
	public void setData_timestamp(String data_timestamp) {
		this.data_timestamp = data_timestamp;
	}
	public String getPay_success() {
		return pay_success;
	}
	public void setPay_success(String pay_success) {
		this.pay_success = pay_success;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_des() {
		return product_des;
	}
	public void setProduct_des(String product_des) {
		this.product_des = product_des;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getOrder_serial() {
		return order_serial;
	}
	public void setOrder_serial(String order_serial) {
		this.order_serial = order_serial;
	}
	public String getOrder_sign() {
		return order_sign;
	}
	public void setOrder_sign(String order_sign) {
		this.order_sign = order_sign;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName=" + cpName
				+ ",order_no=" + order_no + ",data_timestamp="
				+ data_timestamp + ",pay_success=" + pay_success + ",sign="
				+ sign + ",app_id=" + app_id + ",uid=" + uid + ",amount="
				+ amount + ",product_name=" + product_name + ",product_des="
				+ product_des + ",number=" + number + ",order_serial="
				+ order_serial + ",order_sign=" + order_sign;
	}
}
