package com.linekong.union.charge.consume.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.linekong.union.charge.consume.rabbitmq.ChargeToGameSuccessListioner;
import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.consume.util.log.LoggerUtil;

public class ChargeToGameSuccessMQ implements Runnable {
	@Autowired
	private ChargeToGameSuccessListioner chargeToGameSuccessListioner;
	
	public ChargeToGameSuccessMQ(
			ChargeToGameSuccessListioner chargeToGameSuccessListioner,ChargeServerDao chargeServerDao,QueryServerDao queryServerDao) {
		super();
		this.chargeToGameSuccessListioner = chargeToGameSuccessListioner;
		this.chargeToGameSuccessListioner.setChargeServerDao(chargeServerDao);
		this.chargeToGameSuccessListioner.setQueryServerDao(queryServerDao);
	}

	@Override
	public void run() {

		//推送订单信息
		try {
			chargeToGameSuccessListioner.initListioner();
		} catch (IOException e) {
			LoggerUtil.error(ChargeToGameSuccessMQ.class, "ChargeToGameSuccessMQ_error"+e.toString());
		} catch (Exception e) {
			LoggerUtil.error(ChargeToGameSuccessMQ.class, "ChargeToGameSuccessMQ_error"+e.toString());
		}
	}
}
