package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class PPTVChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "服务器标识，使用字母与数字的组合", nullable = false)
	private String sid;
	@DataValidate(description = "服务器角色id，使用字母与数字的组合，无特殊必要，可以为空（游戏方的）", nullable = false)
	private String roid;
	@DataValidate(description = "用户名， urlencode", nullable = false)
	private String username;
	@DataValidate(description = "订单号(pptv订单号)", nullable = false)
	private String oid;
	@DataValidate(description = "充值金额 元", nullable = false)
	private String amount;
	@DataValidate(description = "附属信息，需要urlencode。", nullable = false)
	//游戏方自己定义，建议使用游戏方自己的订单号，以表识此订单（不能用"," 和 "|"字符，可以用 "-" 尽量不要用其他特殊符号）"
	private String extra;
	@DataValidate(description = "充值发起时间，unix 时间戳", nullable = false)
	private String time;
	@DataValidate(description = "验证串，生成规则,", nullable = false)
	// "md5(sid + username + roid + oid + amount + time + key) ，
	// 为小写即对应字符串串联（没有 "+" 号)， key为 密钥，由PPTV给出注意：sign中的 username 为urlencode前的值"
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
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getRoid() {
		return roid;
	}
	public void setRoid(String roid) {
		this.roid = roid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
				+ cpName + ",sid=" + sid + ",roid=" + roid + ",username="
				+ username + ",oid=" + oid + ",amount=" + amount + ",extra="
				+ extra + ",time=" + time + ",sign=" + sign;
	}
	
}
