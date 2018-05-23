package com.linekong.union.charge.rpc.pojo;

import java.io.Serializable;

/**
 * log_charge_common 
 * @author Administrator
 *
 */
public class LogChargeCommonPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5066953487937429497L;

	private String	chargeDetailId; 	//蓝港订单号
	
	private String passportId;      		//充值账号id
	
	private String gameId;          	//游戏id
	
	private String chargeGatewayId;	//充值区服
	
	private String chargeSubjectId;	//充值所有的游戏币都是3
	
	private String chargeChannelId;	//1=银行卡（环迅）;2=蓝港一卡通;3=神州行（神州付）;4=esales;5=联通卡;7=蓝钻;8=神州行（19pay）;9=银行卡（易宝）;10=联运渠道;12=骏网一卡通;13=银行卡（支付宝）;14=支付宝账户充值;15=财付通
	
	private String chargeAmount;    	//对于得RMB，而不是转换后的游戏金币。
	
	private String  chargeTime;      	//充值时间
	
	private String  chargeMoney;     	//充值面值
	
	private String  chargeRealMoney; 	//充值实际金额，有些渠道没有这个值
	
	private String  clientIp;        	//请求IP
	
	private String  serverIp;           //服务器IP
	
	private String  discount;         	//折扣
	
	private String dealState;       	//0--表示没有充值的;1--已经通过第三方平台交过费了，但还没充值;2--表示充值的对于非调用第三方的扣费而言;3--无效的订单;4--eSales充值撤销
	
	private String cardNum;         	//充值一卡通时最好在log中保留卡号
	
	private String  unchargeTime;    	//eSales充值后该log被撤销的时间
	
	private String  province;           //省份
	
	private String  city;				//城市

	public String getChargeDetailId() {
		return chargeDetailId;
	}

	public void setChargeDetailId(String chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}

	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getChargeGatewayId() {
		return chargeGatewayId;
	}

	public void setChargeGatewayId(String chargeGatewayId) {
		this.chargeGatewayId = chargeGatewayId;
	}

	public String getChargeSubjectId() {
		return chargeSubjectId;
	}

	public void setChargeSubjectId(String chargeSubjectId) {
		this.chargeSubjectId = chargeSubjectId;
	}

	public String getChargeChannelId() {
		return chargeChannelId;
	}

	public void setChargeChannelId(String chargeChannelId) {
		this.chargeChannelId = chargeChannelId;
	}

	public String getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(String chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	public String getChargeRealMoney() {
		return chargeRealMoney;
	}

	public void setChargeRealMoney(String chargeRealMoney) {
		this.chargeRealMoney = chargeRealMoney;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDealState() {
		return dealState;
	}

	public void setDealState(String dealState) {
		this.dealState = dealState;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getUnchargeTime() {
		return unchargeTime;
	}

	public void setUnchargeTime(String unchargeTime) {
		this.unchargeTime = unchargeTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("chargeDetailId=").append(chargeDetailId);
		sb.append(",passportId=").append(passportId);
		sb.append(",gameId=").append(gameId);
		sb.append(",chargeGatewayId=").append(chargeGatewayId);
		sb.append(",chargeSubjectId=").append(chargeSubjectId);
		sb.append(",chargeChannelId=").append(chargeChannelId);
		sb.append(",chargeAmount=").append(chargeAmount);
		sb.append(",chargeTime=").append(chargeTime);
		sb.append(",chargeMoney=").append(chargeMoney);
		sb.append(",chargeRealMoney=").append(chargeRealMoney);
		sb.append(",clientIp=").append(clientIp);
		sb.append(",serverIp=").append(serverIp);
		sb.append(",discount=").append(discount);
		sb.append(",dealState=").append(dealState);
		sb.append(",cardNum=").append(cardNum);
		sb.append(",unchargeTime=").append(unchargeTime);
		sb.append(",province=").append(province);
		sb.append(",city=").append(city);
		return sb.toString();
	}
}
