package com.linekong.union.charge.consume.service.invoke.impl;

import java.io.File;
import java.util.Map;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.rpc.charge.QueryChargeServiceInterface;
import com.linekong.union.charge.rpc.pojo.AppstoreProductPOJO;
import com.linekong.union.charge.rpc.pojo.LogChargeCommonPOJO;

@Repository("queryServerDaoImpl")
public class QueryServerDaoImpl implements QueryServerDao{
	
	private QueryChargeServiceInterface queryChargeServiceInterface;
	
	@SuppressWarnings("resource")
	public QueryServerDaoImpl(){
		String baseDir = System.getenv("CHARGE_CONFIG");
		String os = System.getProperty("os.name"); 
		if(!os.toLowerCase().startsWith("win")){
			baseDir = "/" + baseDir;
		}
		this.queryChargeServiceInterface = new FileSystemXmlApplicationContext(baseDir+File.separator+"spring-dubbo-consumer.xml").getBean("queryChargeServiceInterface",QueryChargeServiceInterface.class);	
	}
	
	public QueryChargeServiceInterface getQueryChargeServiceInterface() {
		return queryChargeServiceInterface;
	}

	public void setQueryChargeServiceInterface(QueryChargeServiceInterface queryChargeServiceInterface) {
		this.queryChargeServiceInterface = queryChargeServiceInterface;
	}

	public Map<String, Object> getPayMentInfo(String payMentId) {
		return queryChargeServiceInterface.getPayMentInfo(payMentId);
	} 
	/**
	 * 匹配最近的一笔预支付
	 * @param userName
	 * @param cpId
	 * @param cpGameId
	 * @param gatewayId
	 * @param productId
	 * @return
	 */
	public Map<String,Object> matchPayMentInfo(String userName, int cpId, int cpGameId, String gatewayId, String productId){
		return queryChargeServiceInterface.matchPayMentInfo(userName, cpId, cpGameId, gatewayId, productId);
	}
	/**
	 * 通过渠道订单号获取预支付信息
	 * @param unionOrderId
	 * @return
	 */
	public Map<String,Object> getPreInfoByChargeDetailId(String unionOrderId){
		return queryChargeServiceInterface.getPreInfoByChargeDetailId(unionOrderId);
	}
	/**
	 * 查询该渠道此订单号是否已经支付成功
	 * @param paymentId  预支付订单号
	 * @return 大于0 说明已经支付成功
	 */
	public int queryPayMentIsSuccess(String paymentId){
		return queryChargeServiceInterface.queryPayMentIsSuccess(paymentId);
	}
	public int getRoleCount(String userName, int gameId,int gatewayId){
		return queryChargeServiceInterface.queryRoleCount(userName, gameId, gatewayId);
	}
	/**
	 * 通过游戏ID和产品ID获取Product配置信息
	 * @param gameId   		游戏ID
	 * @param productId		购买物品的product标示符
	 * @return	AppstoreProductPOJO 单个产品所对应的游戏币和产品单价 
	 */
	public AppstoreProductPOJO getAppleProductInfo(int gameId, String productId){
		return queryChargeServiceInterface.getAppleProductInfo(gameId, productId);
	}	
	/**
	 * 获取符合再次推送的，推送记录
                          推送次数小于3，错误码不为0或者推送时间已经超过2小时
	 */
	public Map<String,Object> getPushInfo() {
		return queryChargeServiceInterface.getPushInfo();
	}
	
	/**
	 * 获取推送过程因Dubbo请求超时的订单
	 *  order表中state为0，与当前时间差超过两小时，push表中无数据
	 * @return
	 */
	public Map<String,Object> getOrderInfo(){
		return queryChargeServiceInterface.getOrderInfo();
	}
	
	/**
	 * 查询推送活动MQ是否重复
	 * @param gameId
	 * @param chargeOrderCode
	 * @param chargeDetailId
	 * @return -1472 有重复
	 * 			1 无重复
	 */
	public int checkActMQInfo(int gameId,String chargeOrderCode,Long chargeDetailId){
		return queryChargeServiceInterface.checkActMQInfo(gameId, chargeOrderCode, chargeDetailId);
	}
	/**
	 * 根据蓝港账号去查询log_charge_common表中数据
	 * @param chargeDetailId
	 * @return
	 */
	public LogChargeCommonPOJO getLogChargeCommon(long chargeDetailId){
		return queryChargeServiceInterface.getLogChargeCommon(chargeDetailId);
	}

	/**
	 * 查询用户在sys_passport表里的账号
	 * @param userName
	 * @return
	 */
	@Override
	public String getRealName(String userName) {
		return queryChargeServiceInterface.getRealName(userName);
	}


}
