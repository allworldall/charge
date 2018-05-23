package com.linekong.union.charge.consume.web.jsonbean.resbean;

public class HuaWeiResBean {
	/***
	 * 操作结果， 0 表示成功，
     * 1: 验签失败,
     * 2: 超时,
     * 3: 业务信息错误，比如订单不存在,
     * 94: 系统错误,
     * 95: IO 错误,
     * 96: 错误的 url,
     * 97: 错误的响应,
     * 98: 参数错误,
     * 99: 其他错误
	 * */
     private int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public HuaWeiResBean(int result) {
		super();
		this.result = result;
	}
	public HuaWeiResBean() {
		super();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("result=").append(result);
		return sb.toString();
	}
}
