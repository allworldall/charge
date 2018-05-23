package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class WDJReqBean {
    private String timeStamp;
    private String orderId;
    private String money;
    private String chargeType;
    private String appKeyId;
    private String buyerId;
    private String out_trade_no;
    private String cardNo;
    
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getAppKeyId() {
		return appKeyId;
	}
	public void setAppKeyId(String appKeyId) {
		this.appKeyId = appKeyId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("timeStamp=").append(timeStamp);
		sb.append(",orderId=").append(orderId);
		sb.append(",money=").append(money);
		sb.append(",chargeType=").append(chargeType);
		sb.append(",appKeyId=").append(appKeyId);
		sb.append(",buyerId=").append(buyerId);
		sb.append(",out_trade_no=").append(out_trade_no);
		sb.append(",cardNo=").append(cardNo);
		return sb.toString();
	}
}
