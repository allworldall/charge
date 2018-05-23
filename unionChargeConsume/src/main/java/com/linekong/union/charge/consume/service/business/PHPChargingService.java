package com.linekong.union.charge.consume.service.business;

import com.linekong.union.charge.consume.rabbitmq.ChargeInvokeRabbitMQ;
import com.linekong.union.charge.consume.rabbitmq.pojo.PushOrderWithMQInfoPOJO;
import com.linekong.union.charge.consume.service.ActInfoWithMQ;
import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.consume.util.Common;
import com.linekong.union.charge.consume.util.Constant;
import com.linekong.union.charge.consume.util.ThreadPoolUtil;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.util.sign.MD5SignatureChecker;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PHPReturnResBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("phpChargingService")
public class PHPChargingService {
	@Autowired
	private QueryServerDao queryServerDao;
	@Autowired
	private ChargeInvokeRabbitMQ chargeInvokeRabbitMQ;
	@Autowired
	private ChargeServerDao chargeDao;
	
	/**
	 * 推送MQ活动计算信息
	 * @param pushOrderWithMQInfoPOJO
	 * @param sign
	 */
	public PHPReturnResBean pushActMQ(PushOrderWithMQInfoPOJO pojo,String sign){
		Long phpChargeDetailId = pojo.getChargeDetailId();
		//获取秘钥
		String key = "";
		try {
//			key = CacheUtil.getRSAKey(Constant.CONFIG_PROJECT_CODE, 2).replace("\n", "");

//			if(key.equals("0")){
//				chargeDao.recordActMQInfo(Common.getLogActivityMQPushPOJO(pojo), Constant.ERROR_CHARGE_SIGN);
//				return new PHPReturnResBean(Constant.ERROR_CHARGE_SIGN,Constant.DESC_ERROR_CHARGE_SIGN);
//			}
			//验签   
			if(!MD5SignatureChecker.phpChargingChecker(pojo, sign, key)){
				chargeDao.recordActMQInfo(Common.getLogActivityMQPushPOJO(pojo), Constant.ERROR_SIGN);
				return new PHPReturnResBean(Constant.ERROR_SIGN,Constant.DESC_ERROR_SIGN);
			}
			//验证订单是否重复
			int check = queryServerDao.checkActMQInfo(pojo.getGameId(), pojo.getChargeOrderCode(), pojo.getChargeDetailId());
			if(check != Constant.SUCCESS){
				//如果重复直接返回
				if(check == Constant.ERROR_ORDER_DUPLICATE){
					return new PHPReturnResBean( Constant.ERROR_ORDER_DUPLICATE, Constant.DESC_ERROR_ORDER_DUPLICATE);
				}
				chargeDao.recordActMQInfo(Common.getLogActivityMQPushPOJO(pojo), check);
				return new PHPReturnResBean( check, "");
			}
//			//查询log_charge_common数据，
//			LogChargeCommonPOJO logChargeCommon = null;
//			do{
//				logChargeCommon = queryServerDao.getLogChargeCommon(pojo.getChargeDetailId());
//				if(logChargeCommon != null && logChargeCommon.getChargeDetailId()!= null && !"".equals(logChargeCommon.getChargeDetailId())){
//					pojo.setChargeDetailId(Common.ChargeDetailIdGenerator());
//				}
//			}while (logChargeCommon != null && logChargeCommon.getChargeDetailId()!= null && !"".equals(logChargeCommon.getChargeDetailId()));

			//推送ＭＱ
			ThreadPoolUtil.pool.submit(new ActInfoWithMQ(pojo,chargeInvokeRabbitMQ,chargeDao));
	
			return new PHPReturnResBean(Constant.SUCCESS,Constant.DESC_SUCCESS);
		} catch (Exception e) {
			LoggerUtil.error(PHPChargingService.class, "deal pushActMQ param error:"+pojo+",sign"+sign+"error info:" + e.toString());
			chargeDao.recordActMQInfo(Common.getLogActivityMQPushPOJO(pojo), Constant.ERROR_SYSTEM);
			return new PHPReturnResBean(Constant.ERROR_SYSTEM,Constant.DESC_ERROR_SYSTEM);
		}
	}
	
}
