package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

public class ICCChargingFormBean {
	
	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	
	@DataValidate(description = "用户名", nullable = false)
	private String userName;
	@DataValidate(description = "订单号", nullable = false)
	private String orderidClient;
	@DataValidate(description = "网关ID", nullable = false)
	private String gatewayId;
	@DataValidate(description = "游戏ID", nullable = false)
	private String gameId;
	@DataValidate(description = "透传信息", nullable = false)
	private String customInfo;
	@DataValidate(description = "chargeMoney", nullable = false)
	private String chargeMoney;
	@DataValidate(description = "渠道", nullable = false)
	private String platName;
	@DataValidate(description = "sign", nullable = false)
	private String sign;
	@DataValidate(description = "content", nullable = false)
	private String content;
	
	public class content{
		@DataValidate(description = "物品名", nullable = false)
		private String name;
		@DataValidate(description = "count", nullable = false)
		private String count;
		@DataValidate(description = "price", nullable = false)
		private String price;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		@Override
		public String toString() {
			return "content [name=" + name + ", count=" + count + ", price="
					+ price + "]";
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderidClient() {
		return orderidClient;
	}

	public void setOrderidClient(String orderidClient) {
		this.orderidClient = orderidClient;
	}

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getCustomInfo() {
		return customInfo;
	}

	public void setCustomInfo(String customInfo) {
		this.customInfo = customInfo;
	}

	public String getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(String chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	public String getPlatName() {
		return platName;
	}

	public void setPlatName(String platName) {
		this.platName = platName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign.replace(" ", "+").replace("\\", "");
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",userName=" + userName + ",orderidClient="
				+ orderidClient + ",gatewayId=" + gatewayId + ",gameId="
				+ gameId + ",customInfo=" + customInfo + ",chargeMoney="
				+ chargeMoney + ",platName=" + platName + ",sign=" + sign
				+ ",content=" + content;
	}
	
}
