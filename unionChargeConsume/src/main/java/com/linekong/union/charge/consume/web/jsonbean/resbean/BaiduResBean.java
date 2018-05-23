package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class BaiduResBean {
	
	private int AppID;
	private int ResultCode;
	private String ResultMsg;
	private String Sign;
	private String Content;
	public int getAppID() {
		return AppID;
	}
	public void setAppID(int appID) {
		AppID = appID;
	}
	public int getResultCode() {
		return ResultCode;
	}
	public void setResultCode(int resultCode) {
		ResultCode = resultCode;
	}
	public String getResultMsg() {
		return ResultMsg;
	}
	public void setResultMsg(String resultMsg) {
		ResultMsg = resultMsg;
	}
	public String getSign() {
		return Sign;
	}
	public void setSign(String sign) {
		Sign = sign;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public BaiduResBean(int appID, int resultCode, String content) {
		super();
		AppID = appID;
		switch(resultCode){
			case  Constant.SUCCESS : 			   	ResultCode = 1; ResultMsg = Constant.DESC_SUCCESS; break;	//执行成功状态*****1
			case  Constant.ERROR_SYSTEM : 			ResultCode = 4; ResultMsg = Constant.DESC_ERROR_SYSTEM; break;	//系统异常****-200
			case  Constant.ERROR_UID:               ResultCode = 91;ResultMsg = "UID不一致";break;
			case  Constant.ERROR_MerchandiseName:   ResultCode = 91;ResultMsg = "MerchandiseName不一致";break;
			case  Constant.ERROR_ExtInfo:           ResultCode = 91;ResultMsg = "ExtInfo不一致";break;
			case  Constant.ERROR_ORDER_EXIST : 		ResultCode = 91; ResultMsg = Constant.DESC_ERROR_ORDER_EXIST; break;	//订单号不存在****-1490
			case  Constant.ERROR_SIGN : 			ResultCode = 91; ResultMsg = "签名异常"; break;	//签名验证异常****-21000
			case  Constant.ERROR_REQUEST_CPGAME : 	ResultCode = 4; ResultMsg = Constant.DESC_ERROR_REQUEST_CPGAME; break;	//预支付中请求地址和请求参数不匹配(gameName和gameId不匹配)****-21001
			case  Constant.ERROR_REQUEST_CPGAME_STATE:ResultCode=4; ResultMsg = Constant.DESC_ERROR_REQUEST_CPGAME_STATE; break;	//渠道游戏配置未生效****-21023
			case  Constant.ERROR_REQUEST_CP_STATE:	ResultCode = 4;	ResultMsg = Constant.DESC_ERROR_REQUEST_CP_STATE; break; 	//渠道配置未开放****-21022
			case  Constant.ERROR_REQUEST_CP : 		ResultCode = 4; ResultMsg = Constant.DESC_ERROR_REQUEST_CP; break;	//预支付中请求地址和请求参数不匹配(cpName和cpId不匹配)****-21002
			case  Constant.ERROR_CHARGE_STATUS : 	ResultCode = 4; ResultMsg = Constant.DESC_ERROR_CHARGE_STATUS; break;	//合作伙伴没有开通充值请联系管理员开通****-21003
			case  Constant.ERROR_CHARGE_SIGN : 		ResultCode = 4; ResultMsg = Constant.DESC_ERROR_CHARGE_SIGN; break;	//获取CpKey值异常****-21005
			case  Constant.ERROR_CHARGE_MONEY : 	ResultCode = 91; ResultMsg = "orderMoney金额不一致"; break;	//预支付金额和回调金额不符****-21010
			case  Constant.ERROR_CHARGE_SERVER_IP : ResultCode = 4; ResultMsg = Constant.DESC_ERROR_CHARGE_SERVER_IP; break;	//没有配置合作伙伴ServerIP白名单****-21012
			case  Constant.ERROR_SERVER_IP : 		ResultCode = 4; ResultMsg = Constant.DESC_ERROR_SERVER_IP; break;	//与配置合作伙伴ServerIP白名单，不匹配****-21013
			case  Constant.ERROR_ORDER_DUPLICATE : 	ResultCode = 4; ResultMsg = Constant.DESC_ERROR_ORDER_DUPLICATE; break;//订单号重复*****-1472
			case  Constant.ERROR_FAIL_ORDER	:		ResultCode = 4; ResultMsg = Constant.DESC_ERROR_FAIL_ORDER; break;//此订单为失败订单****-21011
			case  Constant.ERROR_PARAM_VALIDATE	:	ResultCode = 4; ResultMsg = Constant.DESC_ERROR_PARAM_VALIDATE; break;//参数校验异常****-21006
			default : ResultCode = 4; ResultMsg = Constant.DESC_ERROR_SYSTEM;
		}
		Content = content;
	}
	public BaiduResBean() {
		super();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("AppID=").append(AppID);
		sb.append(",ResultCode=").append(ResultCode);
		sb.append(",ResultMsg=").append(ResultMsg);
		sb.append(",Sign=").append(Sign);
		sb.append(",Content=").append(Content);
		return sb.toString();
	}
}
