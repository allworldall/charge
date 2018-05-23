package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class NeihanChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description = "通知ID", nullable = false)
	private String notify_id;
	@DataValidate(description = "通知类型", nullable = false)
	private String notify_type;
	@DataValidate(description = "通知时间", nullable = false)
	private String notify_time;
	@DataValidate(description = "支付订单状态", nullable = false)
	private String trade_status;
	@DataValidate(description = "支付类型", nullable = false)
	private String way;
	@DataValidate(description = "client_id", nullable = false)
	private String client_id;
	@DataValidate(description = "商品订单号", nullable = false)
	private String out_trade_no;
	@DataValidate(description = "支付流水号", nullable = true)
	private String trade_no;
	@DataValidate(description = "支付时间", nullable = true)
	private String pay_time;
	@DataValidate(description = "总费用", nullable = false)
	private String total_fee;
	@DataValidate(description = "购买者", nullable = false)
	private String buyer_id;
	@DataValidate(description = "头条签名", nullable = false)
	private String tt_sign;
	@DataValidate(description = "头条签名类型", nullable = false)
	private String tt_sign_type;
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
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getTt_sign() {
		return tt_sign;
	}
	public void setTt_sign(String tt_sign) {
		this.tt_sign = tt_sign.replace(" ", "+").replace("\\", "");
	}
	public String getTt_sign_type() {
		return tt_sign_type;
	}
	public void setTt_sign_type(String tt_sign_type) {
		this.tt_sign_type = tt_sign_type;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",notify_id=" + notify_id + ",notify_type="
				+ notify_type + ",notify_time=" + notify_time
				+ ",trade_status=" + trade_status + ",way=" + way
				+ ",client_id=" + client_id + ",out_trade_no=" + out_trade_no
				+ ",trade_no=" + trade_no + ",pay_time=" + pay_time
				+ ",total_fee=" + total_fee + ",buyer_id=" + buyer_id
				+ ",tt_sign=" + tt_sign + ",tt_sign_type=" + tt_sign_type;
	}
	
	
}
