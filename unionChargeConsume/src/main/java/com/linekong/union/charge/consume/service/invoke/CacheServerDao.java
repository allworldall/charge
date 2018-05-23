package com.linekong.union.charge.consume.service.invoke;

import java.util.List;

public interface CacheServerDao {
	
	/**
	 * 通过游戏ID和属性节点key获取游戏的特殊配置
	 * @param Integer gameId   游戏ID
	 * @param Integer cpId   渠道ID
	 * @param String  key      属性节点
	 * @return String 节点属性值
	 */
	public String getGameKeyValue(int gameId,int cpId ,String key);
	/**
	 * 通过渠道标识和属性节点key获取游戏的特殊配置
	 * @param String cpCode  渠道标识
	 * @param String  key      属性节点
	 * @return String 节点属性值
	 */
	public String getGameKeyValueByCpCode(String cpCode,String key);
	/**
	 * 验证该游戏对应的联运游戏是否已经配置
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
	public int vaildCpGameById(int  cpGameId,int cpId);
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
	 * 通过项目名称获取项目ras加密key值
	 * @param String  projectCode  项目代码
	 * @param Integer type  1、公钥 2 私钥
	 * @return String rsa密钥
	 */
	public String getRSAKey(String projectCode,int type);
	
	/**
	 * 通过游戏名称和cp名称获取对应的游戏ID
	 * @return Integer 游戏ID
	 */
	public List<Object> getCpGameId(String gameName, String cpName);
	
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
	
}
