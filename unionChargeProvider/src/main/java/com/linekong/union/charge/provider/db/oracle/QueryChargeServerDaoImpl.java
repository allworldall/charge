package com.linekong.union.charge.provider.db.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.linekong.union.charge.provider.db.DataSourceConfigureFactory;
import com.linekong.union.charge.provider.db.dao.QueryChargeServerDao;
import com.linekong.union.charge.provider.db.util.Constant;
import com.linekong.union.charge.provider.util.log.LoggerUtil;
import com.linekong.union.charge.rpc.pojo.AppstoreProductPOJO;
import com.linekong.union.charge.rpc.pojo.LogChargeCommonPOJO;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;

import oracle.jdbc.OracleTypes;
@Repository("queryChargeServerDaoImpl")
public class QueryChargeServerDaoImpl extends DataSourceConfigureFactory implements QueryChargeServerDao{
	//获取游戏ID SQL
	private static String vaildCpGameByName ="select t1.state\n" +
											"  from sys_game t, uc_cp_charge_config t1, uc_cp_config t2\n" + 
											" where t.game_id = t1.game_id\n" + 
											"   and t1.cp_id = t2.cp_id\n" + 
											"   and t.game_code = ?\n" + 
											"   and t2.cp_code = ?";
	
	//验证游戏和渠道对应关系以及开启状态
	private static String vaildCpGameById = 
											"select uccc.state\n" +
											"from uc_cp_charge_config  uccc\n" + 
											"where uccc.cp_game_id =  ?\n" + 
											"  and cp_id= ?";

	//获取渠道标识 SQL
	private static String queryCPState = "select ucc.state from uc_cp_config ucc where ucc.cp_code = ? and ucc.cp_id = ? ";
	//根据Cpname获取渠道标识 SQL
	private static String queryCPStateByCpName = "select ucc.state from uc_cp_config ucc where ucc.cp_code = ? ";
	//获取项目对应的rsa私钥
	private static String queryRSAPrivateKey = "select t1.secret_private_key\n" +
											   "  from sys_project t, sys_project_secret t1\n" + 
					                           " where t.project_id = t1.project_id\n" + 
					                           "   and t.project_code = ?";
	//获取项目对应的rsa公钥
	private static String queryRSAPublicKey = "select t1.secret_public_key\n" +
												   "  from sys_project t, sys_project_secret t1\n" + 
						                           " where t.project_id = t1.project_id\n" + 
						                           "   and t.project_code = ?";
	//查询游戏属性节点配置值
	private static String queryConfigInfo = "select uccd.value from uc_cp_charge_detail uccd left join uc_config_info uci "
			+ " on uccd.config_id = uci.config_id where uccd.cp_game_id = ? and uccd.cp_ID = ? and uci.key = ? and uci.type=1";
	//查询游戏属性节点配置值
	private static String queryConfigInfoByCpCode = "select uccd.value from uc_cp_charge_config uccc left join uc_cp_charge_detail uccd on uccc.cp_game_id = uccd.cp_game_id and uccc.cp_id = uccd.cp_id"
													                                                +"left join uc_cp_config        ucc  on uccc.cp_id = ucc.cp_id "
													                                                +"where ucc.cp_code = ? and uccd.key = ?";
	//通过gameName和cpName 获取对应的联运游戏ID
	private static String queryCpGameId = "select t.cp_game_id, t.cp_ID from uc_cp_charge_config t,sys_game t1,uc_cp_config t2\n" +
										  "where t.game_id = t1.game_id and t.cp_id = t2.cp_id\n" + 
										  "  and t1.game_code = ?\n" + 
										  "  and t2.cp_code = ?";

