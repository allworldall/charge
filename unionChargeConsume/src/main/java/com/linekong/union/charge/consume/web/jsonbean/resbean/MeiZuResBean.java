package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class MeiZuResBean {
    private int code;
    private String message;
    private String value;
    private String redirect;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRedirect() {
		return redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	public MeiZuResBean(int code, String value, String redirect) {
		super();
		switch(code){
			case  Constant.SUCCESS : 			   	code = 200; message = Constant.DESC_SUCCESS; break;	//执行成功状态*****1
			case  Constant.ERROR_SYSTEM : 			code = 900000; message = Constant.DESC_ERROR_SYSTEM; break;	//系统异常****-200
			case  Constant.ERROR_ORDER_EXIST : 		code = 900000; message = Constant.DESC_ERROR_ORDER_EXIST; break;	//订单号不存在****-1490
			case  Constant.ERROR_SIGN : 			code = 900000; message = Constant.DESC_ERROR_SIGN; break;	//签名验证异常****-21000
			case  Constant.ERROR_REQUEST_CPGAME : 	code = 900000; message = Constant.DESC_ERROR_REQUEST_CPGAME; break;	//****-21001
			case  Constant.ERROR_REQUEST_CP : 		code = 900000; message = Constant.DESC_ERROR_REQUEST_CP; break;	//****-21002
			case  Constant.ERROR_REQUEST_CPGAME_STATE:code=900000; message = Constant.DESC_ERROR_REQUEST_CPGAME_STATE; break;	//渠道游戏配置未生效****-21023
			case  Constant.ERROR_REQUEST_CP_STATE:	code = 900000; message = Constant.DESC_ERROR_REQUEST_CP_STATE; break; 	//渠道配置未开放****-21022
			case  Constant.ERROR_CHARGE_STATUS : 	code = 900000; message = Constant.DESC_ERROR_CHARGE_STATUS; break;	//合作伙伴没有开通充值请联系管理员开通****-21003
			case  Constant.ERROR_CHARGE_SIGN : 		code = 900000; message = Constant.DESC_ERROR_CHARGE_SIGN; break;	//获取CpKey值异常****-21005
			case  Constant.ERROR_CHARGE_MONEY : 	code = 900000; message = Constant.DESC_ERROR_CHARGE_MONEY; break;	//预支付金额和回调金额不符****-21010
			case  Constant.ERROR_CHARGE_SERVER_IP : code = 900000; message = Constant.DESC_ERROR_CHARGE_SERVER_IP; break;	//没有配置合作伙伴ServerIP白名单****-21012
			case  Constant.ERROR_SERVER_IP : 		code = 900000; message = Constant.DESC_ERROR_SERVER_IP; break;	//与配置合作伙伴ServerIP白名单，不匹配****-21013
			case  Constant.ERROR_ORDER_DUPLICATE : 	code = 200; message = Constant.DESC_ERROR_ORDER_DUPLICATE; break;//订单号重复*****-1472
			case  Constant.ERROR_FAIL_ORDER	:		code = 900000; message = Constant.DESC_ERROR_FAIL_ORDER; break;//此订单为失败订单****-21011
			case  Constant.ERROR_PARAM_VALIDATE	:	code = 900000; message = Constant.DESC_ERROR_PARAM_VALIDATE; break;//参数校验异常****-21006
			default : code = 900000; message = Constant.DESC_ERROR_SYSTEM;
		}
		this.code = code;
		this.value = value;
		this.redirect = redirect;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("code=").append(code);
		sb.append(",message=").append(message);
		sb.append(",value=").append(value);
		sb.append(",redirect=").append(redirect);
		return sb.toString();
	}
}
