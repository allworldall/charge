package com.linekong.union.charge.consume.service;

import java.io.IOException;

import com.linekong.union.charge.consume.rabbitmq.ChargeInvokeRabbitMQ;
import com.linekong.union.charge.consume.rabbitmq.pojo.PushOrderWithMQInfoPOJO;
import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.consume.util.Common;
import com.linekong.union.charge.consume.util.Constant;
import com.linekong.union.charge.consume.util.DateUtil;
import com.linekong.union.charge.consume.util.JsonUtil;
import com.linekong.union.charge.consume.util.log.LoggerUtil;

public class ActInfoWithMQ implements Runnable {
	
	private ChargeInvokeRabbitMQ chargeInvokeRabbitMQ;
	
	private ChargeServerDao chargeDao;
	
	private PushOrderWithMQInfoPOJO pushWithMQPojo;
	
	public ActInfoWithMQ(PushOrderWithMQInfoPOJO pushWithMQPojo,ChargeInvokeRabbitMQ chargeInvokeRabbitMQ,ChargeServerDao chargeDao){
		this.pushWithMQPojo = pushWithMQPojo;
		this.chargeInvokeRabbitMQ = chargeInvokeRabbitMQ;
		this.chargeDao = chargeDao;
	}
	
	@Override
	public void run() {
		//推送订单信息
		try {
			pushWithMQPojo.setChargeTime(DateUtil.getTimeString1(DateUtil.convertString2DateInMiType1(pushWithMQPojo.getChargeTime())));
			LoggerUtil.info(this.getClass(), pushWithMQPojo.toString());
			chargeInvokeRabbitMQ.chargeActivityInvokeMQ(JsonUtil.convertBeanToJson(pushWithMQPojo));
			chargeDao.recordActMQInfo(Common.getLogActivityMQPushPOJO(pushWithMQPojo), Constant.SUCCESS);
		} catch (IOException e) {
			//记录日志
			chargeDao.recordActMQInfo(Common.getLogActivityMQPushPOJO(pushWithMQPojo), Constant.ERROR_SYSTEM);
			LoggerUtil.error(ActInfoWithMQ.class, "ActInfoWithMQ_param:"+pushWithMQPojo+"pushMQerror"+e.toString());
		} catch (Exception e) {
			//记录日志
			chargeDao.recordActMQInfo(Common.getLogActivityMQPushPOJO(pushWithMQPojo), Constant.ERROR_SYSTEM);
			LoggerUtil.error(ActInfoWithMQ.class, "ActInfoWithMQ_param:"+pushWithMQPojo+"pushMQerror"+e.toString());
		}
	}
}
