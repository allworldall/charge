package com.linekong.union.charge.consume.web.xmlbean.reqbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class JifengReqDetailBean {
	@DataValidate(description = "订单号", nullable = false)
	private String order_id;
	@DataValidate(description = "支付 key", nullable = false)
	private String appkey;
	@DataValidate(description = "机锋卷", nullable = false)
	private String cost;
	@DataValidate(description = "订单创建时间", nullable = false)
	private String create_time;
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public JifengReqDetailBean(String order_id, String appkey, String cost,
			String create_time) {
		super();
		this.order_id = order_id;
		this.appkey = appkey;
		this.cost = cost;
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("order_id=").append(order_id);
		sb.append(",appkey=").append(appkey);
		sb.append(",cost=").append(cost);
		sb.append(",create_time=").append(create_time);
		return sb.toString();
	}

	public JifengReqDetailBean() {
		super();
	}

}
