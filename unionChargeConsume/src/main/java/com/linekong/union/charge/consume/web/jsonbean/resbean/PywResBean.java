package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class PywResBean {
    private int ack;
    private String msg;
	public int getAck() {
		return ack;
	}
	public void setAck(int ack) {
		this.ack = ack;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("ack=").append(ack);
		sb.append(",msg=").append(msg);
		return sb.toString();
	}
}
