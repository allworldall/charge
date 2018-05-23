package com.linekong.union.charge.provider.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.linekong.union.charge.provider.db.dao.QueryChargeServerDao;
import com.linekong.union.charge.rpc.charge.QueryChargeServiceInterface;
import com.linekong.union.charge.rpc.pojo.AppstoreProductPOJO;
import com.linekong.union.charge.rpc.pojo.LogChargeCommonPOJO;

public class QueryChargeServiceImpl implements QueryChargeServiceInterface{

	@Autowired
	private QueryChargeServerDao queryChargeServerDao;
	
	public QueryChargeServerDao getQueryChargeServerDao() {
		return queryChargeServerDao;
	}
	public void setQueryChargeServerDao(QueryChargeServerDao queryChargeServerDao) {
		this.queryChargeServerDao = queryChargeServerDao;
	}
	/**
	 * 获取游对应的配置信息
	 * @param Integer gameId 游戏ID
	 * @param Integer cpId 渠道ID
	 * @param String  key	   配置属性节点
	 * @return String
	 */
	public String getConfigInfo(int gameId, int cpId, String key) {
		return this.queryChargeServerDao.getConfigInfo(gameId, cpId, key);
	}
	/**
	 * 获取游对应的配置信息
	 * @param String cpCode 渠道标识
	 * @param String  key	   配置属性节点
	 * @return String
	 */
	public String getConfigInfoByCpCode(String cpCode, String key) {
		return this.queryChargeServerDao.getConfigInfoByCpCode(cpCode, key);
	}
	/**
	 * 查询玩家在指定的区服下是否存在角色，返回的是角色数量
	 * @param String  userName  充值玩家账号
	 * @param Integer gameId    游戏ID
	 * @param Integer gatewayId 网关区服ID
	 * @return Integer 返回角色数量
	 */
	public int queryRoleCount(String userName, int gameId, int gatewayId) {
		return this.queryChargeServerDao.queryRoleCount(userName, gameId, gatewayId);
	}
	/**
	 * 获取RAS加密算法的密钥
	 * @param String projectCode 项目名称
	 * @param int    type        1、获取公钥。2获取私钥
	 * @return String 
	 */
	public String getRSAKey(String projectCode, int type) {
		return this.queryChargeServerDao.getRSAKey(projectCode, type);
	}
	/**
	 * 通过游戏ID和产品ID获取Product配置信息
	 * @param gameId   		游戏ID
	 * @param productId		购买物品的product标示符
	 * @return	AppstoreProductPOJO 单个产品所对应的游戏币和产品单价 
	 */
	public AppstoreProductPOJO getAppleProductInfo(int gameId, String productId){
		return this.queryChargeServerDao.getAppleProductInfo(gameId, productId);
	}
	/** 
	 * 验证该游戏对应的联运游戏是否已经配置
	 * @param String gameName 游戏名称简写
	 * @param String cpName	    联运游戏简称
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public int vaildCpGameByName(String gameName,String cpName) {
		return queryChargeServerDao.vaildCpGameByName(gameName, cpName);
	}
	/**
	 * 验证游戏和渠道对应关系以及开启状态
	 * @param cpGameId
	 * @param cpId
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public int vaildCpGameById(int  cpGameId,int cpId){
		return queryChargeServerDao.vaildCpGameById(cpGameId, cpId);
	}
	/**
	 * 通过合作伙伴编码和ID获取合作伙伴state
	 * @param cpName
	 * @param cpId
	 * @return 如果为空则返回-1
	 */
	public Integer getCPState(String cpName,Integer cpId){
		return queryChargeServerDao.getCPState(cpName, cpId);
	}
	/**
	 * 通过合作伙伴编码获取合作伙伴state
	 * @param cpName
	 * @return 如果为空则返回-1
	 */
	public Integer getCPStateByCpName(String cpName){
		return queryChargeServerDao.getCPStateByCpName(cpName);
	}
	/**
	 * 通过预支付ID获取预支付订单信息
	 * @param String payMentId
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getPayMentInfo(String payMentId) {
		return queryChargeServerDao.getPayMentInfo(payMentId);
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
		return queryChargeServerDao.matchPayMentInfo(userName, cpId, cpGameId, gatewayId, productId);
	}
	/**
	 * 查询该渠道此订单号是否已经支付成功
	 * @param paymentId  预支付订单号
	 * @return 大于0 说明已经支付成功
	 */
	public int queryPayMentIsSuccess(String paymentId){
		return queryChargeServerDao.queryPayMentIsSuccess(paymentId);
	}
	/**
	 * 通过游戏名称和cp名称获取对应的游戏ID
	 * @return Integer 游戏ID
	 */
	public List<Object> getCpGameId(String gameName, String cpName) {
		return this.queryChargeServerDao.getCpGameId(gameName, cpName);
	}
	
	/**
	 * 通过gameId以及test_state,查询总的充值金额
	 * @param gameId
	 * @param testState
	 * @return
	 */
	public double getSunMoneyByGameId( int gameId, int testState){
		return this.queryChargeServerDao.getSunMoneyByGameId(gameId, testState);
	}
	/**查询苹果用户充值小额金额
	 * 
	 * @param userName
	 * @param cpId
	 * @param gameId
	 * @param date  当前时间（yyyy-mm-dd）
	 * @return
	 */
	public int queryAppleChargeTime(String userName, Integer cpId, Integer gameId,String date){
		return this.queryChargeServerDao.queryAppleChargeTime(userName, cpId, gameId, date);
	}
	/**
	 * 获取回调地址
	 * @param cpId
	 * @param cpGameId
	 * @return
	 */
	public String queryCallBackURL(Integer cpId, Integer cpGameId){
		return this.queryChargeServerDao.queryCallBackURL(cpId, cpGameId);
	}
	/**
	 * 通过渠道订单号获取预支付信息
	 * @param unionOrderId
	 * @return
	 */
	public Map<String,Object> getPreInfoByChargeDetailId(String unionOrderId){
		return this.queryChargeServerDao.getPreInfoByChargeDetailId(unionOrderId);
	}
	/**
	 * 获取符合再次推送的，推送记录
                          推送次数小于3，错误码不为0或者推送时间已经超过2小时
	 */
	public Map<String,Object> getPushInfo() {
		return this.queryChargeServerDao.getPushInfo();
	}
	
	/**
	 * 获取推送过程因Dubbo请求超时的订单
	 *  order表中state为0，与当前时间差超过两小时，push表中无数据
	 * @return
	 */
	public Map<String,Object> getOrderInfo(){
		return queryChargeServerDao.getOrderInfo();
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
		return this.queryChargeServerDao.checkActMQInfo(gameId, chargeOrderCode, chargeDetailId);
	}
	/**
	 * 根据蓝港账号去查询log_charge_common表中数据
	 * @param chargeDetailId
	 * @return
	 */
	public LogChargeCommonPOJO getLogChargeCommon(long chargeDetailId){
		return this.queryChargeServerDao.getLogChargeCommon(chargeDetailId);
	}

	/**
	 * 查询用户在sys_passport表里的账号
	 * @param userName
	 * @return
	 */
	public String getRealName(String userName) {
		return this.queryChargeServerDao.getRealName(userName);
	}
}
