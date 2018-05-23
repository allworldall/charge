package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class HaodongIOSFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "平 台 订 单 号", nullable = false)
	private String orderno;
	@DataValidate(description = "订 单 对 应 的 金 额", nullable = false)
	private String amount;		
	
	private String notifyurl;	//用 户 提 供 对 应 的notify_url
	
	private String server;		//角色所有服务器
	@DataValidate(description = "扩展信息", nullable = false)
	private String ext;
	@DataValidate(description = "数量(默认为 1)", nullable = false)
	private String count;
	@DataValidate(description = "加密秘钥", nullable = false)
	private String sign;
	
	private String player;		//角色名
	@DataValidate(description = "应用自己的订单号", nullable = false)
	private String code;
	
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
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getNotifyurl() {
		return notifyurl;
	}
	public void setNotifyurl(String notifyurl) {
		this.notifyurl = notifyurl;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public HaodongIOSFormBean() {
		super();
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",orderno=" + orderno + ",amount=" + amount
				+ ",notifyurl=" + notifyurl + ",server=" + server + ",ext="
				+ ext + ",count=" + count + ",sign=" + sign + ",player="
				+ player + ",code=" + code;
	}
}
