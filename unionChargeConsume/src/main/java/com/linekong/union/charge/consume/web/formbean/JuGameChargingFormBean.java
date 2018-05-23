package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;
/**
 * 8864
 * @author Administrator
 *
 */
public class JuGameChargingFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description = "签名参数", nullable = false)
	private String sign;
	@DataValidate(description = "支付结果数据", nullable = false)
	private JugameReqBean data;
	
	public class JugameReqBean {	
		@DataValidate(description = "充值订单号", nullable = false)
		private String orderId;
		@DataValidate(description = "游戏编号", nullable = false)
		private String gameId;
		@DataValidate(description = "服务器编号", nullable = false)
		private String serverId;
		@DataValidate(description = "角色编号", nullable = true)
		private String roleId;
		@DataValidate(description = "聚好玩游戏账号ID", nullable = false)
		private String suid;
		@DataValidate(description = "支付通道代码", nullable = false)
		private String payWay;
		@DataValidate(description = "支付金额", nullable = false)
		private String amount;
		@DataValidate(description = "游戏合作商自定义参数", nullable = false)
		private String callbackInfo;
		@DataValidate(description = "订单状态", nullable = false)
		private String orderStatus;
		@DataValidate(description = "订单失败原因详细描述", nullable = true)
		private String failedDesc;
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getGameId() {
			return gameId;
		}
		public void setGameId(String gameId) {
			this.gameId = gameId;
		}
		public String getServerId() {
			return serverId;
		}
		public void setServerId(String serverId) {
			this.serverId = serverId;
		}
		public String getRoleId() {
			return roleId;
		}
		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
		public String getSuid() {
			return suid;
		}
		public void setSuid(String suid) {
			this.suid = suid;
		}
		public String getPayWay() {
			return payWay;
		}
		public void setPayWay(String payWay) {
			this.payWay = payWay;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getCallbackInfo() {
			return callbackInfo;
		}
		public void setCallbackInfo(String callbackInfo) {
			this.callbackInfo = callbackInfo;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public String getFailedDesc() {
			return failedDesc;
		}
		public void setFailedDesc(String failedDesc) {
			this.failedDesc = failedDesc;
		}
		@Override
		public String toString() {
			return "orderId=" + orderId + ",gameId=" + gameId
					+ ",serverId=" + serverId + ",roleId=" + roleId
					+ ",suid=" + suid + ",payWay=" + payWay + ",amount="
					+ amount + ",callbackInfo=" + callbackInfo
					+ ",orderStatus=" + orderStatus + ",failedDesc="
					+ failedDesc;
		}
		
	}

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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}

	public JugameReqBean getData() {
		return data;
	}

	public void setData(JugameReqBean data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",sign=" + sign + ",data=" + data;
	}
	
}
