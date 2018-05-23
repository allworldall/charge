package com.linekong.union.charge.consume.web.formbean;

import com.linekong.union.charge.consume.util.annotation.DataValidate;

/**
 * 泡椒IOS
 * @author Administrator
 *
 */
public class PaoJiaoIOSFormBean {

	@DataValidate(description = "请求地址中的gameName", nullable = false)
	private String gameName;
	@DataValidate(description = "请求地址中的cpName", nullable = false)
	private String cpName;
	@DataValidate(description = "玩家ID(泡椒ID)", nullable = false)
	private String uid; 
	@DataValidate(description = "订单编号(泡椒网订单号)", nullable = false)
	private String orderNo; 
	@DataValidate(description = "订单价格，浮点类型(单位：元)", nullable = false)
	private String price; 
	@DataValidate(description = "角色ID(游戏服上角色ID)", nullable = false)
	private String roleId; 
	
	private String gameId; 		//游戏ID(接入sdk时泡椒提供)
	@DataValidate(description = "游戏服务器id", nullable = false)
	private String serverId; 
	@DataValidate(description = "订单完成时间", nullable = false)
	private String finishedTime; 
	@DataValidate(description = "订单编号(游戏服务器上的订单编号)", nullable = false)
	private String cpOrderNo; 
	
	private String ext; 		
	@DataValidate(description = "签名", nullable = false)
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
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
	public String getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(String finishedTime) {
		this.finishedTime = finishedTime;
	}
	public String getCpOrderNo() {
		return cpOrderNo;
	}
	public void setCpOrderNo(String cpOrderNo) {
		this.cpOrderNo = cpOrderNo;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public PaoJiaoIOSFormBean() {
		super();
	}
	@Override
	public String toString() {
		return "gameName=" + gameName + ",cpName="
				+ cpName + ",uid=" + uid + ",orderNo=" + orderNo + ",price="
				+ price + ",roleId=" + roleId + ",gameId=" + gameId
				+ ",serverId=" + serverId + ",finishedTime=" + finishedTime
				+ ",cpOrderNo=" + cpOrderNo + ",ext=" + ext + ",sign="
				+ sign;
	}
}
