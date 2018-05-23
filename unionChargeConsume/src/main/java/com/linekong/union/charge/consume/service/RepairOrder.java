package com.linekong.union.charge.consume.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.linekong.union.charge.consume.util.Common;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.linekong.union.charge.consume.rabbitmq.ChargeInvokeRabbitMQ;
import com.linekong.union.charge.consume.rabbitmq.pojo.PushOrderWithMQInfoPOJO;
import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.consume.util.Constant;
import com.linekong.union.charge.consume.util.HttpUtils;
import com.linekong.union.charge.consume.util.JsonUtil;
import com.linekong.union.charge.consume.util.config.SendChargeInfoToKafkaConfig;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.rpc.charge.QueryChargeServiceInterface;
import com.linekong.union.charge.rpc.pojo.LogChargeCommonPOJO;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;

public class RepairOrder implements Runnable {
	@Autowired
	private QueryServerDao queryServerDao;
	
	private ChargeServerDao chargeServerDao;
	
	private QueryChargeServiceInterface   queryChargeServiceInterface;
	
	private ChargeInvokeRabbitMQ chargeInvokeRabbitMQ;
	
	public RepairOrder(QueryServerDao queryServerDao,ChargeServerDao chargeServerDao,QueryChargeServiceInterface   queryChargeServiceInterface,ChargeInvokeRabbitMQ chargeInvokeRabbitMQ){
		this.queryServerDao = queryServerDao;
		this.chargeServerDao = chargeServerDao;
		this.queryChargeServiceInterface = queryChargeServiceInterface;
		this.chargeInvokeRabbitMQ = chargeInvokeRabbitMQ;
	}
	
	@Override
	public void run() {
		while(true){
			LoggerUtil.info(RepairOrder.class, "--------START  repairThread---------");
			//获取推送消息过程中Dubbo请求超时的订单
			Map<String,Object> pushOutTimeResult = queryServerDao.getOrderInfo();
			if((Integer)pushOutTimeResult.get("result")==Constant.SUCCESS){
				@SuppressWarnings("unchecked")
				List<PushOrderInfoPOJO> pushList = (List<PushOrderInfoPOJO>) pushOutTimeResult.get("pushOrderInfoList");
				push(pushList);
			}
			
			//获取所有的需要补单的订单信息
			Map<String, Object> pushInfoResult = queryServerDao.getPushInfo();
			if((Integer)pushInfoResult.get("result")==Constant.SUCCESS){
				@SuppressWarnings("unchecked")
				List<PushOrderInfoPOJO> pushList = (List<PushOrderInfoPOJO>) pushInfoResult.get("pushOrderInfoList");
				push(pushList);
			}
			
		


			LoggerUtil.info(RepairOrder.class, "--------END  repairThread  sleep 2 hours---------");

			//休眠2小时
			try {
//测试完成后放开				Thread.sleep(1000*60*60*2);
				Thread.sleep(1000*60*60*2);
			} catch (InterruptedException e) {
				LoggerUtil.error(RepairOrder.class, "--------ERROR  repairThread exception---------"+e.toString());
			}
		}
		
	}
	
