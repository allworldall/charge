package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.TTReqBean;

public class TTChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "用户 ID", nullable = false)
	private String uid;
	@DataValidate(description = "游戏 ID", nullable = false)
    private String gameId;
	@DataValidate(description = "SDK订单号", nullable = false)
    private String sdkOrderId;
	@DataValidate(description = "CP订单号", nullable = false)
    private String cpOrderId;
	@DataValidate(description = "实际支付金额(单位元)", nullable = false)
    private String payFee;
	@DataValidate(description = "支付结果 1 成功，其它失败", nullable = false)
    private String payResult;
	@DataValidate(description = "支付时间，格式'yyyy-mm-dd hh:mm:ss'", nullable = false)
    private String payDate;
	@DataValidate(description = "CP 扩展信息", nullable = false)
    private String exInfo;
	@DataValidate(description = "MD5加密签名，BASE64编码", nullable = false)
    private String sign;

	public TTChargingFormBean(String gameName,String cpName,TTReqBean bean,String sign){
	           this.gameName = gameName;
	           this.cpName = cpName;
	           this.uid = bean.getUid();
	           this.gameId = bean.getGameId();
	           this.sdkOrderId = bean.getSdkOrderId();
	           this.cpOrderId = bean.getCpOrderId().substring(bean.getCpOrderId().indexOf('2'));
	           this.payFee = bean.getPayFee();
	           this.payResult = bean.getPayResult();
	           this.payDate = bean.getPayDate();
	           this.exInfo = bean.getExInfo();
	           this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getSdkOrderId() {
		return sdkOrderId;
	}
	public void setSdkOrderId(String sdkOrderId) {
		this.sdkOrderId = sdkOrderId;
	}
	public String getCpOrderId() {
		return cpOrderId;
	}
	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}
	public String getPayFee() {
		return payFee;
	}
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}
	public String getPayResult() {
		return payResult;
	}
	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getExInfo() {
		return exInfo;
	}
	public void setExInfo(String exInfo) {
		this.exInfo = exInfo;
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
				+ cpName + ",uid=" + uid + ",gameId=" + gameId
				+ ",sdkOrderId=" + sdkOrderId + ",cpOrderId=" + cpOrderId
				+ ",payFee=" + payFee + ",payResult=" + payResult
				+ ",payDate=" + payDate + ",exInfo=" + exInfo + ",sign="
				+ sign;
	}

}
