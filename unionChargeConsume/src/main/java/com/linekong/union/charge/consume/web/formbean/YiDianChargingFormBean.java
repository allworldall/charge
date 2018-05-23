package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * 一点渠道-掌宜付聚合支付平台
 * @author admin
 *
 */
public class YiDianChargingFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "交易结果", nullable = false)
	public String code;			//交易结果		0	交易易成功  1	交易易失败(保留留值，交易易失败暂不不发回调通知)
	@DataValidate(description = "应用ID	", nullable = false)
	public String app_id;		//应用ID			可在商户后台创建和查询
	
	public String pay_way;	 	//支付方式		1	微信  2	 支付宝 3 QQ钱包 9	银联
	@DataValidate(description = "商户订单编号", nullable = false)
	public String out_trade_no;	//商户订单编号	支付请求同名参数透传				
	@DataValidate(description = "平台订单编号", nullable = false)
	public String invoice_no;	//平台订单编号	平台 自动 生成，全局唯 一				
	
	public String up_invoice_no;//通道订单编号	银 行行或微信 支付流 水号，不不是所有通道或 支付 方式都提供		
	@DataValidate(description = "交易金额", nullable = false)
	public String money;		//交易金额		正整数，以分为单位		
	
	public String qn;			//商户渠道代码	支付请求同名参数透传	
	@DataValidate(description = "参数签名", nullable = false)
	public String sign;			//参数签名
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	public String getUp_invoice_no() {
		return up_invoice_no;
	}
	public void setUp_invoice_no(String up_invoice_no) {
		this.up_invoice_no = up_invoice_no;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getQn() {
		return qn;
	}
	public void setQn(String qn) {
		this.qn = qn;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("YiDianChargingFormBean [gameName=");
		builder.append(gameName);
		builder.append(", cpName=");
		builder.append(cpName);
		builder.append(", code=");
		builder.append(code);
		builder.append(", app_id=");
		builder.append(app_id);
		builder.append(", pay_way=");
		builder.append(pay_way);
		builder.append(", out_trade_no=");
		builder.append(out_trade_no);
		builder.append(", invoice_no=");
		builder.append(invoice_no);
		builder.append(", up_invoice_no=");
		builder.append(up_invoice_no);
		builder.append(", money=");
		builder.append(money);
		builder.append(", qn=");
		builder.append(qn);
		builder.append(", sign=");
		builder.append(sign);
		builder.append("]");
		return builder.toString();
	}
}
