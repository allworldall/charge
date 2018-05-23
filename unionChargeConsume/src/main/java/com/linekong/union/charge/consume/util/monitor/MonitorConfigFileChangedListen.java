package com.linekong.union.charge.consume.util.monitor;

import com.linekong.union.charge.consume.util.log.LoggerUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 上报规则配置文件改动时触发监听
 */
public class MonitorConfigFileChangedListen {
	
	// 定义线程池
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
	// 定义监控对象
	private WatchService watchService;
	// 定义监控文件路径
	private String listenPath;

	public MonitorConfigFileChangedListen(String path,String fileName) {
		readConfigFile(path+File.separator+fileName);
		this.listenPath = path;
		try {
			watchService = FileSystems.getDefault().newWatchService();
			Path p = Paths.get(path);
			p.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
			start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		fixedThreadPool.execute(new ListenFileChange(watchService, listenPath));
	}

//	public static void addListener(String path) throws IOException {
//		//MonitorConfigFileChangedListen resourceListener = new MonitorConfigFileChangedListen(path);
//		Path p = Paths.get(path);
//		p.register(resourceListener.watchService, StandardWatchEventKinds.ENTRY_MODIFY,
//				StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
//	}
	
	private class ListenFileChange implements Runnable {

		private WatchService watchService;  
	    private String listenPath;
	    
	    public ListenFileChange(WatchService watchService,String path){
	    	this.watchService = watchService;
	    	this.listenPath = path;
	    }
		@Override
		public void run() {
			try {  
	            while(true){  
	                WatchKey watchKey = watchService.take();  
	                List<WatchEvent<?>> watchEvents = watchKey.pollEvents();  
	                for(WatchEvent<?> event : watchEvents){
	                    //TODO 根据事件类型采取不同的操作。。。。。。。  
	                    if(event.kind().name().equals("ENTRY_MODIFY")){//文件发生了改动
	                    	readConfigFile(listenPath+File.separator+event.context());
	                    }
	                }  
	                watchKey.reset();  
	            }  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }finally{  
	            System.out.println("fdsfsdf");  
	            try {
	            	watchService.close();  
	            } catch (IOException e) {
	                e.printStackTrace();  
	            }  
	        } 
		}
	}
	public static void readConfigFile(String path){
   	//读取配置文件
   	//读取配置文件属性
		Path p = Paths.get(path);
		List<String> list = new ArrayList<String>();
		try {
			list = Files.readAllLines(p, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<String,String>();
		for (String str : list) {
			String arr[] = str.split("=");
			map.put(arr[0], arr[1]);
		}
		//获取上报状态参数
		if(map.containsKey(MonitorConfig.status_config)){
			MonitorConfig.setStatus(Boolean.parseBoolean(map.get(MonitorConfig.status_config)));
		}
		//获取调用上报地址
		if(map.containsKey(MonitorConfig.invokeUrl_config)){
			MonitorConfig.setInvokeUrl(map.get(MonitorConfig.invokeUrl_config));
		}
		//获取返回值监控范围参数
		if(map.containsKey(MonitorConfig.resultCode_config)){
			MonitorConfig.setResultCode(map.get(MonitorConfig.resultCode_config));
		}
		//获取时间监控范围参数
		if(map.containsKey(MonitorConfig.timer_config)){
			MonitorConfig.setTimer(Long.parseLong(map.get(MonitorConfig.timer_config)));
		}
		LoggerUtil.info(MonitorConfigFileChangedListen.class, map.toString());
	}
	public static void main(String[] args) {
		MonitorConfigFileChangedListen  monitor = new MonitorConfigFileChangedListen("/Users/fangming/Downloads","test.conf");
	}
}
