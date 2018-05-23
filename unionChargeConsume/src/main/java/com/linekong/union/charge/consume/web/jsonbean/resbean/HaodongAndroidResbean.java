package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class HaodongAndroidResbean {
	private String code;		//状态码(code='000'表示成功)
	
	private String message;//状态码对应信息描述
	
	private String data;	//返回结果(此接口 data 返回空字符)

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public HaodongAndroidResbean(String code, String message, String data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public HaodongAndroidResbean() {
		super();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("code=").append(code);
		sb.append(",message=").append(message);
		sb.append(",data=").append(data);
		return sb.toString();
	}
}
