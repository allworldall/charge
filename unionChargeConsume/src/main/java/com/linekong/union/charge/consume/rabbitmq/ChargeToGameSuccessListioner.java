package com.linekong.union.charge.consume.rabbitmq;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.linekong.union.charge.consume.rabbitmq.connection.RabbitMQConnection;
import com.linekong.union.charge.consume.rabbitmq.pojo.EratingChargeNotifyPOJO;
import com.linekong.union.charge.consume.rabbitmq.pojo.PushOrderWithMQInfoPOJO;
import com.linekong.union.charge.consume.service.SendToKafkaThread;
import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.consume.util.Common;
import com.linekong.union.charge.consume.util.Constant;
import com.linekong.union.charge.consume.util.JsonUtil;
import com.linekong.union.charge.consume.util.ThreadPoolUtil;
import com.linekong.union.charge.consume.util.annotation.support.ValidateService;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
/*
 * erating推送完成，回调，监听队列
 */
@Resource
public class ChargeToGameSuccessListioner extends RabbitMQConnection {

	
	
	private ChargeServerDao chargeServerDao;
	
	private QueryServerDao queryServerDao;
	
	private ChargeInvokeRabbitMQ chargeMQ = new ChargeInvokeRabbitMQ();
	
	private String 			exchangeName;		//交换机名称
	
	private String			queueName;			//监听队列名称
	
	private String			routKey;			//路由key
	
