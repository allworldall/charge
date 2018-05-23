package com.linekong.union.charge.rpc.pojo;

import java.io.Serializable;

/**
 * APPSTORE物品表对应ＰＯＪＯ
 * @author Administrator
 *
 */
public class AppstoreProductPOJO  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5873722645693841386L;

		private int gameId;				//游戏
		
		private String productId;		//购买物品的product标示符， 示例： arthur.linekong.hz1
		
		private Integer chargeAmount;	//单个产品所对应的游戏币
		
		private Double productPrice;	//产品单价(单位：美元)

		public int getGameId() {
			return gameId;
		}

		public void setGameId(int gameId) {
			this.gameId = gameId;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public Integer getChargeAmount() {
			return chargeAmount;
		}

		public void setChargeAmount(Integer chargeAmount) {
			this.chargeAmount = chargeAmount;
		}

		public Double getProductPrice() {
			return productPrice;
		}

		public void setProductPrice(Double productPrice) {
			this.productPrice = productPrice;
		}

		public AppstoreProductPOJO() {
			super();
		}

		public AppstoreProductPOJO(int gameId, String productId) {
			super();
			this.gameId = gameId;
			this.productId = productId;
		}

		public AppstoreProductPOJO(Integer chargeAmount, Double productPrice) {
			super();
			this.chargeAmount = chargeAmount;
			this.productPrice = productPrice;
		}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("gameId=").append(gameId);
		sb.append(",productId=").append(productId);
		sb.append(",chargeAmount=").append(chargeAmount);
		sb.append(",productPrice=").append(productPrice);
		return sb.toString();
	}
}
