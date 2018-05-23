package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class HaodongAndroidReqBean {

	private String codeNo;		//外部交易码
	
	private String tradeNo;		//游戏猫交易码
	
	private String openId;		//用户唯一标识
	
	private String amount;		//支付金额
	
	private String payWay;		//支付方式       1-支付宝 app 支付,2-微信,3-银联,4-ios 内支付,5-喵点,6-微信 app 支付,7-代金劵,8-微信 wap 支付,9-支付宝wap支付(微信支付最大金额500W)
	
	private String ext;			//额外信息
	
	private String notifyUrl;	//渠道通知地址
	
	private String sign;		//签名

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getNotifyUr() {
		return notifyUrl;
	}

	public void setNotifyUr(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "codeNo=" + codeNo + ",tradeNo=" + tradeNo
				+ ",openId=" + openId + ",amount=" + amount + ",payWay="
				+ payWay + ",ext=" + ext + ",notifyUrl=" + notifyUrl
				+ ",sign=" + sign;
	}
				
}
