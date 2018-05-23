package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class GuoPanChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "果盘唯一订单号", nullable = false)
	private String trade_no;
	@DataValidate(description = "游戏方订单序列号", nullable = false)
	private String serialNumber;
	@DataValidate(description = "消费金额。单位是元，精确到分，如10.00。", nullable = false)
	private String money;
	@DataValidate(description = "状态:0=失败；1=成功；2=失败，原因是余额不足。", nullable = false)
	private String status;
	@DataValidate(description = "时间戳(果盘服务器发起通知的北京时间)", nullable = false)
	private String t;
	@DataValidate(description = "加密串", nullable = false)
	// sign=md5(serialNumber+money+status+t+SERVER_KEY) 是五个变量值拼接后经md5后的值，其中SERVER_KEY在果盘开放平台上获得。
	private String sign;
	@DataValidate(description = "", nullable = true)
	private String appid;
	@DataValidate(description = "", nullable = true)
	private String item_id;
	@DataValidate(description = "", nullable = true)
	private String item_price;
	@DataValidate(description = "", nullable = true)
	private String item_count;
	@DataValidate(description = "扩展参数，SDK发起支付时有传递，则这里会回传。", nullable = true)
	private String reserved;
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
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_price() {
		return item_price;
	}
	public void setItem_price(String item_price) {
		this.item_price = item_price;
	}
	public String getItem_count() {
		return item_count;
	}
	public void setItem_count(String item_count) {
		this.item_count = item_count;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",trade_no=" + trade_no + ",serialNumber="
				+ serialNumber + ",money=" + money + ",status=" + status
				+ ",t=" + t + ",sign=" + sign + ",appid=" + appid
				+ ",item_id=" + item_id + ",item_price=" + item_price
				+ ",item_count=" + item_count + ",reserved=" + reserved;
	}
    
}
