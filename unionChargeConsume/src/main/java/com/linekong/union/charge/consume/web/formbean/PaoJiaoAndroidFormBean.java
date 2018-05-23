package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * 泡椒Android渠道
 * @author Administrator
 *
 */
public class PaoJiaoAndroidFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "付款用户在泡椒网的唯一标识", nullable = false)
	private String uid;
	@DataValidate(description = "付款订单在泡椒网的订单号", nullable = false)
	private String orderNo;
	@DataValidate(description = "订单金额，浮点类型，单位为：RMB元", nullable = false)
	private String price;
	@DataValidate(description = "订单状态固定为5，支付失败的情况不会通知", nullable = false)
	private String status;
	
	private String remark;   //订单的备注
	
	private String subject;	 //订单标题
	@DataValidate(description = "接入时获取的appId或gamId", nullable = false)
	private String gameId;
	@DataValidate(description = "付款时间，格式为：2014-07-21 08:28:26", nullable = false)
	private String payTime;	
	@DataValidate(description = "自定义扩展数据", nullable = false)
	private String ext;
	@DataValidate(description = "根据请求内容产生的加密串", nullable = false)
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
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
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",uid=" + uid + ",orderNo=" + orderNo + ",price="
				+ price + ",status=" + status + ",remark=" + remark
				+ ",subject=" + subject + ",gameId=" + gameId + ",payTime="
				+ payTime + ",ext=" + ext + ",sign=" + sign;
	}
	
	
}
