package com.linekong.union.charge.consume.service;

import java.net.URLEncoder;
import java.util.concurrent.ConcurrentHashMap;

import com.linekong.union.charge.consume.util.Constant;
import net.sf.json.JSONObject;

import com.linekong.union.charge.consume.rabbitmq.ChargeToGameSuccessListioner;
import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.consume.util.HttpUtils;
import com.linekong.union.charge.consume.util.config.SendChargeInfoToKafkaConfig;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.rpc.pojo.LogChargeCommonPOJO;

public class SendToKafkaThread implements Runnable{
	
	private Long orderID;
	
	private QueryServerDao queryServerDao;

	//此map用来保存kafka推送失败数据
	private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	public SendToKafkaThread(Long orderID,QueryServerDao queryServerDao){
		this.orderID = orderID;
		this.queryServerDao = queryServerDao;
	}
	
	@Override
	public void run() {
		//查询log_charge_common数据，
		LogChargeCommonPOJO logChargeCommon = queryServerDao.getLogChargeCommon(orderID);
		//请求kafaka
		if(logChargeCommon == null || logChargeCommon.getChargeDetailId() == null){
			LoggerUtil.info(PushInfo.class, "notify kafaka charge message log_charge_common no datas param:"+orderID);
		}else{
			try {
				logChargeCommon.setChargeTime(URLEncoder.encode(logChargeCommon.getChargeTime(),"utf-8"));
			} catch (Exception e1) {
				LoggerUtil.error(PushInfo.class, "notify kafaka charge message error:"+e1.toString());
				return;
			}
			String kafkaData =  "";
			JSONObject json = new JSONObject();
			json.put("key", SendChargeInfoToKafkaConfig.key);
			json.put("message", JSONObject.fromObject(logChargeCommon));
			long begin = System.currentTimeMillis();
			try {
				kafkaData = "topic="+SendChargeInfoToKafkaConfig.topic+"&message="+json.toString();
				String resultGet = HttpUtils.httpGet(SendChargeInfoToKafkaConfig.url, kafkaData, "notify kafaka-mq charge message");
				LoggerUtil.info(ChargeToGameSuccessListioner.class, "info-mq notify kafaka charge message result:"+resultGet+",url:"+SendChargeInfoToKafkaConfig.url+",param:"+kafkaData +",time="+(System.currentTimeMillis()-begin));
				//网络稳定的时候处理map中已有的数据
				PushInfo.dealFailKafka(map, SendToKafkaThread.class);
			} catch (Exception e) {
				LoggerUtil.info(ChargeToGameSuccessListioner.class, "error-mq notify kafaka charge message error:"+e.toString()+",url:"+SendChargeInfoToKafkaConfig.url+",param:"+kafkaData +",time="+(System.currentTimeMillis()-begin));
				//设置一个阈值的判断（放在此处不放在dealFailKafka方法中是因为防止网络瘫痪,一直put数据）
				if(map.size() < Constant.KAFKAFAILCOUNT){
					//将失败的数据保存在map中
					map.put(logChargeCommon.getChargeDetailId(), kafkaData);
				}else{
					LoggerUtil.error(PushInfo.class, "kafka fail record reach "+map.size());
				}
			}
		}
	}

	//测试代码
	/*public static void test1() throws InterruptedException {
		for (int i=0; i<10; i++){
			map.put(String.valueOf(i), "--name-"+i);
		}
		for(int i = 0;i<100;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					PushInfo.dealFailKafka(map, SendToKafkaThread.class);
				}
			},"BBB-"+i).start();
		}
		//测试边删除又会往里放数据的情况
		for(int i=0;i<10;i++){
			map.put("elseB"+i, "threadBBB");
		}
		*//*new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=9;i>=0; i++){
					map.remove(String.valueOf(i));
				}
			}
		}).start();*//*
		Thread.sleep(1000);
		System.out.println(map);
	}*/
}
