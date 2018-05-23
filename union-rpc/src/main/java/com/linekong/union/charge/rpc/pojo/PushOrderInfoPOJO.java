package com.linekong.union.charge.rpc.pojo;

import java.io.Serializable;

public class PushOrderInfoPOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2693216824689146747L;
	
	private String 			unionOrderId;			//联运订单号
	
	private long			chargeDetailId;			//蓝港订单号
	
	private int				gameId;					//游戏ID
	
	private String			serverIP;				//服务器IP
	
	private String			clientIp;				//客户端请求IP

	public String getUnionOrderId() {
		return unionOrderId;
	}

	public void setUnionOrderId(String unionOrderId) {
		this.unionOrderId = unionOrderId;
	}

	public long getChargeDetailId() {
		return chargeDetailId;
	}

	public void setChargeDetailId(long chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public PushOrderInfoPOJO(String unionOrderId, long chargeDetailId,
			String serverIP, String clientIp) {
		super();
		this.unionOrderId = unionOrderId;
		this.chargeDetailId = chargeDetailId;
		this.serverIP = serverIP;
		this.clientIp = clientIp;
	}

	public PushOrderInfoPOJO() {
		super();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("unionOrderId=").append(unionOrderId);
		sb.append(",chargeDetailId=").append(chargeDetailId);
		sb.append(",gameId=").append(gameId);
		sb.append(",serverIP=").append(serverIP);
		sb.append(",clientIp=").append(clientIp);
		return sb.toString();
	}
}
