package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.util.annotation.RegexType;

public class PreChargeFormBean {
	@DataValidate(description = "充值玩家用户名",nullable = false, maxLength = 50)
	private String	userName;			//用户名
	@DataValidate(description = "联运方ID",regexType = RegexType.NUMBER)
	private Integer		cpId;				//联运方ID
	@DataValidate(description = "游戏ID",regexType = RegexType.NUMBER)
	private Integer		gameId;				//游戏ID
	@DataValidate(description = "网关ID",regexType = RegexType.NUMBER)
	private Integer		gatewayId;			//充值区服ID
	@DataValidate(description = "充值金额",regexType = RegexType.NUMBER)
	private Double  chargeMoney;		//充值金额
	@DataValidate(description = "充值元宝数",regexType = RegexType.NUMBER)
	private Integer		chargeAmount;		//充值获得元宝数
	
	private Long	role;				//角色ID

	private String	payMentTime;		//预支付时间
	
	private String	attachCode;			//透传字段信息
	
	private String platformName;		//合作商名称
	
	private String	expandInfo;			//拓展字段
	@DataValidate(description = "请求url地址中合作商名称名称",nullable = false)
	private String  cpName;				//合作商名称拼音简写
	@DataValidate(description = "数据校验sign",nullable = false)
	private String  sign;				//数据校验sign		
	
	private String  productId;			//产品ID
	
	private String  productName;		//产品Name
	
	private String  productDesc;        //产品描述
	
	private String  cpSignType;			//渠道预支付签名类型
	
	private String  var;				//版本号（与SDK 商议后决定，以区分不同版本）

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCpId() {
		return cpId;
	}

	public void setCpId(Integer cpId) {
		this.cpId = cpId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Integer gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Double getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(Double chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	public Integer getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Integer chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getPayMentTime() {
		return payMentTime;
	}

	public void setPayMentTime(String payMentTime) {
		this.payMentTime = payMentTime;
	}

	public String getExpandInfo() {
		return expandInfo;
	}

	public void setExpandInfo(String expandInfo) {
		this.expandInfo = expandInfo;
	}

	public String getAttachCode() {
		return attachCode;
	}

	public void setAttachCode(String attachCode) {
		this.attachCode = attachCode;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+");
	}
	

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role == null ? 0:role;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getCpSignType() {
		return cpSignType;
	}

	public void setCpSignType(String cpSignType) {
		this.cpSignType = cpSignType;
	}

	public PreChargeFormBean() {
		super();
	}

	public PreChargeFormBean(Integer gameId, Double chargeMoney) {
		super();
		this.gameId = gameId;
		this.chargeMoney = chargeMoney;
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

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("userName=").append(userName);
		sb.append(",cpId=").append(cpId);
		sb.append(",gameId=").append(gameId);
		sb.append(",gatewayId=").append(gatewayId);
		sb.append(",chargeMoney=").append(chargeMoney);
		sb.append(",chargeAmount=").append(chargeAmount);
		sb.append(",role=").append(role);
		sb.append(",payMentTime=").append(payMentTime);
		sb.append(",attachCode=").append(attachCode);
		sb.append(",platformName=").append(platformName);
		sb.append(",expandInfo=").append(expandInfo);
		sb.append(",cpName=").append(cpName);
		sb.append(",sign=").append(sign);
		sb.append(",productId=").append(productId);
		sb.append(",productName=").append(productName);
		sb.append(",productDesc=").append(productDesc);
		sb.append(",cpSignType=").append(cpSignType);
		sb.append(",var=").append(var);
		return sb.toString();
	}
}
