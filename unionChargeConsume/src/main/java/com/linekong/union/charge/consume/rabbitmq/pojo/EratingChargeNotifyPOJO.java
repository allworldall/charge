package com.linekong.union.charge.consume.rabbitmq.pojo;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class EratingChargeNotifyPOJO {
	
	@DataValidate(description = "游戏ID", nullable = false)
	private int		gameId;				//游戏ID
	
	private String	userName;			//通行证名称
	@DataValidate(description = "通行证ID", nullable = false)
	private String	userId;				//通行证ID
	
	private int		gatewayId;			//充值区服ID
	@DataValidate(description = "联运充值订单号", nullable = false)
	private String	chargeOrderCode;	//联运充值订单号
	@DataValidate(description = "蓝港充值订单号", nullable = false)
	private long	chargeDetailId;		//蓝港充值订单号
	@DataValidate(description = "状态", nullable = false)
	private int		status;				//状态 1,成功，失败，Erating业务错误代码
	
	private String  reason;				//出错原因
	
	private String serverIp;
	
	private String clientIp;		

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(int gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getChargeOrderCode() {
		return chargeOrderCode;
	}

	public void setChargeOrderCode(String chargeOrderCode) {
		this.chargeOrderCode = chargeOrderCode;
	}

	public long getChargeDetailId() {
		return chargeDetailId;
	}

	public void setChargeDetailId(long chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		if(serverIp ==null){
			this.serverIp = "";
		}
		this.serverIp = serverIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		if(clientIp ==null){
			this.clientIp = "";
		}
		this.clientIp = clientIp;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("gameId=").append(gameId);
		sb.append(",userName=").append(userName);
		sb.append(",userId=").append(userId);
		sb.append(",gatewayId=").append(gatewayId);
		sb.append(",chargeOrderCode=").append(chargeOrderCode);
		sb.append(",chargeDetailId=").append(chargeDetailId);
		sb.append(",status=").append(status);
		sb.append(",reason=").append(reason);
		sb.append(",serverIp=").append(serverIp);
		sb.append(",clientIp=").append(clientIp);
		return sb.toString();
	}
}
