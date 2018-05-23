package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class JifengChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "加密的 MD5 标示", nullable = false)
	private String sign;
	@DataValidate(description = "用于加密的时间，此为时间戳", nullable = false)
	private String time;
	
	public JifengChargingFormBean(String gameName, String cpName, String sign,
			String time ) {
		super();
		this.gameName = gameName;
		this.cpName = cpName;
		this.sign = sign.replace(" ", "+").replace("\\", "");
		this.time = time;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",sign=" + sign + ",time=" + time;
	}
	
}
