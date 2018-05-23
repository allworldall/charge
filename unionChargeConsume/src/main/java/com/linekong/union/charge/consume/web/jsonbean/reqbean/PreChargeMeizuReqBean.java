package com.linekong.union.charge.consume.web.jsonbean.reqbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class PreChargeMeizuReqBean {
	private String product_subject;		//订单标题
	private String product_per_price;	//产品单价
	@DataValidate(description = "支付类型： 1 不定金额充值， 0 购买",nullable = false)
	private String pay_type;			//支付类型： 1 不定金额充值， 0 购买
	
	public String getProduct_subject() {
		return product_subject;
	}
	public void setProduct_subject(String product_subject) {
		this.product_subject = product_subject;
	}
	public String getProduct_per_price() {
		return product_per_price;
	}
	public void setProduct_per_price(String product_per_price) {
		this.product_per_price = product_per_price;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("product_subject=").append(product_subject);
		sb.append(",product_per_price=").append(product_per_price);
		sb.append(",pay_type=").append(pay_type);
		return sb.toString();
	}
}
