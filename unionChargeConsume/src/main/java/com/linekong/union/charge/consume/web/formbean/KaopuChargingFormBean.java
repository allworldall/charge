package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class KaopuChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "充值用户名", nullable = false)
	private String username;
	@DataValidate(description = "靠谱订单系统唯一号", nullable = false)
	private String kpordernum;
	@DataValidate(description = "游戏订单系统唯一号", nullable = false)
	private String ywordernum;
	@DataValidate(description = "订单状态", nullable = false)
	private String status;
	@DataValidate(description = "充值类型", nullable = false)
	private String paytype;
	@DataValidate(description = "成功充值金额，单位为分", nullable = false)
	private String amount;
	@DataValidate(description = "游戏区服 ", nullable = false)
	private String gameserver;
	@DataValidate(description = "充值失败错误码，成功为空。", nullable = true)
	private String errdesc;
	@DataValidate(description = "充值成功时间", nullable = false)
	private String paytime;
	@DataValidate(description = "游戏名称", nullable = false)
	private String gamename;
	@DataValidate(description = "sign", nullable = false)
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getKpordernum() {
		return kpordernum;
	}
	public void setKpordernum(String kpordernum) {
		this.kpordernum = kpordernum;
	}
	public String getYwordernum() {
		return ywordernum;
	}
	public void setYwordernum(String ywordernum) {
		this.ywordernum = ywordernum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getGameserver() {
		return gameserver;
	}
	public void setGameserver(String gameserver) {
		this.gameserver = gameserver;
	}
	public String getErrdesc() {
		return errdesc;
	}
	public void setErrdesc(String errdesc) {
		this.errdesc = errdesc;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	@Override
	public String toString() {
		return "KaopuChargingFormBean [gameName=" + gameName + ", cpName="
				+ cpName + ", username=" + username + ", kpordernum="
				+ kpordernum + ", ywordernum=" + ywordernum + ", status="
				+ status + ", paytype=" + paytype + ", amount=" + amount
				+ ", gameserver=" + gameserver + ", errdesc=" + errdesc
				+ ", paytime=" + paytime + ", gamename=" + gamename + ", sign="
				+ sign + "]";
	}
}
