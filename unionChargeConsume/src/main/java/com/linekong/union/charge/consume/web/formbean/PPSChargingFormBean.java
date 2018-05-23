package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class PPSChargingFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "用户id", nullable = false)
	private String user_id;
	@DataValidate(description = "角色ID，没有传空", nullable = false)
	private String role_id;
	@DataValidate(description = "平台订单号（唯一）", nullable = false)
	private String order_id;
	@DataValidate(description = "充值金额（人民币，单位元）", nullable = false)
	private String money;
	@DataValidate(description = "时间戳 time()", nullable = false)
	private String time;
	@DataValidate(description = "回传参数(urlencode)", nullable = false)
	//注意：该参数内容为游戏方自定义内容,如游戏方服务器ID等。PPS将原样传回给游戏方
	private String userData;
	@DataValidate(description = "经过加密后的签名", nullable = false)
	//sign= MD5($user_id.$role_id.$order_id.$money.$time.$key)
	//注意：上面的 MD5函数中的“.” 号实为PHP语言字符串拼接符，等同于Java语言中的”+”号，非常规意义的点号
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
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
				+ cpName + ",user_id=" + user_id + ",role_id=" + role_id
				+ ",order_id=" + order_id + ",money=" + money + ",time="
				+ time + ",userData=" + userData + ",sign=" + sign;
	}
	 
}
