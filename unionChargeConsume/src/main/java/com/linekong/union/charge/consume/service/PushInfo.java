package com.linekong.union.charge.consume.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.consume.util.Constant;
import com.linekong.union.charge.consume.util.HttpUtils;
import com.linekong.union.charge.consume.util.config.SendChargeInfoToKafkaConfig;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.rpc.pojo.LogChargeCommonPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;


/**
 * DB-push
 * @author Administrator
 *
 */
public class PushInfo implements Runnable {
	@Autowired
	private ChargeServerDao chargeServerDao;
	@Autowired
	private QueryServerDao queryServerDao;
	
	private PushOrderInfoPOJO pushPojo;

	private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	public PushInfo(PushOrderInfoPOJO pushPojo ,ChargeServerDao chargeServerDao,QueryServerDao queryServerDao){
		this.pushPojo = pushPojo;
		this.chargeServerDao = chargeServerDao;
		this.queryServerDao = queryServerDao;
	}
	
	@Override
	public void run() {
		//推送订单信息
		int result = chargeServerDao.pushChargeInfo(pushPojo);
		LoggerUtil.info(PushInfo.class, "PushInfo_param："+pushPojo+"pushOrderResult:"+result);
		//如果推送成功，则通知DB，以便他们统计实时数据
		if(result == Constant.SUCCESS){
			//查询log_charge_common数据，
			LogChargeCommonPOJO logChargeCommon = queryServerDao.getLogChargeCommon(pushPojo.getChargeDetailId());
			try {
				logChargeCommon.setChargeTime(URLEncoder.encode(logChargeCommon.getChargeTime(),"utf-8"));
			} catch (UnsupportedEncodingException e1) {
				LoggerUtil.error(PushInfo.class, "notify kafaka charge message error:"+e1.toString());
				return;
			}
			//请求kafaka
			if(logChargeCommon == null || logChargeCommon.getChargeDetailId() == null){
				LoggerUtil.info(PushInfo.class, "notify kafaka charge message log_charge_common no datas param:"+pushPojo.getChargeDetailId());
			}else{
				String kafkaData = "";
				JSONObject json = new JSONObject();
				json.put("key", SendChargeInfoToKafkaConfig.key);
				json.put("message", JSONObject.fromObject(logChargeCommon));
				long begin = System.currentTimeMillis();
				try {
					kafkaData = "topic="+SendChargeInfoToKafkaConfig.topic+"&message="+json.toString();
					String resultGet= HttpUtils.httpGet(SendChargeInfoToKafkaConfig.url, kafkaData, "notify kafaka charge message");
					LoggerUtil.info(PushInfo.class, "notify kafaka charge message result:"+resultGet+",url="+SendChargeInfoToKafkaConfig.url+",param="+kafkaData+",time="+(System.currentTimeMillis()-begin));
					//网络稳定的时候处理map中已有的数据
					dealFailKafka(map, PushInfo.class);
				} catch (Exception e) {
					LoggerUtil.info(PushInfo.class, "notify kafaka charge message error:"+e.toString()+",url="+SendChargeInfoToKafkaConfig.url+",param="+kafkaData+",time="+(System.currentTimeMillis()-begin));
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
	}

	/**
	 * 此方法用于处理发送kafka失败后，进行重新发送
	 * 考虑到SendToKafkaThread这个类也会进行发送卡夫卡数据，且方法同本类代码一样，估也会出现发送kafka报错的情况
	 * 所以将本方法设置成静态方法，供两个类使用，
	 * 注意，两个类的储存发送kafka失败的数据的时候，分别用个的map进行装载，
	 * 如果想共用一个map，则本方法需要加上类对象锁，如果并发稍微高点，线程长时间阻塞在此处会导致线程池资源耗尽，
	 * 所以此处各自用各自的map，并且将同步锁也分开，通过clazz参数传递，使得DB订单和MQ订单各自一把锁，互不影响（后续MQ的订单量也会增多，所以分两把锁）
	 * @param map
	 */
	public static  void dealFailKafka(Map<String, String> map, Class clazz){

		synchronized(clazz) {
			//设置一个处理时间阈值，当达到或超过后，直接break,
			long begin = System.currentTimeMillis();
			try {
				if (map != null && map.size() > 0) {    //如果没有数据直接通过，不做任何打印

					//这里打印日志可告之，现在有没有待处理的Kafka失败数据，如果有，可以更新服务器的时候，稍等片刻，不过这种情况很少发生
					LoggerUtil.info(clazz, "exist fail kafkaData:" + map.toString());
					Set<Map.Entry<String, String>> entries = map.entrySet();
					Iterator<Map.Entry<String, String>> iterator = entries.iterator();
					while (iterator.hasNext()) {
						Map.Entry<String, String> next = iterator.next();
						try {
							String resultGet = HttpUtils.httpGet(SendChargeInfoToKafkaConfig.url, next.getValue(), "notify kafaka");
							//*测试用代码
							/*String resultGet = "1";
							SendChargeInfoToKafkaConfig.url = "";
							System.out.println(Thread.currentThread().getName() + "发送：" + next);*/
							//前期需要根据此处日志检查，没有重复发送的情况
							LoggerUtil.info(PushInfo.class, "dealFailKafka message result:" + resultGet + ",url="+SendChargeInfoToKafkaConfig.url+",kafka data=" + next.getValue());
							//处理完一条数据就删除原map中的该条记录
							iterator.remove();
							if(System.currentTimeMillis()-begin>1000){
								break;
							}
						} catch (Exception e) {
							//此处不打印error级别，此处日志只供调查此方案上线后追踪此问题有没有解决
							LoggerUtil.info(clazz, "dealFailKafka error param:" + next.getValue() + ",url:" + SendChargeInfoToKafkaConfig.url + "error info:" + e.toString());
							//报出异常就直接跳出循环，待下次再处理,防止短时间内网络没有恢复
							break;
						}
					}
				}
			} catch (Exception e) {
				LoggerUtil.error(clazz, "dealFailKafka method error info:" + e.toString() + ",param=" + map);
			}
		}
	}

	/*public static void main(String[] args) throws Exception {
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					SendToKafkaThread.test1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"XXX-").start();
		for (int i=0; i<10; i++){
			map.put(String.valueOf(i), "name-"+i);
		}
		for(int i = 0;i<100;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					dealFailKafka(map, PushInfo.class);
				}
			},"AAA-"+i).start();
		}
		//测试边删除又会往里放数据的情况
		for(int i=0;i<10;i++){
			map.put("elseA"+i, "threadAAA");
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