	private int				basicQos = 50;		//同一时间最多接收数据数
	@PostConstruct
	public void initListioner() throws Exception{
		ConnectionFactory connection = null;
		if(this.connectionFactory == null){//rabbitmq连接异常创建连接
			this.initRabbitMQConnection();
		}
		connection = this.connectionFactory;
		Connection conn = connection.newConnection();
		Channel channel = conn.createChannel();
		channel.exchangeDeclare(exchangeName, "direct");
		channel.queueBind(queueName, exchangeName, routKey);
		// 指定该线程同时只接收一条消息  
		channel.basicQos(basicQos);
		//创建消费者对象，用于读取消息
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//consumer监控队列queueName
		channel.basicConsume(queueName, false, consumer);

		 /*读取队列，并且阻塞，即在读到消息之前在这里阻塞，直到等到消息，完成消息的阅读后，继续阻塞循环*/
		while (true) {
			try {
				StringBuilder buffer = new StringBuilder();
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				buffer.append("recivedEratingMsg:"+message+",");
				long begin = System.currentTimeMillis();
				try {
					//eratingReturn包含IP数据
					EratingChargeNotifyPOJO eratingReturn = JsonUtil.convertJsonToBean(message, EratingChargeNotifyPOJO.class);
					LoggerUtil.info(ChargeToGameSuccessListioner.class, "receive callback MQ-erating listen queue-"+eratingReturn);
					//判断返回结果
					ValidateService.valid(eratingReturn);
					//推送信息数据库处理的result
					int resultCode = eratingReturn.getStatus();
					//判断返回值，如果正常
					if(eratingReturn.getStatus() == Constant.SUCCESS){
						//获取预支付信息(查出封装在ＰＲＥＰＡＹＭＥＮＴＰＯＪＯ) 
						Map<String, Object> resultMap = queryServerDao.getPreInfoByChargeDetailId(eratingReturn.getChargeOrderCode()+"");
						// 验证预支付订单是否存在
						if (resultMap.get("result").equals(Constant.SUCCESS)) {
							PrePaymentPOJO payment = (PrePaymentPOJO) resultMap.get("payment");
							//此POJO无IP数据
							PushOrderWithMQInfoPOJO pojo = new PushOrderWithMQInfoPOJO(payment,eratingReturn,Constant.DEFAULT_DISCOUNT,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
							//如果成功调用活动队列，推送订单信息
							chargeMQ.chargeActivityInvokeMQ(JsonUtil.convertBeanToJson(pojo));
							//插入数据库LOG_ACTIVITY_MQ_PUSH，没有IP数据
							chargeServerDao.recordActMQInfo(Common.getLogActivityMQPushPOJO(pojo), Constant.SUCCESS);
							//另起线程推送卡夫卡，应该在common表有数据之后再推送kafka，
//							ThreadPoolUtil.pool.execute(new NewActToKafka(eratingReturn.getChargeDetailId(),queryServerDao));
						}else{
							resultCode = Constant.ERROR_MQ_UNION_ORDER_EXIST;
							buffer.append(";result="+resultCode+",times="+(System.currentTimeMillis() - begin)+"]");
							LoggerUtil.info(ChargeToGameSuccessListioner.class, "ChargeToGameSuccessListioner erating return unionOrder error"+buffer.toString());
						}
					}

					
					//进行推送信息数据库处理（存入log_charge_common）
					int result = chargeServerDao.pushMQChargeInfoRecord(new PushOrderInfoPOJO(eratingReturn.getChargeOrderCode(), eratingReturn.getChargeDetailId(),eratingReturn.getServerIp(),eratingReturn.getClientIp()), resultCode,Integer.valueOf(eratingReturn.getUserId()));	
					LoggerUtil.info(ChargeToGameSuccessListioner.class, "get callback MQ-----------pushMQChargeInfoRecord--------result:"+result);
					//将数据发送kafaka以便实时统计数据
					if(eratingReturn.getStatus() == Constant.SUCCESS && result == Constant.SUCCESS){
						ThreadPoolUtil.pool.submit(new SendToKafkaThread(eratingReturn.getChargeDetailId(),queryServerDao));
					}
				} catch (Exception e) {
					buffer.append(";timer="+(System.currentTimeMillis() - begin)+",");
					LoggerUtil.error(ChargeToGameSuccessListioner.class, "get callback MQ_Erating deal message exception,"+buffer.toString()+",errorMessage="+e.toString());
				}
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			} catch (Exception e) {
				LoggerUtil.error(ChargeToGameSuccessListioner.class, "listen message queue sleep 60 seconds,recreate connection, error_message=" + e.toString());
				Thread.sleep(1000*60);
				try {
					conn = connection.newConnection();
					channel = conn.createChannel();
					channel.exchangeDeclare(exchangeName, "direct");
					channel.queueBind(queueName, exchangeName, routKey);
					// 指定该线程同时只接收一条消息  
					channel.basicQos(basicQos);
					//创建消费者对象，用于读取消息
					consumer = new QueueingConsumer(channel);
					channel.basicConsume(queueName, false, consumer);
				} catch (Exception e2) {
					try {
						if(conn != null){
							conn.close();
						}
					} catch (Exception e1) {
						LoggerUtil.error(ChargeToGameSuccessListioner.class, "close connection exception, error_message=" + e1.toString());
					}
					LoggerUtil.error(ChargeToGameSuccessListioner.class, "recreate connection exception, error_message=" + e2.toString());
				}
			} 
		}
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getRoutKey() {
		return routKey;
	}

	public void setRoutKey(String routKey) {
		this.routKey = routKey;
	}

	public int getBasicQos() {
		return basicQos;
	}

	public void setBasicQos(int basicQos) {
		this.basicQos = basicQos;
	}

	public ChargeServerDao getChargeServerDao() {
		return chargeServerDao;
	}

	public void setChargeServerDao(ChargeServerDao chargeServerDao) {
		this.chargeServerDao = chargeServerDao;
	}

	public QueryServerDao getQueryServerDao() {
		return queryServerDao;
	}

	public void setQueryServerDao(QueryServerDao queryServerDao) {
		this.queryServerDao = queryServerDao;
	}

	public ChargeInvokeRabbitMQ getChargeMQ() {
		return chargeMQ;
	}

	public void setChargeMQ(ChargeInvokeRabbitMQ chargeMQ) {
		this.chargeMQ = chargeMQ;
	}

}
