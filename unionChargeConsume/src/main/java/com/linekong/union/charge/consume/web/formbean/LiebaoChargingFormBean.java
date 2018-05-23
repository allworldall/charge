package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.Common;
import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class LiebaoChargingFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "LB 订单号 ", nullable = false)
	private String  orderid;
	@DataValidate(description = "LB 登录帐号", nullable = false)
	private String  username;
	@DataValidate(description = "游戏 ID", nullable = false)
	private String  gameid;
	@DataValidate(description = "游戏角色 ID", nullable = false)
	private String  roleid;
	@DataValidate(description = "服务器 ID ", nullable = false)
	private String  serverid;
	@DataValidate(description = "支付类型", nullable = false)
	private String  paytype;
	@DataValidate(description = "成功充值金额", nullable = false)
	private String  amount;
	@DataValidate(description = "玩家充值时间", nullable = false)
	private String  paytime;
	@DataValidate(description = "商户拓展参数  ", nullable = false)
	private String  attach;
	@DataValidate(description = "参数签名", nullable = false)
	private String  sign;
	
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
		this.orderid = Common.urlEncode(orderid,"UTF-8");
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = Common.urlEncode(username,"UTF-8");
	}
	public String getGameid() {
		return gameid;
	}
	public void setGameid(String gameid) {
		this.gameid = gameid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = Common.urlEncode(roleid,"UTF-8");
	}
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = Common.urlEncode(paytype,"UTF-8");
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = Common.urlEncode(attach,"UTF-8");
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = Common.urlEncode(sign,"UTF-8").replace(" ", "+").replace("\\", "");
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",orderid=" + orderid + ",username=" + username
				+ ",gameid=" + gameid + ",roleid=" + roleid + ",serverid="
				+ serverid + ",paytype=" + paytype + ",amount=" + amount
				+ ",paytime=" + paytime + ",attach=" + attach + ",sign="
				+ sign;
	}
	
	
}
