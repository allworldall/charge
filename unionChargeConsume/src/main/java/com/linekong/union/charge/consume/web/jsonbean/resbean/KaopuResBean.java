package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class KaopuResBean {

	private String code;
	
	private String msg;
	
	private String sign;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public KaopuResBean(Integer result) {
		super();
		switch(result){
			case Constant.SUCCESS : 				code = "1000"; msg=Constant.DESC_SUCCESS;break;
			case Constant.ERROR_PARAM_VALIDATE : 	code = "1004"; msg=Constant.DESC_ERROR_PARAM_VALIDATE;break;
			case Constant.ERROR_SYSTEM : 			code = "1005"; msg=Constant.DESC_ERROR_SYSTEM;break;
			case Constant.ERROR_ORDER_EXIST : 		code = "1003"; msg=Constant.DESC_ERROR_ORDER_EXIST;break;
			case Constant.ERROR_SIGN : 				code = "1002"; msg=Constant.DESC_ERROR_SIGN;break;
			case Constant.ERROR_REQUEST_CPGAME : 	code = "1004"; msg=Constant.DESC_ERROR_REQUEST_CPGAME;break;
			case Constant.ERROR_REQUEST_CP : 		code = "1004"; msg=Constant.DESC_ERROR_REQUEST_CP;break;
			case Constant.ERROR_REQUEST_CPGAME_STATE:code= "1004"; msg=Constant.DESC_ERROR_REQUEST_CPGAME_STATE; break;	//渠道游戏配置未生效****-21023
			case Constant.ERROR_REQUEST_CP_STATE:	code = "1004"; msg=Constant.DESC_ERROR_REQUEST_CP_STATE; break; 	//渠道配置未开放****-21022
			case Constant.ERROR_CHARGE_STATUS : 	code = "1005"; msg=Constant.DESC_ERROR_CHARGE_STATUS;break;
			case Constant.ERROR_CHARGE_SIGN : 		code = "1005"; msg=Constant.DESC_ERROR_CHARGE_SIGN;break;
			case Constant.ERROR_CHARGE_MONEY : 		code = "1009"; msg=Constant.DESC_ERROR_CHARGE_MONEY;break;
			case Constant.ERROR_FAIL_ORDER : 		code = "1004"; msg=Constant.DESC_ERROR_FAIL_ORDER;break;
			case Constant.ERROR_CHARGE_SERVER_IP :  code = "1005"; msg=Constant.DESC_ERROR_CHARGE_SERVER_IP;break;
			case Constant.ERROR_SERVER_IP : 		code = "1005"; msg=Constant.DESC_ERROR_SERVER_IP;break;
			case Constant.ERROR_ORDER_DUPLICATE : 	code = "1000"; msg=Constant.DESC_ERROR_ORDER_DUPLICATE; break;//订单号重复*****-1472
			default	 	: 							code = "1005"; msg=Constant.DESC_ERROR_SYSTEM;
		}
	}

	public KaopuResBean(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "code=" + code + ",msg=" + msg + ",sign=" + sign;
	}
}
