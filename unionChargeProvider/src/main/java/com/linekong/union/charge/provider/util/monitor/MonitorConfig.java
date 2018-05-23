package com.linekong.union.charge.provider.util.monitor;

import com.linekong.union.charge.provider.util.HttpUtils;
import com.linekong.union.charge.provider.util.log.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

public class MonitorConfig {
    public  static String  status_config      = "status";

    public  static String  invokeUrl_config   = "invokeUrl";

    public  static String  timer_config       = "timer";

    public  static String  resultCode_config  = "resultCode";

    private static boolean status;        //监控状态是否打开 true打开，false 关闭

    private static String  invokeUrl;     //调用监控URL地址

    private static Long    timer ;		  //处理时长 0 不进行监控 单位是毫秒

    private static String  resultCode;    //匹配错误码格式 例如 -10074,-1238,-1888,

    public static boolean getStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        MonitorConfig.status = status;
    }

    public static String getInvokeUrl() {
        return invokeUrl;
    }

    public static void setInvokeUrl(String invokeUrl) {
        MonitorConfig.invokeUrl = invokeUrl;
    }

    public static long getTimer() {
        return timer;
    }

    public static void setTimer(long timer) {
        MonitorConfig.timer = timer;
    }

    public static String getResultCode() {
        return resultCode;
    }

    public static void setResultCode(String resultCode) {
        MonitorConfig.resultCode = resultCode;
    }

    /**
     * 上报异常信息
     */
    public static void uploadInfo(String invokeurl,String projectName,String msglevel,String message,String resultCode){
        if(!MonitorConfig.getStatus()){
            LoggerUtil.info(MonitorConfig.class, "monitor_status:closed");
            return;
        }
        Map<String, String> monitorParams = new HashMap<String, String>();
        try{
            //monitorParams.put("invokeurl", invokeurl);
            monitorParams.put("result",resultCode);
            monitorParams.put("sysmodel", projectName);
            monitorParams.put("msglevel", msglevel);
            monitorParams.put("message",message);
            String result = HttpUtils.httpPost(MonitorConfig.getInvokeUrl(), monitorParams);
            LoggerUtil.info(MonitorConfig.class, "invoke monitor api:"+MonitorConfig.getInvokeUrl()+",param:"+monitorParams+",result:"+result);
        }catch (Exception e){
            LoggerUtil.error(MonitorConfig.class, "invoke monitor api:"+MonitorConfig.getInvokeUrl()+",param:"+monitorParams+",error:"+e.toString());
        }
    }
}
