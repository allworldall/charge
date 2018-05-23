package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class YouximaoChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;	
	@DataValidate(description = "蓝港订单号", nullable = false)
    private String codeNo;
	@DataValidate(description = "游戏猫订单号", nullable = false)
    private long   tradeNo;
	@DataValidate(description = "用户id", nullable = false)
    private String openId;
	@DataValidate(description = "支付金额 单位元", nullable = false)
    private String amount;
	@DataValidate(description = "支付方式", nullable = false)
    private short  payWay;
	@DataValidate(description = "扩展字段", nullable = true)
    private String ext;
	@DataValidate(description = "充值回调url", nullable = false)
    private String notifyUrl;
	@DataValidate(description = "签名", nullable = false)
    private String sign;
    
    public String getGameName(){
    	return this.gameName;
    }
    
    public void setGameName(String gameName){
    	this.gameName = gameName;
    }
    
    public String getCpName(){
    	return this.cpName;
    }
    
    public void setCpName(String cpName){
    	this.cpName = cpName;
    }
    
	public String getCodeNo() {
		return codeNo;
	}
	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}
	public long getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(long tradeNo) {
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
	public short getPayWay() {
		return payWay;
	}
	public void setPayWay(short payWay) {
		this.payWay = payWay;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
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
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",codeNo=" + codeNo + ",tradeNo=" + tradeNo
				+ ",openId=" + openId + ",amount=" + amount + ",payWay="
				+ payWay + ",ext=" + ext + ",notifyUrl=" + notifyUrl
				+ ",sign=" + sign;
	}	
}
