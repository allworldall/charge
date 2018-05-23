package com.linekong.union.charge.consume.web.formbean;


import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * 快看接收参数封装的Bean
 * @author Administrator
 *
 */
public class KuaikanChargingFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description="应用编号" ,nullable = false)
	private String app_id;
	
	@DataValidate(description = "快看订单ID",nullable = false)
	private String order_id;
	
	@DataValidate(description = "游戏订单号" , nullable = false)
	private String out_order_id;
	
	@DataValidate(description = "用户在游戏应用的唯一标识" ,nullable = false)
	private String open_uid;
	
	@DataValidate(description = "商品ID" , nullable = false)
	private String wares_id ;
	
	@DataValidate(description = "交易流水号" ,nullable = false)
	private String trans_id;
	
	@DataValidate(description = "交易金额" ,nullable = false)
	private String trans_money;
	
	@DataValidate(description = "币种",nullable = false)
	private String currency;
	
	@DataValidate(description = "支付类型", nullable = false)
	private String pay_type;
	
	@DataValidate(description = "支付状态", nullable = false)
	private String pay_status;
	
	@DataValidate(description = "成交时间", nullable = false)
	private String trans_time;
	
	@DataValidate(description = "无实际意义", nullable = false)
	private String trans_result;

	@DataValidate(description = "签名" ,nullable = false)
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

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public String getOpen_uid() {
		return open_uid;
	}

	public void setOpen_uid(String open_uid) {
		this.open_uid = open_uid;
	}

	public String getWares_id() {
		return wares_id;
	}

	public void setWares_id(String wares_id) {
		this.wares_id = wares_id;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public String getTrans_money() {
		return trans_money;
	}

	public void setTrans_money(String trans_money) {
		this.trans_money = trans_money;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getTrans_time() {
		return trans_time;
	}

	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}

	public String getTrans_result() {
		return trans_result;
	}

	public void setTrans_result(String trans_result) {
		this.trans_result = trans_result;
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
				+ cpName + ",app_id=" + app_id + ",order_id=" + order_id
				+ ",out_order_id=" + out_order_id + ",open_uid=" + open_uid
				+ ",wares_id=" + wares_id + ",trans_id=" + trans_id
				+ ",trans_money=" + trans_money + ",currency=" + currency
				+ ",pay_type=" + pay_type + ",pay_status=" + pay_status
				+ ",trans_time=" + trans_time + ",trans_result="
				+ trans_result + ",sign=" + sign;
	}
}
