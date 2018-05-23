package com.linekong.union.charge.consume.rabbitmq.pojo;

import java.text.SimpleDateFormat;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.rpc.pojo.ChargingPOJO;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;
/**
 * MQ推送订单信息
 * 字段如无，则默认传0
 * 
 */
public class PushOrderWithMQInfoPOJO{
	@DataValidate(description = "游戏ID", nullable = false)
	private int				gameId;							//游戏ID
	@DataValidate(description = "通行证ID", nullable = false)
	private String			userId;							//通行证ID
	@DataValidate(description = "通行证名称", nullable = false)
	private String			userName;						//通行证名称
	@DataValidate(description = "网关ID", nullable = false)
	private int				gatewayId;						//网关ID
	@DataValidate(description = "充值渠道ID", nullable = false)
	private int				chargeChannelId;				//充值渠道ID
	@DataValidate(description = "充值渠道折扣", nullable = false)
	private double			discount;						//充值渠道折扣
	@DataValidate(description = "充值游戏的货币类型", nullable = false)
	private int				chargeSubjectId;				//充值游戏的货币类型
	@DataValidate(description = "充值货币数", nullable = false)
	private double			chargeMoney;					//充值货币数
	@DataValidate(description = "充值元宝数", nullable = false)
	private int				chargeAmount;					//充值元宝数
	@DataValidate(description = "联运订单号", nullable = false)
	private String			chargeOrderCode;				//联运订单号
	@DataValidate(description = "充值订单号", nullable = false)
	private long			chargeDetailId;					//充值订单号
	@DataValidate(description = "充值类型", nullable = false)
	private int				chargeType;						//充值类型1直充
	@DataValidate(description = "货币类型", nullable = false)
	private int				moneyType;						//货币类型 1 人民币
	@DataValidate(description = "透传字段信息", nullable = false)
	private String			attachCode;						//透传字段信息
	@DataValidate(description = "角色ID", nullable = false)
	private long				roleId;							//角色ID
	@DataValidate(description = "下单时间 ", nullable = false)
	private String			chargeTime;						//下单时间 (时间戳，秒级 或 yyyy-MM-dd HH:mm:ss)
	@DataValidate(description = "服务器IP", nullable = false)
	private String			serverIp;						//服务器IP
	@DataValidate(description = "请求IP", nullable = false)
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

	public PushOrderWithMQInfoPOJO() {
		super();
	}

	public PushOrderWithMQInfoPOJO(ChargingPOJO pojo,int discount,PrePaymentPOJO payment) {
		super();
		this.gameId = pojo.getGameId();
		this.userId = "0";
		this.userName = pojo.getUserName();
		this.gatewayId = pojo.getGatewayId();
		this.chargeChannelId = 0;
		this.discount = discount;
		this.chargeSubjectId = 0;
		this.chargeMoney = pojo.getChargeMoney();
		this.chargeAmount = pojo.getChargeAmount();
		this.chargeOrderCode = pojo.getOrderId();
		this.chargeDetailId = pojo.getChargeDetailId();
		this.chargeType = 0;
		this.moneyType = 0;
		this.attachCode = pojo.getAttachCode();
		this.roleId = payment.getRoleId();
		this.chargeTime = payment.getChargeTime().getTime()/1000+"";
		this.serverIp = pojo.getServerIP();
		this.clientIp = pojo.getRequestIP();
	}
	public PushOrderWithMQInfoPOJO(int discount,PrePaymentPOJO payment,PushOrderInfoPOJO pushPojo) {
		super();
		this.gameId = payment.getGameId();
		this.userId = "0";
		this.userName = payment.getUserName();
		this.gatewayId = payment.getGatewayId();
		this.chargeChannelId = 0;
		this.discount = discount;
		this.chargeSubjectId = 0;
		this.chargeMoney = payment.getChargeMoney();
		this.chargeAmount = payment.getChargeAmount();
		this.chargeOrderCode = pushPojo.getUnionOrderId();
		this.chargeDetailId = pushPojo.getChargeDetailId();
		this.chargeType = 0;
		this.moneyType = 0;
		this.attachCode = payment.getAttachCode();
		this.roleId = payment.getRoleId();
		this.chargeTime = payment.getChargeTime().getTime()/1000+"";
		this.serverIp = pushPojo.getServerIP();
		this.clientIp = pushPojo.getClientIp();
	}
	public PushOrderWithMQInfoPOJO(PrePaymentPOJO payment,EratingChargeNotifyPOJO erating,int discount) {
		super();

		this.gameId = payment.getGameId();
		this.userId = erating.getUserId();
		this.userName = payment.getUserName();
		this.gatewayId = payment.getGatewayId();
		this.chargeChannelId = 0;
		this.discount = discount;
		this.chargeSubjectId = 0;
		this.chargeMoney = payment.getChargeMoney();
		this.chargeAmount = payment.getChargeAmount();
		this.chargeOrderCode = erating.getChargeOrderCode();
		this.chargeDetailId = erating.getChargeDetailId();
		this.chargeType = 0;
		this.moneyType = 0;
		this.attachCode = payment.getAttachCode();
		this.roleId = payment.getRoleId();
		this.chargeTime = payment.getChargeTime().getTime()/1000+"";
	}
	public PushOrderWithMQInfoPOJO(PrePaymentPOJO payment,EratingChargeNotifyPOJO erating,int discount,SimpleDateFormat sdf) {
		super();

		this.gameId = payment.getGameId();
		this.userId = erating.getUserId();
		this.userName = payment.getUserName();
		this.gatewayId = payment.getGatewayId();
		this.chargeChannelId = 0;
		this.discount = discount;
		this.chargeSubjectId = 0;
		this.chargeMoney = payment.getChargeMoney();
		this.chargeAmount = payment.getChargeAmount();
		this.chargeOrderCode = erating.getChargeOrderCode();
		this.chargeDetailId = erating.getChargeDetailId();
		this.chargeType = 0;
		this.moneyType = 0;
		this.attachCode = payment.getAttachCode();
		this.roleId = payment.getRoleId();
		this.chargeTime = sdf.format(payment.getChargeTime());
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
