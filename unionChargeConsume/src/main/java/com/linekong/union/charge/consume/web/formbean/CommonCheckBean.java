package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;

public class CommonCheckBean {
	
	private String gameName;			//游戏Code
		
	private String cpName;				//合作伙伴Code
	
	private String ordeID;				//预支付订单号
	
	private PrePaymentPOJO payment;		//预支付信息
	
	private Integer ratio;				//充值比例
	
	private String remoteIp;			//请求的IP
	
	private int cpGameId;				//渠道游戏ID
	
	private int cpId;					//渠道ID
	
	private int pushType;				//推送方式

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getOrdeID() {
		return ordeID;
	}

	public void setOrdeID(String ordeID) {
		this.ordeID = ordeID;
	}

	public PrePaymentPOJO getPayment() {
		return payment;
	}

	public void setPayment(PrePaymentPOJO payment) {
		this.payment = payment;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public int getCpGameId() {
		return cpGameId;
	}

	public void setCpGameId(int cpGameId) {
		this.cpGameId = cpGameId;
	}

	public int getCpId() {
		return cpId;
	}

	public void setCpId(int cpId) {
		this.cpId = cpId;
	}

	public int getPushType() {
		return pushType;
	}

	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

	public CommonCheckBean( String ordeID, String remoteIp, int cpId,
			int cpGameId,String cpName) {
		super();
		this.cpId = cpId;
		this.ordeID = ordeID;
		this.remoteIp = remoteIp;
		this.cpGameId = cpGameId;
		this.cpName = cpName;
	}

	public CommonCheckBean(String gameName, String cpName, String ordeID,
			String remoteIp) {
		super();
		this.gameName = gameName;
		this.cpName = cpName;
		this.ordeID = ordeID;
		this.remoteIp = remoteIp;
	}
	public CommonCheckBean() {
		super();
	}
	
}
