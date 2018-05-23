package com.linekong.union.charge.consume.util.monitor;

import com.linekong.union.charge.consume.util.Common;

public class MonitorErrorInfoUpload implements Runnable{

	private String message;
	
	public MonitorErrorInfoUpload(String message){
		this.message = message;
	}
	@Override
	public void run() {
		MonitorConfig.uploadInfo(MonitorConfig.getInvokeUrl(), "unionConsumer("+Common.getLocalIp()+")", "严重", message, null);
	}

}
