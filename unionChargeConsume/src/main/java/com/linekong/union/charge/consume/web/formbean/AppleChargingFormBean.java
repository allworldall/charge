package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * 苹果支付后传给我方SDK的数据
 * @author Administrator
 *
 */
public class AppleChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	private String productId;		//购买物品的product标示符
	
	@DataValidate(description = "apple凭证", nullable = false)
	private String transactionReceipt;
	
	private String payMentId;   	//预支付订单号
	
	@DataValidate(description = "用户名", nullable = false)
	private String userName;
	
	@DataValidate(description = "网关ID", nullable = false)
	private String gatewayID;
	
	@DataValidate(description = "cpGameID", nullable = false)
	private Integer cpGameId;
	
	@DataValidate(description = "cpID", nullable = false)
	private Integer cpId;
	
	//与sdk沟通定义，版本对应，苹果凭证返回json格式的不同  
	@DataValidate(description = "苹果接口版本", nullable = false)
	private String version;
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTransactionReceipt() {
		return transactionReceipt;
	}

	public void setTransactionReceipt(String transactionReceipt) {
		this.transactionReceipt = transactionReceipt.replace(" ", "+");
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getPayMentId() {
		return payMentId;
	}

	public void setPayMentId(String payMentId) {
		this.payMentId = payMentId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGatewayID() {
		return gatewayID;
	}

	public void setGatewayID(String gatewayID) {
		this.gatewayID = gatewayID;
	}
	public Integer getCpGameId() {
		return cpGameId;
	}

	public void setCpGameId(Integer cpGameId) {
		this.cpGameId = cpGameId;
	}

	public Integer getCpId() {
		return cpId;
	}

	public void setCpId(Integer cpId) {
		this.cpId = cpId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",productId=" + productId + ",transactionReceipt="
				+ transactionReceipt + ",payMentId=" + payMentId
				+ ",userName=" + userName + ",gatewayID=" + gatewayID
				+ ",cpGameId=" + cpGameId + ",cpId=" + cpId + ",version="
				+ version;
	}

}
