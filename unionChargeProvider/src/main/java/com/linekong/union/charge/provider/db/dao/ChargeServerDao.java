package com.linekong.union.charge.provider.db.dao;

import com.linekong.union.charge.rpc.pojo.AppstoreChargePOJO;
import com.linekong.union.charge.rpc.pojo.ChargingPOJO;
import com.linekong.union.charge.rpc.pojo.LogActivityMQPushPOJO;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;

public interface ChargeServerDao {
	
	/**
	 * sdk端预支付
	 * @param PrePaymentPOJO pojo
	 * @return Integer
	 */
	public int preCharge(PrePaymentPOJO pojo);
	/**
	 * 合作伙伴支付成功充值
	 * @param ChargingPOJO pojo
	 * @return Integer 
	 */
	public int charging(ChargingPOJO pojo);
	/**
	 * 联运订单信息推送
	 * @param PushOrderInfoPOJO pojo
	 * @return Integer
	 * 
	 */
	public int pushChargeInfo(PushOrderInfoPOJO pojo);
	/**
	 * 记录APPSTORE 充值日志表
	 * @param pojo
	 * @param appPojo
	 * @return
	 */
	public int chargingForApple(ChargingPOJO pojo, AppstoreChargePOJO appPojo);
	
	public int getOrderSatus(String payMentId);
	
	/**
	 * MQ推送前信息检查
	 * @param pojo
	 * @return
	 */
	public int pushMQChargeInfoCheck(PushOrderInfoPOJO pojo);
	/**
	 *  MQ推送后信息记录
	 * @param pojo
	 * @return
	 */
	public int pushMQChargeInfoRecord(PushOrderInfoPOJO pojo,int result,int userId);
	
	/**
	 * 记录活动推送MQ
	 * @param pojo
	 * @param result
	 * @return
	 */
	public int recordActMQInfo(LogActivityMQPushPOJO pojo,int result);

	/**
	 * 将预支付表的试玩账号改成正式账号
	 * @param paymentId
	 * @param realName
	 * @return
	 */
	int updateUserName(String paymentId, String realName);

	/**
	 * 标记沙盒订单，预支付test_state字段改成9，
	 * @param paymentId            预支付订单号
	 * @param sandboxState    沙盒订单标记，9
	 * @return
	 */
	int markSandCharge(String paymentId, int sandboxState);
}
