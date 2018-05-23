package com.linekong.union.charge.consume.web.xmlbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class JifengResBean {

	private int errorCode;
	
	private String errorDesc;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public JifengResBean(int errorCode) {
		super();
		switch(errorCode){
			case  Constant.SUCCESS : 			   	errorCode = 1; errorDesc = Constant.DESC_SUCCESS; break;	//执行成功状态*****1
			case  Constant.ERROR_SYSTEM : 			errorCode = 0; errorDesc = Constant.DESC_ERROR_SYSTEM; break;	//系统异常****-200
			case  Constant.ERROR_ORDER_DUPLICATE : 	errorCode = 1; errorDesc = Constant.DESC_ERROR_ORDER_DUPLICATE; break;//订单号重复*****-1472
			case  Constant.ERROR_FAIL_ORDER	:		errorCode = 0; errorDesc = Constant.DESC_ERROR_FAIL_ORDER; break;//此订单为失败订单****-21011
			case  Constant.ERROR_ORDER_EXIST : 		errorCode = 0; errorDesc = Constant.DESC_ERROR_ORDER_EXIST; break;	//订单号不存在****-1490
			case  Constant.ERROR_SIGN : 			errorCode = 0; errorDesc = Constant.DESC_ERROR_SIGN; break;	//签名验证异常****-21000
			case  Constant.ERROR_REQUEST_CPGAME : 	errorCode = 0; errorDesc = Constant.DESC_ERROR_REQUEST_CPGAME; break;	//****-21001
			case  Constant.ERROR_REQUEST_CP : 		errorCode = 0; errorDesc = Constant.DESC_ERROR_REQUEST_CP; break;	//****-21002
			case  Constant.ERROR_REQUEST_CPGAME_STATE:errorCode=0; errorDesc = Constant.DESC_ERROR_REQUEST_CPGAME_STATE; break;	//渠道游戏配置未生效****-21023
			case  Constant.ERROR_REQUEST_CP_STATE:	errorCode = 0; errorDesc = Constant.DESC_ERROR_REQUEST_CP_STATE; break; 	//渠道配置未开放****-21022
			case  Constant.ERROR_CHARGE_STATUS : 	errorCode = 0; errorDesc = Constant.DESC_ERROR_CHARGE_STATUS; break;	//合作伙伴没有开通充值请联系管理员开通****-21003
			case  Constant.ERROR_CHARGE_SIGN : 		errorCode = 0; errorDesc = Constant.DESC_ERROR_CHARGE_SIGN; break;	//获取CpKey值异常****-21005
			case  Constant.ERROR_CHARGE_MONEY : 	errorCode = 0; errorDesc = Constant.DESC_ERROR_CHARGE_MONEY; break;	//预支付金额和回调金额不符****-21010
			case  Constant.ERROR_CHARGE_SERVER_IP : errorCode = 0; errorDesc = Constant.DESC_ERROR_CHARGE_SERVER_IP; break;	//没有配置合作伙伴ServerIP白名单****-21012
			case  Constant.ERROR_SERVER_IP : 		errorCode = 0; errorDesc = Constant.DESC_ERROR_SERVER_IP; break;	//与配置合作伙伴ServerIP白名单，不匹配****-21013
			case  Constant.ERROR_PARAM_VALIDATE :	errorCode = 0; errorDesc = Constant.DESC_ERROR_PARAM_VALIDATE; break;//参数校验异常
			default : errorCode = 0; errorDesc = "未知异常错误";
		}
		this.errorCode = errorCode;
	}
	public JifengResBean() {
		super();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("errorCode=").append(errorCode);
		sb.append(",errorDesc=").append(errorDesc);
		return sb.toString();
	}
}
