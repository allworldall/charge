package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class LePhoneChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "应用所属 AppId, 乐视平台分配的应用参数", nullable = false)
	private String app_id;
	@DataValidate(description = "乐视支付平台流水号, 由乐视支付平台生成", nullable = false)
	private String lepay_order_no;
	@DataValidate(description = "乐视用户 id", nullable = false)
	private String letv_user_id;
	@DataValidate(description = "支付 sdk 订单号, 与 cp 订单号对应", nullable = false)
	private String out_trade_no;
	@DataValidate(description = "支付时间", nullable = false)
	private String pay_time;
	@DataValidate(description = "支付金额", nullable = false)
	private String price;
	@DataValidate(description = "商品 id, cp 方商品 id", nullable = false)
	private String product_id;
	@DataValidate(description = "签名, 注意 null 要转换为 ”” ，并确保无乱码", nullable = false)
	private String sign;
	@DataValidate(description = "默认 MD5", nullable = false)
	private String sign_type;
	@DataValidate(description = "取值“ TRADE_SUCCESS”", nullable = false)
	private String trade_result;
	@DataValidate(description = "版本号： 2.0", nullable = false)
	private String version;
	@DataValidate(description = "cp 方支付订单号", nullable = false)
	private String cooperator_order_no;
	@DataValidate(description = "cp 自定义参数", nullable = false)
	private String extra_info;
	@DataValidate(description = "标识商品的原始金额，可以对比 original_price 和price 的值，如果相同则表示未使用卡券，如果原金额大，则表示使用了卡券。", nullable = false)
	private String original_price;
	
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
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
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
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getTrade_result() {
		return trade_result;
	}
	public void setTrade_result(String trade_result) {
		this.trade_result = trade_result;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCooperator_order_no() {
		return cooperator_order_no;
	}
	public void setCooperator_order_no(String cooperator_order_no) {
		this.cooperator_order_no = cooperator_order_no;
	}
	public String getExtra_info() {
		return extra_info;
	}
	public void setExtra_info(String extra_info) {
		this.extra_info = extra_info;
	}
	public String getOriginal_price() {
		return original_price;
	}
	public void setOriginal_price(String original_price) {
		this.original_price = original_price;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",app_id=" + app_id + ",lepay_order_no="
				+ lepay_order_no + ",letv_user_id=" + letv_user_id
				+ ",out_trade_no=" + out_trade_no + ",pay_time=" + pay_time
				+ ",price=" + price + ",product_id=" + product_id + ",sign="
				+ sign + ",sign_type=" + sign_type + ",trade_result="
				+ trade_result + ",version=" + version
				+ ",cooperator_order_no=" + cooperator_order_no
				+ ",extra_info=" + extra_info + ",original_price="
				+ original_price;
	}
}
