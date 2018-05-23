package com.linekong.union.charge.consume.service.invoke;

import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;

public interface PreChargeServerDao {
	
	/**
	 * 预支付接口由SDK调用生成预支付订单信息
	 * @param PrePaymentPOJO pojo
	 * @return Integer
	 */
	public int preCharge(PrePaymentPOJO pojo);
}
