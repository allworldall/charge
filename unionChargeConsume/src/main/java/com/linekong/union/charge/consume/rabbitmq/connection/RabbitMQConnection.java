package com.linekong.union.charge.consume.rabbitmq.connection;

import java.io.File;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.linekong.union.charge.consume.util.DBSecurityUtils;
import com.linekong.union.charge.consume.util.MQConnetionConfigure;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.rabbitmq.client.ConnectionFactory;

@SuppressWarnings("resource")
public class RabbitMQConnection {
	
	protected ConnectionFactory connectionFactory = null;
	protected static MQConnetionConfigure mqConnetionConfigure;
	
	static {		
		String baseDir = System.getenv("CHARGE_CONFIG");
		String os = System.getProperty("os.name"); 
		if(!os.toLowerCase().startsWith("win")){
			baseDir = "/" + baseDir;
		}
		mqConnetionConfigure = new FileSystemXmlApplicationContext(baseDir+File.separator+"spring-mq-consumer.xml").getBean("mqConnetionConfigure",MQConnetionConfigure.class);		
	}
	
	protected void initRabbitMQConnection() throws Exception{
		if(connectionFactory == null){
			connectionFactory = new ConnectionFactory();
			connectionFactory.setHost(mqConnetionConfigure.getHost());
			connectionFactory.setPort(mqConnetionConfigure.getPort());
			connectionFactory.setUsername(mqConnetionConfigure.getUserName());
			String pwd = DBSecurityUtils.decode(mqConnetionConfigure.getPassword());
			LoggerUtil.info(RabbitMQConnection.class, "RabbitMQ userName="+mqConnetionConfigure.getUserName()+",pwd="+pwd+",secrect_pwd="+mqConnetionConfigure.getPassword());
			connectionFactory.setPassword(pwd);
			//自动重连设置
			connectionFactory.setAutomaticRecoveryEnabled(true);
			connectionFactory.setConnectionTimeout(mqConnetionConfigure.getConnectionTimeOut());
			connectionFactory.setRequestedHeartbeat(mqConnetionConfigure.getRequestedHeartbeat());
		}
	} 

}
