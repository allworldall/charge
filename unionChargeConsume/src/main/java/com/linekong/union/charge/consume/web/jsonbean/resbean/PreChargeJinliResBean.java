package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class PreChargeJinliResBean {

	private String status;			//返回状态码
	private String description;		//描述信息
	private String order_no;		//支付订单号，创建失败时返回空
	private String api_key;			//申请创建该支付订单的APIKey
	private String out_order_no;	//商户订单号，与创建支付订单请求时相同，请以这个订单号来调起收银台。
	private String submit_time ;	//订单提交时间，格式为yyyyMMddHHmmss(2009年12月27日9点10分10秒表示为20091227091010)，时区为GMT+8 beijing。该返回值同请求创建支付订单的“submit_time”字段值一致
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public String getOut_order_no() {
		return out_order_no;
	}
	public void setOut_order_no(String out_order_no) {
		this.out_order_no = out_order_no;
	}
	public String getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("status=").append(status);
		sb.append(",description=").append(description);
		sb.append(",order_no=").append(order_no);
		sb.append(",api_key=").append(api_key);
		sb.append(",out_order_no=").append(out_order_no);
		sb.append(",submit_time=").append(submit_time);
		return sb.toString();
	}
}
