package com.linekong.union.charge.consume.util.monitor;

import com.linekong.union.charge.consume.util.Common;

public class MonitorInfoUpload implements Runnable{ 
	
	private String message;
	
	private long timer;
	
	private String result;
	
	public MonitorInfoUpload(String result,String message,long timer){
		this.message = message;
		this.result = result;
		this.timer = timer;
	}
	@Override
	public void run() {
		if(timer >= MonitorConfig.getTimer()){//进行监控上报
			//监控时间超时
			MonitorConfig.uploadInfo(MonitorConfig.getInvokeUrl(), "unionConsumer("+Common.getLocalIp()+")", "警告-超时", message+"timeout:"+timer, null);
		}else{
			//匹配监控的错误码
			if(MonitorConfig.getResultCode().indexOf(result+";") >= 0){
				MonitorConfig.uploadInfo(MonitorConfig.getInvokeUrl(), "unionConsume", "警告-错误码", message, result);
			}
		}
	}
}
