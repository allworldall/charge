package com.linekong.union.charge.consume.service.business;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.linekong.union.charge.consume.util.*;
import net.iharder.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linekong.union.charge.consume.service.invoke.PreChargeServerDao;
import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.consume.util.cache.CacheUtil;
import com.linekong.union.charge.consume.util.iapppay.RSA;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.util.rsa.RSAEncrypt;
import com.linekong.union.charge.consume.util.sign.MD5SignatureChecker;
import com.linekong.union.charge.consume.util.sign.RSASignatureChecker;
import com.linekong.union.charge.consume.web.formbean.PreChargeFormBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.JinliExpandInfo;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.PreChargeJinliReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.PreChargeMeizuReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.PreChargeVivoReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.SamsungReqBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeJinliResBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeResBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeResBeanKuaikan;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeVivoResBean;
import com.linekong.union.charge.rpc.pojo.AppstoreProductPOJO;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;

@Service("preChargeService")
public class PreChargeService {
	@Autowired
	private PreChargeServerDao preChargeServerDao;
	
	@Autowired
	private QueryServerDao queryServerDao;
	

	private SimpleDateFormat sdfOne = new SimpleDateFormat("yyyyMMddHHmmss");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public PreChargeServerDao getPreChargeServerDao() {
		return preChargeServerDao;
	}

	public void setPreChargeServerDao(PreChargeServerDao preChargeServerDao) {
		this.preChargeServerDao = preChargeServerDao;
	}
	