	//通过gameId以及test_state查询该游戏下已经有多少的成功充值金额
	private static String querySumMoneyByGameId = "select sum(lpp.charge_money)\n" +
													"from log_pre_payment lpp\n" + 
													"where lpp.game_id = ? and lpp.test_state = ? and lpp.state = 1";
	//查询苹果用户充值小额金额
	private static String queryAppleChargeTime = 
											   "select count(1)\n" + 
											     "from log_cp_payment_order ucpo\n" +
												"where ucpo.user_name = ?\n" + 
												"  and ucpo.cp_id = ?\n" + 
												"  and ucpo.game_id = ?\n" + 
												"  and ucpo.success_time > to_date(?,'yyyy-mm-dd')\n" + 
												"  and ucpo.charge_money <= 30\n" + 
												"  and ucpo.state = 1";
	//查询该渠道此订单号是否已经支付成功
	private static String queryPayMentIsSuccess = 
										"select count(1)\n" +
										"  from log_cp_payment_order lcpo\n" + 
										" where lcpo.payment_id = ? ";

	//查询回调地址
	private static String queryCallBackURL = 
										"select uccc.cp_callback_address\n" +
										"from uc_cp_charge_config uccc\n" + 
										"where uccc.cp_id = ? and uccc.cp_game_id = ?";

