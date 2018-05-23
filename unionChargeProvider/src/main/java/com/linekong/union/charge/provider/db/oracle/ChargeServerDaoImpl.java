package com.linekong.union.charge.provider.db.oracle;

import org.springframework.stereotype.Repository;

import com.linekong.union.charge.provider.db.DataSourceConfigureFactory;
import com.linekong.union.charge.provider.db.dao.ChargeServerDao;
import com.linekong.union.charge.rpc.pojo.AppstoreChargePOJO;
import com.linekong.union.charge.rpc.pojo.ChargingPOJO;
import com.linekong.union.charge.rpc.pojo.LogActivityMQPushPOJO;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;
@Repository("chargeServerDaoImpl")
public class ChargeServerDaoImpl extends DataSourceConfigureFactory implements ChargeServerDao{
	/**
	 * sdk发起预支付
	 * @param PrePaymentPOJO pojo
	 * @return Integer 预支付结果
	 */
	public int preCharge(PrePaymentPOJO pojo) {
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.precharge(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
				new Object[]{pojo.getPaymentId(),pojo.getUserName(),pojo.getCpId(),
			pojo.getGameId(),pojo.getGatewayId(),pojo.getChargeMoney(),pojo.getChargeAmount(),
			pojo.getPlatformName(),pojo.getAttachCode(),pojo.getExpandInfo(),pojo.getRoleId(),
			pojo.getTestState(),pojo.getProductId(),pojo.getVar(),pojo.getCpSignType(),
			pojo.getProductDesc(),pojo.getProductName()});
	}
	/**
	 * 合作伙伴支付成功信息
	 * @param ChargingPOJO pojo
	 * @return Integer 
	 */
	public int charging(ChargingPOJO pojo) {
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.cpChargeCallBack(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[]{pojo.getOrderId(),pojo.getChargeDetailId(),pojo.getPaymentId(),pojo.getUserName(),
			pojo.getCpId(),pojo.getGameId(),pojo.getGatewayId(),pojo.getChargeMoney(),pojo.getChargeAmount(),
			pojo.getPlatformName(),pojo.getAttachCode(),pojo.getExpandInfo(),pojo.getServerIP(),pojo.getRequestIP()});
	}
	/**
	 * 订单信息推送
	 * @param PushOrderInfoPOJO pojo
	 * @return Integer
	 */
	public int pushChargeInfo(PushOrderInfoPOJO pojo) {
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.pushChargeInfo(?,?,?,?,?)", 
				new Object[]{pojo.getUnionOrderId(),pojo.getChargeDetailId(),pojo.getGameId(),pojo.getServerIP(),pojo.getClientIp()});
	}
	/**
	 * 记录APPSTORE 充值日志表
	 * @param pojo
	 * @param appPojo
	 * @return
	 */
	public int chargingForApple(ChargingPOJO pojo, AppstoreChargePOJO appPojo){
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.cpChargeCallBackForApple(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[]{pojo.getOrderId(),pojo.getChargeDetailId(),pojo.getPaymentId(),pojo.getUserName(),pojo.getCpId(),pojo.getGameId(),
			pojo.getGatewayId(),pojo.getChargeMoney(),pojo.getChargeAmount(),pojo.getPlatformName(),pojo.getAttachCode(),pojo.getExpandInfo()
			,appPojo.getChargeDetailId(),appPojo.getUserName(),appPojo.getGameId(),appPojo.getGatewayId(),appPojo.getProductId(),appPojo.getQuantity(),
			appPojo.getAppItemId(),appPojo.getTransactionId(),appPojo.getPurchaseDate(),appPojo.getPurchaseDateMs(),appPojo.getPurchaseDatePst(),
			appPojo.getOriginalTransactionId(),appPojo.getOriginalPurchaseDate(),appPojo.getOriginalPurchaseDateMs(),appPojo.getOriginalPurchaseDatePst(),
			appPojo.getUniqueIdentifier(),appPojo.getBid(),appPojo.getBvrs(),appPojo.getChargeAmount(),appPojo.getProductPrice(),appPojo.getAllChargeAmount()
			,appPojo.getAllProductPrice(),pojo.getServerIP(),pojo.getRequestIP()});
	}
	public int getOrderSatus(String payMentId) {
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.getChargeOrderStatus(?)", new Object[]{payMentId});
	}
	
	/**
	 * MQ推送前信息检查
	 * @param pojo
	 * @return
	 */
	public int pushMQChargeInfoCheck(PushOrderInfoPOJO pojo){
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.pushMQChargeInfoCheck(?,?,?)",
				new Object[]{pojo.getUnionOrderId(),pojo.getChargeDetailId(),pojo.getGameId()});
	}
	/**
	 *  MQ推送后信息记录
	 * @param pojo
	 * @return
	 */
	public int pushMQChargeInfoRecord(PushOrderInfoPOJO pojo,int result,int userId){
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.pushMQChargeInfoRecord(?,?,?,?,?,?)", 
				new Object[]{pojo.getUnionOrderId(),pojo.getChargeDetailId(),pojo.getServerIP(),pojo.getClientIp(),userId,result});
	}
	/**
	 * 记录活动推送MQ
	 * @param pojo
	 * @param result
	 * @return
	 */
	public int recordActMQInfo(LogActivityMQPushPOJO pojo,int result){
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.recordActMQInfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
				new Object[]{pojo.getGameId(),pojo.getUserId(),pojo.getUserName(),pojo.getGatewayId(),pojo.getChargeChannelId(),pojo.getDiscount()
			,pojo.getChargeSubjectId(),pojo.getChargeMoney(),pojo.getChargeAmount(),pojo.getChargeOrderCode(),pojo.getChargeDetailId()
			,pojo.getChargeType(),pojo.getMoneyType(),pojo.getAttachCode(),pojo.getRoleId(),pojo.getChargeTime(),pojo.getServerIp()
			,pojo.getClientIp(),result});
	}

	/**
	 * 将预支付表的试玩账号改成正式账号
	 * @param paymentId
	 * @param realName
	 * @return
	 */
	public int updateUserName(String paymentId, String realName) {
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.updateUserName(?,?)", new Object[]{paymentId,realName});
	}

	public int markSandCharge(String paymentId, int sandboxState) {
		return this.invokeIntFunction(this.getDataSource(), "pkg_echarging_cp_charge.markSandCharge(?,?)", new Object[]{paymentId, sandboxState});
	}


}
