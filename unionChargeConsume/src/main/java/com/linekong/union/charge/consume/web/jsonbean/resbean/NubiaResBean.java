package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.google.gson.JsonObject;
import com.linekong.union.charge.consume.util.JsonUtil;
/**
 * 努比亚回调返回结果
 * @author Administrator
 *
 */
public class NubiaResBean {

	public int code;			//0：成功；10000：发货失败；90000：其他异常
	
	public JsonObject data;		//暂可为空
	
	public String message;		//失败时必填，请填写失败的原因；成功可以不用填写

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NubiaResBean(int code,JsonObject data, String message) {
		super();
		this.code = code;
		this.data = data;
		this.message = message;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("code=").append(code);
		sb.append(",data=").append(data);
		sb.append(",message=").append(message);
		return sb.toString();
	}
}
