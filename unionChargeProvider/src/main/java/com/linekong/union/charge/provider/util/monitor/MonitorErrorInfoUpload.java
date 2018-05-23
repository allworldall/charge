package com.linekong.union.charge.provider.util.monitor;

import com.linekong.union.charge.provider.util.Common;

public class MonitorErrorInfoUpload implements Runnable{
    private String message;

    public MonitorErrorInfoUpload(String message){
        this.message = message;
    }
    @Override
    public void run() {
        MonitorConfig.uploadInfo(MonitorConfig.getInvokeUrl(), "unionProvider("+ Common.getLocalIp()+")", "严重", message, null);
    }
}
