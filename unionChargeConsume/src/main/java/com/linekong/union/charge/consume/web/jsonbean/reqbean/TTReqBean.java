package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class TTReqBean {
    private String uid;
    private String gameId;
    private String sdkOrderId;
    private String cpOrderId;
    private String payFee;
    private String payResult;
    private String payDate;
    private String exInfo;
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("uid=").append(uid);
		sb.append(",gameId=").append(gameId);
		sb.append(",sdkOrderId=").append(sdkOrderId);
		sb.append(",cpOrderId=").append(cpOrderId);
		sb.append(",payFee=").append(payFee);
		sb.append(",payResult=").append(payResult);
		sb.append(",payDate=").append(payDate);
		sb.append(",exInfo=").append(exInfo);
		return sb.toString();
	}
}
