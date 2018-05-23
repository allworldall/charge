package com.linekong.union.charge.rpc.charge;

import java.util.List;
import java.util.Map;

import com.linekong.union.charge.rpc.pojo.AppstoreProductPOJO;
import com.linekong.union.charge.rpc.pojo.LogChargeCommonPOJO;

public interface QueryChargeServiceInterface {
	
	/**
	 * 获取游戏缓存中的持久化数据配置信息
	 * @param Integer gameId 游戏ID
	 * @param Integer cpId 渠道ID
	 * @param String  key    缓存中的key 
	 * @return String
	 */
	public String getConfigInfo(int gameId,int cpId ,String key);
	/**
	 * 获取游戏缓存中的持久化数据配置信息
	 * @param String cpCode 渠道标识
	 * @param String  key    缓存中的key 
	 * @return String
	 */
	public String getConfigInfoByCpCode(String cpCode,String key);
	/**
	 * 判断用户账号在制定网关下是否否有角色存在
	 * @param String userName   用户账号
	 * @param int	 gameId	         游戏ID
	 * @param int	 gatewayId  网关ID
	 * @return Integer 返回该账号在网关下的角色数量
	 */
	public int queryRoleCount(String userName,int gameId,int gatewayId);
	/**
	 * 获取RAS加密算法的密钥
	 * @param String projectCode 项目代码
	 * @param int    type        1、获取公钥。2获取私钥
	 * @return String 
	 */
	public String getRSAKey(String projectName,int type);
	/**
	 * 通过游戏ID和产品ID获取Product配置信息
	 * @param gameId   		游戏ID
	 * @param productId		购买物品的product标示符
	 * @return	AppstoreProductPOJO 单个产品所对应的游戏币和产品单价 
	 */
	public AppstoreProductPOJO getAppleProductInfo(int gameId, String productId);
	/**
	 * 验证该游戏对应的联运游戏是否配置
	 * @param String gameName 游戏名称简写
	 * @param String cpName   联运游戏简称
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public int vaildCpGameByName(String gameName,String cpName);
	/**
	 * 验证游戏和渠道对应关系以及开启状态
	 * @param cpGameId
	 * @param cpId
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public int vaildCpGameById(int cpGameId,int cpId);
	/**
	 * 通过合作伙伴编码和ID获取合作伙伴state
	 * @param cpName
	 * @param cpId
	 * @return
	 */
	public Integer getCPState(String cpName,Integer cpId);
	/**
	 * 通过合作伙伴编码获取合作伙伴state
	 * @param cpName
	 * @return
	 */
	public Integer getCPStateByCpName(String cpName);
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
	 * 查询该渠道此订单号是否已经支付成功
	 * @param paymentId  预支付订单号
	 * @return 大于0 说明已经支付成功
	 */
	public int queryPayMentIsSuccess(String paymentId);
	/**
	 * 通过gameName 和 cpName获取对应的联运游戏Id
	 * @param String gameName 游戏代码
	 * @param String cpName   合作伙伴代码
	 * @return Integer 游戏ID
	 */
	public List<Object> getCpGameId(String gameName,String cpName);
	
	/**
	 * 通过gameId以及test_state,查询总的充值金额
	 * @param gameId
	 * @param testState
	 * @return
	 */
	public double getSunMoneyByGameId( int gameId, int testState);
	/**查询苹果用户充值小额金额
	 * 
	 * @param userName
	 * @param cpId
	 * @param gameId
	 * @param date  当前时间（yyyy-mm-dd）
	 * @return
	 */
	public int queryAppleChargeTime(String userName, Integer cpId, Integer gameId,String date);
	/**
	 * 获取回调地址
	 * @param cpId
	 * @param cpGameId
	 * @return
	 */
	public String queryCallBackURL(Integer cpId, Integer cpGameId);
	/**
	 * 获取符合再次推送的，推送记录
                          推送次数小于3，错误码不为0或者推送时间已经超过2小时
	 */
	public Map<String,Object> getPushInfo() ;
	
	/**
	 * 获取推送过程因Dubbo请求超时的订单
	 *  order表中state为0，与当前时间差查过两小时，push表中无数据
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
	public String getRealName(String userName);


}
