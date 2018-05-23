package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class YouximaoResbean {
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

	public YouximaoResbean(String code, String message, String data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public YouximaoResbean() {
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

    public YouximaoResbean buildRespBean(int resCode) {
		switch (resCode) {
			case Constant.SUCCESS:
			case Constant.ERROR_ORDER_DUPLICATE: this.code = "000"; this.message = "success";break;
			case Constant.ERROR_SIGN : this.code = String.valueOf(Constant.ERROR_SIGN); this.message = "error sign"; break;
			case Constant.ERROR_SERVER_IP : this.code = String.valueOf(Constant.ERROR_SERVER_IP); this.message = "error white ip"; break;
			default: this.code = String.valueOf(resCode); this.message = "other error"; break;
		}
		return this;
    }
}
