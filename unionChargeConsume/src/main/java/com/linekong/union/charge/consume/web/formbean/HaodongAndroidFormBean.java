package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.HaodongAndroidReqBean;

public class HaodongAndroidFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "外部交易码", nullable = false)
	private String codeNo;		//外部交易码
	@DataValidate(description = "nullable", nullable = false)
	private String tradeNo;		//游戏猫交易码
	
	private String openId;		//用户唯一标识
	@DataValidate(description = "支付金额", nullable = false)
	private String amount;		//支付金额
	
	private String payWay;		//支付方式       1-支付宝 app 支付,2-微信,3-银联,4-ios 内支付,5-喵点,6-微信 app 支付,7-代金劵,8-微信 wap 支付,9-支付宝wap支付(微信支付最大金额500W)
	
	private String ext;			//额外信息
	
	private String notifyUr;	//渠道通知地址
	@DataValidate(description = "签名", nullable = false)
	private String sign;		//签名
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
		return notifyUr;
	}
	public void setNotifyUr(String notifyUr) {
		this.notifyUr = notifyUr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public HaodongAndroidFormBean(HaodongAndroidReqBean bean,String gameName,String cpName) {
		super();
		this.codeNo = bean.getCodeNo();
		this.tradeNo = bean.getTradeNo();
		this.openId = bean.getOpenId();
		this.amount = bean.getAmount();
		this.payWay = bean.getPayWay();
		this.ext = bean.getExt();
		this.notifyUr = bean.getNotifyUr();
		this.sign = bean.getSign();
		this.gameName = gameName;
		this.cpName = cpName;
	}
	@Override
	public String toString() {
		return "HaodongAndroidFormBean [gameName=" + gameName + ", cpName="
				+ cpName + ", codeNo=" + codeNo + ", tradeNo=" + tradeNo
				+ ", openId=" + openId + ", amount=" + amount + ", payWay="
				+ payWay + ", ext=" + ext + ", notifyUr=" + notifyUr
				+ ", sign=" + sign + "]";
	}
	public HaodongAndroidFormBean() {
		super();
	}
	
}
