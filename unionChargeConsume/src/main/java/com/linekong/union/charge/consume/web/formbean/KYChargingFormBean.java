package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class KYChargingFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description = "RSA 加密的订单信息", nullable = false)
	private String notify_data;
	@DataValidate(description = "快用平台订单号", nullable = false)
	private String orderid;
	@DataValidate(description = "游戏订单号", nullable = false)
	private String dealseq;
	@DataValidate(description = "游戏帐号", nullable = false)
	private String uid;
	@DataValidate(description = "购买物品名", nullable = false)
	private String subject;
	@DataValidate(description = "版本号", nullable = false)
	private String v;
	@DataValidate(description = "RSA 签名", nullable = false)
	private String sign;
	
	@DataValidate(description = "支付金额", nullable = false)
	private String fee;
	@DataValidate(description = "支付结果", nullable = false)
	private String payresult;
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
	public String getNotify_data() {
		return notify_data;
	}
	public void setNotify_data(String notify_data) {
		this.notify_data = notify_data.replace(" ", "+").replace("\\", "");
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getDealseq() {
		return dealseq;
	}
	public void setDealseq(String dealseq) {
		this.dealseq = dealseq;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getPayresult() {
		return payresult;
	}
	public void setPayresult(String payresult) {
		this.payresult = payresult;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",notify_data=" + notify_data + ",orderid="
				+ orderid + ",dealseq=" + dealseq + ",uid=" + uid
				+ ",subject=" + subject + ",v=" + v + ",sign=" + sign
				+ ",fee=" + fee + ",payresult=" + payresult;
	}
}
