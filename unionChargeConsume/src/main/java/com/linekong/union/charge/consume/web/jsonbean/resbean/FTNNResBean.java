package com.linekong.union.charge.consume.web.jsonbean.resbean;

import com.linekong.union.charge.consume.util.Constant;

public class FTNNResBean {
	private int status;
	private String code;
	private int money;
	private long gamemoney;
	private String msg;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public long getGamemoney() {
		return gamemoney;
	}

	public void setGamemoney(long gamemoney) {
		this.gamemoney = gamemoney;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public FTNNResBean(int status, int money, long gamemoney) {
		super();
		switch(status){
			case  Constant.SUCCESS : 			   	status = 2; code = "null"; msg = Constant.DESC_SUCCESS; break;	//执行成功状态*****1
			case  Constant.ERROR_SYSTEM : 			status = 1; code = "other_error"; msg = Constant.DESC_ERROR_SYSTEM; break;	//系统异常****-200
			case  Constant.ERROR_ORDER_EXIST : 		status = 1; code = "other_error"; msg = Constant.DESC_ERROR_ORDER_EXIST; break;	//订单号不存在****-1490
			case  Constant.ERROR_SIGN : 			status = 1; code = "sign_error"; msg = Constant.DESC_ERROR_SIGN; break;	//签名验证异常****-21000
			case  Constant.ERROR_REQUEST_CPGAME : 	status = 1; code = "other_error"; msg = Constant.DESC_ERROR_REQUEST_CPGAME; break;	//****-21001
			case  Constant.ERROR_REQUEST_CP : 		status = 1; code = "other_error"; msg = Constant.DESC_ERROR_REQUEST_CP; break;	//****-21002
			case  Constant.ERROR_REQUEST_CPGAME_STATE:status=4; code = "other_error"; msg = Constant.DESC_ERROR_REQUEST_CPGAME_STATE; break;	//渠道游戏配置未生效****-21023
			case  Constant.ERROR_REQUEST_CP_STATE:	status = 4;	code = "other_error"; msg = Constant.DESC_ERROR_REQUEST_CP_STATE; break; 	//渠道配置未开放****-21022
			case  Constant.ERROR_CHARGE_STATUS : 	status = 1; code = "other_error"; msg = Constant.DESC_ERROR_CHARGE_STATUS; break;	//合作伙伴没有开通充值请联系管理员开通****-21003
			case  Constant.ERROR_CHARGE_SIGN : 		status = 1; code = "other_error"; msg = Constant.DESC_ERROR_CHARGE_SIGN; break;	//获取CpKey值异常****-21005
			case  Constant.ERROR_CHARGE_MONEY : 	status = 1; code = "money_error"; msg = Constant.DESC_ERROR_CHARGE_MONEY; break;	//预支付金额和回调金额不符****-21010
			case  Constant.ERROR_CHARGE_SERVER_IP : status = 1; code = "other_error"; msg = Constant.DESC_ERROR_CHARGE_SERVER_IP; break;	//没有配置合作伙伴ServerIP白名单****-21012
			case  Constant.ERROR_SERVER_IP : 		status = 1; code = "other_error"; msg = Constant.DESC_ERROR_SERVER_IP; break;	//与配置合作伙伴ServerIP白名单，不匹配****-21013
			case  Constant.ERROR_ORDER_DUPLICATE : 	status = 2; code = "other_error"; msg = Constant.DESC_ERROR_ORDER_DUPLICATE; break;//订单号重复*****-1472
			case  Constant.ERROR_FAIL_ORDER	:		status = 1; code = "other_error"; msg = Constant.DESC_ERROR_FAIL_ORDER; break;//此订单为失败订单****-21011
			case  Constant.ERROR_PARAM_VALIDATE	:	status = 1; code = "other_error"; msg = Constant.DESC_ERROR_PARAM_VALIDATE; break;//参数校验异常****-21006

			default : status = 1; code = "other_error"; msg = Constant.DESC_ERROR_SYSTEM;
		}
		this.status = status;
		this.money = money;
		this.gamemoney = gamemoney;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("status=").append(status);
		sb.append(",code=").append(code);
		sb.append(",money=").append(money);
		sb.append(",gamemoney=").append(gamemoney);
		sb.append(",msg=").append(msg);
		return sb.toString();
	}
}
