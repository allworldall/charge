package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class LeTvReqBean {
	private String externalProductId;
	private String quantity;
	private String sku;
	private String total;

	public String getExternalProductId() {
		return externalProductId;
	}

	public void setExternalProductId(String externalProductId) {
		this.externalProductId = externalProductId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("externalProductId=").append(externalProductId);
		sb.append(",quantity=").append(quantity);
		sb.append(",sku=").append(sku);
		sb.append(",total=").append(total);
		return sb.toString();
	}
}
