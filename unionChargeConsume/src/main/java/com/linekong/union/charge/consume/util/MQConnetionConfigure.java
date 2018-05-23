package com.linekong.union.charge.consume.util;

public class MQConnetionConfigure {
	
	public String			host;				//连接MQ的地址
	
	public int				port;				//MQ端口
	
	public String			userName;			//连接MQ需要的用户名
	
	public String			password;			//连接MQ需要的密码
	
	public int		    	connectionTimeOut;  //连接超时时间
	
	public int				requestedHeartbeat; //请求心跳超时时间单位秒

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public int getRequestedHeartbeat() {
		return requestedHeartbeat;
	}

	public void setRequestedHeartbeat(int requestedHeartbeat) {
		this.requestedHeartbeat = requestedHeartbeat;
	}
}
