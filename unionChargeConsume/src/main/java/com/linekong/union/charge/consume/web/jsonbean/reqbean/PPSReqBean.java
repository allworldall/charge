package com.linekong.union.charge.consume.web.jsonbean.reqbean;

public class PPSReqBean {
    private int result;
    private String messge;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMessge() {
		return messge;
	}
	public void setMessge(String messge) {
		this.messge = messge;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("result=").append(result);
		sb.append(",messge=").append(messge);
		return sb.toString();
	}
}
