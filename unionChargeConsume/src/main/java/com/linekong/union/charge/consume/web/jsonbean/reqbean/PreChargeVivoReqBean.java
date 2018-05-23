package com.linekong.union.charge.consume.web.jsonbean.reqbean;

import java.util.HashMap;
import java.util.Map;

import com.linekong.union.charge.consume.web.formbean.PreChargeFormBean;

public class PreChargeVivoReqBean {
	private String version;			//接口版本号,方便接口升级，当前为：1.0.0
	private String signMethod;		//签名算法，固定值为：MD5
	private String signature;		//对关键信息签名后得到的字符串
	private String cpId;			//定长20位数字，在开发者平台注册获取
	private String appId;			//应用ID，在开发者平台创建应用之后获取
	private String cpOrderNumber;	//商户自定义的订单号,商户自定义，最长 64 位字母、数字和下划线组成,商户订单号必须唯一
	private String notifyUrl;		//交易结束时用于通知商户服务器的url
	private String orderTime;		//yyyyMMddHHmmss, 交易发生时的时间日期
	private String orderAmount;		//交易金额,单位：分，币种：人民币，必须是整数
	private String orderTitle;		//商品的标题
	private String orderDesc;		//订单描述
	private String extInfo;			//CP扩展参数，异步通知时会透传给CP服务器，最大64位,不能为空
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSignMethod() {
		return signMethod;
	}
	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCpOrderNumber() {
		return cpOrderNumber;
	}
	public void setCpOrderNumber(String cpOrderNumber) {
		this.cpOrderNumber = cpOrderNumber;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderTitle() {
		return orderTitle;
	}
	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getExtInfo() {
		return extInfo;
	}
	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}
	public PreChargeVivoReqBean(PreChargeFormBean bean,String sign,String cpId,String appId,String paymentId,String callBackURL,String date) {
		super();
		this.version = bean.getVar();
		this.signMethod = bean.getCpSignType();
		this.signature = sign;
		this.cpId = cpId;
		this.appId = appId;
		this.cpOrderNumber = paymentId;
		this.notifyUrl = callBackURL;
		this.orderTime = date;
		this.orderAmount =  ""+bean.getChargeMoney().intValue() *100;
		this.orderTitle = bean.getProductName();
		this.orderDesc = bean.getProductDesc();
		this.extInfo = bean.getAttachCode();
	}
	public Map<String, String> toMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("version", 		 version		);
		map.put("signMethod", 	 signMethod	   );
		map.put("signature", 	 signature	   );
		map.put("cpId", 		 cpId			);
		map.put("appId", 		 appId		   );
		map.put("cpOrderNumber", cpOrderNumber );
		map.put("notifyUrl", 	 notifyUrl	   );
		map.put("orderTime", 	 orderTime	   );
		map.put("orderAmount", 	 orderAmount	);
		map.put("orderTitle", 	 orderTitle	   );
		map.put("orderDesc", 	 orderDesc	   );
		map.put("extInfo", 		 extInfo		);
		return map;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("version=").append(version);
		sb.append(",signMethod=").append(signMethod);
		sb.append(",signature=").append(signature);
		sb.append(",cpId=").append(cpId);
		sb.append(",appId=").append(appId);
		sb.append(",cpOrderNumber=").append(cpOrderNumber);
		sb.append(",notifyUrl=").append(notifyUrl);
		sb.append(",orderTime=").append(orderTime);
		sb.append(",orderAmount=").append(orderAmount);
		sb.append(",orderTitle=").append(orderTitle);
		sb.append(",orderDesc=").append(orderDesc);
		sb.append(",extInfo=").append(extInfo);
		return sb.toString();
	}
}
