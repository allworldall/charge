package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class PPSResBean {
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

	public PPSResBean(int result) {
		super();
		switch(result){
			case  Constant.SUCCESS : 			   	result =  0; message = Constant.DESC_SUCCESS; break;	//执行成功状态*****1
			case  Constant.ERROR_SYSTEM : 			result = -6; message = Constant.DESC_ERROR_SYSTEM; break;	//系统异常****-200
			case  Constant.ERROR_ORDER_EXIST : 		result = -6; message = Constant.DESC_ERROR_ORDER_EXIST; break;	//订单号不存在****-1490
			case  Constant.ERROR_SIGN : 			result = -1; message = Constant.DESC_ERROR_SIGN; break;	//签名验证异常****-21000
			case  Constant.ERROR_REQUEST_CPGAME : 	result = -6; message = Constant.DESC_ERROR_REQUEST_CPGAME; break;	//****-21001
			case  Constant.ERROR_REQUEST_CP : 		result = -6; message = Constant.DESC_ERROR_REQUEST_CP; break;	//****-21002
			case  Constant.ERROR_REQUEST_CPGAME_STATE:result=-6; message = Constant.DESC_ERROR_REQUEST_CPGAME_STATE; break;	//渠道游戏配置未生效****-21023
			case  Constant.ERROR_REQUEST_CP_STATE:	result = -6; message = Constant.DESC_ERROR_REQUEST_CP_STATE; break; 	//渠道配置未开放****-21022
			case  Constant.ERROR_CHARGE_STATUS : 	result = -6; message = Constant.DESC_ERROR_CHARGE_STATUS; break;	//合作伙伴没有开通充值请联系管理员开通****-21003
			case  Constant.ERROR_CHARGE_SIGN : 		result = -6; message = Constant.DESC_ERROR_CHARGE_SIGN; break;	//获取CpKey值异常****-21005
			case  Constant.ERROR_CHARGE_MONEY : 	result = -6; message = Constant.DESC_ERROR_CHARGE_MONEY; break;	//预支付金额和回调金额不符****-21010
			case  Constant.ERROR_CHARGE_SERVER_IP : result = -6; message = Constant.DESC_ERROR_CHARGE_SERVER_IP; break;	//没有配置合作伙伴ServerIP白名单****-21012
			case  Constant.ERROR_SERVER_IP : 		result = -6; message = Constant.DESC_ERROR_SERVER_IP; break;	//与配置合作伙伴ServerIP白名单，不匹配****-21013
			case  Constant.ERROR_ORDER_DUPLICATE : 	result = 0; message = Constant.DESC_ERROR_ORDER_DUPLICATE; break;//订单号重复*****-1472
			case  Constant.ERROR_FAIL_ORDER	:		result = -6; message = Constant.DESC_ERROR_FAIL_ORDER; break;//此订单为失败订单****-21011
			case  Constant.ERROR_PARAM_VALIDATE	:	result = -6; message = Constant.DESC_ERROR_PARAM_VALIDATE; break;//参数校验异常****-21006

			default : result = -6; message = Constant.DESC_ERROR_SYSTEM;
		}
		this.result = result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("result=").append(result);
		sb.append(",message=").append(message);
		return sb.toString();
	}
}
