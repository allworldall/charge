package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class DangLeChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "支付结果，固定值。“1”代表成功，“0”代表失败", nullable = false)
	private String result;
	@DataValidate(description = "支付金额，单位：元，两位小数。", nullable = false)
	private String money;
	@DataValidate(description = "本次支付 SDK生成的订单号", nullable = false)
	private String order;
	@DataValidate(description = "本次支付用户的乐号， 既登录后返回的 umid参数。 最长长度 64 字符",maxLength = 64, nullable = false)
	private String mid;
	@DataValidate(description = "时间戳，格式：yyyymmddHH24mmss 月日小时分秒小于 10 前面补充 0", nullable = false)
	private String time;
	@DataValidate(description = "客户端购买商品时候传入的 TransNo 字段。（厂家用于金额验证）", nullable = false)
	private String ext;
	@DataValidate(description = "MD5 验证串，用于与接口生成的验证串做比较，保证计费通知的合法性。", nullable = false)
	private String signature;

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
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature.replace(" ", "+").replace("\\", "");
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",result=" + result + ",money=" + money
				+ ",order=" + order + ",mid=" + mid + ",time=" + time
				+ ",ext=" + ext + ",signature=" + signature;
	}
    
}
