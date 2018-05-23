package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class CGameFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "充值来源", nullable = false)
	private String from;		//固定值,游戏sdk时值为sdk;平台网站时值为web
	@DataValidate(description = "平台用户唯一标识", nullable = false)
	private String userid;
	@DataValidate(description = "平台充值单号", nullable = false)
	private String orderid;
	@DataValidate(description = "充值金额，单位为人民币 分", nullable = false)
	private String money;
	@DataValidate(description = "游戏方充值单号", nullable = false)
	private String outorderid;
	@DataValidate(description = "游戏id", nullable = false)
	private String gameid;
	
	private String serverid;//区服id
	
	private String role;	//角色id
	
	private String ext1;	//扩展参数
	
	private String ext2;	//扩展参数
	@DataValidate(description = "linux时间戳，发起通知时间", nullable = false)
	private String time;
	@DataValidate(description = "校验串", nullable = false)
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
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getOutorderid() {
		return outorderid;
	}
	public void setOutorderid(String outorderid) {
		this.outorderid = outorderid;
	}
	public String getGameid() {
		return gameid;
	}
	public void setGameid(String gameid) {
		this.gameid = gameid;
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
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
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName=" + cpName
				+ ",from=" + from + ",userid=" + userid + ",orderid="
				+ orderid + ",money=" + money + ",outorderid=" + outorderid
				+ ", gameid=" + gameid + ",serverid=" + serverid + ",role="
				+ role + ",ext1=" + ext1 + ",ext2=" + ext2 + ",time=" + time
				+ ",sign=" + sign;
	}
	
}
