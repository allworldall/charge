package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class DouyuChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "用户登录的游戏服务器ID", nullable = false)
	private String serverId;
	@DataValidate(description = "客户端传入的自定义数据， 字符和数字组成", nullable = false)
	private String callbackInfo;
	@DataValidate(description = "cp 充值时指定的角色名(roleName玩家的角色名)", nullable = false)
	private String openId;
	@DataValidate(description = "斗鱼充值订单订单号", nullable = false)
	private int orderId;
	@DataValidate(description = "订单状态,1 为成功，其他状态均为失败", nullable = false)
	private String orderStatus;
	@DataValidate(description = "目前只有 ALIPAY", nullable = false)
	private String payType;
	@DataValidate(description = "成功充值金额（人民币，单位为元，浮点数）", nullable = false)
	private float amount;
	@DataValidate(description = "备注说明", nullable = false)
	private String remark;
	@DataValidate(description = "充值回调校验签名", nullable = false)
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
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getCallbackInfo() {
		return callbackInfo;
	}
	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
				+ cpName + ",serverId=" + serverId + ",callbackInfo="
				+ callbackInfo + ",openId=" + openId + ",orderId=" + orderId
				+ ",orderStatus=" + orderStatus + ",payType=" + payType
				+ ",amount=" + amount + ",remark=" + remark + ",sign="
				+ sign;
	}

}
