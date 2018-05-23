package com.linekong.union.charge.consume.service.invoke.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linekong.union.charge.consume.service.invoke.CacheServerDao;
import com.linekong.union.charge.rpc.charge.QueryChargeServiceInterface;
@Repository("cacheServerDaoImpl")
public class CacheServerDaoImpl implements CacheServerDao{
	@Autowired
	private QueryChargeServiceInterface   queryChargeServiceInterface;
	
	public QueryChargeServiceInterface getQueryChargeServiceInterface() {
		return queryChargeServiceInterface;
	}

	public void setQueryChargeServiceInterface(
			QueryChargeServiceInterface queryChargeServiceInterface) {
		this.queryChargeServiceInterface = queryChargeServiceInterface;
	}
	/**
	 * 通过游戏ID和属性节点获取配置结果
	 * @param Integer gameId   游戏ID
	 * @param String  key      节点值
	 * @return String 节点对应的属性值
	 */
	public String getGameKeyValue(int gameId, int cpId, String key) {
		return this.queryChargeServiceInterface.getConfigInfo(gameId, cpId, key);
	}
	/**
	 * 通过渠道标识和属性节点获取配置结果
	 * @param String cpCode   	渠道标识
	 * @param String  key      节点值
	 * @return String 节点对应的属性值
	 */
	public String getGameKeyValueByCpCode(String cpCode, String key) {
		return this.queryChargeServiceInterface.getConfigInfoByCpCode(cpCode, key);
	}
	/**
	 * 查询玩家在指定的区服下是否存在角色，返回的是角色数量
	 * @param String  userName  充值玩家账号
	 * @param Integer gameId    游戏ID
	 * @param Integer gatewayId 网关区服ID
	 * @return Integer 返回角色数量
	 */
	public int getRoleCount(String userName, int gameId, int gatewayId){
		return this.queryChargeServiceInterface.queryRoleCount(userName, gameId, gatewayId);
	}

	/**
	 * 验证该游戏对应的联运游戏是否已经配置
	 * @param String gameName  游戏名称
	 * @param String cpName	       联运游戏简称
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public int vaildCpGameByName(String gameName,String cpName) {
		return queryChargeServiceInterface.vaildCpGameByName(gameName, cpName);
	}
	/**
	 * 验证游戏和渠道对应关系以及开启状态
	 * @param cpGameId
	 * @param cpId
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public int vaildCpGameById(int  cpGameId,int cpId){
		return queryChargeServiceInterface.vaildCpGameById(cpGameId, cpId);
	}
	/**
	 * 通过合作伙伴编码和ID获取合作伙伴state
	 * @param cpName
	 * @param cpId
	 * @return 如果为空则返回-1
	 */
	public Integer getCPState(String cpName,Integer cpId){
		return queryChargeServiceInterface.getCPState(cpName, cpId);
	}
	/**
	 * 通过合作伙伴编码获取合作伙伴state
	 * @param cpName
	 * @return 如果为空则返回-1
	 */
	public Integer getCPStateByCpName(String cpName){
		return queryChargeServiceInterface.getCPStateByCpName(cpName);
	}
    /**
     * 通过项目名称获取rsa密钥
     * @param String  projectCode 项目名称
     * @param Integer type  1、公钥 2、私钥
     */
	public String getRSAKey(String projectCode, int type) {
		return queryChargeServiceInterface.getRSAKey(projectCode, type);
	}
	
	/**
	 * 通过游戏名称和cp名称获取对应的游戏ID
	 * @return Integer 游戏ID
	 */
	public List<Object> getCpGameId(String gameName, String cpName) {
		return this.queryChargeServiceInterface.getCpGameId(gameName, cpName);
	}
	
	/**
	 * 通过gameId以及test_state,查询总的充值金额
	 * @param gameId
	 * @param testState
	 * @return
	 */
	public double getSunMoneyByGameId( int gameId, int testState){
		return this.queryChargeServiceInterface.getSunMoneyByGameId(gameId, testState);
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
		return this.queryChargeServiceInterface.queryAppleChargeTime(userName, cpId, gameId, date);
	}
	/**
	 * 获取回调地址
	 * @param cpId
	 * @param cpGameId
	 * @return
	 */
	public String queryCallBackURL(Integer cpId, Integer cpGameId){
		return this.queryChargeServiceInterface.queryCallBackURL(cpId, cpGameId);
	}
}
