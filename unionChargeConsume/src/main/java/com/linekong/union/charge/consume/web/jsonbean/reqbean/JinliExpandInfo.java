package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class JinliExpandInfo {
    private String payType;
    private String jl_uid;
    
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getJl_uid() {
		return jl_uid;
	}
	public void setJl_uid(String jl_uid) {
		this.jl_uid = jl_uid;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("payType=").append(payType);
		sb.append(",jl_uid=").append(jl_uid);
		return sb.toString();
	}
}
