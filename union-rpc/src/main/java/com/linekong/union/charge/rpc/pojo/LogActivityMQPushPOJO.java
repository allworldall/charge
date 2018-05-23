package com.linekong.union.charge.rpc.pojo;

import java.io.Serializable;

/**
 * MQ推送订单信息
 * 字段如无，则默认传0
 * 游戏回调成功
 */
public class LogActivityMQPushPOJO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 554756625560681382L;
	private int				gameId;							//游戏ID
	private String			userId;							//通行证ID
	private String			userName;						//通行证名称
	private int				gatewayId;						//网关ID
	private int				chargeChannelId;				//充值渠道ID
	private double			discount;						//充值渠道折扣
	private int				chargeSubjectId;				//充值游戏的货币类型
	private double			chargeMoney;					//充值货币数
	private int				chargeAmount;					//充值元宝数
	private String			chargeOrderCode;				//联运订单号
	private long			chargeDetailId;					//充值订单号
	private int				chargeType;						//充值类型1直充
	private int				moneyType;						//货币类型 1 人民币
	private String			attachCode;						//透传字段信息
	private long			roleId;							//角色ID
	private String			chargeTime;						//下单时间 (时间戳，秒级 或 yyyy-MM-dd HH:mm:ss)
	private String			serverIp;						//服务器IP
	private String 			clientIp;						//请求IP

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(int gatewayId) {
		this.gatewayId = gatewayId;
	}

	public int getChargeChannelId() {
		return chargeChannelId;
	}

	public void setChargeChannelId(int chargeChannelId) {
		this.chargeChannelId = chargeChannelId;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getChargeSubjectId() {
		return chargeSubjectId;
	}

	public void setChargeSubjectId(int chargeSubjectId) {
		this.chargeSubjectId = chargeSubjectId;
	}

	public double getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(double chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	public int getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(int chargeAmount) {
		this.chargeAmount = chargeAmount;
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

	public int getChargeType() {
		return chargeType;
	}

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}

	public int getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(int moneyType) {
		this.moneyType = moneyType;
	}

	public String getAttachCode() {
		return attachCode;
	}

	public void setAttachCode(String attachCode) {
		this.attachCode = attachCode;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}
	
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public LogActivityMQPushPOJO() {
		super();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("gameId=").append(gameId);
		sb.append(",userId=").append(userId);
		sb.append(",userName=").append(userName);
		sb.append(",gatewayId=").append(gatewayId);
		sb.append(",chargeChannelId=").append(chargeChannelId);
		sb.append(",discount=").append(discount);
		sb.append(",chargeSubjectId=").append(chargeSubjectId);
		sb.append(",chargeMoney=").append(chargeMoney);
		sb.append(",chargeAmount=").append(chargeAmount);
		sb.append(",chargeOrderCode=").append(chargeOrderCode);
		sb.append(",chargeDetailId=").append(chargeDetailId);
		sb.append(",chargeType=").append(chargeType);
		sb.append(",moneyType=").append(moneyType);
		sb.append(",attachCode=").append(attachCode);
		sb.append(",roleId=").append(roleId);
		sb.append(",chargeTime=").append(chargeTime);
		sb.append(",serverIp=").append(serverIp);
		sb.append(",clientIp=").append(clientIp);
		return sb.toString();
	}
}
