package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.Common;
import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class XunleiChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description = "充值订单号", nullable = false)
	private String orderid;
	@DataValidate(description = "充值用户", nullable = false)
	private String user;
	@DataValidate(description = "游戏币数量", nullable = false)
	private String gold;
	@DataValidate(description = "支付金额", nullable = false)
	private String money;
	@DataValidate(description = "UNIX时间戳", nullable = false)
	private String time;
	@DataValidate(description = "加密串", nullable = false)
	private String sign;
	@DataValidate(description = "服务器编号", nullable = false)
	private String server;
	@DataValidate(description = "用户ip", nullable = false)
	private String ip;
	@DataValidate(description = "扩展字段", nullable = false)
	private String ext;
	@DataValidate(description = "角色编号", nullable = true)
	private String roleid;
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
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = Common.urlDecode(user, "utf-8");
	}
	public String getGold() {
		return gold;
	}
	public void setGold(String gold) {
		this.gold = gold;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
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
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",orderid=" + orderid + ",user=" + user
				+ ",gold=" + gold + ",money=" + money + ",time=" + time
				+ ",sign=" + sign + ",server=" + server + ",ip=" + ip
				+ ",ext=" + ext + ",roleid=" + roleid;
	}
}
