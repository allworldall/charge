package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class FTNNChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "(4399生成的订单号) 22位以内的字符串 唯一", nullable = false)
	private String orderid;
	@DataValidate(description = "充值渠道id", nullable = false)
	private Integer p_type;
	@DataValidate(description = "要充值的用户ID，my平台的用户uid", nullable = false)
	private String uid;
	@DataValidate(description = "用户充值的人民币金额，单位：元y", nullable = false)
	private Integer money;
	@DataValidate(description = "兑换的游戏币数量", nullable = false)
	//兑换标准由双方共同约定，若在标准兑换比率基础上，有一些优惠或者赠送策略，可无视此数据，由应用端自行计算实际的兑换数额
	private Integer gamemoney;
	@DataValidate(description = "要充值的服务区号(最多不超过8位)", nullable = true)
	//只针对有分服的游戏有效。参数的格式为：区服id。 例如 1服 为 1, 11服为 11
	private String serverid;
	@DataValidate(description = "游戏方生成的订单号", nullable = false)
	//作为预留字段，部分游戏在游戏内发起充值时，会生成唯一标识来标注该笔充值的相关信息时，可以用本字段。
	private String mark;
	@DataValidate(description = "要充值的游戏角色id，只针对PC端充值时", nullable = true)
	private String roleid;
	@DataValidate(description = "发起请求时的时间戳", nullable = false)
	private String time;
	//签名计算为：$sign = md5($orderid. $uid.$money.$gamemoney.$serverid.$secrect.$mark . $roleid.$time); 	当参数$serverid,$mark ,$roleid为空时，不参与签名计算。
	@DataValidate(description = "加密签名", nullable = false)
	private String sign;
	
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
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	public Integer getP_type() {
		return p_type;
	}
	public void setP_type(Integer p_type) {
		this.p_type = p_type;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getGamemoney() {
		return gamemoney;
	}
	public void setGamemoney(Integer gamemoney) {
		this.gamemoney = gamemoney;
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",orderid=" + orderid + ",p_type=" + p_type
				+ ",uid=" + uid + ",money=" + money + ",gamemoney="
				+ gamemoney + ",serverid=" + serverid + ",mark=" + mark
				+ ",roleid=" + roleid + ",time=" + time + ",sign=" + sign;
	}
}
