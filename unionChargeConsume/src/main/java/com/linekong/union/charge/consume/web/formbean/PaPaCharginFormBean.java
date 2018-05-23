package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class PaPaCharginFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "商户标识", nullable = false)
	private String app_key;
	@DataValidate(description = "游戏方内部订单号", nullable = false)
	private String app_order_id;
	@DataValidate(description = "区标识(数字)", nullable = false)
	private String app_district;
	@DataValidate(description = "服标识(数字)", nullable = false)
	private String app_server;
	@DataValidate(description = "用户角色编号", nullable = false)
	private String app_user_id;
	@DataValidate(description = "用户角色名称", nullable = false)
	private String app_user_name;
	@DataValidate(description = "购买产品编号", nullable = false)
	private String product_id;
	@DataValidate(description = "购买产品名称", nullable = false)
	private String product_name;
	@DataValidate(description = "金额(单元:元)", nullable = false)
	private String money_amount;
	@DataValidate(description = "登录后返回的用户编号", nullable = false)
	private String pa_open_uid;
	@DataValidate(description = "附加信息(回调时原样返回)", nullable = false)
	private String app_extra1;
	@DataValidate(description = "附加信息(回调时原样返回)", nullable = false)
	private String app_extra2;
	@DataValidate(description = "啪啪订单号", nullable = false)
	private String pa_open_order_id;
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

	public String getApp_order_id() {
		return app_order_id;
	}

	public void setApp_order_id(String app_order_id) {
		this.app_order_id = app_order_id;
	}

	public String getApp_district() {
		return app_district;
	}

	public void setApp_district(String app_district) {
		this.app_district = app_district;
	}

	public String getApp_server() {
		return app_server;
	}

	public void setApp_server(String app_server) {
		this.app_server = app_server;
	}

	public String getApp_user_id() {
		return app_user_id;
	}

	public void setApp_user_id(String app_user_id) {
		this.app_user_id = app_user_id;
	}

	public String getApp_user_name() {
		return app_user_name;
	}

	public void setApp_user_name(String app_user_name) {
		this.app_user_name = app_user_name;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getMoney_amount() {
		return money_amount;
	}

	public void setMoney_amount(String money_amount) {
		this.money_amount = money_amount;
	}

	public String getPa_open_uid() {
		return pa_open_uid;
	}

	public void setPa_open_uid(String pa_open_uid) {
		this.pa_open_uid = pa_open_uid;
	}

	public String getApp_extra1() {
		return app_extra1;
	}

	public void setApp_extra1(String app_extra1) {
		this.app_extra1 = app_extra1;
	}

	public String getApp_extra2() {
		return app_extra2;
	}

	public void setApp_extra2(String app_extra2) {
		this.app_extra2 = app_extra2;
	}

	public String getPa_open_order_id() {
		return pa_open_order_id;
	}

	public void setPa_open_order_id(String pa_open_order_id) {
		this.pa_open_order_id = pa_open_order_id;
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
				+ cpName + ",app_key=" + app_key + ",app_order_id="
				+ app_order_id + ",app_district=" + app_district
				+ ", app_server=" + app_server + ", app_user_id=" + app_user_id
				+ ", app_user_name=" + app_user_name + ", product_id="
				+ product_id + ", product_name=" + product_name
				+ ", money_amount=" + money_amount + ", pa_open_uid="
				+ pa_open_uid + ", app_extra1=" + app_extra1 + ", app_extra2="
				+ app_extra2 + ", pa_open_order_id=" + pa_open_order_id
				+ ", sign=" + sign;
	}

}
