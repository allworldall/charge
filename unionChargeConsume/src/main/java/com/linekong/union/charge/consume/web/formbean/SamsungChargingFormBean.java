package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class SamsungChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	
	@DataValidate(description = "", nullable = false)
	private String transdata;
	@DataValidate(description = "", nullable = false)
	private String sign;
	@DataValidate(description = "", nullable = false)
	private String signtype;
	
	public class OrderInfo{
		@DataValidate(description = "交易类型", nullable = false)
		private String transtype;
		@DataValidate(description = "商户订单号", nullable = false)
		private String cporderid;
		@DataValidate(description = "交易流水号", nullable = false)
		private String transid;
		@DataValidate(description = "用户在商户应用的唯一标识", nullable = false)
		private String appuserid;
		@DataValidate(description = "游戏id", nullable = false)
		private String appid;
		@DataValidate(description = "商品编码", nullable = false)
		private String waresid;
		@DataValidate(description = "计费方式", nullable = false)
		private String feetype;
		@DataValidate(description = "交易金额", nullable = false)
		private String money;
		@DataValidate(description = "货币类型", nullable = false)
		private String currency;
		@DataValidate(description = "交易结果", nullable = false)
		private String result;
		@DataValidate(description = "交易完成时间", nullable = false)
		private String transtime;
		@DataValidate(description = "商户私有信息", nullable = true)
		private String cpprivate;
		@DataValidate(description = "支付方式", nullable = true)
		private String paytype;
		
		public String getTranstype() {
			return transtype;
		}
		public void setTranstype(String transtype) {
			this.transtype = transtype;
		}
		public String getCporderid() {
			return cporderid;
		}
		public void setCporderid(String cporderid) {
			this.cporderid = cporderid;
		}
		public String getTransid() {
			return transid;
		}
		public void setTransid(String transid) {
			this.transid = transid;
		}
		public String getAppuserid() {
			return appuserid;
		}
		public void setAppuserid(String appuserid) {
			this.appuserid = appuserid;
		}
		public String getAppid() {
			return appid;
		}
		public void setAppid(String appid) {
			this.appid = appid;
		}
		public String getWaresid() {
			return waresid;
		}
		public void setWaresid(String waresid) {
			this.waresid = waresid;
		}
		public String getFeetype() {
			return feetype;
		}
		public void setFeetype(String feetype) {
			this.feetype = feetype;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getTranstime() {
			return transtime;
		}
		public void setTranstime(String transtime) {
			this.transtime = transtime;
		}
		public String getCpprivate() {
			return cpprivate;
		}
		public void setCpprivate(String cpprivate) {
			this.cpprivate = cpprivate;
		}
		public String getPaytype() {
			return paytype;
		}
		public void setPaytype(String paytype) {
			this.paytype = paytype;
		}
		public OrderInfo(String transtype, String cporderid, String transid,
				String appuserid, String appid, String waresid, String feetype,
				String money, String currency, String result, String transtime,
				String cpprivate, String paytype) {
			super();
			this.transtype = transtype;
			this.cporderid = cporderid;
			this.transid = transid;
			this.appuserid = appuserid;
			this.appid = appid;
			this.waresid = waresid;
			this.feetype = feetype;
			this.money = money;
			this.currency = currency;
			this.result = result;
			this.transtime = transtime;
			this.cpprivate = cpprivate;
			this.paytype = paytype;
		}
		public OrderInfo() {
			super();
		}
		@Override
		public String toString() {
			return "transtype=" + transtype + ",cporderid="
					+ cporderid + ",transid=" + transid + ",appuserid="
					+ appuserid + ",appid=" + appid + ",waresid=" + waresid
					+ ",feetype=" + feetype + ",money=" + money
					+ ",currency=" + currency + ",result=" + result
					+ ",transtime=" + transtime + ",cpprivate=" + cpprivate
					+ ",paytype=" + paytype;
		}
		
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
	public String getTransdata() {
		return transdata;
	}
	public void setTransdata(String transdata) {
		this.transdata = transdata;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	public String getSigntype() {
		return signtype;
	}
	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",transdata=" + transdata + ",sign=" + sign
				+ ",signtype=" + signtype;
	}
	
}
