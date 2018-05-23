package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class MeiZuChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "支付成功时间： CP 需要保存该时间进行后续的对账，对账时间以支付成功时间为统计", nullable = false)
	private String notify_time;
	@DataValidate(description = "通知 id", nullable = false)
	private String notify_id;
	@DataValidate(description = "订单 id", nullable = false)
	private String order_id;
	@DataValidate(description = "应用 id", nullable = false)
	private String app_id;
	@DataValidate(description = "用户 id", nullable = false)
	private String uid;
	@DataValidate(description = "商户 id", nullable = false)
	private String partner_id;
	@DataValidate(description = "游戏订单 id", nullable = false)
	private String cp_order_id;
	@DataValidate(description = "产品 id", nullable = false)
	private String product_id;
	@DataValidate(description = "产品单位", nullable = true)
	private String product_unit;
	@DataValidate(description = "购买数量", nullable = true)
	private String buy_amount;
	@DataValidate(description = "产品单价", nullable = true)
	private String product_per_price;
	@DataValidate(description = "购买总价", nullable = false)
	private String total_price;
	@DataValidate(description = "交易状态：1：待支付（订单已创建）2： 支付中3：已支付4：取消订单5：未知异常取消订单", nullable = false)
	private String trade_status;
	@DataValidate(description = "订单时间", nullable = false)
	private String create_time;
	@DataValidate(description = "支付时间", nullable = false)
	private String pay_time;
	@DataValidate(description = "支付类型： 1 不定金额充值， 0 购买", nullable = true)
	private String pay_type;
	@DataValidate(description = "用户自定义信息", nullable = true)
	private String user_info;
	@DataValidate(description = "参数签名", nullable = false)
	private String sign;
	@DataValidate(description = "签名类型，常量 md5", nullable = false)
	private String sign_type;
	
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

	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String getCp_order_id() {
		return cp_order_id;
	}
	public void setCp_order_id(String cp_order_id) {
		this.cp_order_id = cp_order_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_unit() {
		return product_unit;
	}

	public void setProduct_unit(String product_unit) {
		this.product_unit = product_unit;
	}

	public String getBuy_amount() {
		return buy_amount;
	}

	public void setBuy_amount(String buy_amount) {
		this.buy_amount = buy_amount;
	}

	public String getProduct_per_price() {
		return product_per_price;
	}

	public void setProduct_per_price(String product_per_price) {
		this.product_per_price = product_per_price;
	}

	public String getTotal_price() {
		return total_price;
	}


	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}


	public String getTrade_status() {
		return trade_status;
	}


	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}


	public String getCreate_time() {
		return create_time;
	}


	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}


	public String getPay_time() {
		return pay_time;
	}


	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}


	public String getPay_type() {
		return pay_type;
	}


	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}


	public String getUser_info() {
		return user_info;
	}


	public void setUser_info(String user_info) {
		this.user_info = user_info;
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

	@Override
	public String toString() {
		return "MeiZuChargingFormBean [gameName=" + gameName + ", cpName="
				+ cpName + ", notify_time=" + notify_time + ", notify_id="
				+ notify_id + ", order_id=" + order_id + ", app_id=" + app_id
				+ ", uid=" + uid + ", partner_id=" + partner_id
				+ ", cp_order_id=" + cp_order_id + ", product_id=" + product_id
				+ ", product_unit=" + product_unit + ", buy_amount="
				+ buy_amount + ", product_per_price=" + product_per_price
				+ ", total_price=" + total_price + ", trade_status="
				+ trade_status + ", create_time=" + create_time + ", pay_time="
				+ pay_time + ", pay_type=" + pay_type + ", user_info="
				+ user_info + ", sign=" + sign + ", sign_type=" + sign_type
				+ "]";
	}
	
}
