package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class UCReqBean {
    private String ver;
    private UCData data;
    private String sign;
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public UCData getData() {
		return data;
	}
	public void setData(UCData data) {
		this.data = data;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("ver=").append(ver);
		sb.append(",data=").append(data);
		sb.append(",sign=").append(sign);
		return sb.toString();
	}
}
