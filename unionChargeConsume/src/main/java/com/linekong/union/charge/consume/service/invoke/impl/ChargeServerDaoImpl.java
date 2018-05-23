package com.linekong.union.charge.consume.service.invoke.impl;

import java.io.File;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.rpc.charge.ChargeServiceInterface;
import com.linekong.union.charge.rpc.pojo.AppstoreChargePOJO;
import com.linekong.union.charge.rpc.pojo.ChargingPOJO;
import com.linekong.union.charge.rpc.pojo.LogActivityMQPushPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;

public class ChargeServerDaoImpl implements ChargeServerDao {	
	
	private ChargeServiceInterface chargeServiceInterface;
	
	@SuppressWarnings("resource")
	public ChargeServerDaoImpl(){
		String baseDir = System.getenv("CHARGE_CONFIG");
		String os = System.getProperty("os.name"); 
		if(!os.toLowerCase().startsWith("win")){
			baseDir = "/" + baseDir;
		}
		this.chargeServiceInterface = new FileSystemXmlApplicationContext(baseDir+File.separator+"spring-dubbo-consumer.xml").getBean("chargeServiceInterface",ChargeServiceInterface.class);	
	}

	public ChargeServiceInterface getChargeServiceInterface() {
		return chargeServiceInterface;
	}

	public void setChargeServiceInterface(ChargeServiceInterface chargeServiceInterface) {
		this.chargeServiceInterface = chargeServiceInterface;		
	}

	/**
	 * 合作伙伴充值成功进行充值
	 */
	public int charging(ChargingPOJO pojo) {
		return chargeServiceInterface.charging(pojo);
	}
	
	/**
	 * 订单信息推送
	 * @param pojo
	 * @return
	 */
	public int pushChargeInfo(PushOrderInfoPOJO pojo){
		return chargeServiceInterface.pushChargeInfo(pojo);
	}
	/**
	 * 记录APPSTORE 充值日志表
	 * @param pojo
	 * @param appPojo
	 * @return
	 */
	public int chargingForApple(ChargingPOJO pojo, AppstoreChargePOJO appPojo){
		return chargeServiceInterface.chargingForApple(pojo, appPojo);
	}
	
	/**
	 * 查询订单是支付状态
	 * @param payMentId
	 * @return
	 */
	public int getOrderStatus(String payMentId){
		return chargeServiceInterface.getOrderStatus(payMentId);
	}
	/**
	 * MQ推送前信息检查
	 * @param pojo
	 * @return
	 */
	public int pushMQChargeInfoCheck(PushOrderInfoPOJO pojo){
		return chargeServiceInterface.pushMQChargeInfoCheck(pojo);
	}
	/**
	 *  MQ推送后信息记录
	 * @param pojo
	 * @param result :erating推送结果
	 * @return  result :erating推送结果
	 */
	public int pushMQChargeInfoRecord(PushOrderInfoPOJO pojo,int result,int userId){
		return chargeServiceInterface.pushMQChargeInfoRecord(pojo, result,userId);
	}
	/**
	 * 记录活动推送MQ
	 * @param pojo
	 * @param result
	 * @return
	 */
	public int recordActMQInfo(LogActivityMQPushPOJO pojo,int result){
		return chargeServiceInterface.recordActMQInfo(pojo, result);
	}

	/**
	 * 将预支付表的试玩账号改成正式账号
	 * @param paymentId
	 * @param realName
	 */
	@Override
	public int updateUserName(String paymentId, String realName) {
		return chargeServiceInterface.updateUserName(paymentId, realName);
	}

	/**
	 * 标记沙盒订单，预支付test_state字段改成9，
	 * @param paymentId            预支付订单号
	 * @param appleSandboxState    沙盒订单标记，9
	 * @return
	 */
	@Override
	public int markSandCharge(String paymentId, int appleSandboxState) {
		return chargeServiceInterface.markSandCharge(paymentId, appleSandboxState);
	}
}
