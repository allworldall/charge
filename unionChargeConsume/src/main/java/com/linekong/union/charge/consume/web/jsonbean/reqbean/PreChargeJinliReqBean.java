package com.linekong.union.charge.consume.web.jsonbean.reqbean;

import com.linekong.union.charge.consume.web.formbean.PreChargeFormBean;

public class PreChargeJinliReqBean {

	private String user_id;			//用户id（参与签名）客户端登录返回的信息中 包含user_id,		
	private String out_order_no;	//商户订单号，数字字母构成，64个字符以内。商户需要确保订单号的唯一性，不能重复
	private String subject;			//商品名称，32个字符以内，不能含有半用“+”、“&”或特殊字符集
	private String submit_time;		//订单提交时间，格式为yyyyMMddHHmmss(2009年12月27日9点10分10秒表示为20091227091010)，时区为GMT+8 beijing。该时间需由商户服务器提供
	private String total_fee;		//需支付金额，值必须等于商品总金额（deal_price字段）
	private String sign;			//RSA签名
	private String api_key;			//商户APIKey
	private String deal_price;		//商品总金额
	private String deliver_type;	//付款方式：1为立即付款，2为货到付款(目前支付方式1，请选1)
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOut_order_no() {
		return out_order_no;
	}
	public void setOut_order_no(String out_order_no) {
		this.out_order_no = out_order_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public String getDeal_price() {
		return deal_price;
	}
	public void setDeal_price(String deal_price) {
		this.deal_price = deal_price;
	}
	public String getDeliver_type() {
		return deliver_type;
	}
	public void setDeliver_type(String deliver_type) {
		this.deliver_type = deliver_type;
	}

	public PreChargeJinliReqBean(JinliExpandInfo info, PreChargeFormBean bean,String paymentId,String apiKey,String date,String sign) {
		super();
		this.user_id = info.getJl_uid();
		this.out_order_no = paymentId;
		this.subject = bean.getProductName();
		this.submit_time = date;
		this.total_fee = ""+bean.getChargeMoney();
		this.sign = sign;
		this.api_key = apiKey;
		this.deal_price = ""+bean.getChargeMoney();
		this.deliver_type = info.getPayType();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("user_id=").append(user_id);
		sb.append(",out_order_no=").append(out_order_no);
		sb.append(",subject=").append(subject);
		sb.append(",submit_time=").append(submit_time);
		sb.append(",total_fee=").append(total_fee);
		sb.append(",sign=").append(sign);
		sb.append(",api_key=").append(api_key);
		sb.append(",deal_price=").append(deal_price);
		sb.append(",deliver_type=").append(deliver_type);
		return sb.toString();
	}
}
