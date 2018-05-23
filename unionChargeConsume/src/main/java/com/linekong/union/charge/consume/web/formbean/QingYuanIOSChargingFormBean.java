package com.linekong.union.charge.consume.web.formbean;

import java.io.UnsupportedEncodingException;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class QingYuanIOSChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "商户订单号", nullable = false)
	private String orderid;
	@DataValidate(description = "交易流水号", nullable = false)
	private String transid;
	@DataValidate(description = "支付渠道", nullable = false)
	private String channel;		//1=微信，2=支付宝，3=AppStore 内购
	@DataValidate(description = "应用 ID ", nullable = false)
	private String appid;
	
	private String ordername;	//商品名称
	@DataValidate(description = "订单价格", nullable = false)
	private String price;		//单位为元
	@DataValidate(description = "用户 ID", nullable = false)
	private String userid;
	@DataValidate(description = "用户名", nullable = false)
	private String username;
	
	private String attach;		//商户私有信息
	@DataValidate(description = "订单状态", nullable = false)
	private String status;		//3=系统异常，4=交易失败，5=交易成功
	@DataValidate(description = "沙盒环境  0=正式环境，1=沙盒环境", nullable = false)
	private String sandbox;
	@DataValidate(description = "调用订单创建接口的时间", nullable = false)
	private String createat;
	@DataValidate(description = "用户开始支付的时间", nullable = false)
	private String startat;
	@DataValidate(description = "支付时间", nullable = false)
	private String payat;
	@DataValidate(description = "参数签名", nullable = false)
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
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getOrdername() {
		return ordername;
	}
	public void setOrdername(String ordername) throws UnsupportedEncodingException {
		this.ordername = ordername;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) throws UnsupportedEncodingException {
		this.username = username;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) throws UnsupportedEncodingException {
		this.attach = attach;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSandbox() {
		return sandbox;
	}
	public void setSandbox(String sandbox) {
		this.sandbox = sandbox;
	}
	public String getCreateat() {
		return createat;
	}
	public void setCreateat(String createat) {
		this.createat = createat;
	}
	public String getStartat() {
		return startat;
	}
	public void setStartat(String startat) {
		this.startat = startat;
	}
	public String getPayat() {
		return payat;
	}
	public void setPayat(String payat) {
		this.payat = payat;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+");
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",orderid=" + orderid + ",transid=" + transid
				+ ",channel=" + channel + ",appid=" + appid + ",ordername="
				+ ordername + ",price=" + price + ",userid=" + userid
				+ ",username=" + username + ",attach=" + attach + ",status="
				+ status + ",sandbox=" + sandbox + ",createat=" + createat
				+ ",startat=" + startat + ",payat=" + payat + ",sign="
				+ sign;
	}
}
