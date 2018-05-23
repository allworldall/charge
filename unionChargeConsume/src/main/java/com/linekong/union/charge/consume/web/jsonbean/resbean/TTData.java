package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class TTData {
    private int result;
    private String message;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("result=").append(result);
		sb.append(",message=").append(message);
		return sb.toString();
	}
}
