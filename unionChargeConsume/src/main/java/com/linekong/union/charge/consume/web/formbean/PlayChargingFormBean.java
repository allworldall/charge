package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class PlayChargingFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "应用标识：     ", nullable = false)
	private String site;
	@DataValidate(description = "服务器ID：      ", nullable = false)
	private String sid;
	@DataValidate(description = "渠道平台用户ID", nullable = false)
	private String uid;
	@DataValidate(description = "渠道订单号", nullable = false)
	private String order_id;
	@DataValidate(description = "游戏订单号(lk)", nullable = false)
	private String cp_order_id;
	@DataValidate(description = "游戏角色ID", nullable = false)
	private String roleid;
	@DataValidate(description = "游戏角色名", nullable = false)
	private String rolename;
	@DataValidate(description = "支付金额（元）", nullable = false)
	private String order_money;
	@DataValidate(description = "商品ID", nullable = false)
	private String productid;
	@DataValidate(description = "支付方式（1 苹果 2 支付宝 3 微信）", nullable = false)
	private String pay_type;
	@DataValidate(description = "透传字 ", nullable = true)
	private String ext;
	@DataValidate(description = "签名 ", nullable = false)
	private String sign;
	@DataValidate(description = "时间戳 ", nullable = false)
	private String time;
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
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCp_order_id() {
		return cp_order_id;
	}
	public void setCp_order_id(String cp_order_id) {
		this.cp_order_id = cp_order_id;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getOrder_money() {
		return order_money;
	}
	public void setOrder_money(String order_money) {
		this.order_money = order_money;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",site=" + site + ",sid=" + sid + ",uid=" + uid
				+ ",order_id=" + order_id + ",cp_order_id=" + cp_order_id
				+ ",roleid=" + roleid + ",rolename=" + rolename
				+ ",order_money=" + order_money + ",productid=" + productid
				+ ",pay_type=" + pay_type + ",ext=" + ext + ",sign=" + sign
				+ ",time=" + time;
	}
	
}
