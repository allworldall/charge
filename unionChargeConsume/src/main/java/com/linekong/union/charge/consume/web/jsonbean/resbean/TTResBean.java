package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class TTResBean {
    private TTData head;

	public TTData getHead() {
		return head;
	}

	public void setHead(TTData head) {
		this.head = head;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("head=").append(head);
		return sb.toString();
	}
}
