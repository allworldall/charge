package com.linekong.union.charge.provider.bootstrap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.linekong.union.charge.provider.service.ChargeServiceImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.linekong.union.charge.provider.util.Constant;


/**
 * 应用启动入口
 * 当args[0] == 'develop'时怎直接去classpath中读取spring和log4j的配置信息（开发环境中使用）
 * 正式环境中则通过配置文件来进行配置在readme.txt中有详细配置说明
 * 
 */
public class BootStrap {
	
	private static final Logger log = Logger.getLogger(BootStrap.class);
	
	private static final Map<String,Object> map = new HashMap<String,Object>();
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext applicationContext = null;
		int arg = args.length;
		if(arg == 0){
			System.err.println("没有指定配置文件的路径，请指定配置文件路径！");
			System.exit(-1);
		}else{
			if(args[0].equals("develop")){
				log.info("Loading applicationContext.xml..........................");
				applicationContext = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
				log.info("Loading applicationContext.xml success...................");
				DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
				log.info("Loading dataSources.................................");
				if(dataSource != null){
					log.info("Loading dataSources success.....................");
				}else{
					log.info("Loading dataSources error.......................");
				}
			}else{
				//加载spring 和 log4j 配置
				loadConfigFile(args);
			}
		}
		
		synchronized (BootStrap.class) {
			while (true) {
				try {
					BootStrap.class.wait();
				} catch (InterruptedException e) {
					log.error("system InterruptedException:" + e.getMessage());
					System.exit(-1);
				}
			}
		}
	}
	
	//加载配置文件信息
	private static void loadConfigFile(String [] args){
		FileSystemXmlApplicationContext applicationContext= null;
		//获取配置文件路径
		String path = args[0];
		System.out.println("config file path:"+path);
		//读取配置文件属性
		Path p = Paths.get(path);
		List<String> list = new ArrayList<String>();
		try {
			list = Files.readAllLines(p, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("read config file error:" + e.getMessage());
			System.exit(-1);
		}
		// 校验配置信息中是否有配置内容
		if(list.size() == 0){
			System.err.println("config file content is null");
		}
		//获取配置文件中的内容
		for (String str : list) {
			String arr[] = str.split("=");
			map.put(arr[0], arr[1]);
		}
		//检查log4j配置值
		if(map.containsKey(Constant.CONFIG_LOG4J)){
			Constant.LOG4J_PATH = (String) map.get(Constant.CONFIG_LOG4J);
			PropertyConfigurator.configure(Constant.LOG4J_PATH);
		}else{
			System.err.println("log4j config path error...............");
			System.exit(-1);
		}
		//检查spring配置值
		if(map.containsKey(Constant.CONFIG_SPRING)){
			Constant.SPRING_PATH = (String) map.get(Constant.CONFIG_SPRING);
			log.info("Loading applicationContext.xml..........................");
			applicationContext = new FileSystemXmlApplicationContext(Constant.SPRING_PATH);
			applicationContext.start();
			log.info("Loading applicationContext.xml success.................................");
		}else{
			System.err.println("spring config path error ...............");
			System.exit(-1);
		}
		//判断数据库配置信息是否加载成功
		log.info("Loading dataSource........................................");
		DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
		if(dataSource != null){
			log.info("Loading dataSource success....................................");
		}else{
			log.error("Loading dataSource failure...................................");
			System.exit(-1);
		}


	}
}
