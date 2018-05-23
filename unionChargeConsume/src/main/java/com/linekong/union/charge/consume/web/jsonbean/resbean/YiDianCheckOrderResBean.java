package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class YiDianCheckOrderResBean {

	public int code;		//0 交易易成功    1	交易易处理理中      2	交易易未完成 （超过60秒的交易易）  3	 无此订单号其他为错误代码
	
	public String message;	//结果说明

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("YiDianCheckOrderResBean [code=");
		builder.append(code);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