	public void push(List<PushOrderInfoPOJO> pushList) {
		try {
			//推送类型
			int pushType = 1;
			for (PushOrderInfoPOJO pojo : pushList) {
				//获取预支付信息（并未查出IP）
				Map<String, Object> preInfoMap = queryServerDao.getPreInfoByChargeDetailId(pojo.getUnionOrderId());
				if ((Integer) preInfoMap.get("result") != Constant.SUCCESS) {
					LoggerUtil.info(RepairOrder.class, "repair-PushInfo_param:" + pojo + "_getPreInfoByChargeDetailId:" + (Integer) preInfoMap.get("result"));
					continue;
				}
				PrePaymentPOJO preInfo = (PrePaymentPOJO) preInfoMap.get("payment");
				//设置IP
				pojo.setClientIp(Common.iplong2Str(Long.parseLong(pojo.getClientIp())));
				pojo.setServerIP(Common.iplong2Str(Long.parseLong(pojo.getServerIP())));
				//获取推送类型
				String config = queryChargeServiceInterface.getConfigInfo(pojo.getGameId(), preInfo.getCpId(), Constant.CONFIG_IS_NEW_ACT);
				if (!config.equals("true") || config.equals("0")) {
					pushType = Constant.DB_PUSH;
				} else {
					pushType = Constant.MQ_PUSH;
				}
				int result = Constant.SUCCESS;

				//推送消息
				switch (pushType) {
					case Constant.DB_PUSH:
						//推送订单信息
						int pushDBResult = chargeServerDao.pushChargeInfo(pojo);
						LoggerUtil.info(RepairOrder.class, "repair-DB-PushInfo_param:" + pojo + "pushOrderResult:" + pushDBResult);
						//推送成功，发送kafka信息
						if (pushDBResult == Constant.SUCCESS) {
							//查询log_charge_common数据，
							LogChargeCommonPOJO logChargeCommon = queryServerDao.getLogChargeCommon(pojo.getChargeDetailId());
							try {
								logChargeCommon.setChargeTime(URLEncoder.encode(logChargeCommon.getChargeTime(), "utf-8"));
							} catch (UnsupportedEncodingException e1) {
								LoggerUtil.error(PushInfo.class, "notify kafaka charge mesasge:" + e1.toString());
								return;
							}
							//请求kafaka
							if (logChargeCommon == null || logChargeCommon.getChargeDetailId() == null) {
								LoggerUtil.info(PushInfo.class, "notify kafaka charge mesasge log_charge_common no datas param:" + pojo.getChargeDetailId());
							} else {
								JSONObject json = new JSONObject();
								json.put("key", SendChargeInfoToKafkaConfig.key);
								json.put("message", JSONObject.fromObject(logChargeCommon));
								try {
									String resultGet = HttpUtils.httpGet(SendChargeInfoToKafkaConfig.url, "topic=" + SendChargeInfoToKafkaConfig.topic + "&message=" + json.toString(), "【通知kafaka-充值信息】");
									LoggerUtil.info(PushInfo.class, "notify kafaka charge mesasge result:" + resultGet + ",url:" + SendChargeInfoToKafkaConfig.url + ",param:" + "topic=" + SendChargeInfoToKafkaConfig.topic + ",message=" + json.toString());
								} catch (Exception e) {
									LoggerUtil.error(PushInfo.class, "notify kafaka charge mesasge error:" + e.toString() + ",url:" + SendChargeInfoToKafkaConfig.url + ",param:" + "topic=" + SendChargeInfoToKafkaConfig.topic + ",message=" + json.toString());
								}
							}
						}
						break;
					case Constant.MQ_PUSH:
						PushOrderWithMQInfoPOJO pushOrderWithMQInfoPOJO = new PushOrderWithMQInfoPOJO(Constant.DEFAULT_DISCOUNT, preInfo, pojo);
						//检查是否符合发送条件
						result = chargeServerDao.pushMQChargeInfoCheck(pojo);
						if (result != Constant.SUCCESS) {
							LoggerUtil.info(RepairOrder.class, "repair-MQ-PushInfo_param:" + pojo + "pushOrderResult:" + result);
							continue;
						}
						try {
							chargeInvokeRabbitMQ.chargePushInvokeMQ(JsonUtil.convertBeanToJson(pushOrderWithMQInfoPOJO));
						} catch (Exception e) {
							LoggerUtil.error(RepairOrder.class, "repair-MQ-Error-PushOrderWithMQInfoPOJO_param:" + pushOrderWithMQInfoPOJO + "pushMessageResult:" + result + ",error:" + e.toString());
						}
						break;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), "repair Thread error："+e.toString());
		}
	}
}
