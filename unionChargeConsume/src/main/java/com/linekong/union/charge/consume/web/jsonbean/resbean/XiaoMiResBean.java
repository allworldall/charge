package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class XiaoMiResBean {
	private Integer errcode;
	private String errMsg;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public XiaoMiResBean(Integer errcode) {
		super();
		switch(errcode){
			case  Constant.SUCCESS : 			   	errcode =  200; errMsg = Constant.DESC_SUCCESS; break;	//执行成功状态*****1
			case  Constant.ERROR_SYSTEM : 			errcode = Constant.ERROR_SYSTEM; errMsg = Constant.DESC_ERROR_SYSTEM; break;	//系统异常****-200
			case  Constant.ERROR_ORDER_EXIST : 		errcode = 1506; errMsg = Constant.DESC_ERROR_ORDER_EXIST; break;	//订单号不存在****-1490
			case  Constant.ERROR_SIGN : 			errcode = 1525; errMsg = Constant.DESC_ERROR_SIGN; break;	//签名验证异常****-21000
			case  Constant.ERROR_REQUEST_CPGAME : 	errcode = Constant.ERROR_REQUEST_CPGAME; errMsg = Constant.DESC_ERROR_REQUEST_CPGAME; break;	//****-21001
			case  Constant.ERROR_REQUEST_CP : 		errcode = Constant.ERROR_REQUEST_CP; errMsg = Constant.DESC_ERROR_REQUEST_CP; break;	//****-21002
			case  Constant.ERROR_REQUEST_CPGAME_STATE:errcode=Constant.ERROR_REQUEST_CPGAME_STATE; errMsg = Constant.DESC_ERROR_REQUEST_CPGAME_STATE; break;	//渠道游戏配置未生效****-21023
			case  Constant.ERROR_REQUEST_CP_STATE:	errcode = Constant.ERROR_REQUEST_CP_STATE;	errMsg = Constant.DESC_ERROR_REQUEST_CP_STATE; break; 	//渠道配置未开放****-21022
			case  Constant.ERROR_CHARGE_STATUS : 	errcode = Constant.ERROR_CHARGE_STATUS; errMsg = Constant.DESC_ERROR_CHARGE_STATUS; break;	//合作伙伴没有开通充值请联系管理员开通****-21003
			case  Constant.ERROR_CHARGE_SIGN : 		errcode = Constant.ERROR_CHARGE_SIGN; errMsg = Constant.DESC_ERROR_CHARGE_SIGN; break;	//获取CpKey值异常****-21005
			case  Constant.ERROR_CHARGE_MONEY : 	errcode = 3515; errMsg = Constant.DESC_ERROR_CHARGE_MONEY; break;	//预支付金额和回调金额不符****-21010
			case  Constant.ERROR_CHARGE_SERVER_IP : errcode = Constant.ERROR_CHARGE_SERVER_IP; errMsg = Constant.DESC_ERROR_CHARGE_SERVER_IP; break;	//没有配置合作伙伴ServerIP白名单****-21012
			case  Constant.ERROR_SERVER_IP : 		errcode = Constant.ERROR_SERVER_IP; errMsg = Constant.DESC_ERROR_SERVER_IP; break;	//与配置合作伙伴ServerIP白名单，不匹配****-21013
			case  Constant.ERROR_ORDER_DUPLICATE : 	errcode = 200; errMsg = Constant.DESC_ERROR_ORDER_DUPLICATE; break;//订单号重复*****-1472
			case  Constant.ERROR_FAIL_ORDER	:		errcode = 1506; errMsg = Constant.DESC_ERROR_FAIL_ORDER; break;//此订单为失败订单****-21011
			case  Constant.ERROR_PARAM_VALIDATE	:	errcode = Constant.ERROR_PARAM_VALIDATE; errMsg = Constant.DESC_ERROR_PARAM_VALIDATE; break;//参数校验异常****-21006
			default : errcode = 3510; errMsg = Constant.DESC_ERROR_SYSTEM;
		}
		this.errcode = errcode;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("errcode=").append(errcode);
		sb.append(",errMsg=").append(errMsg);
		return sb.toString();
	}
}
