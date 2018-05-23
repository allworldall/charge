package com.linekong.union.charge.consume.web.bootinit;

import java.io.File;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.linekong.union.charge.consume.rabbitmq.ChargeInvokeRabbitMQ;
import com.linekong.union.charge.consume.rabbitmq.ChargeToGameSuccessListioner;
import com.linekong.union.charge.consume.service.ChargeToGameSuccessMQ;
import com.linekong.union.charge.consume.service.RepairOrder;
import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.consume.util.ThreadPoolUtil;
import com.linekong.union.charge.rpc.charge.QueryChargeServiceInterface;
import com.linekong.union.charge.consume.util.config.SendChargeInfoToKafkaConfig;

public class BootStrapInit {
	
	private FileSystemXmlApplicationContext applicationMQContext= null;
	
	private FileSystemXmlApplicationContext applicationDubboContext= null;
	
	public void loadInit(){
		PropertyConfigurator.configure(System.getenv("CHARGE_CONFIG")+File.separator+"log4j.properties");
		String baseDir = System.getenv("CHARGE_CONFIG");
		//获取操作系统类型
		String os = System.getProperty("os.name"); 
		if(!os.toLowerCase().startsWith("win")){
			baseDir = "/" + baseDir;
		}
		//扫描文件，装配IOC
		applicationMQContext = new FileSystemXmlApplicationContext(baseDir+File.separator+"spring-mq-consumer.xml");
		applicationDubboContext = new FileSystemXmlApplicationContext(baseDir+File.separator+"spring-dubbo-consumer.xml");
		//初始化kafka信息
		SendChargeInfoToKafkaConfig config = (SendChargeInfoToKafkaConfig)applicationMQContext.getBean("sendChargeInfoConfig");
		//启动线程监听
		final ChargeToGameSuccessListioner chargeToGameSuccessListioner = (ChargeToGameSuccessListioner) applicationMQContext.getBean("chargeToGameSuccessListioner");
		ChargeServerDao chargeServerDao= (ChargeServerDao) applicationMQContext.getBean("chargeServerDaoImpl");
		
		QueryServerDao queryServerDao= (QueryServerDao) applicationMQContext.getBean("queryServerDaoImpl");
		QueryChargeServiceInterface  queryChargeServiceInterface = (QueryChargeServiceInterface) applicationDubboContext.getBean("queryChargeServiceInterface");
		ChargeInvokeRabbitMQ chargeInvokeRabbitMQ= (ChargeInvokeRabbitMQ)applicationMQContext.getBean("chargeInvokeRabbitMQ");
		
		//监听erating充值回复MQ  线程
		ThreadPoolUtil.pool.execute(new ChargeToGameSuccessMQ(chargeToGameSuccessListioner,chargeServerDao,queryServerDao));
		//补单线程
		ThreadPoolUtil.pool.execute(new RepairOrder(queryServerDao,chargeServerDao,queryChargeServiceInterface,chargeInvokeRabbitMQ));	
	}
}
