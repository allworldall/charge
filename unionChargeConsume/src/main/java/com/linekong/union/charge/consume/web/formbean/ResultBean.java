package com.linekong.union.charge.consume.web.formbean;

public class ResultBean {

	public Integer resultCode;
	
	public String cpKey;

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getCpKey() {
		return cpKey;
	}

	public void setCpKey(String cpKey) {
		this.cpKey = cpKey;
	}

	public ResultBean(Integer resultCode, String cpKey) {
		super();
		this.resultCode = resultCode;
		this.cpKey = cpKey;
	}

	public ResultBean() {
		super();
	}

	@Override
	public String toString() {
		return "resultCode=" + resultCode + ",cpKey=" + cpKey
				+ ",result=" + resultCode;
	}
}
