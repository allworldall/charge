package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class YiwanCharginFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "游戏服务器 ID", nullable = false)
	private int serverid;
	@DataValidate(description = "客户端传入的自定义数据", nullable = false)
	private String custominfo;
	@DataValidate(description = "合作方账号唯一标识", nullable = false)
	private String openid;
	@DataValidate(description = "订单 ID,益玩订单系统唯一号", nullable = false)
	private String ordernum;
	@DataValidate(description = "订单状态,1 为成功,其他为失败，例如：1", nullable = false)
	private String status;
	@DataValidate(description = "充值类型", nullable = false)
	// 1 支付宝，2 财付通，3 移动充值卡，4 电信充值卡，5 联通充值卡，6 Apple 官方支付，7 骏网一卡通，8 银行卡，9 MM 支付
	private String paytype;
	@DataValidate(description = "成功充值金额，单位为分，例如：100000", nullable = false)
	private int amount;
	@DataValidate(description = "充值失败错误码，成功为空", nullable = true)
	private String errdesc;
	@DataValidate(description = "充值成功时间,yyyyMMddHHmmss，例如：20121129234817", nullable = false)
	private String paytime;
	@DataValidate(description = "所有参数+appkey 的 MD5", nullable = false)
	//验证说明:合作方接收到我们的数据后根据 sign 的格式进行拼装好然后进行 MD5 后与 我们传过来的 sign 做比较，确保两个值是一致的（拼装时不要把 sign 拼装进去）
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
	public int getServerid() {
		return serverid;
	}
	public void setServerid(int serverid) {
		this.serverid = serverid;
	}
	public String getCustominfo() {
		return custominfo;
	}
	public void setCustominfo(String custominfo) {
		this.custominfo = custominfo;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getErrdesc() {
		return errdesc;
	}
	public void setErrdesc(String errdesc) {
		this.errdesc = errdesc;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
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
				+ cpName + ",serverid=" + serverid + ",custominfo="
				+ custominfo + ",openid=" + openid + ",ordernum=" + ordernum
				+ ",status=" + status + ",paytype=" + paytype + ",amount="
				+ amount + ",errdesc=" + errdesc + ",paytime=" + paytime
				+ ",sign=" + sign;
	}
	
}
