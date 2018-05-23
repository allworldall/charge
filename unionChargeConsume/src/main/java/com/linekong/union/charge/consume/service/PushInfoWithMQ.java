package com.linekong.union.charge.consume.service;

import java.io.IOException;

import com.linekong.union.charge.consume.rabbitmq.ChargeInvokeRabbitMQ;
import com.linekong.union.charge.consume.rabbitmq.pojo.PushOrderWithMQInfoPOJO;
import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.consume.util.Constant;
import com.linekong.union.charge.consume.util.JsonUtil;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;

public class PushInfoWithMQ implements Runnable {
	
	private ChargeInvokeRabbitMQ chargeInvokeRabbitMQ;
	
	private ChargeServerDao chargeDao;
	
	private PushOrderWithMQInfoPOJO pushPojo;
	
	public PushInfoWithMQ(PushOrderWithMQInfoPOJO pushPojo,ChargeInvokeRabbitMQ chargeInvokeRabbitMQ,ChargeServerDao chargeDao){
		this.pushPojo = pushPojo;
		this.chargeInvokeRabbitMQ = chargeInvokeRabbitMQ;
		this.chargeDao = chargeDao;
	}
	
	@Override
	public void run() {
		//推送订单信息
		try {
			chargeInvokeRabbitMQ.chargePushInvokeMQ(JsonUtil.convertBeanToJson(pushPojo));
		} catch (IOException e) {
			chargeDao.pushMQChargeInfoRecord(new PushOrderInfoPOJO(pushPojo.getChargeOrderCode(),pushPojo.getChargeDetailId(),"",""), Constant.ERROR_MQ_IOEXCEPTION,0);
			LoggerUtil.error(PushInfoWithMQ.class, "PushInfoWithMQ_param:"+pushPojo+"pushMQerror"+e.toString());
		} catch (Exception e) {
			chargeDao.pushMQChargeInfoRecord(new PushOrderInfoPOJO(pushPojo.getChargeOrderCode(),pushPojo.getChargeDetailId(),"",""), Constant.ERROR_MQ_EXCEPTION,0);
			LoggerUtil.error(PushInfoWithMQ.class, "PushInfoWithMQ_param:"+pushPojo+"pushMQerror"+e.toString());
		}
	}
}
