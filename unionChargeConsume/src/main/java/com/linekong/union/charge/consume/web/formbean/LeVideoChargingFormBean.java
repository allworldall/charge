package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class LeVideoChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "版本号", nullable = false)
	private String version;
	@DataValidate(description = "交易类型; 支付： pay；退款： refund", nullable = false)
	private String trade_type;
	@DataValidate(description = "交易结果; 取值 TRADE_SUCCESS", nullable = false)
	private String trade_result;
	@DataValidate(description = "业务方业务线 id", nullable = false)
	private String merchant_business_id;
	@DataValidate(description = "业务方订单号; 和 out_trade_no 值相同", nullable = false)
	private String merchant_no;
	@DataValidate(description = "业务方流水号; 商户构造时传入.支付方订单号", nullable = false)
	private String out_trade_no;
	@DataValidate(description = "乐视支付平台流水号，乐视平台生成", nullable = false)
	private String lepay_order_no;
	@DataValidate(description = "乐视集团用户 id, 商户构造时传入", nullable = false)
	private String letv_user_id;
	@DataValidate(description = "交易金额, 商户构造时传入,单位：元", nullable = false)
	private String price;
	@DataValidate(description = "产品 id, 商户构造时传入", nullable = true)
	private String product_id;
	@DataValidate(description = "产品 name, 商户构造时传入", nullable = true)
	private String product_name;
	@DataValidate(description = "产品描述, 商户构造时传入", nullable = true)
	private String product_desc;
	@DataValidate(description = "产品图片, 商户构造时传入", nullable = true)
	private String product_urls;
	@DataValidate(description = "交易时间, 格式： YYYY-MM-DD hh:mm:ss", nullable = true)
	private String pay_time;
	@DataValidate(description = "服务器回调 url, 商户构造时传入", nullable = true)
	private String notify_url;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTrade_result() {
		return trade_result;
	}
	public void setTrade_result(String trade_result) {
		this.trade_result = trade_result;
	}
	public String getMerchant_business_id() {
		return merchant_business_id;
	}
	public void setMerchant_business_id(String merchant_business_id) {
		this.merchant_business_id = merchant_business_id;
	}
	public String getMerchant_no() {
		return merchant_no;
	}
	public void setMerchant_no(String merchant_no) {
		this.merchant_no = merchant_no;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getLepay_order_no() {
		return lepay_order_no;
	}
	public void setLepay_order_no(String lepay_order_no) {
		this.lepay_order_no = lepay_order_no;
	}
	public String getLetv_user_id() {
		return letv_user_id;
	}
	public void setLetv_user_id(String letv_user_id) {
		this.letv_user_id = letv_user_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}
	public String getProduct_urls() {
		return product_urls;
	}
	public void setProduct_urls(String product_urls) {
		this.product_urls = product_urls;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
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
				+ cpName + ",version=" + version + ",trade_type="
				+ trade_type + ",trade_result=" + trade_result
				+ ",merchant_business_id=" + merchant_business_id
				+ ",merchant_no=" + merchant_no + ",out_trade_no="
				+ out_trade_no + ",lepay_order_no=" + lepay_order_no
				+ ",letv_user_id=" + letv_user_id + ",price=" + price
				+ ",product_id=" + product_id + ",product_name="
				+ product_name + ",product_desc=" + product_desc
				+ ",product_urls=" + product_urls + ",pay_time=" + pay_time
				+ ",notify_url=" + notify_url + ",sign=" + sign;
	}
	
}
