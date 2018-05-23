package com.linekong.union.charge.consume.rabbitmq;

import java.io.IOException;

import com.linekong.union.charge.consume.rabbitmq.connection.RabbitMQConnection;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ChargeInvokeRabbitMQ extends RabbitMQConnection{
	
	private static String exchangeName;				//交换机名称
	
	private static String chargeQueue;				//充值推送元宝队列名称
	
	private static String chargeActivity;			//充值活动队列信息推送
	
	private static String chargeRoutKey;			//充值路由队列key
	
	private static String activityRoutKey;			//活动路由队列key
	
	
	/**
	 * 充值成功推送消息到队列中
	 * @param String data 需要推送的消息内容
	 * @throws Exception 
	 * @throws IOException 
	 */
	public void chargePushInvokeMQ(String chargeQueueData) throws IOException, Exception{
		LoggerUtil.info(ChargeInvokeRabbitMQ.class, "call MQ-chargePushInvokeMQ-charge success push message queue_successOrderInfo:"+chargeQueueData);
		ConnectionFactory connection = null;
		if(this.connectionFactory == null){//rabbitmq连接异常创建连接
			this.initRabbitMQConnection();
		}
		connection = this.connectionFactory;
		//创建连接对象  
        Connection conn = connection.newConnection(); 
        Channel channel = conn.createChannel();
        //定义交换机
        channel.exchangeDeclare(exchangeName, "direct");
        //声明队列
        channel.queueDeclare(chargeQueue, false, false, false, null);//队列名称，是否持久化
        //交换机队列信息绑定
        channel.queueBind(chargeQueue, exchangeName, chargeRoutKey);
        //消息发送
        channel.basicPublish(exchangeName,chargeRoutKey, null, chargeQueueData.getBytes());
        channel.close();
		conn.close();
	}
	/**
	 * 充值成功推送活动队列
	 * @param successOrderInfo 回调参数的推送
	 * @throws IOException
	 * @throws Exception
	 */
	public void chargeActivityInvokeMQ(String successOrderInfo) throws IOException, Exception{
		LoggerUtil.info(ChargeInvokeRabbitMQ.class, "call MQ-chargeActivityInvokeMQ-charge success push activity queue_successOrderInfo:"+successOrderInfo);
		ConnectionFactory connection = null;
		if(this.connectionFactory == null){//rabbitmq连接异常创建连接
			this.initRabbitMQConnection();
		}
		connection = this.connectionFactory;
		//创建连接对象  
        Connection conn = connection.newConnection(); 
        Channel channel = conn.createChannel();
        //定义交换机
        channel.exchangeDeclare(exchangeName, "direct");
        //声明队列
        channel.queueDeclare(chargeActivity, false, false, false, null);
        //交换机队列信息绑定
        channel.queueBind(chargeActivity, exchangeName, activityRoutKey);
        //消息发送
        channel.basicPublish(exchangeName,activityRoutKey, null, successOrderInfo.getBytes());
        channel.close();
		conn.close();
	}
	/**
	 * 补单推送消息到队列中
	 * @param String data 需要推送的消息内容
	 * @throws Exception 
	 * @throws IOException 
	 */
	public void repairOrderMQ(String data) throws IOException, Exception{
		ConnectionFactory connection = null;
		if(this.connectionFactory == null){//rabbitmq连接异常创建连接
			this.initRabbitMQConnection();
		}else{
			connection = this.connectionFactory;
		}
		//创建连接对象  
		Connection conn = connection.newConnection();
		Channel channel = conn.createChannel();
		//定义交换机
		channel.exchangeDeclare(exchangeName, "direct");
		//声明队列
		channel.queueDeclare(chargeQueue, false, false, false, null);//队列名称，是否持久化
		//交换机队列信息绑定
		channel.queueBind(chargeQueue, exchangeName, chargeRoutKey);
		//消息发送
		channel.basicPublish(exchangeName,chargeRoutKey, null, data.getBytes());
		channel.close();
		conn.close();
	}

	public static String getExchangeName() {
		return exchangeName;
	}

	public static void setExchangeName(String exchangeName) {
		ChargeInvokeRabbitMQ.exchangeName = exchangeName;
	}

	public static String getChargeQueue() {
		return chargeQueue;
	}

	public static void setChargeQueue(String chargeQueue) {
		ChargeInvokeRabbitMQ.chargeQueue = chargeQueue;
	}

	public static String getChargeActivity() {
		return chargeActivity;
	}

	public static void setChargeActivity(String chargeActivity) {
		ChargeInvokeRabbitMQ.chargeActivity = chargeActivity;
	}

	public static String getChargeRoutKey() {
		return chargeRoutKey;
	}

	public static void setChargeRoutKey(String chargeRoutKey) {
		ChargeInvokeRabbitMQ.chargeRoutKey = chargeRoutKey;
	}

	public static String getActivityRoutKey() {
		return activityRoutKey;
	}

	public static void setActivityRoutKey(String activityRoutKey) {
		ChargeInvokeRabbitMQ.activityRoutKey = activityRoutKey;
	}

}
