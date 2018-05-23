package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class QihuChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description = "应用app key", nullable = false)
	private String app_key;       
	
	@DataValidate(description = "应用自定义的商品id", nullable = false)
	private String product_id;     
	
	@DataValidate(description = "商品金额,以分为单位", nullable = false)
	private String amount;       
	
	@DataValidate(description = "应用分配给用户的id", nullable = false)
	private String app_uid;       
	
	private String app_ext1;       //应用扩展信息1原样返回
	
	private String app_ext2;       //应用扩展信息2原样返回
	
	@DataValidate(description = "360帐号id", nullable = false)
	private String user_id;       
	
	@DataValidate(description = "360返回的支付订单号", nullable = false)
	private String order_id;      
	
	@DataValidate(description = "如果支付返回成功，返回success应用需要确认是success才给用户加钱", nullable = false)
	private String gateway_flag;   
	
	@DataValidate(description = "定值 md5", nullable = false)
	private String sign_type;     
	
	private String app_order_id;   //应用订单号支付请求时传递，原样返回
	
	@DataValidate(description = "应用回传给订单核实接口的参数,不加入签名校验计算(不加入签名计算)", nullable = false)
	private String sign_return;   
	
	@DataValidate(description = "签名", nullable = false)
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

	public String getApp_key() {
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getApp_uid() {
		return app_uid;
	}

	public void setApp_uid(String app_uid) {
		this.app_uid = app_uid;
	}

	public String getApp_ext1() {
		return app_ext1;
	}

	public void setApp_ext1(String app_ext1) {
		this.app_ext1 = app_ext1;
	}

	public String getApp_ext2() {
		return app_ext2;
	}

	public void setApp_ext2(String app_ext2) {
		this.app_ext2 = app_ext2;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGateway_flag() {
		return gateway_flag;
	}

	public void setGateway_flag(String gateway_flag) {
		this.gateway_flag = gateway_flag;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getApp_order_id() {
		return app_order_id;
	}

	public void setApp_order_id(String app_order_id) {
		this.app_order_id = app_order_id;
	}

	public String getSign_return() {
		return sign_return;
	}

	public void setSign_return(String sign_return) {
		this.sign_return = sign_return;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",app_key=" + app_key + ",product_id="
				+ product_id + ",amount=" + amount + ",app_uid=" + app_uid
				+ ",app_ext1=" + app_ext1 + ",app_ext2=" + app_ext2
				+ ",user_id=" + user_id + ",order_id=" + order_id
				+ ",gateway_flag=" + gateway_flag + ",sign_type=" + sign_type
				+ ",app_order_id=" + app_order_id + ",sign_return="
				+ sign_return + ",sign=" + sign;
	}
	
	
}
