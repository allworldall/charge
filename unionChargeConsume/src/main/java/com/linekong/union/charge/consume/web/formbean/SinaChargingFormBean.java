package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class SinaChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "订单号", nullable = false)
	private String order_id;
	@DataValidate(description = "支付金额", nullable = false)
	private String amount;
	@DataValidate(description = "支付用户id", nullable = false)
	private String order_uid;
	@DataValidate(description = "应用的appkey", nullable = false)
	private String source;
	@DataValidate(description = "实际支付金额", nullable = false)
	private String actual_amount;
	@DataValidate(description = "用于参数校验的签名，生成办法参考附录一", nullable = false)
	private String signature;
	@DataValidate(description = "透传字段", nullable = false)
	private String pt;
	
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrder_uid() {
		return order_uid;
	}
	public void setOrder_uid(String order_uid) {
		this.order_uid = order_uid;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getActual_amount() {
		return actual_amount;
	}
	public void setActual_amount(String actual_amount) {
		this.actual_amount = actual_amount;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature.replace(" ", "+").replace("\\", "");
	}
	public String getPt() {
		return pt;
	}
	public void setPt(String pt) {
		this.pt = pt;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",order_id=" + order_id + ",amount=" + amount
				+ ",order_uid=" + order_uid + ",source=" + source
				+ ",actual_amount=" + actual_amount + ",signature="
				+ signature + ",pt=" + pt;
	}

}
