package com.linekong.union.charge.consume.util.config;

/**
 * 发送充值推送成功的信息给DB组，来以便他们的实时统计
 * @author Administrator
 *
 */
public class SendChargeInfoToKafkaConfig {

	public static String url;		//发送kafka-地址
	
	public static String topic;		//发送kafka-topic
	
	public static String key;		//发送kafka-key

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		SendChargeInfoToKafkaConfig.url = url;
	}

	public static String getTopic() {
		return topic;
	}

	public static void setTopic(String topic) {
		SendChargeInfoToKafkaConfig.topic = topic;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		SendChargeInfoToKafkaConfig.key = key;
	}

	@Override
	public String toString() {
		return "SendChargeInfoConfig "+SendChargeInfoToKafkaConfig.key+"_"+SendChargeInfoToKafkaConfig.topic;
	}
	
	
}
