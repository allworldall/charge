package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class HuaWeiChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "支付结果：“0”，表示支付成功“1”，表示退款成功（暂未启用）", nullable = false)
	private String result;
	@DataValidate(description = "开发者社区用户名或联盟用户编号", nullable = false)
	private String userName;
	@DataValidate(description = "商品名称", nullable = false)
	private String productName;
	@DataValidate(description = "支付类型:各种类型未完待续", nullable = true)
	private int payType;
	@DataValidate(description = "商品支付金额 (格式为：元.角分，最小金额为分， 例如： 20.00)", nullable = false)
	private String amount;
	@DataValidate(description = "华为订单号", nullable = false)
	private String orderId;
	@DataValidate(description = "通知时间。 (自 1970 年 1 月 1 日 0 时起的毫秒数)", nullable = false)
	private String notifyTime;
	@DataValidate(description = "开发者支付请求 ID，原样返回开发者 App 调用支付 SDK 时填写的requestId 参数值", nullable = false)
	private String requestId;
	@DataValidate(description = "银行编码-支付通道信息，可选参数", nullable = true)
	private String bankId;
	@DataValidate(description = "下单时间 yyyy-MM-dd hh:mm:ss", nullable = true)
	private String orderTime;
	@DataValidate(description = "交易/退款时间 yyyy-MM-dd hh:mm:ss", nullable = true)
	private String tradeTime;
	@DataValidate(description = "接入方式", nullable = true)
	private String accessMode;
	@DataValidate(description = "渠道开销，保留两位小数，单位元", nullable = true)
	private String spending;
	@DataValidate(description = "商户侧保留信息，原样返回商户调用支付 sdk 输入的保留信息。未输入时，不返回。", nullable = true)
	private String extReserved;
	@DataValidate(description = "系统保留信息", nullable = true)
	private String sysReserved;
	@DataValidate(description = "签名类型，不参与签名，非法值按缺省值处理", nullable = true)
	private String signType;
	@DataValidate(description = "RSA 签名", nullable = false)
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getAccessMode() {
		return accessMode;
	}

	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}

	public String getSpending() {
		return spending;
	}

	public void setSpending(String spending) {
		this.spending = spending;
	}

	public String getExtReserved() {
		return extReserved;
	}

	public void setExtReserved(String extReserved) {
		this.extReserved = extReserved;
	}

	public String getSysReserved() {
		return sysReserved;
	}

	public void setSysReserved(String sysReserved) {
		this.sysReserved = sysReserved;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
    
	public void setNull() {
		if("null".equals(this.gameName)) this.gameName = null;
		if("null".equals(this.cpName)) this.cpName = null;
		if("null".equals(this.result)) this.result = null;
		if("null".equals(this.userName)) this.userName = null;
		if("null".equals(this.productName)) this.productName = null;
		if("null".equals(this.amount)) this.amount = null;
		if("null".equals(this.orderId)) this.orderId = null;
		if("null".equals(this.notifyTime)) this.notifyTime = null;
		if("null".equals(this.requestId)) this.requestId = null;
		if("null".equals(this.bankId)) this.bankId = null;
		if("null".equals(this.orderTime)) this.orderTime = null;
		if("null".equals(this.tradeTime)) this.tradeTime = null;
		if("null".equals(this.accessMode)) this.accessMode = null;
		if("null".equals(this.spending)) this.spending = null;
		if("null".equals(this.extReserved)) this.extReserved = null;
		if("null".equals(this.sysReserved)) this.sysReserved = null;
		if("null".equals(this.signType)) this.signType = null;
		if("null".equals(this.sign)) this.sign = null;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",result=" + result + ",userName=" + userName
				+ ",productName=" + productName + ",payType=" + payType
				+ ",amount=" + amount + ",orderId=" + orderId
				+ ",notifyTime=" + notifyTime + ",requestId=" + requestId
				+ ",bankId=" + bankId + ",orderTime=" + orderTime
				+ ",tradeTime=" + tradeTime + ",accessMode=" + accessMode
				+ ",spending=" + spending + ",extReserved=" + extReserved
				+ ",sysReserved=" + sysReserved + ",signType=" + signType
				+ ",sign=" + sign;
	}
}
