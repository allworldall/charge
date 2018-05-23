package com.linekong.union.charge.provider.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.linekong.union.charge.provider.db.dao.ChargeServerDao;
import com.linekong.union.charge.rpc.charge.ChargeServiceInterface;
import com.linekong.union.charge.rpc.pojo.AppstoreChargePOJO;
import com.linekong.union.charge.rpc.pojo.ChargingPOJO;
import com.linekong.union.charge.rpc.pojo.LogActivityMQPushPOJO;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;

public class ChargeServiceImpl implements ChargeServiceInterface{
	@Autowired
	private ChargeServerDao chargeServerDao;
	/**
	 * SDK发起预支付下订单
	 */
	public int preCharge(PrePaymentPOJO pojo) {
		return chargeServerDao.preCharge(pojo);
	}
	/**
	 * 合作伙伴充值成功进行充值
	 */
	public int charging(ChargingPOJO pojo) {
		return chargeServerDao.charging(pojo);
	}
	/**
	 * 订单信息推送
	 */
	public int pushChargeInfo(PushOrderInfoPOJO pojo) {
		return chargeServerDao.pushChargeInfo(pojo);
	}
	/**
	 * 记录APPSTORE 充值日志表
	 * @param pojo
	 * @param appPojo
	 * @return
	 */
	public int chargingForApple(ChargingPOJO pojo, AppstoreChargePOJO appPojo){
		return chargeServerDao.chargingForApple(pojo, appPojo);
	}
	
	
	
	public ChargeServerDao getChargeServerDao() {
		return chargeServerDao;
	}

	public void setChargeServerDao(ChargeServerDao chargeServerDao) {
		this.chargeServerDao = chargeServerDao;
	}
	public int getOrderStatus(String payMentId) {
		return chargeServerDao.getOrderSatus(payMentId);
	}
	/**
	 * MQ推送前信息检查
	 * @param pojo
	 * @return
	 */
	public int pushMQChargeInfoCheck(PushOrderInfoPOJO pojo){
		return chargeServerDao.pushMQChargeInfoCheck(pojo);
	}
	/**
	 *  MQ推送后信息记录
	 * @param pojo
	 * @return
	 */
	public int pushMQChargeInfoRecord(PushOrderInfoPOJO pojo,int result,int userId){
		return chargeServerDao.pushMQChargeInfoRecord(pojo, result,userId);
	}
	/**
	 * 记录活动推送MQ
	 * @param pojo
	 * @param result
	 * @return
	 */
	public int recordActMQInfo(LogActivityMQPushPOJO pojo,int result){
		return chargeServerDao.recordActMQInfo(pojo, result);
	}

	/**
	 * 将预支付表的试玩账号改成正式账号
	 * @param paymentId
	 * @param realName
	 * @return
	 */
	public int updateUserName(String paymentId, String realName) {
		return chargeServerDao.updateUserName(paymentId, realName);
	}

	/**
	 * 标记沙盒订单，预支付test_state字段改成9，
	 * @param paymentId            预支付订单号
	 * @param sandboxState    沙盒订单标记，9
	 * @return
	 */
	public int markSandCharge(String paymentId, int sandboxState) {
		return chargeServerDao.markSandCharge(paymentId, sandboxState);
	}

}
