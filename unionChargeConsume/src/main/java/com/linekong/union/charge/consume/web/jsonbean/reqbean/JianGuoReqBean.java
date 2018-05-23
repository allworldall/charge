package com.linekong.union.charge.consume.web.jsonbean.reqbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class JianGuoReqBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "订单号", nullable = false)
	private String order_id;
	@DataValidate(description = "玩家ID", nullable = false)
	private String mem_id;
	@DataValidate(description = "游戏ID", nullable = false)
	private String app_id;
	@DataValidate(description = "充值金额 (单位：元)", nullable = false)
	private String money;
	@DataValidate(description = "支付状态", nullable = false)
	private String order_status;	//1 未支付  2成功支付 3支付失败
	@DataValidate(description = "时间戳", nullable = false)
	private String paytime;			// Unix timestamp
	@DataValidate(description = "CP扩展参数", nullable = false)
	private String attach;
	@DataValidate(description = "SIGN", nullable = false)
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+");
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName=" + cpName
				+ ",order_id=" + order_id + ",mem_id=" + mem_id + ",app_id="
				+ app_id + ",money=" + money + ",order_status="
				+ order_status + ",paytime=" + paytime + ",attach=" + attach
				+ ",sign=" + sign;
	}
}
