package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class SouGouChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "game id。由平台分配的游戏编号", nullable = false)
	private String gid;
	@DataValidate(description = "server id。由平台分配的游戏服务", nullable = false)
	private String sid;
	@DataValidate(description = "user id。平台的用户id", nullable = false)
	private String uid;
	@DataValidate(description = "如游戏为充值到角色，传角色名。默认会传空。", nullable = true)
	private String role;
	@DataValidate(description = "订单号，同一订单有可能多次发送通知", nullable = false)
	private String oid;
	@DataValidate(description = "订单创建日期，格式为yyMMdd", nullable = false)
	private String date;
	@DataValidate(description = "金额(人民币元)", nullable = false)
	private String amount1;
	@DataValidate(description = "金额(游戏币数量)", nullable = false)
	private String amount2;
	@DataValidate(description = "此时间并不是订单的产生或支付时间，而是通知发送的时间，也即当前时间", nullable = false)
	private String time;
	@DataValidate(description = "验证字符串, 生成方式同auth token, 区别是在第三步, 附加支付秘钥而不是app secret", nullable = false)
	private String auth;
	@DataValidate(description = "透传信息", nullable = false)
	private String appdata;
	@DataValidate(description = "真实付款", nullable = false)
	private String realAmount;
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
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmount1() {
		return amount1;
	}
	public void setAmount1(String amount1) {
		this.amount1 = amount1;
	}
	public String getAmount2() {
		return amount2;
	}
	public void setAmount2(String amount2) {
		this.amount2 = amount2;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth.replace(" ", "+").replace("\\", "");
	}
	public String getAppdata() {
		return appdata;
	}
	public void setAppdata(String appdata) {
		this.appdata = appdata;
	}
	public String getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",gid=" + gid + ",sid=" + sid + ",uid=" + uid
				+ ",role=" + role + ",oid=" + oid + ",date=" + date
				+ ",amount1=" + amount1 + ",amount2=" + amount2 + ",time="
				+ time + ",auth=" + auth + ",appdata=" + appdata
				+ ",realAmount=" + realAmount;
	}

	
}
