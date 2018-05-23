package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.AnzhiReqBean;

/**
 * 安智FormBean JsonBean到FormBean转换
 */
public class AnzhiChargingFormBean {
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "安智账号，除匿名支付外， uid 必须存在", nullable = false, maxLength = 30)
	private String uid;
	@DataValidate(description = "安智订单号", nullable = false, maxLength = 30)
	private String orderId;
	@DataValidate(description = "用户实际支付金额，单位为分（不包括不分成礼券）", nullable = false)
	private String orderAmount;
	@DataValidate(description = "支付时间，格式为： yyyy-MM-dd HH:mm:ss", nullable = false)
	private String orderTime;
	@DataValidate(description = "订单状态（ 1 为成功）", nullable = false)
	private int code;
	@DataValidate(description = "空。兼容使用", nullable = true)
	private String msg;
	@DataValidate(description = "等于 orderAmount兼容使用", nullable = true)
	private String payAmount;
	@DataValidate(description = "第三方游戏订单号，可作为厂商判别用户充值的身份标记", nullable = false, maxLength = 2000)
	private String cpInfo;
	@DataValidate(description = "通知时间， 10 位 unix 时间戳（精确到秒）", nullable = false)
	private int notifyTime;
	@DataValidate(description = "备注", nullable = true)
	private String memo;
	@DataValidate(description = "不分成礼券金额（单位为分）", nullable = true)
	private String redBagMoney;
	@DataValidate(description = "自定义信息", nullable = true, maxLength = 300)
	private String cpCustomInfo;
	@DataValidate(description = "订单账号", nullable = true)
	private String orderAccount;

	public AnzhiChargingFormBean(String gameName, String cpName, AnzhiReqBean bean) {
		this.gameName = gameName;
		this.cpName = cpName;
		this.uid = bean.getUid();
		this.orderId = bean.getOrderId();
		this.orderAmount = bean.getOrderAmount();
		this.orderTime = bean.getOrderTime();
		this.code = bean.getCode();
		this.msg = bean.getMsg();
		this.payAmount = bean.getPayAmount();
		this.cpInfo = bean.getCpInfo();
		this.notifyTime = bean.getNotifyTime();
		this.memo = bean.getMemo();
		this.redBagMoney = bean.getRedBagMoney();
		this.cpCustomInfo = bean.getCpCustomInfo();
		this.orderAccount = bean.getOrderAccount();
	}

	public String getGameName() {
		return gameName;
	}

	public String getCpName() {
		return cpName;
	}

	public String getUid() {
		return uid;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public String getCpInfo() {
		return cpInfo;
	}

	public int getNotifyTime() {
		return notifyTime;
	}

	public String getMemo() {
		return memo;
	}

	public String getRedBagMoney() {
		return redBagMoney;
	}

	public String getCpCustomInfo() {
		return cpCustomInfo;
	}
	
	public String getOrderAccount() {
		return orderAccount;
	}

	public void setOrderAccount(String orderAccount) {
		this.orderAccount = orderAccount;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("gameName=").append(gameName);
		sb.append(",cpName=").append(cpName);
		sb.append(",uid=").append(uid);
		sb.append(",orderId=").append(orderId);
		sb.append(",orderAmount=").append(orderAmount);
		sb.append(",orderTime=").append(orderTime);
		sb.append(",code=").append(code);
		sb.append(",msg=").append(msg);
		sb.append(",payAmount=").append(payAmount);
		sb.append(",cpInfo=").append(cpInfo);
		sb.append(",notifyTime=").append(notifyTime);
		sb.append(",memo=").append(memo);
		sb.append(",redBagMoney=").append(redBagMoney);
		sb.append(",cpCustomInfo=").append(cpCustomInfo);
		sb.append(",orderAccount=").append(orderAccount);
		return sb.toString();
	}
}
