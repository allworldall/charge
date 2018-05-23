package com.linekong.union.charge.consume.service.invoke;

import java.util.Map;

import com.linekong.union.charge.rpc.pojo.AppstoreProductPOJO;
import com.linekong.union.charge.rpc.pojo.LogChargeCommonPOJO;

public interface QueryServerDao {
	
	
	/**
	 * 通过预支付ID获取预支付信息
	 * @param String payMentId  预支付ID
	 * @return Map<String,Object>  预支付订单信息
	 */
	public Map<String,Object> getPayMentInfo(String payMentId);
	/**
	 * 匹配最近的一笔预支付
	 * @param userName
	 * @param cpId
	 * @param cpGameId
	 * @param gatewayId
	 * @param productId
	 * @return
	 */
	public Map<String,Object> matchPayMentInfo(String userName, int cpId, int cpGameId, String gatewayId, String productId);
	
	/**
	 * 通过渠道订单号获取预支付信息
	 * @param unionOrderId
	 * @return
	 */
	public Map<String,Object> getPreInfoByChargeDetailId(String unionOrderId);
	
	/**
	 * 查询玩家在指定的区服下是否存在角色，返回的是角色数量
	 * @param userName
	 * @param gameId
	 * @param gatewayId
	 * @return
	 */
	public int getRoleCount(String userName, int gameId,int gatewayId);
	
	/**
	 * 通过游戏ID和产品ID获取Product配置信息
	 * @param gameId   		游戏ID
	 * @param productId		购买物品的product标示符
	 * @return	AppstoreProductPOJO 单个产品所对应的游戏币和产品单价 
	 */
	public AppstoreProductPOJO getAppleProductInfo(int gameId, String productId);
	
	/**
	 * 查询该渠道此订单号是否已经支付成功
	 * @param paymentId  预支付订单号
	 * @return 大于0 说明已经支付成功
	 */
	public int queryPayMentIsSuccess(String paymentId);
	
	/**
	 * 获取符合再次推送的，推送记录
                          推送次数小于3，错误码不为0或者推送时间已经超过2小时
	 */
	public Map<String,Object> getPushInfo() ;
	
	/**
	 * 获取推送过程因Dubbo请求超时的订单
	 *  order表中state为0，与当前时间差超过两小时，push表中无数据
	 * @return
	 */
	public Map<String,Object> getOrderInfo();
	
	/**
	 * 查询推送活动MQ是否重复
	 * @param gameId
	 * @param chargeOrderCode
	 * @param chargeDetailId
	 * @return -1472 有重复
	 * 			1 无重复
	 */
	public int checkActMQInfo(int gameId,String chargeOrderCode,Long chargeDetailId);
	
	/**
	 * 根据蓝港账号去查询log_charge_common表中数据
	 * @param chargeDetailId
	 * @return
	 */
	public LogChargeCommonPOJO getLogChargeCommon(long chargeDetailId);

	/**
	 * 查询用户在sys_passport表里的账号
	 * @param userName
	 * @return
	 */
	String getRealName(String userName);
}
