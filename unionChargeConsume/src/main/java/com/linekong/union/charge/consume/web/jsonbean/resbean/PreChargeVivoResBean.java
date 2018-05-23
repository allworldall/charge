package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class PreChargeVivoResBean {
	private String respCode;	//响应码  成功返回：200，非200时，respMsg会提示错误信息。
	private String respMsg;		//响应信息
	private String signMethod;	//签名方法
	private String signature;	//签名信息
	private String accessKey;	//vivoSDK需要的参数
	private String orderNumber;	//交易流水号   vivo订单号
	private String orderAmount;	//交易金额   单位：分，币种：人民币，必须是整数
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getSignMethod() {
		return signMethod;
	}
	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("respCode=").append(respCode);
		sb.append(",respMsg=").append(respMsg);
		sb.append(",signMethod=").append(signMethod);
		sb.append(",signature=").append(signature);
		sb.append(",accessKey=").append(accessKey);
		sb.append(",orderNumber=").append(orderNumber);
		sb.append(",orderAmount=").append(orderAmount);
		return sb.toString();
	}
}