	public void setQueryServerDao(QueryServerDao queryServerDao) {
		this.queryServerDao = queryServerDao;
	}
	/**
	 * SDK预支付逻辑处理，此方法通过预支付时传给我方的参数判断渠道合法性
	 * @param PreChargeFormBean form
	 * @return Integer
	 */
	public PreChargeResBean preCharge(PreChargeFormBean form){
		PrePaymentPOJO pojo = new PrePaymentPOJO();
		//验证渠道是否可用：状态、游戏配置等等
		try {
			//获取渠道是否可用：状态、游戏配置等等
			Integer cpState = CacheUtil.getCPState(form.getCpName(), form.getCpId());
			// 验证渠道状态
			if (cpState == Constant.STATE_NULL) {
				return new PreChargeResBean(Constant.ERROR_REQUEST_CP,"");
			}
			//验证渠道状态是否正常
			if (cpState == Constant.STATE_STOP_COOPERATE) {
				return new PreChargeResBean(Constant.ERROR_REQUEST_CP_STATE,"");
			}
			//获取渠道游戏配置状态
			Integer cpGameState = CacheUtil.vaildCpGameById(form.getGameId(), form.getCpId());
			//验证渠道游戏
			if(cpGameState == Constant.STATE_NULL){
				return new PreChargeResBean(Constant.ERROR_REQUEST_CPGAME,"");
			}
			//验证渠道游戏是否生效
			if(cpGameState == Constant.STATE_NOT_EFFECTIVE){
				return new PreChargeResBean(Constant.ERROR_REQUEST_CPGAME_STATE,"");
			}
			//验证游戏是否开通充值
			if(!CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_IS_OPEN_CHARGE).equals("true")){
				return new PreChargeResBean(Constant.ERROR_CHARGE_STATUS,"");
			}
			//验证是否为测试中的游戏，并且是否符合测试金额限制,以及设置预支付信息中的测试状态（testState）
			int checkTest = checkTestInfo(form, pojo);
			if(checkTest != Constant.SUCCESS){
				return new PreChargeResBean(checkTest,"");
			}
			//获取游戏的rsa加密key值 type=2默认获取私钥
			String rsaKey = CacheUtil.getRSAKey(Constant.CONFIG_PROJECT_CODE, 2);
			if(rsaKey.equals("0")){
				return new PreChargeResBean(Constant.ERROR_CHARGE_SIGN,"");
			}
			//数据加密认证（equals方法前面是加密私钥得到的串）
			if(!RSAEncrypt.rsaDecrypt(rsaKey, form.getSign()).equals(form.getUserName()+form.getCpId()+form.getGameId())){
				return new PreChargeResBean(Constant.ERROR_SIGN,"");
			}
			//订单金钱验证分为苹果和其他 
			//如果合作伙伴为苹果，比例和金额，去sys_appstore_product表查询 ,并且在非沙盒测试的苹果订单中，需要验证充值小额的次数
			if(form.getCpName().toLowerCase().contains("apple")){
				//获取产品对应配置信息
				try {
					AppstoreProductPOJO  productInfo = queryServerDao.getAppleProductInfo(form.getGameId(), form.getProductId());
					if(productInfo.getChargeAmount()  == null  || productInfo.getProductPrice() == null){
						return new PreChargeResBean(Constant.ERROR_PRODUCT_INFO,"");
					}else{
						form.setChargeAmount(productInfo.getChargeAmount());
						form.setChargeMoney(productInfo.getChargeAmount()/10.0);
					}
				} catch (Exception e) {
					LoggerUtil.error(PreChargeService.class, "deal PreChargeResBean error param:"+form.getGameId()+",productId:"+form.getProductId()+",error info:"+e.toString());
					return new PreChargeResBean(Constant.ERROR_PRODUCT_INFO,"");
				}
				//验证是否是沙盒测试 :如果没有配置默认为非沙盒测试
				boolean isAppleTest = Common.isAppleTest(String.valueOf(form.getGatewayId()),form.getUserName(),form.getGameId(),form.getCpId());	

				//---  begin 为区分沙盒订单，给沙盒订单的预支付test_state字段设置为9----------
//				if(isAppleTest){
//					pojo.setTestState(Constant.APPLE_SANDBOX_STATE);
//				}
				//---  end   ---------

				//如果不是沙盒测试，验证充值小额的次数
				if(!isAppleTest && form.getChargeMoney() <= 30.0D){
					//获取配置的充值小额的次数
					int appleChargingTime = Integer.parseInt(CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_APPLE_CHARGE_TIME));
					//获取该用户今天已经充值小额的次数
					int userAppleCharingTime = CacheUtil.queryAppleChargeTime(form.getUserName(), form.getCpId(), form.getGameId(), sdf.format(new Date()));
					//查看是否需要验证小额充值次数
					if(appleChargingTime < (userAppleCharingTime +1) ){
						return new PreChargeResBean(Constant.ERROR_APPLE_OVER_CHARGE_TIME,"");
					}
				}
			}else{
				//获取人民币对应的游戏元宝数
				int gameRatio = Integer.parseInt(CacheUtil.getKey(form.getGameId(), form.getCpId(), Constant.CONFIG_CHARGE_RATIO).toString());
				if(gameRatio == 0){ //验证充值比例不能为0
					return new PreChargeResBean(Constant.ERROR_CHARGE_RATIO,"");
				} 
				//根据比例和充值的钱数，来计算元宝数
				form.setChargeAmount(Integer.valueOf(gameRatio*new Double(form.getChargeMoney()).intValue()));
			}
			
			
			//验证角色信息是否正确
			if("true".equals(CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_IS_CHECK_ROLE))){
				if(form.getRole() != 0){
					if(queryServerDao.getRoleCount(form.getUserName(), form.getGameId(), form.getGatewayId())<=0){
						return new PreChargeResBean(Constant.ERROR_ROLE,"");
					}
				}
			}
			
			
			pojo.setGameId(form.getGameId());
			pojo.setGatewayId(form.getGatewayId());
			pojo.setCpId(form.getCpId());
			pojo.setUserName(form.getUserName());
			pojo.setChargeMoney(form.getChargeMoney());
			pojo.setChargeAmount(form.getChargeAmount());
			pojo.setAttachCode(form.getAttachCode());
			pojo.setExpandInfo(form.getExpandInfo());
			pojo.setRoleId(form.getRole());
			pojo.setProductId(form.getProductId());
			pojo.setAttachCode(form.getAttachCode());
			pojo.setPlatformName(form.getPlatformName());
			pojo.setVar(form.getVar());
			pojo.setCpSignType(form.getCpSignType());
			pojo.setProductDesc(form.getProductDesc());
			pojo.setProductName(form.getProductName());
			//写入预支付订单表结果,如果订单号重复，则重新生成，
			int result = Constant.ERROR_SYSTEM;
			do{
				pojo.setPaymentId(Common.createPaymentId());
				result = this.preChargeServerDao.preCharge(pojo);
			}while(result == Constant.ERROR_ORDER_DUPLICATE);
			return new PreChargeResBean(result,pojo.getPaymentId());
		} catch (Exception e) {
			LoggerUtil.error(PreChargeService.class, "deal preCharge param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"");
		}
	}
	/**
	 * SDK快看预支付处理
	 * @param form
	 * @return
	 */
	public PreChargeResBeanKuaikan preChargeKuaikan(PreChargeFormBean form) {
		PreChargeResBeanKuaikan bean = new PreChargeResBeanKuaikan();
		try{
			//查出app_id参数
			String appId = CacheUtil.getKey(form.getGameId(), form.getCpId(), Constant.CONFIG_ENCRYPTION_APPID);
			if(appId==null || "".equals(appId)){
				return new PreChargeResBeanKuaikan("", "", Constant.ERROR_APP_ID);
			}
			//查出提供给渠道的回调地址
			String callBackURL = CacheUtil.queryCallBackURL(form.getCpId(), form.getGameId());
			if(callBackURL == null || "".equals(callBackURL)){
				return new PreChargeResBeanKuaikan("","",Constant.ERROR_CALL_BACK_URL);
			}
			//查出MD5秘钥
			String key = null;
			if("MD5".equals(form.getCpSignType())){
				 key = CacheUtil.getKey(form.getGameId(), form.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			}
			if(key == null || "".equals(key)){
				return new PreChargeResBeanKuaikan("", "" ,Constant.ERROR_CHARGE_SIGN);
			}
			//执行常规与支付流程
			PreChargeResBean preChargeResBean = preCharge(form);
			//拼一个加密前的字符串
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("app_id", appId);
			map.put("open_uid", form.getUserName());
			map.put("out_notify_url", callBackURL);
			map.put("out_order_id", preChargeResBean.getPayMentId());
			map.put("wares_id", Integer.parseInt(form.getProductId()));
			map.put("trans_money", Float.parseFloat(form.getChargeMoney()+""));
			//按ACCID顺序拼串(连上key)
			String str = "app_id=" + map.get("app_id") + "&open_uid=" + map.get("open_uid") + "&out_notify_url=" + map.get("out_notify_url")
					+ "&out_order_id=" + map.get("out_order_id") +"&trans_money=" +map.get("trans_money") +  "&wares_id=" + map.get("wares_id") +"&key=" + key;
			//生成渠道需要的sign
//			String temp = MD5SignatureChecker.doMD5HexEncrypt(str, MD5SignatureChecker.getCharset());
//			String sign = Base64.encodeBytes(temp.getBytes("utf-8"));
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] b = md5.digest(str.getBytes("utf-8"));
			String sign = Base64.encodeBytes(b);
			//给返回的对象属性赋值
			bean.setResult(preChargeResBean.getResult());
			bean.setTransSecret(sign);
			//将Map转换成json串
			String transData = JsonUtil.convertBeanToJson(map);
			bean.setTransData(transData);
			return bean;
		}catch(Exception e){
			LoggerUtil.error(PreChargeService.class, "deal preChargeKuaikan param error:"+form+",error info:" + e.toString());
			return new PreChargeResBeanKuaikan("", "", Constant.ERROR_SYSTEM);
		}
	}
	/**
	 * SDK预支付逻辑处理----UC
	 * @param PreChargeUCFormBean form
	 * @return PreChargeResBean
	 */
	public PreChargeResBean preChargeUC(PreChargeFormBean form){
		try {
			//获取签名KEY
			String ucCpKey = null;
			if(form.getCpSignType().equals("MD5")){
				ucCpKey = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
				
			}
			if (ucCpKey == null || ucCpKey.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CHARGE_SIGN,"","");
			}
			//获取回调地址
			String callBackURL = null;
			//String callBackURL = CacheUtil.queryCallBackURL(form.getCpId(), form.getGameId());
			
			PreChargeResBean resultBean = preCharge(form);
			//如果不成功成功，返回
			if(resultBean.getResult() != 1){
				return resultBean;
			}
			//生成渠道需要的sign
			String ucSign = MD5SignatureChecker.createUCSign(form, callBackURL, resultBean.getPayMentId(), ucCpKey);
			resultBean.setExpandInfo(ucSign);
			
			return resultBean;
		} catch (Exception e) {
			LoggerUtil.error(PreChargeService.class, "deal preChargeUC param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}
	/**
	 * SDK预支付逻辑处理----魅族
	 * @param form
	 * @param bean
	 * @return
	 */
	public PreChargeResBean preChargeMeizu(PreChargeFormBean form, PreChargeMeizuReqBean bean){
		try {
			Integer amount = form.getChargeAmount();
			//获取签名KEY
			String meizuCpKey = null;
			if(form.getCpSignType().equals("MD5")){
				meizuCpKey = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_MD5);
				
			}
			if (meizuCpKey == null || meizuCpKey.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CHARGE_SIGN,"","");
			}
			//获取签名APPID
			String appId = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_APPID);
			if (appId == null || appId.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_APP_ID,"","");
			}
			PreChargeResBean resultBean = preCharge(form);
			//如果不成功成功，返回
			if(resultBean.getResult() != 1){
				return resultBean;
			}
			//生成时间戳
			Date date = new Date();
			//生成渠道需要的sign
			form.setChargeAmount(amount);
			String sign = MD5SignatureChecker.createMeizuSign(form,bean, resultBean.getPayMentId(),date.getTime(),appId, meizuCpKey);
			resultBean.setExpandInfo("{\"createTime\":\""+date.getTime()+"\",\"sign\":\""+sign+"\"}");
			
			return resultBean;
		} catch (Exception e) {
			LoggerUtil.error(PreChargeService.class, "deal preChargeMeizu param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}
	/**
	 * SDK预支付逻辑处理----金立
	 * @param form
	 * @return
	 */
	public PreChargeResBean preChargeJinli(PreChargeFormBean form){
		PreChargeResBean resultBean = null;
		try {
			if(form.getVar() == null || "".equals(form.getVar())){  //由于接受参数的PreChargeFormBean是通用的，尽量不去更改
				return new PreChargeResBean(Constant.ERROR_PARAM_VALIDATE,"","var_empty");
			}
			//获取签名KEY
			String cpKey = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_RSA_PRIVATE);
			String apiKey = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_APIKEY);
			if (cpKey == null || cpKey.equals("0") || apiKey == null || apiKey.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CHARGE_SIGN,"","");
			}
			//获取渠道创建订单地址
			String preURL = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_PRE_CHARGE_URL);
			if (preURL == null || preURL.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CP_PRE_CHARGE_URL,"","");
			}
			 resultBean = preCharge(form);
			//如果不成功，
			if(resultBean.getResult() != 1){
				return resultBean;
			}
			//生成时间戳
			String date = sdfOne.format(new Date());
			
			//获得扩展信息对象
			JinliExpandInfo expandInfoObj = JsonUtil.convertJsonToBean(form.getExpandInfo(), JinliExpandInfo.class);
			//生成渠道需要的sign
			String sign = RSASignatureChecker.createJinliSign(expandInfoObj, form,resultBean.getPayMentId(),date,apiKey, cpKey);
			//去渠道进行创建订单
			String createResult = HttpUtils.httpPost1(preURL, JsonUtil.convertBeanToJson(new PreChargeJinliReqBean(expandInfoObj, form, resultBean.getPayMentId(), apiKey, date, sign)),"preChargeJinli");			
			PreChargeJinliResBean jinliResBean = JsonUtil.convertJsonToBean(createResult, PreChargeJinliResBean.class);
			//判断返回状态
			if(!jinliResBean.getStatus().equals("200010000")){
				resultBean.setResult(Constant.ERROR_CP_PRE_CHARGE);
				return resultBean;
			}
			//成功的话验证返回值
			if(!jinliResBean.getOut_order_no().equals(resultBean.getPayMentId()) || !jinliResBean.getSubmit_time().equals(date)){
				return new PreChargeResBean(Constant.ERROR_CP_PRE_CHARGE_RES_INFO,"","");
			}
			//成功，并且返回值没有问题，将信息设置到ExpandInfo
				if("4.0.5".equals(form.getVar())){
					resultBean.setExpandInfo("{\"submit_time\":\""+date+"\",\"status\":\""+jinliResBean.getStatus()+"\",\"description\":\""+jinliResBean.getDescription()+"\"}");
				}else{
					resultBean.setExpandInfo(createResult);
				}
			return resultBean;
		} catch (Exception e) {
			LoggerUtil.error(PreChargeService.class, "deal preChargeJinli param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}
	/**
	 * SDK预支付逻辑处理----VIVO(步步高)
	 * @param form
	 * @return
	 */
	public PreChargeResBean preChargeVivo(PreChargeFormBean form){
		try {
			//获取签名CPID
			String cpId = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_CPID);
			if (cpId == null || cpId.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CP_ID,"","");
			}
			//获取签名APPID
			String appId = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_APPID);
			if (appId == null || appId.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_APP_ID,"","");
			}
			//获取签名KEY
			String appKey = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_MD5);
			if (appKey == null || appKey.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CHARGE_SIGN,"","");
			}
			//获取渠道创建订单地址
			String preURL = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_PRE_CHARGE_URL);
			if (preURL == null || preURL.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CP_PRE_CHARGE_URL,"","");
			}
			//获取回调地址
			String callBackURL = CacheUtil.queryCallBackURL(form.getCpId(), form.getGameId());
			PreChargeResBean resultBean = preCharge(form);
			//如果不成功
			if(resultBean.getResult() != 1){
				return resultBean;
			}
			//生成时间戳
			String date = sdfOne.format(new Date());
			//生成渠道需要的sign
			String sign = null;
			if(form.getCpSignType().equals(Constant.CONFIG_ENCRYPTION_MD5)){
				sign = MD5SignatureChecker.createVivoSign(form,appId,cpId,resultBean.getPayMentId(),callBackURL,date,appKey);
			}
			//去渠道进行创建订单
			String createResult = HttpUtils.httpPost(preURL, new PreChargeVivoReqBean(form,sign,cpId,appId,resultBean.getPayMentId(),callBackURL,date).toMap());			
			PreChargeVivoResBean vivoResBean = JsonUtil.convertJsonToBean(createResult, PreChargeVivoResBean.class);			
			//验证返回结果
			if(!vivoResBean.getRespCode().equals("200")){
				resultBean.setResult(Constant.ERROR_CP_PRE_CHARGE);
				return resultBean;
			}
			//验签
			if(!MD5SignatureChecker.vivoPreChargeCheck(vivoResBean, appKey, vivoResBean.getSignature())){
				resultBean.setResult(Constant.ERROR_CP_PRE_CHARGE_SIGN);
				return resultBean;
			}
			//成功的话，验证返回值
			if(!vivoResBean.getOrderAmount().equals(""+form.getChargeMoney().intValue()*100)){
				return new PreChargeResBean(Constant.ERROR_CP_PRE_CHARGE_RES_INFO,"","");
			}
			//成功，并且返回值没有问题，将信息设置到ExpandInfo
			resultBean.setExpandInfo("{\"accessKey\":\""+vivoResBean.getAccessKey()+"\",\"status\":\""+vivoResBean.getRespCode()+"\",\"description\":\""+vivoResBean.getRespMsg()+"\",\"orderNumber\":\""+vivoResBean.getOrderNumber()+"\"}");
			return resultBean;
		} catch (Exception e) {
			LoggerUtil.error(PreChargeService.class, "deal preChargeVivo param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}

	/**
	 * 三星预支付业务层
	 * @param form
	 */
	public PreChargeResBean preChargeSamsung(PreChargeFormBean form) {
		try{
			//获取签名APPID
			String appId = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_APPID);
			if (appId == null || appId.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_APP_ID,"","");
			}
			//获取签名KEY
			String appKey = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_APPKEY);
			if (appKey == null || appKey.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_APPKEY_CONFIG,"","");
			}
			//查询回调地址
			String callBackURL = CacheUtil.queryCallBackURL(form.getCpId(), form.getGameId());
			if(callBackURL == null || "".equals(callBackURL)) {
				return new PreChargeResBean(Constant.ERROR_CALL_BACK_URL, "");
			}
			//获取渠道创建订单地址
			String preURL = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_PRE_CHARGE_URL);
			if (preURL == null || preURL.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CP_PRE_CHARGE_URL,"","");
			}
			PreChargeResBean resultBean = preCharge(form);
			//如果不成功
			if(resultBean.getResult() != 1){
				return resultBean;
			}
			//创建下单参数对象
			SamsungReqBean bean = new SamsungReqBean(appId, Integer.parseInt(form.getProductId()), form.getChargeMoney(), resultBean.getPayMentId(), form.getUserName(), callBackURL);
			//拼接下单参数
			//待签名串
			String content = JsonUtil.convertBeanToJson(bean);
			String sign = RSA.sign(content, appKey, "utf-8");
			Map<String, String> map = new HashMap<>();
			map.put("transdata", content);
			map.put("sign", sign);
			map.put("signtype", "RSA");
			//去渠道创建订单号
			String createResult = HttpUtils.httpPost(preURL, map);
			//将返回渠道返回接口进行解析，并获取渠道订单号
			String order = Common.getSamsungOrder(createResult);
			if(Common.isEmptyString(order)){
				return new PreChargeResBean(Constant.ERROR_PRE_SAMSUNG_CHARGE,"","");
			}
			LoggerUtil.info(this.getClass(), "data="+createResult);
			resultBean.setExpandInfo(order);
			return resultBean;
		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(PreChargeService.class, "deal preChargeSamsung param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}

	/**
	 * 努比亚预支付
	 * @param form
	 * @return
	 */
	public PreChargeResBean preChargeNubia(PreChargeFormBean form){
		try {
			//获取签名KEY
			String nubiaCpKey = null;
			nubiaCpKey = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_MD5);
			if (nubiaCpKey == null || nubiaCpKey.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CHARGE_SIGN,"","");
			}
			//获取签名APPID
			String appId = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_APPID);
			if (appId == null || appId.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_APP_ID,"","");
			}
			PreChargeResBean resultBean = preCharge(form);
			//如果不成功，返回
			if(resultBean.getResult() != Constant.SUCCESS){
				return resultBean;
			}
			//生成时间戳
			Date date = new Date();
			//生成渠道需要的sign
			String sign = MD5SignatureChecker.createNubiaSign(form, resultBean.getPayMentId(), date.getTime(), appId, nubiaCpKey);
			resultBean.setExpandInfo("{\"data_timestamp\":\""+date.getTime()+"\",\"cp_order_sign\":\""+sign+"\"}");
			
			return resultBean;
		} catch (Exception e) {
			LoggerUtil.error(PreChargeService.class, "deal preChargeNubia param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}

	/**
	 * eagleHaodong预支付业务层
	 * @param form
	 * @return
	 */
	public PreChargeResBean preChargeEagleHaodong(PreChargeFormBean form){
		try {
			PreChargeResBean bean = preCharge(form);
			if(bean.getResult()==1){
				//查出回调地址
				String callBackURL = CacheUtil.queryCallBackURL(form.getCpId(), form.getGameId());
				if(callBackURL == null || "".equals(callBackURL)){
					return new PreChargeResBean(Constant.ERROR_CALL_BACK_URL, "");
				}else{
					bean.setExpandInfo(callBackURL.trim());
				}
			}
			return bean;
		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(PreChargeService.class, "deal preChargeEagleHaodong param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}

	/**
	 * 华为预支付业务层
	 * @param form
	 * @return
	 */
	public PreChargeResBean preChargeHuawei(PreChargeFormBean form) {
		try {
			PreChargeResBean bean = preCharge(form);
			if(bean.getResult()==1){
				//加密前拼串
				String appId = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_ENCRYPTION_APPID);
				if(Common.isEmptyString(appId)){
					return new PreChargeResBean(Constant.ERROR_APP_ID,"");
				}
				//加密前拼串
				String payId = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_ENCRYPTION_PAYID);
				if(Common.isEmptyString(payId)){
					return new PreChargeResBean(Constant.ERROR_PAY_ID,"");
				}
				//获取预支付加密私钥
				String privateKey = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_ENCRYPTION_PRE_KEY);
				if(Common.isEmptyString(privateKey)){
					return new PreChargeResBean(Constant.ERROR_PAY_PRE_KEY,"");
				}
				String str = "amount="+form.getChargeMoney()+"0&applicationID="+appId+"&country=CN&currency=CNY&merchantId="+payId+"&productDesc="+form.getProductDesc()+
						"&productName="+form.getProductName()+"&requestId="+bean.getPayMentId()+"&sdkChannel="+1+"&urlver=2";
				/*privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAxPe1gCS9PptFhoDadTFmkB4BRx+5D9pWBZKIXkOsvCIqLUUd8DGuBDxj" +
						"sbFF6JtUv61/g/HkJf5hvUudGVbIWQIDAQABAkBrRSzH+TpHmnCm7A4NvTXKT00K2zACfjpTFpbGH703R75jjhUYo3HW/lCbeJDxaIlsJHMhCLvvYj" +
						"p1FBo0rMQxAiEA9mZiouAM271ZGw6oPnBA0IgI88cDunBEmEx1NKRkL/MCIQDMpEgCdbscKiOyB98yQuTIEGkE/+S+5YHxcIDq0XOFgwIhAJjFnn6SI" +
						"CbWXbsS+WnNO8KPtc1AJaWhGka7kSgUjTHVAiAh3Fz96zUWp/JYFu1bh64LXIvEBAN5gshJhvAN6rJOuQIhAJqXxEGNY+tJ0bca62HrVe0Rz+QJpM" +
						"h/iLVn5zZ6pdiu";*/
				String cipher = RSASignatureChecker.signHuawei(str, privateKey);
				bean.setExpandInfo(cipher);
			}
			return bean;
		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(PreChargeService.class, "deal preChargeHuawei param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}
	/**
	 * 一点预支付逻辑处理
	 * @param form
	 * @param bean
	 * @return
	 */
	public PreChargeResBean preChargeYiDian(PreChargeFormBean form){
		try {
			//获取签名秘钥
			String yidianCpKey = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if(yidianCpKey == null || yidianCpKey.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CHARGE_SIGN,"","");
			}
			//获取appID
			String appId = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_ENCRYPTION_APPID);
			if (appId == null || appId.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_APP_ID,"","");
			}
			//获取partner_id
			String partnerId = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_ENCRYPTION_CPID);
			if (partnerId == null || partnerId.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CP_ID,"","");
			}
			//进行预支付逻辑处理
			PreChargeResBean resultBean = preCharge(form);
			//如果不成功，返回
			if(resultBean.getResult() != 1){
				return resultBean;
			}
			//进行数据加密-生成 请求支付 sign
			String sign = MD5SignatureChecker.createYidianCreateOrderSign(form,resultBean.getPayMentId(),appId,yidianCpKey,partnerId);
			resultBean.setExpandInfo(sign.toUpperCase());
			return resultBean;
		} catch (Exception e) {
			LoggerUtil.error(PreChargeService.class, "deal preChargeYiDian error, param:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}

	/**
	 * 大唐剑侠预支付业务层
	 * @param form
	 * @return
	 */
	public PreChargeResBean preChargeHuaSheng(PreChargeFormBean form) {
		try {
			String appkey = CacheUtil.getKey(form.getGameId(), form.getCpId(), Constant.CONFIG_ENCRYPTION_APPKEY);
			if (appkey == null || appkey.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_APPKEY_CONFIG,"","");
			}
			//查询回调地址
			String callBackURL = CacheUtil.queryCallBackURL(form.getCpId(), form.getGameId());
			if(callBackURL == null || "".equals(callBackURL)) {
				return new PreChargeResBean(Constant.ERROR_CALL_BACK_URL, "");
			}
			//获取渠道创建订单地址
			String preURL = CacheUtil.getKey(form.getGameId(),form.getCpId(), Constant.CONFIG_PRE_CHARGE_URL);
			if (preURL == null || preURL.equals("0")) {
				return new PreChargeResBean(Constant.ERROR_CP_PRE_CHARGE_URL,"","");
			}
			//进行预支付逻辑处理
			PreChargeResBean resultBean = preCharge(form);
			//如果不成功，返回
			if(resultBean.getResult() != 1){
				return resultBean;
			}
			//下单
			String jsonString = form.getExpandInfo();
			HashMap<String, Object> map = JsonUtil.convertJsonToBean(jsonString, HashMap.class);
			String moneyStr = map.get("money").toString();
			int money = (int)Double.parseDouble(moneyStr)/*moneyStr.substring(0, moneyStr.indexOf("."))*/;
			map.put("extension", resultBean.getPayMentId());
			map.put("signType", "md5");
			map.put("money", money);
			map.put("sign", MD5SignatureChecker.createhuaShengSign(map, appkey));
			String resultCreateOrder = HttpUtils.httpGetParamMap(preURL, map, "huaSheng");
			JSONObject jo = JSON.parseObject(resultCreateOrder);
			if(jo.getInteger("state") != 1) {
				return new PreChargeResBean(Constant.ERROR_PLACE_ORDER,"","");
			}else {
				JSONObject jsonObject = jo.getJSONObject("data");
				if(!jsonObject.containsKey("extension") || StringUtils.isEmpty(jsonObject.getString("extension"))) {
					jsonObject.fluentPut("extension", "aa");
				}
				resultBean.setExpandInfo(jsonObject.toJSONString());
				return resultBean;
			}
		}catch (Exception e) {
			LoggerUtil.error(PreChargeService.class, "deal preChargeHuaSheng error, param:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}
	/**
	 * 爱贝预支付
	 * @param form
	 * @return
	 */
	public PreChargeResBean preChargeAibei(PreChargeFormBean form) {
		PreChargeResBean res = preChargeSamsung(form);
		try {
			if (res.getResult() != 1) {
				return res;
			} else {
				//获取签名APPID
				String appId = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_APPID);
				if (appId == null || appId.equals("0")) {
					return new PreChargeResBean(Constant.ERROR_APP_ID,"","");
				}
				//获取签名KEY
				String appKey = CacheUtil.getKey(form.getGameId(),form.getCpId(),  Constant.CONFIG_ENCRYPTION_APPKEY);
				if (appKey == null || appKey.equals("0")) {
					return new PreChargeResBean(Constant.ERROR_APPKEY_CONFIG,"","");
				}
				//爱贝渠道的业务和三星一样，唯一的不同是SDK需要的参数不同，它需要用户支付成功和取消支付的跳转地址
				String successUrl = CacheUtil.getKey(form.getGameId(), form.getCpId(), Constant.SUCCESS_REDIRECT_URL);
				String cancelUrl = CacheUtil.getKey(form.getGameId(), form.getCpId(), Constant.FAIL_REDIRECT_URL);
				if (successUrl == null || successUrl.equals("0") || cancelUrl == null || cancelUrl.equals("0")) {
					return new PreChargeResBean(Constant.ERROR_APP_ID,"","");
				}else {
					//将流水号和两个地址都封装到expandInfo字段里，作为一个Json串
					JsonObject jo = new JsonObject();
					jo.addProperty("transid", res.getExpandInfo());
					jo.addProperty("successUrl", successUrl);
					jo.addProperty("cancelUrl", cancelUrl);
					//sdk请求爱贝的url让我提供，我觉得这块不是很合理，原则上我应该把sign给它就行，url由它自己去拼
					JsonObject joSdk = new JsonObject();
					joSdk.addProperty("tid", res.getExpandInfo());
					joSdk.addProperty("app", appId);
					joSdk.addProperty("successUrl", successUrl);
					joSdk.addProperty("cancelUrl", cancelUrl);
					String data = joSdk.toString();
					String sign = RSA.sign(joSdk.toString(), appKey, "UTF-8");
					String url = "https://web.iapppay.com/pay/gateway?data="+URLEncoder.encode(data, "UTF-8")+"&sign="+URLEncoder.encode(sign, "UTF-8")+"&sign_type=RSA";
					//再将url加入到返回Json数据中
					jo.addProperty("url", url);
					//将客户端需要的Json串统一赋值给expandInfo
					res.setExpandInfo(jo.toString());
				}
				return res;
			}
		}catch (Exception e) {
			LoggerUtil.error(PreChargeService.class, "deal preChargeAibei param error:"+form+",error info:" + e.toString());
			return new PreChargeResBean(Constant.ERROR_SYSTEM,"","");
		}
	}

	/**
	 * 测试状态检验,并设置预支付信息中的测试状态（testState）
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public int checkTestInfo(PreChargeFormBean form, PrePaymentPOJO pojo) throws Exception{
		String testState = CacheUtil.getKey(form.getGameId(), form.getCpId(), Constant.CONFIG_TEST_STATE);
		//判断是测试状态(如果有值并且为测试状态)
		if(testState.equals("true")){
			pojo.setTestState(1);
			try{
				//获取配置的最大充值总额
				double maxMoney = Double.valueOf(CacheUtil.getKey(form.getGameId(), form.getCpId(), Constant.CONFIG_TEST_MAX_MONEY));
				//获取真实的充值总额
				double sumMoney = CacheUtil.getSunMoneyByGameId(form.getGameId(), 1) + form.getChargeMoney();
				//判断是否超过最大充值总额
				if(maxMoney < sumMoney){
					return Constant.ERROR_EXCEED_TEST_MAX_MONEY;
				}else{
					return Constant.SUCCESS;
				}
			}catch (Exception e) {
				LoggerUtil.error(PreChargeService.class, "deal checkTestInfo param error:"+form+","+pojo+",error info:" + e.toString());
				return Constant.ERROR_TEST_MAX_MONEY;
			}
		}else{
			pojo.setTestState(0);
			return Constant.SUCCESS;
		}
	}


}
