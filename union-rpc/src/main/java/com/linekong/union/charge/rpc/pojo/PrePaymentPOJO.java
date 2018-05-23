package com.linekong.union.charge.rpc.pojo;

import java.io.Serializable;
import java.util.Date;

public class PrePaymentPOJO implements Serializable{
	
	/**
	 * 服务器存入log_cp_payment_order数据时定义的POJO
	 */
	private static final long serialVersionUID = 6713093473483875284L;
	
	//预支付id(现由SDK来生成)
	public String paymentId;
	//支付玩家账号
	public String userName;
	//联运渠道方标识
	public int	  cpId;
	//游戏ID
	public int	  gameId;
	//充值网关区服ID
	public int	  gatewayId;
	//支付金额
	public double chargeMoney;
	//支付元宝数
	public int	  chargeAmount;
	//预支付时间
	public Date	  chargeTime;
	//平台名称
	public String platformName;
	//附加码
	public String attachCode;
	//拓展字段存储json格式数据方便拓展
	public String expandInfo;
	//角色ID
	public long	  roleId;
	//测试状态；0-正常数据  1-测试数据
	public int    testState;
	//苹果产品标识
	public String productId;
	//版本号（与SDK 商议后决定，以区分不同版本）
	public String var;
	//渠道预支付签名类型
	public String cpSignType;
	//产品Name
	public String productName;
	//产品描述
	public String productDesc;
	//请求IP
	public String requestIp;	
	//服务器IP
	public String serverIp;
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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
	public Date getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getAttachCode() {
		return attachCode;
	}
	public void setAttachCode(String attachCode) {
		this.attachCode = attachCode;
	}
	public String getExpandInfo() {
		return expandInfo;
	}
	public void setExpandInfo(String expandInfo) {
		this.expandInfo = expandInfo;
	}
	public int getCpId() {
		return cpId;
	}
	public void setCpId(int cpId) {
		this.cpId = cpId;
	}
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	public int getTestState() {
		return testState;
	}
	public void setTestState(int testState) {
		this.testState = testState;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getCpSignType() {
		return cpSignType;
	}
	public void setCpSignType(String cpSignType) {
		this.cpSignType = cpSignType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	
	public String getRequestIp() {
		return requestIp;
	}
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("paymentId=").append(paymentId);
		sb.append(",userName=").append(userName);
		sb.append(",cpId=").append(cpId);
		sb.append(",gameId=").append(gameId);
		sb.append(",gatewayId=").append(gatewayId);
		sb.append(",chargeMoney=").append(chargeMoney);
		sb.append(",chargeAmount=").append(chargeAmount);
		sb.append(",chargeTime=").append(chargeTime);
		sb.append(",platformName=").append(platformName);
		sb.append(",attachCode=").append(attachCode);
		sb.append(",expandInfo=").append(expandInfo);
		sb.append(",roleId=").append(roleId);
		sb.append(",testState=").append(testState);
		sb.append(",productId=").append(productId);
		sb.append(",var=").append(var);
		sb.append(",cpSignType=").append(cpSignType);
		sb.append(",productName=").append(productName);
		sb.append(",productDesc=").append(productDesc);
		sb.append(",requestIp=").append(requestIp);
		sb.append(",serverIp=").append(serverIp);
		return sb.toString();
	}
}
