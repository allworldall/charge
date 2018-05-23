package com.linekong.union.charge.consume.web.jsonbean.resbean;

/**
 * 预支付回调返回结果
 * @author Administrator
 *
 */
public class PreChargeResBean {

	private String payMentId;  //预支付订单号
	
	private int result;		   //预支付结果

//	private String callbackUrl;//回调地址

	private String expandInfo; //扩展字段

	public String getPayMentId() {
		return payMentId;
	}

	public void setPayMentId(String payMentId) {
		this.payMentId = payMentId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getExpandInfo() {
		return expandInfo;
	}

	public void setExpandInfo(String expandInfo) {
		this.expandInfo = expandInfo;
	}

	public PreChargeResBean() {
		super();
	}

	public PreChargeResBean(int result, String payMentId) {
		super();
		this.payMentId = payMentId;
		this.result = result;
	}

	public PreChargeResBean(int result, String payMentId, String expandInfo) {
		super();
		this.payMentId = payMentId;
		this.result = result;
		this.expandInfo = expandInfo;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("payMentId=").append(payMentId);
		sb.append(",result=").append(result);
		sb.append(",expandInfo=").append(expandInfo);
		return sb.toString();
	}
}
