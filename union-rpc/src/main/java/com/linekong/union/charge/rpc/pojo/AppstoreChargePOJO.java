package com.linekong.union.charge.rpc.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 充值日志表
 * @author Administrator
 *
 */
public class AppstoreChargePOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9515902592246863L;

	private String chargeDetailId;			//充值订单号
	
	private String userName;				//用户标识

	private Integer gameId;					//游戏id

	private Integer gatewayId;				//区服

	private String productId;				//购买物品的product标示符， 示例： arthur.linekong.hz1

	private Integer quantity;				//物品购买数量

	private String appItemId;				//用于标识transaction中app的唯一性字符。在sandbox创建的receipt没有该字段

	private String transactionId;			//购买物品的transaction(事务)标示符，为transaction的transactionIdentifier 属性, 示例：1000000063634947

	private String purchaseDate;			//transaction发生时间，为transaction的transactionDate 属性， 示例：2013-02-02 14:00:39 Etc/GMT

	private String purchaseDateMs;			//

	private String purchaseDatePst;			//

	private String originalTransactionId;	//最初的transaction标示符， 示例： 1000000063634947

	private String originalPurchaseDate;	//最初的购买时间, 示例：2013-02-02 14:00:39 Etc/GMT

	private String originalPurchaseDateMs;	//

	private String originalPurchaseDatePst;	//

	private String uniqueIdentifier;		//设备的唯一标识

	private String bid;						//app的bundle identifier

	private String bvrs;					//app版本号

	private Date operateTime;				//操作时间

	private Integer chargeAmount;			//单个产品所对应的游戏币

	private Float productPrice;				//产品单价

	private Integer allChargeAmount;		//本次订单对应的游戏币

	private Float allProductPrice;			//产品总价

	public String getChargeDetailId() {
		return chargeDetailId;
	}

	public void setChargeDetailId(String chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Integer gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getAppItemId() {
		return appItemId;
	}

	public void setAppItemId(String appItemId) {
		this.appItemId = appItemId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getPurchaseDateMs() {
		return purchaseDateMs;
	}

	public void setPurchaseDateMs(String purchaseDateMs) {
		this.purchaseDateMs = purchaseDateMs;
	}

	public String getPurchaseDatePst() {
		return purchaseDatePst;
	}

	public void setPurchaseDatePst(String purchaseDatePst) {
		this.purchaseDatePst = purchaseDatePst;
	}

	public String getOriginalTransactionId() {
		return originalTransactionId;
	}

	public void setOriginalTransactionId(String originalTransactionId) {
		this.originalTransactionId = originalTransactionId;
	}

	public String getOriginalPurchaseDate() {
		return originalPurchaseDate;
	}

	public void setOriginalPurchaseDate(String originalPurchaseDate) {
		this.originalPurchaseDate = originalPurchaseDate;
	}

	public String getOriginalPurchaseDateMs() {
		return originalPurchaseDateMs;
	}

	public void setOriginalPurchaseDateMs(String originalPurchaseDateMs) {
		this.originalPurchaseDateMs = originalPurchaseDateMs;
	}

	public String getOriginalPurchaseDatePst() {
		return originalPurchaseDatePst;
	}

	public void setOriginalPurchaseDatePst(String originalPurchaseDatePst) {
		this.originalPurchaseDatePst = originalPurchaseDatePst;
	}

	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getBvrs() {
		return bvrs;
	}

	public void setBvrs(String bvrs) {
		this.bvrs = bvrs;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Integer getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Integer chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public Float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getAllChargeAmount() {
		return allChargeAmount;
	}

	public void setAllChargeAmount(Integer allChargeAmount) {
		this.allChargeAmount = allChargeAmount;
	}

	public Float getAllProductPrice() {
		return allProductPrice;
	}

	public void setAllProductPrice(Float allProductPrice) {
		this.allProductPrice = allProductPrice;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("chargeDetailId=").append(chargeDetailId);
		sb.append(",userName=").append(userName);
		sb.append(",gameId=").append(gameId);
		sb.append(",gatewayId=").append(gatewayId);
		sb.append(",productId=").append(productId);
		sb.append(",quantity=").append(quantity);
		sb.append(",appItemId=").append(appItemId);
		sb.append(",transactionId=").append(transactionId);
		sb.append(",purchaseDate=").append(purchaseDate);
		sb.append(",purchaseDateMs=").append(purchaseDateMs);
		sb.append(",purchaseDatePst=").append(purchaseDatePst);
		sb.append(",originalTransactionId=").append(originalTransactionId);
		sb.append(",originalPurchaseDate=").append(originalPurchaseDate);
		sb.append(",originalPurchaseDateMs=").append(originalPurchaseDateMs);
		sb.append(",originalPurchaseDatePst=").append(originalPurchaseDatePst);
		sb.append(",uniqueIdentifier=").append(uniqueIdentifier);
		sb.append(",bid=").append(bid);
		sb.append(",bvrs=").append(bvrs);
		sb.append(",operateTime=").append(operateTime);
		sb.append(",chargeAmount=").append(chargeAmount);
		sb.append(",productPrice=").append(productPrice);
		sb.append(",allChargeAmount=").append(allChargeAmount);
		sb.append(",allProductPrice=").append(allProductPrice);
		return sb.toString();
	}

	public AppstoreChargePOJO() {
		super();
	}
	
}