	//
	private static String queryPassportName = "select passport_name from sys_passport where passport_id = (\n" +
					"\t select passport_id from sys_passport_fast_reg where passport_name = ?)";
	/**
	 * 通过游戏ID和产品ID获取Product配置信息
	 * @param gameId   		游戏ID
	 * @param productId		购买物品的product标示符
	 * @return	AppstoreProductPOJO 单个产品所对应的游戏币和产品单价
	 */
	public AppstoreProductPOJO getAppleProductInfo(int gameId, String productId){
		AppstoreProductPOJO result = new AppstoreProductPOJO();
		Connection conn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			conn = this.getDataSource().getConnection();
			call = conn.prepareCall("{?=call pkg_echarging_cp_charge.getAppleProductInfo(?,?,?)}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			call.setInt(2, gameId);
			call.setString(3, productId);
			call.registerOutParameter(4, OracleTypes.CURSOR);
			call.execute();
			if(call.getInt(1) > 0){
				rs = (ResultSet) call.getObject(4);
				while(rs.next()){
					result.setChargeAmount(rs.getInt("charge_amount"));
					result.setProductPrice(rs.getDouble("product_price"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), e.toString());
		}finally{
			this.close(conn, call, null, rs);
		}
		return result;
	}
	/**
	 * 通过游戏编码获取游戏ID
	 * @param String  gameName 游戏编码
	 * @param String  cpName   联运游戏简称
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public int vaildCpGameByName(String gameName,String cpName) {
		Object vali = this.query(this.getDataSource_pm(), vaildCpGameByName,
				new Object[]{gameName,cpName});
		return (vali == null? -1 : Integer.parseInt(vali.toString()));
	}
	
	/**
	 * 验证游戏和渠道对应关系以及开启状态
	 * @param cpGameId
	 * @param cpId
	 * @return Integer 如果为空则返回-1          生效：1        未生效：2
	 */
	public int vaildCpGameById(int  cpGameId,int cpId){
		Object vali = this.query(this.getDataSource_pm(), vaildCpGameById, 
				new Object[]{cpGameId,cpId});
		return (vali == null? -1 : Integer.parseInt(vali.toString()));
	}
	/**
	 * 通过合作伙伴编码和ID获取合作伙伴state
	 * @param cpName
	 * @param cpId
	 * @return  如果为空则返回-1
	 */
	public Integer getCPState(String cpName,Integer cpId) {
		Object state = this.query(this.getDataSource_pm(), queryCPState, 
				new Object[]{cpName,cpId});
		return (state == null ? -1 : Integer.parseInt(state.toString()));
	}
	/**
	 * 通过合作伙伴编码获取合作伙伴state
	 * @param cpName
	 * @return 如果为空则返回-1
	 */
	public Integer getCPStateByCpName(String cpName){
		Object state = this.query(this.getDataSource_pm(), queryCPStateByCpName, 
				new Object[]{cpName});
		return (state == null ? -1 : Integer.parseInt(state.toString()));
	}
	/**
	 * 通过项目代码获取rsa私钥
	 * @param String projectCode 项目代码
	 * @param Integer type 1 公钥，2 私钥
	 * @return rsa私钥
	 */
	public String getRSAKey(String projectCode,int type) {
		Object rsaKey = null;
		if(type == 1){
			rsaKey = this.query(this.getDataSource_pm(), queryRSAPublicKey, 
					new Object[]{projectCode});
		}else if(type == 2){
			rsaKey = this.query(this.getDataSource_pm(), queryRSAPrivateKey, 
					new Object[]{projectCode});
		}
		return (rsaKey == null?"0":String.valueOf(rsaKey));
	}
	/**
	 * 查询游戏节点属性配置
	 * @param Integer gameId  游戏ID
	 * @param Integer cpID  渠道ID
	 * @param String  key     属性节点key值
	 * @return String 返回配置属性值
	 */
	public String getConfigInfo(int gameId,int cpId ,String key) {
		Object value = this.query(this.getDataSource_pm(), queryConfigInfo, new Object[]{
			gameId,cpId,key});
		return (value == null ? "0" : String.valueOf(value));
	}
	/**
	 * 查询游戏节点属性配置
	 * @param String cpCpde  渠道标识
	 * @param String  key     属性节点key值
	 * @return String 返回配置属性值
	 */
	public String getConfigInfoByCpCode(String cpCpde, String key) {
		Object value = this.query(this.getDataSource_pm(), queryConfigInfoByCpCode, new Object[]{
			cpCpde,key});
		return (value == null ? "0" : String.valueOf(value));
	}



	/**
	 * 查询用户在sys_passport表里的账号
	 * @param userName
	 * @return
	 */
	public String getRealName(String userName) {
		Object value = this.query(this.getDataSource_passport(), queryPassportName, new Object[]{
				userName
		});
		return (value == null ? "" : String.valueOf(value));
	}

	/**
	 * 通过预支付ID获取预支付订单信息
	 */
	public Map<String,Object> getPayMentInfo(String payMentId) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			conn = this.getDataSource().getConnection();
			call = conn.prepareCall("{?=call pkg_echarging_cp_charge.getPreInfo(?,?)}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			call.setString(2, payMentId);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.execute();
			if(call.getInt(1) > 0){
				rs = (ResultSet) call.getObject(3);
				while(rs.next()){
					PrePaymentPOJO pojo = new PrePaymentPOJO();
					pojo.setPaymentId(rs.getString("payment_id"));
					pojo.setUserName(rs.getString("user_name"));
					pojo.setCpId(rs.getInt("cp_id"));
					pojo.setGameId(rs.getInt("game_id"));
					pojo.setGatewayId(rs.getInt("gateway_id"));
					pojo.setChargeMoney(rs.getDouble("charge_money"));
					pojo.setChargeAmount(rs.getInt("charge_amount"));
					pojo.setProductId(rs.getString("product_id"));
					pojo.setAttachCode(rs.getString("attach_code"));
					pojo.setExpandInfo(rs.getString("expand_info"));
					pojo.setRoleId(rs.getLong("role_id"));
					pojo.setChargeTime(rs.getTimestamp("payment_time"));
					pojo.setServerIp(rs.getString("server_ip"));
					pojo.setRequestIp(rs.getString("request_ip"));
					pojo.setProductName(rs.getString("product_name"));
					pojo.setProductDesc(rs.getString("product_desc"));
					result.put("result", 1);
					result.put("payment", pojo);
				}
			}else{
				result.put("result", call.getInt(1));
				result.put("payment", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), e.toString());
		}finally{
			this.close(conn, call, null, rs);
		}
		return result;
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

		Map<String,Object> result = new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			conn = this.getDataSource().getConnection();
			call = conn.prepareCall("{?=call pkg_echarging_cp_charge.matchPreInfo(?,?,?,?,?,?)}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			call.setString(2, userName);
			call.setInt(3, cpId);
			call.setInt(4, cpGameId);
			call.setString(5, gatewayId);
			call.setString(6, productId);
			call.registerOutParameter(7, OracleTypes.CURSOR);
			call.execute();
			if(call.getInt(1) > 0){
				rs = (ResultSet) call.getObject(7);
				result.put("result", 1);
				while(rs.next()){
					PrePaymentPOJO pojo = new PrePaymentPOJO();
					pojo.setPaymentId(rs.getString("payment_id"));
					pojo.setUserName(rs.getString("user_name"));
					pojo.setCpId(rs.getInt("cp_id"));
					pojo.setGameId(rs.getInt("game_id"));
					pojo.setGatewayId(rs.getInt("gateway_id"));
					pojo.setChargeMoney(rs.getDouble("charge_money"));
					pojo.setChargeAmount(rs.getInt("charge_amount"));
					pojo.setProductId(rs.getString("product_id"));
					pojo.setAttachCode(rs.getString("attach_code"));
					pojo.setExpandInfo(rs.getString("expand_info"));
					pojo.setChargeTime(rs.getTimestamp("payment_time"));
					pojo.setServerIp(rs.getString("server_ip"));
					pojo.setRequestIp(rs.getString("request_ip"));
					
					result.put("payment", pojo);
				}
			}else{
				result.put("result", call.getInt(1));
				result.put("payment", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), e.toString());
		}finally{
			this.close(conn, call, null, rs);
		}
		return result;
	}
	/**
	 * 获取推送过程因Dubbo请求超时的订单
	 *  order表中state为0，与当前时间差超过两小时，push表中无数据
	 * @return
	 */
	public Map<String,Object> getOrderInfo(){
		Map<String,Object> result = new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		try{
			conn = this.getDataSource().getConnection();
			call=conn.prepareCall("{?=call pkg_echarging_cp_charge.getOrderInfo(?)}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			if(call.getInt(1) > 0){
				rs = (ResultSet) call.getObject(2);
				List<PushOrderInfoPOJO> list = new ArrayList<PushOrderInfoPOJO>();
				while(rs.next()){
					PushOrderInfoPOJO pojo = new PushOrderInfoPOJO();
					pojo.setChargeDetailId(rs.getLong("charge_detail_id"));
					pojo.setGameId(rs.getInt("game_id"));
					pojo.setUnionOrderId(rs.getString("union_order_id"));
					pojo.setServerIP(rs.getString("server_ip"));
					pojo.setClientIp(rs.getString("request_ip"));
					list.add(pojo);
				}
				result.put("result", 1);
				result.put("pushOrderInfoList", list);
			}else{
				result.put("result", call.getInt(1));
				result.put("pushOrderInfoList", null);
			}
		}catch(SQLException e){
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), e.toString());
		}finally{
			this.close(conn, call, null, rs);
		}
		return result;
	}
	
	/**
	 * 获取符合再次推送的，推送记录
                          推送次数小于3，错误码不为0或者推送时间已经超过2小时
	 */
	public Map<String,Object> getPushInfo() {
		
		Map<String,Object> result = new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			conn = this.getDataSource().getConnection();
			call = conn.prepareCall("{?=call pkg_echarging_cp_charge.getPushInfo(?)}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			if(call.getInt(1) > 0){
				rs = (ResultSet) call.getObject(2);
				List<PushOrderInfoPOJO> list = new ArrayList<PushOrderInfoPOJO>();
				while(rs.next()){
					PushOrderInfoPOJO pojo = new PushOrderInfoPOJO();
					pojo.setChargeDetailId(rs.getLong("charge_detail_id"));
					pojo.setGameId(rs.getInt("game_id"));
					pojo.setUnionOrderId(rs.getString("union_order_id"));
					pojo.setServerIP(rs.getString("server_ip"));
					pojo.setClientIp(rs.getString("request_ip"));
					list.add(pojo);
				}
				result.put("result", 1);
				result.put("pushOrderInfoList", list);
			}else{
				result.put("result", call.getInt(1));
				result.put("pushOrderInfoList", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), e.toString());
		}finally{
			this.close(conn, call, null, rs);
		}
		return result;
	}
	/**
	 * 通过渠道订单号获取预支付信息
	 * @param unionOrderId
	 * @return
	 */
	public Map<String,Object> getPreInfoByChargeDetailId(String unionOrderId){
		Map<String,Object> result = new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			conn = this.getDataSource().getConnection();
			call = conn.prepareCall("{?=call pkg_echarging_cp_charge.getPreInfoByChargeDetailId(?,?)}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			call.setString(2, unionOrderId);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.execute();
			if(call.getInt(1) > 0){
				rs = (ResultSet) call.getObject(3);

				LoggerUtil.info(QueryChargeServerDaoImpl.class, rs.toString());
				while(rs.next()){
					PrePaymentPOJO pojo = new PrePaymentPOJO();
					pojo.setPaymentId(rs.getString("payment_id"));
					pojo.setUserName(rs.getString("user_name"));
					pojo.setCpId(rs.getInt("cp_id"));
					pojo.setGameId(rs.getInt("game_id"));
					pojo.setGatewayId(rs.getInt("gateway_id"));
					pojo.setChargeMoney(rs.getDouble("charge_money"));
					pojo.setChargeAmount(rs.getInt("charge_amount"));
					pojo.setProductId(rs.getString("product_id"));
					pojo.setAttachCode(rs.getString("attach_code"));
					pojo.setExpandInfo(rs.getString("expand_info"));
					pojo.setChargeTime(rs.getTimestamp("payment_time"));
					result.put("result", 1);
					result.put("payment", pojo);
				}
			}else{
				result.put("result", call.getInt(1));
				result.put("payment", null);
			}
		} catch (SQLException e) {
			LoggerUtil.error(QueryChargeServerDaoImpl.class, e.toString());
		}finally{
			this.close(conn, call, null, rs);
		}
		return result;
	}
	/**
	 * 查询用户在指定游戏和区服下是否存在角色信息
	 * @param String  userName 用户名
	 * @param Integer gameId   游戏ID
	 */
	public int queryRoleCount(String userName, int gameId, int gatewayId) {
		Connection conn = null;
		CallableStatement call = null;
		int result = Constant.ERROR_DB;
		try {
			conn = this.getDataSource().getConnection();
			call = conn.prepareCall("{?=call pkg_echaring_union.getRoleCount(?,?,?,?)}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			call.setInt(2, gameId);
			call.setString(3, userName);
			call.setInt(4, gatewayId);
			call.registerOutParameter(5, java.sql.Types.INTEGER);
			call.execute();
			result = call.getInt(1);
			if(result > 0){
				return call.getInt(5);
			}else{
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), e.toString());
		}finally{
			this.close(conn, call, null, null);
		}
		return result;
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
		Connection conn = null;
		CallableStatement call = null;
		int result = Constant.ERROR_DB;
		try {
			conn = this.getDataSource().getConnection();
			call = conn.prepareCall("{?=call PKG_ECHARGING_CP_CHARGE.checkActMQInfo(?,?,?)}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			call.setInt(2, gameId);
			call.setString(3, chargeOrderCode);
			call.setLong(4, chargeDetailId);
			call.execute();
			result = call.getInt(1);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), e.toString());
		}finally{
			this.close(conn, call, null, null);
		}
		return result;
	}
	/**
	 * 通过游戏代码和渠道代码获取到联运游戏ID和联运渠道ID
	 * @param String gameName
	 * @param String cpName
	 * @return Integer 联运游戏ID
	 */
	public List<Object> getCpGameId(String gameName, String cpName) {
		List<Object> list = this.queryList(this.getDataSource_pm(), queryCpGameId, new Object[]{
			gameName,cpName});
		return (list.size() == 0 ? null : list);
	}
	public static void main(String[] args) {
		new QueryChargeServerDaoImpl().getCpGameId("sszj", "vivo");
	}
	
	/**
	 * 通过gameId以及test_state,查询总的充值金额
	 * @param gameId
	 * @param testState
	 * @return
	 */
	public double getSunMoneyByGameId( int gameId, int testState){
		Object value = this.query(this.getDataSource(), querySumMoneyByGameId, new Object[]{
			gameId, testState});
		return (value == null ? 0 : Double.parseDouble(String.valueOf(value)));
	}
	/**查询苹果用户充值小额金额次数
	 * 
	 * @param userName
	 * @param cpId
	 * @param gameId
	 * @param date  当前时间（yyyy-mm-dd）
	 * @return  
	 */
	public int queryAppleChargeTime(String userName, Integer cpId, Integer gameId,String date){
		Object vali = this.query(this.getDataSource(), queryAppleChargeTime, 
				new Object[]{userName,cpId,gameId, date});
		return (vali == null? 0 : Integer.parseInt(vali.toString()));
	}
	
	/**
	 * 查询该渠道此订单号是否已经支付成功
	 * @param paymentId  预支付订单号
	 * @return 大于0 说明已经支付成功
	 */
	public int queryPayMentIsSuccess(String paymentId){
			Object vali = this.query(this.getDataSource(), queryPayMentIsSuccess, 
					new Object[]{paymentId});
			return (vali == null? 0 : Integer.parseInt(vali.toString()));
	}
	/**
	 * 获取回调地址
	 * @param cpId
	 * @param cpGameId
	 * @return
	 */
	public String queryCallBackURL(Integer cpId, Integer cpGameId){
		Object value = this.query(this.getDataSource_pm(), queryCallBackURL, new Object[]{
			cpId,cpGameId});
		return (value == null ? "0" : String.valueOf(value));
	}
	/**
	 * 根据蓝港账号去查询log_charge_common表中数据
	 * @param chargeDetailId
	 * @return
	 */
	public LogChargeCommonPOJO getLogChargeCommon(long chargeDetailId){
		LogChargeCommonPOJO pojo = new LogChargeCommonPOJO();
		Connection conn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		try {
			conn = this.getDataSource().getConnection();
			call = conn.prepareCall("{?=call pkg_echarging_cp_charge.getLogChargeCommon(?,?)}");
			call.registerOutParameter(1, java.sql.Types.INTEGER);
			call.setLong(2, chargeDetailId);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.execute();
			if(call.getInt(1) > 0){
				rs = (ResultSet) call.getObject(3);
				while(rs.next()){
					pojo.setCardNum(String.valueOf(rs.getInt("card_num")));
					pojo.setChargeAmount(String.valueOf(rs.getInt("charge_amount")));
					pojo.setChargeChannelId(String.valueOf(rs.getInt("charge_channel_id")));
					pojo.setChargeDetailId(rs.getString("charge_detail_id"));
					pojo.setChargeGatewayId(String.valueOf(rs.getInt("charge_gateway_id")));
					pojo.setChargeMoney(String.valueOf(rs.getDouble("charge_money")));
					pojo.setChargeRealMoney(String.valueOf(rs.getDouble("charge_realmoney")));
					pojo.setChargeSubjectId(String.valueOf(rs.getInt("charge_subject_id")));
					pojo.setChargeTime(rs.getString("charge_time"));
					pojo.setCity(rs.getString("city"));
					pojo.setClientIp(rs.getString("client_ip"));
					pojo.setDealState(String.valueOf(rs.getInt("deal_state")));
					pojo.setDiscount(String.valueOf(rs.getDouble("discount")));
					pojo.setGameId(String.valueOf(rs.getInt("game_id")));
					pojo.setPassportId(String.valueOf(rs.getLong("passport_id")));
					pojo.setProvince(rs.getString("province"));
					pojo.setServerIp(String.valueOf(rs.getLong("server_ip"))+"");
					pojo.setUnchargeTime(rs.getString("uncharge_time"));
				}
			}else{
				pojo = null;
			}
		} catch (SQLException e) {
			LoggerUtil.error(QueryChargeServerDaoImpl.class, e.toString());
		}finally{
			this.close(conn, call, null, rs);
		}
		return pojo;
	}


}
