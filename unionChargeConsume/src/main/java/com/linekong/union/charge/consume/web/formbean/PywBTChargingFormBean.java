package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.PywBTReqBean;

public class PywBTChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "版本 ID,例如：1.0", nullable = true)
	private String ver;
	@DataValidate(description = "请求流水号，16位字串（唯一），例如：cd79421cbe7bcd4c", nullable = false)
	private String tid;
	@DataValidate(description = "签名串", nullable = false)
	//md5(apiSecret+cp_orderid+ch_orderid+amount)每个游戏的 apiSecret 都不同，
		//朋友玩提供例如：fc1dc4549df0335d7f506edb5d66af16
	private String sign;
	@DataValidate(description = "游戏标识，由朋友玩提供。例如：123456abc", nullable = false)
	private String gamekey;
	@DataValidate(description = "渠道标识，例如：PYW", nullable = false)
	private String channel;
	@DataValidate(description = "CP订单号，例如：1445332489", nullable = false)
	private String cp_orderid;
	@DataValidate(description = "渠道订单ID，例如：L1510191N0000951", nullable = false)
	private String ch_orderid;
	@DataValidate(description = "订单金额，单位：元，保留小数点后两位，例如：100.00", nullable = false)
	private String amount;
	@DataValidate(description = "Cp接收参数（订单生成时额外参数）", nullable = true)
	private String cp_param;
	
	public PywBTChargingFormBean(String gameName,String cpName,PywBTReqBean bean){
		this.gameName = gameName;
		this.cpName = cpName;
		this.ver = bean.getVer();
		this.tid = bean.getTid();
		this.sign =bean.getSign().replace(" ", "+").replace("\\", "");
		this.gamekey = bean.getGamekey();
		this.channel = bean.getChannel();
		this.cp_orderid = bean.getCp_orderid();
		this.ch_orderid = bean.getCh_orderid();
		this.amount = bean.getAmount();
		this.cp_param = bean.getCp_param().toString();
	}
	
	public String getGameName() {
		return gameName;
	}
	public String getCpName() {
		return cpName;
	}
	
	public String getGamekey() {
		return gamekey;
	}

	public void setGamekey(String gamekey) {
		this.gamekey = gamekey;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setCp_orderid(String cp_orderid) {
		this.cp_orderid = cp_orderid;
	}

	public void setCh_orderid(String ch_orderid) {
		this.ch_orderid = ch_orderid;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setCp_param(String cp_param) {
		this.cp_param = cp_param;
	}

	public String getVer() {
		return ver;
	}
	
	public String getTid() {
		return tid;
	}
	
	public String getSign() {
		return sign;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public String getCp_orderid() {
		return cp_orderid;
	}
	
	public String getCh_orderid() {
		return ch_orderid;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public String getCp_param() {
		return cp_param;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",ver=" + ver + ",tid=" + tid + ",sign=" + sign
				+ ",gamekey=" + gamekey + ",channel=" + channel
				+ ",cp_orderid=" + cp_orderid + ",ch_orderid=" + ch_orderid
				+ ",amount=" + amount + ",cp_param=" + cp_param;
	}
	
}
