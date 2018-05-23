package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class TSChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "用户名", nullable = false)
	private String username;
	@DataValidate(description = "订单号", nullable = false)
	private String change_id;
	@DataValidate(description = "金额(元)", nullable = false)
	private String money;
	@DataValidate(description = "MD5(username|change_id|money|app_key)", nullable = false)
	private String hash;
	@DataValidate(description = "游戏客户端传回自定义扩展参数", nullable = false)
	private String object;
	
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
	public String getChange_id() {
		return change_id;
	}
	public void setChange_id(String change_id) {
		this.change_id = change_id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash.replace(" ", "+").replace("\\", "");
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",username=" + username + ",change_id="
				+ change_id + ",money=" + money + ",hash=" + hash
				+ ",object=" + object;
	}
	
	
}
