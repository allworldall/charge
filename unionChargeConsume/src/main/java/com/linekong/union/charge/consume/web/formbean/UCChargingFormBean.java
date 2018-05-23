package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.UCReqBean;

public class UCChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "版本号", nullable = false)
	private String ver;
	@DataValidate(description = "充值订单号，此订单号由 UC游戏 SDK生成，游戏客户端在进行充值时从 SDK获得", nullable = false)
	private String orderId;
	@DataValidate(description = "游戏编号，由 UC分配", nullable = false)
	private String gameId;
	@DataValidate(description = "账号标识", nullable = false)
	private String accountId;
	@DataValidate(description = "账号的创建者。例如JY：九游,PP：PP助手", nullable = false)
	private String creator;
	@DataValidate(description = "支付通道代码，统一费率“ 999”，不提供详细充值方式", nullable = false)
	// 单位：元。 游戏服务端必须根据 UC回调的金额值进行校验并下发相应价值的虚拟货币。
	private String payWay;
	@DataValidate(description = "支付金额", nullable = false)
	private String amount;
	@DataValidate(description = "游戏合作商自定义参数，", nullable = false)
	// 游戏客户端在充值时传入，SDK 服务器不做任何处理，在进行充值结果回调时发送给游戏服务器，
	// 该字段建议使用字母或数字形式传入，建议不要传入特殊字符。 (注意：长度不超过250）
	private String callbackInfo;
	@DataValidate(description = "订单状态，", nullable = false)
	// S-成功支付
	// F-支付失败，
	// 游戏需判断S 的订单再下发道具
	private String orderStatus;
	@DataValidate(description = "订单失败原因详细描述，如果是成功支付，则为空串", nullable = true)
	private String failedDesc;
	@DataValidate(description = "Cp订单号", nullable = true)
	// 仅当客户端调用支付方法传入了transactionNumCP 参数时，才会将原内容通过 cpOrderId 参数透传回游戏服务端。
	// 该参数有传递时，才需加入签名，如无，则不需要加入签名(注意：长度不超过 30）
	private String cpOrderId;
	@DataValidate(description = "MD5(签名内容+apiKey),签名内容为 data 所有子字段按字段名升序拼接（剔除&符号及回车和换行符）", nullable = false)
	private String sign;

	public UCChargingFormBean() {
		super();
	}

	public UCChargingFormBean(UCReqBean bean, String gameName, String cpName){
		this.gameName=gameName;
		this.cpName = cpName;
		this.ver = bean.getVer();
		this.sign = bean.getSign().replace(" ", "+").replace("\\", "");
		this.orderId =bean.getData().getOrderId();
		this.gameId=bean.getData().getGameId();
		this.accountId=bean.getData().getAccountId();
		this.creator=bean.getData().getCreator();
		this.payWay=bean.getData().getPayWay();
		this.amount=bean.getData().getAmount();
        this.callbackInfo=bean.getData().getCallbackInfo();
        this.orderStatus=bean.getData().getOrderStatus();
        this.failedDesc=bean.getData().getFailedDesc();
        this.cpOrderId=bean.getData().getCpOrderId();
	}


	public String getGameName() {
		return gameName;
	}

	public String getCpName() {
		return cpName;
	}

	public String getVer() {
		return ver;
	}

	public String getOrderId() {
		return orderId;
	}
	
	public String getGameId() {
		return gameId;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getCreator() {
		return creator;
	}

	public String getPayWay() {
		return payWay;
	}

	public String getAmount() {
		return amount;
	}

	public String getCallbackInfo() {
		return callbackInfo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public String getFailedDesc() {
		return failedDesc;
	}

	public String getCpOrderId() {
		return cpOrderId;
	}

	public String getSign() {
		return sign;
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

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setFailedDesc(String failedDesc) {
		this.failedDesc = failedDesc;
	}

	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",ver=" + ver + ",orderId=" + orderId
				+ ",gameId=" + gameId + ",accountId=" + accountId
				+ ",creator=" + creator + ",payWay=" + payWay + ",amount="
				+ amount + ",callbackInfo=" + callbackInfo + ",orderStatus="
				+ orderStatus + ",failedDesc=" + failedDesc + ",cpOrderId="
				+ cpOrderId + ",sign=" + sign;
	}

}
