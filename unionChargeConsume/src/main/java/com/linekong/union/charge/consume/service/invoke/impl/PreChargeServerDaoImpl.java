package com.linekong.union.charge.consume.service.invoke.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linekong.union.charge.consume.service.invoke.PreChargeServerDao;
import com.linekong.union.charge.rpc.charge.ChargeServiceInterface;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
@Repository("preChargeServerDaoImpl")
public class PreChargeServerDaoImpl implements PreChargeServerDao{
	
	@Autowired
	private ChargeServiceInterface chargeServiceInterface;
	
	public ChargeServiceInterface getChargeServiceInterface() {
		return chargeServiceInterface;
	}

	public void setChargeServiceInterface(
			ChargeServiceInterface chargeServiceInterface) {
		this.chargeServiceInterface = chargeServiceInterface;
	}
	
	
	/**
	 * 预支付接口由sdk调用生成预支付订单
	 * @param PrePaymentPOJO pojo
	 * @return Integer
	 */
	public int preCharge(PrePaymentPOJO pojo) {
		return chargeServiceInterface.preCharge(pojo);
	}
}
