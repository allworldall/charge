package com.linekong.union.charge.consume.service.invoke;

import com.linekong.union.charge.rpc.pojo.AppstoreChargePOJO;
import com.linekong.union.charge.rpc.pojo.ChargingPOJO;
import com.linekong.union.charge.rpc.pojo.LogActivityMQPushPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;

public interface ChargeServerDao {
	
	/**
	 * CP充值成功回调
	 * @param ChargingPOJO pojo
	 * @return Integer 返回充值信息值
	 */
	public int charging(ChargingPOJO pojo);
	
	/**
	 * 订单信息推送
	 * @param pojo
	 * @return
	 */
	public int pushChargeInfo(PushOrderInfoPOJO pojo);
	/**
	 * APPS充值成功回调
	 * @param pojo
	 * @param appPojo
	 * @return
	 */
	public int chargingForApple(ChargingPOJO pojo, AppstoreChargePOJO appPojo);
	/**
	 * 查询订单 支付状态
	 * @param payMentId
	 * @return
	 */
	public int getOrderStatus(String payMentId);

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
	 */
	int updateUserName(String paymentId, String realName);

	/**
	 * 标记沙盒订单，预支付test_state字段改成9，
	 * @param paymentId            预支付订单号
	 * @param appleSandboxState    沙盒订单标记，9
	 * @return
	 */
    int markSandCharge(String paymentId, int appleSandboxState);
}
