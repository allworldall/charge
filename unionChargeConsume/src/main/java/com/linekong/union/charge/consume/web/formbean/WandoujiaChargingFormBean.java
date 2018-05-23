package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.WDJReqBean;

public class WandoujiaChargingFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "时间戳", nullable = false)
	private String timeStamp;
	@DataValidate(description = "豌豆荚订单id", nullable = false)
	private String orderId;
	@DataValidate(description = "支付金额", nullable = false)
	private String money;
	@DataValidate(description = "支付类型", nullable = false)
	private String chargeType;
	@DataValidate(description = "APPKeyId", nullable = false)
	private String appKeyId;
	@DataValidate(description = "购买人的账户id", nullable = false)
	private String buyerId;
	@DataValidate(description = "开发者订单号", nullable = false)
	// 创建订单时候传入的订单号原样返回
	private String out_trade_no;
	@DataValidate(description = "充值卡id", nullable = true)
	// 只有充值卡充值的时候才不为空
	private String cardNo;
	@DataValidate(description = "签名RSA", nullable = false)
	private String signType;
	@DataValidate(description = "签名", nullable = false)
	private String sign;
	
	public WandoujiaChargingFormBean(WDJReqBean bean,String gameName,String cpName,String signType,String sign){
	    this.gameName = gameName;
	    this.cpName = cpName;
		this.appKeyId = bean.getAppKeyId();
		this.buyerId = bean.getBuyerId();
		this.cardNo = bean.getCardNo();
		this.chargeType = bean.getChargeType();
		this.money = bean.getMoney();
		this.orderId = bean.getOrderId();
		this.out_trade_no = bean.getOut_trade_no();
		this.timeStamp = bean.getTimeStamp();
	    this.sign = sign.replace(" ", "+").replace("\\", "");
	    this.signType = signType;
	}

	public String getGameName() {
		return gameName;
	}

	public String getCpName() {
		return cpName;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getMoney() {
		return money;
	}

	public String getChargeType() {
		return chargeType;
	}

	public String getAppKeyId() {
		return appKeyId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public String getCardNo() {
		return cardNo;
	}

	public String getSignType() {
		return signType;
	}

	public String getSign() {
		return sign;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",timeStamp=" + timeStamp + ",orderId=" + orderId
				+ ",money=" + money + ",chargeType=" + chargeType
				+ ",appKeyId=" + appKeyId + ",buyerId=" + buyerId
				+ ",out_trade_no=" + out_trade_no + ",cardNo=" + cardNo
				+ ",signType=" + signType + ",sign=" + sign;
	}

}
