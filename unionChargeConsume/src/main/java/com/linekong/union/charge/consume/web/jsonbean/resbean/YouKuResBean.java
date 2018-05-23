package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class YouKuResBean {
	// success,failed
    private String status;
    private String desc;
	
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public YouKuResBean(Integer resultCode) {
		super();
		switch(resultCode){
			case  Constant.SUCCESS : 			   	status ="success"; desc = Constant.DESC_SUCCESS; break;	//执行成功状态*****1
			case  Constant.ERROR_SYSTEM : 			status = "failed"; desc = Constant.DESC_ERROR_SYSTEM; break;	//系统异常****-200
			case  Constant.ERROR_ORDER_EXIST : 		status = "failed"; desc = Constant.DESC_ERROR_ORDER_EXIST; break;	//订单号不存在****-1490
			case  Constant.ERROR_SIGN : 			status = "failed"; desc = Constant.DESC_ERROR_SIGN; break;	//签名验证异常****-21000
			case  Constant.ERROR_REQUEST_CPGAME : 	status = "failed"; desc = Constant.DESC_ERROR_REQUEST_CPGAME; break;	//****-21001
			case  Constant.ERROR_REQUEST_CP : 		status = "failed"; desc = Constant.DESC_ERROR_REQUEST_CP; break;	//****-21002
			case  Constant.ERROR_REQUEST_CPGAME_STATE:status="failed"; desc = Constant.DESC_ERROR_REQUEST_CPGAME_STATE; break;	//渠道游戏配置未生效****-21023
			case  Constant.ERROR_REQUEST_CP_STATE:	status = "failed"; desc = Constant.DESC_ERROR_REQUEST_CP_STATE; break; 	//渠道配置未开放****-21022
			case  Constant.ERROR_CHARGE_STATUS : 	status = "failed"; desc = Constant.DESC_ERROR_CHARGE_STATUS; break;	//合作伙伴没有开通充值请联系管理员开通****-21003
			case  Constant.ERROR_CHARGE_SIGN : 		status = "failed"; desc = Constant.DESC_ERROR_CHARGE_SIGN; break;	//获取CpKey值异常****-21005
			case  Constant.ERROR_CHARGE_MONEY : 	status = "failed"; desc = Constant.DESC_ERROR_CHARGE_MONEY; break;	//预支付金额和回调金额不符****-21010
			case  Constant.ERROR_CHARGE_SERVER_IP : status = "failed"; desc = Constant.DESC_ERROR_CHARGE_SERVER_IP; break;	//没有配置合作伙伴ServerIP白名单****-21012
			case  Constant.ERROR_SERVER_IP : 		status = "failed"; desc = Constant.DESC_ERROR_SERVER_IP; break;	//与配置合作伙伴ServerIP白名单，不匹配****-21013
			case  Constant.ERROR_ORDER_DUPLICATE : 	status = "success"; desc = Constant.DESC_ERROR_ORDER_DUPLICATE; break;//订单号重复*****-1472
			case  Constant.ERROR_FAIL_ORDER	:		status = "failed"; desc = Constant.DESC_ERROR_FAIL_ORDER; break;//此订单为失败订单****-21011
			case  Constant.ERROR_PARAM_VALIDATE	:	status = "failed"; desc = Constant.DESC_ERROR_PARAM_VALIDATE; break;//参数校验异常****-21006
			case  Constant.ERROR_CALL_BACK_URL :	status = "failed"; desc = Constant.DESC_ERROR_CALL_BACK_URL;break;////参与签名的回调URL未配置
			default : status = "failed"; desc = Constant.DESC_ERROR_SYSTEM;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("status=").append(status);
		sb.append(",desc=").append(desc);
		return sb.toString();
	}
}
