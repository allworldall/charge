package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class JinLiChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "商户APIKey", nullable = false)
	private String api_key;
	@DataValidate(description = "支付订单关闭时间，格式为yyyyMMddHHmmss", nullable = false)
	//(2009年12月27日9点10分10秒表示为20091227091010)，时区为GMT+8 beijing。
	private String close_time;
	@DataValidate(description = "支付订单创建时间，格式为yyyyMMddHHmmss", nullable = false)
	//(2009年12月27日9点10分10秒表示为20091227091010)，时区为GMT+8 beijing。
	private String create_time;
	@DataValidate(description = "商品总金额", nullable = false)
	private String deal_price;
	@DataValidate(description = "商户订单号", nullable = false)
	private String out_order_no;
	@DataValidate(description = "用户支付方式(A币支付：100)", nullable = false)
	//(2009年12月27日9点10分10秒表示为20091227091010)，时区为GMT+8 beijing。该返回值与请求创建支付订单的“submit_time”字段值一致
	private Integer pay_channel;
	@DataValidate(description = "商户提交订单时间，格式为yyyyMMddHHmmss", nullable = false)
	private String submit_time;
	@DataValidate(description = "返回null", nullable = false)
	private String user_id;
	@DataValidate(description = "以上字段按照字母顺序都参与签名，同订单创建时签名。", nullable = false)
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
	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public String getClose_time() {
		return close_time;
	}
	public void setClose_time(String close_time) {
		this.close_time = close_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getDeal_price() {
		return deal_price;
	}
	public void setDeal_price(String deal_price) {
		this.deal_price = deal_price;
	}
	public String getOut_order_no() {
		return out_order_no;
	}
	public void setOut_order_no(String out_order_no) {
		this.out_order_no = out_order_no;
	}
	public Integer getPay_channel() {
		return pay_channel;
	}
	public void setPay_channel(Integer pay_channel) {
		this.pay_channel = pay_channel;
	}
	public String getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
				+ cpName + ",api_key=" + api_key + ",close_time="
				+ close_time + ",create_time=" + create_time + ",deal_price="
				+ deal_price + ",out_order_no=" + out_order_no
				+ ",pay_channel=" + pay_channel + ",submit_time="
				+ submit_time + ",user_id=" + user_id + ",sign=" + sign;
	}
}
