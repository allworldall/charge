package com.linekong.union.charge.rpc.charge;

import com.linekong.union.charge.rpc.pojo.AppstoreChargePOJO;
import com.linekong.union.charge.rpc.pojo.ChargingPOJO;
import com.linekong.union.charge.rpc.pojo.LogActivityMQPushPOJO;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;

public interface ChargeServiceInterface {
	/**
	 * 联运充值预支付接口
	 * @param  PrePaymentPOJO pojo
	 * @return Integer
	 */
	public int preCharge(PrePaymentPOJO pojo);
	/**
	 * 合作伙伴回调支付充值
	 * @param  ChargingPOJO	pojo
	 * @return Integer 1 充值成功
	 */
	public int charging(ChargingPOJO pojo);
	/**
	 * 向游戏服务器推送玩家充值信息
	 * @param PushOrderInfoPOJO pojo
	 * @return Integer 返回推送结果信息
	 */
	public int pushChargeInfo(PushOrderInfoPOJO pojo);
	/**
	 * 记录APPSTORE 充值日志表
	 * @param pojo
	 * @param appPojo
	 * @return
	 */
	public int chargingForApple(ChargingPOJO pojo, AppstoreChargePOJO appPojo);
	
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
	 * @return
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
