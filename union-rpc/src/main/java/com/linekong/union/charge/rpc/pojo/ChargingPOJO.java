package com.linekong.union.charge.rpc.pojo;

import java.io.Serializable;
import java.util.Date;

public class ChargingPOJO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4750372794842865671L;
	
	private	String		unionCode;			//联运代码标识
	
	private String		orderId;			//联运充值订单号
	
	private String		paymentId;			//预支付ID
	
	private int			cpId;				//合作伙伴ID
	
	private String		userName;			//充值账号

	private int			gameId;				//充值游戏ID

	private int			gatewayId;			//网关ID
	
	private Date		chargeTime;			//充值时间

	private float		chargeMoney;		//充值金额

	private int			chargeAmount;		//充值元宝数
	
	private String		serverIP;			//服务器IP
	
	private String		requestIP;			//请求IP

	private long		chargeDetailId;		//蓝港充值订单号
	
	private String		attachCode;			//充值透传字段
	
	private String		channelId;			//充值订单号
	
	private String      expandInfo;		    //拓展信息
	
	private String		platformName;		//合作伙伴名称
	
	public String getUnionCode() {
		return unionCode;
	}
	public void setUnionCode(String unionCode) {
		this.unionCode = unionCode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(int gatewayId) {
		this.gatewayId = gatewayId;
	}
	public Date getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}
	public float getChargeMoney() {
		return chargeMoney;
	}
	public void setChargeMoney(float chargeMoney) {
		this.chargeMoney = chargeMoney;
	}
	public int getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(int chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getRequestIP() {
		return requestIP;
	}
	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
	}
	
	public long getChargeDetailId() {
		return chargeDetailId;
	}
	public void setChargeDetailId(long chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}
	public String getAttachCode() {
		return attachCode;
	}
	public void setAttachCode(String attachCode) {
		this.attachCode = attachCode;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getExpandInfo() {
		return expandInfo;
	}
	public void setExpandInfo(String expandInfo) {
		this.expandInfo = expandInfo;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	public int getCpId() {
		return cpId;
	}
	public void setCpId(int cpId) {
		this.cpId = cpId;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("unionCode=").append(unionCode);
		sb.append(",orderId=").append(orderId);
		sb.append(",paymentId=").append(paymentId);
		sb.append(",cpId=").append(cpId);
		sb.append(",userName=").append(userName);
		sb.append(",gameId=").append(gameId);
		sb.append(",gatewayId=").append(gatewayId);
		sb.append(",chargeTime=").append(chargeTime);
		sb.append(",chargeMoney=").append(chargeMoney);
		sb.append(",chargeAmount=").append(chargeAmount);
		sb.append(",serverIP=").append(serverIP);
		sb.append(",requestIP=").append(requestIP);
		sb.append(",chargeDetailId=").append(chargeDetailId);
		sb.append(",attachCode=").append(attachCode);
		sb.append(",channelId=").append(channelId);
		sb.append(",expandInfo=").append(expandInfo);
		sb.append(",platformName=").append(platformName);
		return sb.toString();
	}
}
