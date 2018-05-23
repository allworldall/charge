package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class PreChargeResBeanKuaikan {
	//app_id,wares_id,out_order_id,open_uid,out_notify_url 拼成的Json串
	private String transData;
	//上面5个参数+key进行加密(sign)
	private String transSecret;
	//预支付结果（成功为 1， 其它均失败）
	private int result;
	
	public PreChargeResBeanKuaikan() {
		super();
	}
	
	public PreChargeResBeanKuaikan(String transData, String transSecret,
			int result) {
		super();
		this.transData = transData;
		this.transSecret = transSecret;
		this.result = result;
	}
	
	public String getTransData() {
		return transData;
	}
	public void setTransData(String transData) {
		this.transData = transData;
	}
	public String getTransSecret() {
		return transSecret;
	}
	public void setTransSecret(String transSecret) {
		this.transSecret = transSecret;
	}
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("transData=").append(transData);
		sb.append(",transSecret=").append(transSecret);
		sb.append(",result=").append(result);
		return sb.toString();
	}
}
