package com.linekong.union.charge.consume.service.business;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.linekong.union.charge.consume.web.formbean.*;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import com.lenovo.pay.sign.CpTransSyncSignValid;
import com.linekong.union.charge.consume.rabbitmq.ChargeInvokeRabbitMQ;
import com.linekong.union.charge.consume.rabbitmq.pojo.PushOrderWithMQInfoPOJO;
import com.linekong.union.charge.consume.service.PushInfo;
import com.linekong.union.charge.consume.service.PushInfoWithMQ;
import com.linekong.union.charge.consume.service.invoke.CacheServerDao;
import com.linekong.union.charge.consume.service.invoke.ChargeServerDao;
import com.linekong.union.charge.consume.service.invoke.PreChargeServerDao;
import com.linekong.union.charge.consume.service.invoke.QueryServerDao;
import com.linekong.union.charge.consume.util.*;
import com.linekong.union.charge.consume.util.annotation.support.ValidateService;
import com.linekong.union.charge.consume.util.cache.CacheUtil;
import com.linekong.union.charge.consume.util.kuaiyong.Base64;
import com.linekong.union.charge.consume.util.kuaiyong.RSAEncrypt;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.util.sign.*;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.JianGuoReqBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.AppleResVSevenBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.YiDianCheckOrderResBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.AppleResVSevenBean.Receipt.InApp;
import com.linekong.union.charge.consume.web.jsonbean.resbean.AppleResVSixBean;
import com.linekong.union.charge.consume.web.xmlbean.reqbean.JifengReqDetailBean;
import com.linekong.union.charge.rpc.pojo.AppstoreChargePOJO;
import com.linekong.union.charge.rpc.pojo.ChargingPOJO;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;
import com.linekong.union.charge.rpc.pojo.PushOrderInfoPOJO;

@Service("cPChargeCallBackService")
public class CPChargeCallBackService {
	
	private static Logger log = Logger.getLogger("payInfo");
	
	@Autowired
	private ChargeServerDao chargeServerDao;
	@Autowired
	private QueryServerDao queryServerDao;
	@Autowired
	private PreChargeServerDao preChargeServerDao;
	@Autowired
	private CacheServerDao cacheServerDao;
	@Autowired
	private ChargeInvokeRabbitMQ chargeInvokeRabbitMQ;
	@Autowired
	private ChargeServerDao chargeDao;

	public CacheServerDao getCacheServerDao() {
		return cacheServerDao;
	}

	public void setCacheServerDao(CacheServerDao cacheServerDao) {
		this.cacheServerDao = cacheServerDao;
	}

	public ChargeServerDao getChargeServerDao() {
		return chargeServerDao;
	}

	public void setChargeServerDao(ChargeServerDao chargeServerDao) {
		this.chargeServerDao = chargeServerDao;
	}

	public QueryServerDao getQueryServerDao() {
		return queryServerDao;
	}

	public void setQueryServerDao(QueryServerDao queryServerDao) {
		this.queryServerDao = queryServerDao;
	}

	public PreChargeServerDao getPreChargeServerDao() {
		return preChargeServerDao;
	}

	public void setPreChargeServerDao(PreChargeServerDao preChargeServerDao) {
		this.preChargeServerDao = preChargeServerDao;
	}

	public String getAnZhiDes3Key(String gameName,String cpName) {
		try {
			List<Object> gameIdAndCpId = CacheUtil.getGameId(gameName, cpName);
			if(gameIdAndCpId==null){
				return null;
			}
			int gameId = Integer.parseInt(gameIdAndCpId.get(0).toString());
			int cpId = Integer.parseInt(gameIdAndCpId.get(1).toString());
			String publicKey = CacheUtil.getKey(gameId,cpId, Constant.CONFIG_ENCRYPTION_DES3);
			if (publicKey == null) {
				return null;
			}
			return publicKey;
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackService.class, e.toString());
			return null;
		}
	}
	public String getHaodongAndroidDESKey(String gameName,String cpName){
		try {
			List<Object> gameIdAndCpId = CacheUtil.getGameId(gameName, cpName);
			if(gameIdAndCpId==null){
				return null;
			}
			int gameId = Integer.parseInt(gameIdAndCpId.get(0).toString());
			int cpId = Integer.parseInt(gameIdAndCpId.get(1).toString());
			String publicKey = CacheUtil.getKey(gameId,cpId, Constant.CONFIG_ENCRYPTION_DES);
			if (publicKey == null) {
				return null;
			}
			return publicKey;
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackService.class, e.toString());
			return null;
		}
	}
	
	/**
	 * 快看充值回调处理
	 * @param form
	 * @param remoteIP
	 * @return
	 */
	public Integer kuaikanCharging(KuaikanChargingFormBean form, String remoteIP) {
		try{
			//进行通用检查
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOut_order_id() ,remoteIP);
			//检验渠道
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();
			//验证签名
			String sign = form.getSign();
			String md5Key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (md5Key == null|| md5Key.equals("0") || md5Key.equals("")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.kuaiKanChecker(form, md5Key, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = payment.getChargeMoney();
			if (!Double.valueOf(form.getTrans_money()).equals(price)) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrder_id(), remoteIP , commonCheck.getPushType());
		}catch(Exception e){
			LoggerUtil.error(CPChargeCallBackService.class, "deal kuaikanCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	
	
	/**
	 * play800充值信息回调
	 * @param form
	 * @param trueIP
	 * @return 
	 */
	public Integer playCharging(PlayChargingFormBean form, String remoteIP) {
		try{ 
			//进行通用检查
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCp_order_id() ,remoteIP);
			//检验渠道
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();
			//验证签名
			String sign = form.getSign();
			String play800CpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (play800CpKey == null|| play800CpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.play800Checker(form, play800CpKey, sign)) {
				return Constant.ERROR_SIGN;
				
			}
			// 验证支付金额是否一致
			double price = payment.getChargeMoney();
			if (!Double.valueOf(form.getOrder_money()).equals(price)) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			// 填充订单数据，并推送消息
			return remoteCall(payment, form.getOrder_id(), remoteIP , commonCheck.getPushType());
		}catch(Exception e){
			LoggerUtil.error(CPChargeCallBackService.class, "deal playCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	
	/**
	 * 360回调校验
	 * @param form
	 * @param trueIP
	 * @return
	 */
	public int qihuCharging(QihuChargingFormBean form, String remoteIP) {
		try{
			if(!"success".equalsIgnoreCase(form.getGateway_flag())){
				return Constant.ERROR_ORDER;
			}
			//进行通用检查
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getApp_order_id() ,remoteIP);
			//检验渠道
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();
			//判断user_id和app_uid和游戏方下单时记录的值是否相等
			if(!form.getUser_id().equals(payment.getUserName()) ){
				return Constant.ERROR_UID;
			}
			//获取APPKEY
			String appkey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(), Constant.CONFIG_ENCRYPTION_APPKEY).trim();
			if(appkey == null || "".equals(appkey)){
				return Constant.ERROR_APPKEY_CONFIG;
			}
			//校验APPKEY
			if(!appkey.equals(form.getApp_key())){
				return Constant.ERROR_CALLBACK_APPKEY;
			}
			//校验角色ID
			if(!form.getApp_uid().equals(payment.getRoleId()+"")){
				return Constant.ERROR_ROLE;
			}
			//检验商品Id
			if(!form.getProduct_id().equals(payment.productId)){
				return Constant.ERROR_PRODUCTID;
			}
			//校验金额
			if(Integer.parseInt(form.getAmount())==0 || Double.parseDouble(form.getAmount()) != payment.getChargeMoney()*100){
				return Constant.ERROR_CHARGE_MONEY;
			}
			if(form.getApp_ext1()==null){
				return Constant.ERROR_appext1_CONFIG;
			}
			//透传参数校验
			if(!form.getApp_ext1().equals(payment.getAttachCode())){
				return Constant.ERROR_ExtInfo;
			}
			//验签
			String key = CacheUtil.getKey(payment.getGameId(), payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if(key==null || "".equals(key)){
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.qihuChecker(form, key, form.getSign())) {
				return Constant.ERROR_SIGN;
			}
			// 填充订单数据，并推送消息
			return remoteCall(payment, form.getOrder_id(), remoteIP , commonCheck.getPushType());
		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(CPChargeCallBackService.class, "deal qihuCharging param error:"+form+",error info:" + e.toString());
		    return Constant.ERROR_SYSTEM;
		}
		
	}
	/**
	 * 努比亚回调校验
	 * @param form
	 * @param trueIP
	 * @return
	 */
	public int nubiaCharging(NubiaFormBean form, String remoteIP) {
		try{
			//验证订单时候成功
			if(!"1".equalsIgnoreCase(form.getPay_success())){
				return Constant.ERROR_ORDER;
			}
			//进行通用检查
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOrder_no() ,remoteIP);
			//检验渠道
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();
			
			//验证签名
			String sign = form.getOrder_sign();
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if ("0".equals(key)) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.nubiaChecker(form,key, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() );
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrder_serial(), remoteIP, commonCheck.getPushType());

		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(CPChargeCallBackService.class, "deal nubiaCharging param error:"+form+",error info:"+e.toString());
		    return Constant.ERROR_SYSTEM;
		}
		
	}
	/**
	 * oppo 充值信息回调
	 * 
	 * @param OppoChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer oppoCharging(OppoChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getPartnerOrder() ,remoteIp);
			//检验渠道
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			// 验证签名是否正确（查询数据库存储的value）
			String publicKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_RSA);

			if (publicKey == null || publicKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!RSASignatureChecker.oppoCheck(form, form.getSign(), publicKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			int price = (int) (payment.getChargeMoney() * 100);
			if (form.getPrice() != price) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			// 填充支付订单需要的数据
			return remoteCall(payment, form.getNotifyId(), remoteIp , commonCheck.getPushType());
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal oppoCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * vivo 充值信息回调
	 * 
	 * @param VivoChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer vivoCharging(VivoChargingFormBean form, String remoteIp) {
		try {
			//订单状态
			if(!"0000".equals(form.getTradeStatus())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCpOrderNumber() ,remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSignature();
			String vivoCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_MD5);
			if (vivoCpKey == null || vivoCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//加密值验证
			if (!MD5SignatureChecker.vivoChecker(form, vivoCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() * 100);
			if (Long.parseLong(form.getOrderAmount()) != price) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderNumber(), remoteIp , commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal vivoCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 当乐 充值信息回调
	 * 
	 * @param DangLeChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer dangleCharging(DangLeChargingFormBean form, String remoteIp) {

		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getExt(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSignature();
			String dangleCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (dangleCpKey == null || dangleCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.dangleChecker(form, sign, dangleCpKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != Double.valueOf(form.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrder(), remoteIp , commonCheck.getPushType());

		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal dangleCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 华为 充值信息回调
	 * 
	 * @param HuaWeiChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 *            0 表示成功，1: 验签失败,2:超时,3: 业务信息错误，比如订单不存在, 94: 系统错误, 95: IO 错误,96:
	 *            错误的 url,97: 错误的响应, 98: 参数错误,99: 其他错误
	 * 
	 * @return Integer
	 */
	public Integer huaweiCharging(HuaWeiChargingFormBean form, String remoteIp, String postContent) {
		try {
			//验证订单状态
			if(!"0".equals(form.getResult())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getRequestId(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String huaweiCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_RSA);
			if (huaweiCpKey == null || huaweiCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if(form.getSignType() != null && "RSA256".equalsIgnoreCase(form.getSignType())) {
				if (!RSASignatureChecker.huaweiChecker(postContent, huaweiCpKey, sign, "RSA256")) {
					return Constant.ERROR_SIGN;
				}
			}else {
				if (!RSASignatureChecker.huaweiChecker(postContent, huaweiCpKey, sign, "SHA1")) {
					return Constant.ERROR_SIGN;
				}
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != (long) (Float.parseFloat(form.getAmount()))) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderId(), remoteIp, commonCheck.getPushType()) >=  Constant.SUCCESS ? Constant.SUCCESS : Constant.ERROR_SYSTEM;
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal huaweiCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}


	/**
	 * UC充值信息回调
	 * 
	 * @param UCChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer ucCharging(UCChargingFormBean form, String remoteIp) {

		try {
			//订单转态查询
			if(!"S".equals(form.getOrderStatus())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCpOrderId(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String ucCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (ucCpKey == null || ucCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.ucChecker(form, ucCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != (long) (Float.parseFloat(form.getAmount()))) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderId(), remoteIp , commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal ucCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 益玩 充值信息回调
	 * 
	 * @param YiwanCharginFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 * 
	 * 
	 *         1 成功 100 签名错误 101 金额错误 102 OpenId错误 103 其他未知错误
	 */
	public Integer yiwanCharging(YiwanCharginFormBean form, String remoteIp) {
		try {
			//判断订单状态
			if(!"1".equals(form.getStatus())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCustominfo(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String yiwanCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (yiwanCpKey == null | yiwanCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.yiwanChecker(form, yiwanCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() * 100);
			if (price != (long) (form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrdernum(), remoteIp, commonCheck.getPushType()) == Constant.SUCCESS ? Constant.SUCCESS : Constant.ERROR_SYSTEM;
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal yiwanCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 百度充值信息回调
	 * 
	 * @param BaiduChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return BaiduResBean bean
	 * 
	 *         1 业务正确 2 AppID无效 3 Sign无效 4 参数错误
	 * 
	 */
	public ResultBean baiduCharging(BaiduChargingFormBean form, String content, String remoteIp) {
		
		String baiduCpKey = null;
		try {
			//验证secretkey获取
			List<Object> gameIdAndCpId = CacheUtil.getGameId(form.getGameName(),form.getCpName());
			if(gameIdAndCpId==null){
				return null;
			}
			int gameId = Integer.parseInt(gameIdAndCpId.get(0).toString());
			int cpId = Integer.parseInt(gameIdAndCpId.get(1).toString());
			baiduCpKey = CacheUtil.getKey(gameId,cpId, Constant.CONFIG_ENCRYPTION_MD5);
			if (baiduCpKey == null || baiduCpKey.equals("0") ||  baiduCpKey.equals("")) {
				return new ResultBean(Constant.ERROR_CHARGE_SIGN,null);
			}
			//验证签名
			String sign = form.getSign();
			if (!MD5SignatureChecker.baiduChecker(form, content, baiduCpKey, sign)) {
				return new ResultBean(Constant.ERROR_SIGN,baiduCpKey);
			}
			//验证订单状态
			if(1 != form.getOrderStatus()){
				return new ResultBean(Constant.ERROR_FAIL_ORDER,baiduCpKey);
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCooperatorOrderSerial(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return new ResultBean(commonResult,baiduCpKey);
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证是否与预支付信息一致,百度要求，必须要验证
			if(!(form.getUID()+"").equals(payment.getUserName())){
				return new ResultBean(Constant.ERROR_UID,baiduCpKey);
			}
			if(!form.getExtInfo().equals(payment.getAttachCode())){
				return new ResultBean(Constant.ERROR_ExtInfo,baiduCpKey);
			}
			//此三种情况线上可能都有包
			/*if(!form.getMerchandiseName().equals(payment.getChargeAmount()+""+payment.getProductName())
					&& !form.getMerchandiseName().equals(payment.getChargeAmount()/10+""+payment.getProductName())
					&& !form.getMerchandiseName().equals(payment.getProductName())){
				return new ResultBean(Constant.ERROR_MerchandiseName,baiduCpKey);
			}*/
			
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() );
			if (price !=  (form.getOrderMoney())) {
				return new ResultBean(Constant.ERROR_CHARGE_MONEY,baiduCpKey);
			}

			if (remoteCall(payment, form.getOrderSerial(), remoteIp, commonCheck.getPushType()) >= Constant.SUCCESS) {
				return new ResultBean(Constant.SUCCESS,baiduCpKey);
			} else {
				return new ResultBean(Constant.ERROR_SYSTEM,baiduCpKey);
			}

		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal baiduCharging param error:"+form+",error info:" + t.toString());
			return new ResultBean(Constant.ERROR_SYSTEM,baiduCpKey);
		}
	}
	/**
	 * 果盘安卓充值信息回调
	 * 
	 * @param GuoPanChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer guopanCharging(GuoPanChargingFormBean form, String remoteIp) {
		try {
			//订单状态验证
			if(!"1".equals(form.getStatus())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getSerialNumber(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String guopanCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (guopanCpKey == null | guopanCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.guopanChecker(form, guopanCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != (long) (Float.parseFloat(form.getMoney()))){
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getTrade_no(), remoteIp , commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal guopanCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 魅族充值信息回调
	 * 
	 * @param MeiZuChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 * 
	 *         200 成功发货 120013 尚未发货 120014 发货失败 900000 未知异常
	 */
	public Integer meizuCharging(MeiZuChargingFormBean form, String remoteIp) {
		try {
			//判断订单转态
			if(!"3".equals(form.getTrade_status())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCp_order_id(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String meizuCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (meizuCpKey == null|| meizuCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.meizuChecker(form, meizuCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			Long price = (long) (payment.getChargeMoney());
			//Long count = (long) (payment.getChargeAmount());
			//if (price != (long) (Double.parseDouble(form.getTotal_price())) || count != (long) (Double.parseDouble(form.getBuy_amount()))) {
			if (price != (long) (Double.parseDouble(form.getTotal_price()))) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrder_id(), remoteIp, commonCheck.getPushType()) >=  Constant.SUCCESS ? Constant.SUCCESS : 120014;
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal meizuCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * TT游戏充值信息回调
	 * 
	 * @param TTChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 * 
	 * 
	 */
	public Integer ttCharging(TTChargingFormBean form, String remoteIp, String json) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCpOrderId().replaceFirst("cp_order_", "") ,remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String ttCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (ttCpKey == null || ttCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.ttChecker(json, ttCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = payment.getChargeMoney();
			if (price != Double.parseDouble(form.getPayFee())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getSdkOrderId(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal ttCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * PPS充值信息回调
	 * 
	 * @param PPSChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 * 
	 * 
	 *         0：success -1：Sign error -2：Parameters error -3：User not exists
	 *         -4：Order repeat -5：No server -6：Other errors
	 * 
	 * 
	 */
	public Integer ppsCharging(PPSChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getUserData() ,remoteIp);		
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String ppsCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (ppsCpKey == null || ppsCpKey.equals("0") ) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.ppsChecker(form, ppsCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != (long) (Float.parseFloat(form.getMoney()))) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrder_id(), remoteIp, commonCheck.getPushType()) >= Constant.SUCCESS ? Constant.SUCCESS : Constant.ERROR_SYSTEM;
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal ppsCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 金立充值信息回调
	 * 
	 * @param JinLiChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 * 
	 * 
	 */
	public Integer jinliCharging(JinLiChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOut_order_no(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String jinliCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_RSA);
			if (jinliCpKey == null || jinliCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!RSASignatureChecker.jinlinChecker(form, jinliCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = payment.getChargeMoney();
			if (price != Double.valueOf(form.getDeal_price())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOut_order_no(), remoteIp, commonCheck.getPushType()) >= Constant.SUCCESS ? Constant.SUCCESS : Constant.ERROR_SYSTEM;
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal jinliCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * PPTV充值信息回调
	 * 
	 * @param PPTVChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 * 
	 *         ------resultCode------- 1 支付成功 2 支付失败 3 订单已存在 4 验证失败 5 用户不存在 6
	 *         充值金额错误 ------resultCode------
	 * 
	 */
	public Integer pptvCharging(PPTVChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getExtra() ,remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String pptvCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (pptvCpKey == null || pptvCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.pptvChecker(form, pptvCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != (long) (Float.parseFloat(form.getAmount()))) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOid(), remoteIp, commonCheck.getPushType()) >= Constant.SUCCESS ? Constant.SUCCESS : Constant.ERROR_SYSTEM;
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal pptvCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 酷派充值信息回调
	 * 
	 * @param KuPaiChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer kupaiCharging(KuPaiChargingNewFormBean form, String remoteIp, String json) {
		try {
			//订单转态
			if(!"0".equals(form.getResult())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCpprivate(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String kupaiCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5WITHRSA);
			if (kupaiCpKey == null || kupaiCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5WithRSAChecker.kupaiChecker(json, kupaiCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = payment.getChargeMoney()*100;
			if (price != Double.valueOf(form.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getTransid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal kupaiCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 今日头条充值信息回调
	 * 
	 * @param JRTTChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer jrttCharging(JRTTChargingFormBean form, String remoteIp) {
		try {
			//验证订单状态(0：交易成功，可以退款   3：交易成功，并且不可退款)
			if((!"0".equals(form.getTrade_status()))&&(!"3".equals(form.getTrade_status()))){
				return Constant.ERROR_FAIL_ORDER; 
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOut_trade_no(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getTt_sign();
			String jrttCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_RSA);
			if (jrttCpKey == null || jrttCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!RSASignatureChecker.JRTTCheckcer(form, jrttCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() * 100);
			if (price != (long) (form.getTotal_fee())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getTrade_no(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal jrttCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 联想充值信息回调
	 * 
	 * @param LenovoCharginFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer levonoCharging(LenovoCharginFormBean form, String remoteIp, String json) {
		try {
			//判断订单转态
			if(!"0".equals(form.getResult())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getExorderno(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String lenovoCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_RSA);
			if (lenovoCpKey == null || lenovoCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!CpTransSyncSignValid.validSign(json,sign,  lenovoCpKey )) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() * 100);
			if (price != Long.valueOf(form.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getTransid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal levonoCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 啪啪游戏厅充值信息回调
	 * 
	 * @param PaPaCharginFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer papaCharging(PaPaCharginFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getApp_order_id(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String papaCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (papaCpKey == null || papaCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.papaChecker(form, papaCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != (double) (Double.valueOf(form.getMoney_amount()))) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getPa_open_order_id(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal papaCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 安智充值信息回调
	 * 
	 * @param AnzhiChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer anzhiCharging(AnzhiChargingFormBean form, String remoteIp) {
		try {
			//订单状态检查
			if(1 != form.getCode()){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCpInfo(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney())*100;
			if (price != (long) (Integer.parseInt(form.getOrderAmount()))) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderId(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal anzhiCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 斗鱼充值信息回调
	 * 
	 * @param DouyuChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer douyuCharging(DouyuChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCallbackInfo(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String douyuCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (douyuCpKey == null | douyuCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.douyuChecker(form, douyuCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != (long) (form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getOrderId()), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal douyuCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 4399充值信息回调
	 * 
	 * @param FTNNChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return FTNNResBean bean
	 */
	public Integer ftnnCharging(FTNNChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getMark(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String ftnnCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (ftnnCpKey == null || ftnnCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.ftnnChecker(form, ftnnCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			long count = (long) (payment.getChargeAmount());
			if (price != (long) (form.getMoney()) || count != (long) (form.getGamemoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal ftnnCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 乐视Video充值信息回调
	 * 
	 * @param LeVideoChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer leVideoCharging(LeVideoChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOut_trade_no(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String leVideoCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (leVideoCpKey  == null || leVideoCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.leVideoChecker(form, leVideoCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != (long) (Float.parseFloat(form.getPrice()))) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getLepay_order_no(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal leVideoCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 朋友玩充值信息回调
	 * 
	 * @param PywChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer pywCharging(PywChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCp_orderid(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String pywCpKey  = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_MD5);
			if (pywCpKey  == null || pywCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.pengyouwanChecker(form, pywCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = payment.getChargeMoney();
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getCh_orderid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal pywCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 朋友玩变态版充值信息回调
	 * 
	 * @param PywBTChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer pywBTCharging(PywBTChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCp_orderid(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String pywBTCpKey  = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (pywBTCpKey  == null || pywBTCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.pengyouwanBTChecker(form, pywBTCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = payment.getChargeMoney();
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getCh_orderid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal pywBTCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 小米充值信息回调
	 * 
	 * @param XiaoMiChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 * 
	 *         --- errcode --- 1506 cpOrderId错误 1515 appId错误 1516 uid错误 1525
	 *         signature错误 --- errMsg --- 错误信息 （optional） errcode具体代表含义详见文档
	 */
	public Integer xiaomiCharging(XiaoMiChargingFormBean form, String remoteIp) {
		try {
			//订单转态
			if(!"TRADE_SUCCESS".equals(form.getOrderStatus())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCpOrderId(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSignature();
			String xiaomiCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_HMACSHA1);
			if (xiaomiCpKey == null || xiaomiCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!HmacSHA1SignatureChecker.xiaomiChecker(form, xiaomiCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() * 100);
			if (price != (Long.parseLong(form.getPayFee()))) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderId(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal xiaomiCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 拇指玩充值信息回调
	 * 
	 * @param MuzhiwanChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer muzhiwanCharging(MuzhiwanChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getExtern() ,remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String muzhiwanCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (muzhiwanCpKey == null | muzhiwanCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.muzhiwanChecker(form, muzhiwanCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != Long.parseLong(form.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderID(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal muzhiwanCharging param error:"+form+",error info:" + t.toString());
			return -1;
		}
	}

	/**
	 * 豌豆荚充值信息回调
	 * 
	 * @param WandoujiaChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer wandoujiaCharging(WandoujiaChargingFormBean form, String remoteIp, String jsonContent) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOut_trade_no(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String wandoujiaCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_RSA);
			if (wandoujiaCpKey == null || wandoujiaCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!RSASignatureChecker.wandoujiaChecker(jsonContent, wandoujiaCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() * 100);
			if (price != Long.parseLong(form.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderId(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal wandoujiaCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 新浪充值信息回调
	 * 
	 * @param SinaChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer sinaCharging(SinaChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getPt(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSignature();
			String sinaCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_SHA1);
			if (sinaCpKey == null || sinaCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!SHA1SignatureChecker.sinaChecker(form, sinaCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() * 100);
			if (price != Long.parseLong(form.getActual_amount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getOrder_id()), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal sinaCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 优酷充值信息回调
	 * 
	 * @param YoukuChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * @param String
	 *            callbackURL
	 * 
	 * @return YouKuResBean
	 */
	public Integer youkuCharging(YoukuChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getApporderID(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String youkuCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_HMACMD5);
			if (youkuCpKey == null || youkuCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//获取参与签名的回调地址
			String callbackURL = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_CALL_BACK_URL);
			if (callbackURL == null || callbackURL.equals("0")) {
				return Constant.ERROR_CALL_BACK_URL;
			}
			if (!HmacMD5SignatureChecker.youkuChecker(form, callbackURL, youkuCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() * 100);
			if (price != Long.parseLong(form.getPrice())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getApporderID(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal youkuCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 应用汇充值信息回调
	 * 
	 * @param YYHChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer yingyonghuiCharging(YYHChargingFormBean form, String remoteIp, String transData) {
		try {
			//验证订单状态
			if(0 != form.getResult()){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCporderid(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String yingyonghuiCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_MD5WITHRSA);
			if ( yingyonghuiCpKey == null || yingyonghuiCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5WithRSAChecker.yingyonghuiChecker(transData, yingyonghuiCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = payment.getChargeMoney();
			if (price != form.getMoney()) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getTransid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal yingyonghuiCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 乐视手机充值信息回调
	 * 
	 * @param LePhoneChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer lePhoneCharging(LePhoneChargingFormBean form, String remoteIp) {
		try {
			if(!"TRADE_SUCCESS".equals(form.getTrade_result())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCooperator_order_no(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String lephoneCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_MD5);
			if (lephoneCpKey == null || lephoneCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.lePhoneChecker(form, lephoneCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (payment.getChargeMoney());
			if (price != Double.valueOf(form.getPrice())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getLepay_order_no(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal lePhoneCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 乐视TV充值信息回调
	 * 
	 * @param LePhoneChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer leTvCharging(LeTvChargingFormBean form, String remoteIp,String productStr) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getParams(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String leTvCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if ( leTvCpKey == null || leTvCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//获取回调路径
			String requestURL = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_CALL_BACK_URL);
			if ( requestURL == null || requestURL.equals("0")) {
				return Constant.ERROR_CALL_BACK_URL;
			}
			if (!MD5SignatureChecker.leTvChecker(form,requestURL,productStr,leTvCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != Long.parseLong(form.getPrice())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getPxNumber(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal leTvCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	
	
	/**
	 * 搜狗充值信息回调
	 * 
	 * @param SouGouChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer souGouCharging(SouGouChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getAppdata(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getAuth();
			String souGouCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_MD5);
			if (souGouCpKey == null || souGouCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.souGouChecker(form,souGouCpKey,sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != Long.parseLong(form.getRealAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal souGouCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 机锋
	 * @param params
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer jifengCharging(String params, JifengChargingFormBean form, String remoteIp){
		try {
			//验签
			String sign = form.getSign();
			String jifengCpKey = null;
			if ("0".equals(jifengCpKey = CacheUtil.getKeyByCpCode(form.getCpName(), Constant.CONFIG_ENCRYPTION_MD5))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.jifengChecker(form, jifengCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			//验签成功，解析订单详细信息
			Document document = DocumentHelper.parseText(params);
			Element root = document.getRootElement();
			JifengReqDetailBean jifeng = new JifengReqDetailBean(root.element("order_id").getText(),
													 root.element("appkey").getText(),
													 root.element("cost").getText(),
													 root.element("create_time").getText());
			ValidateService.valid(jifeng);
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), jifeng.getOrder_id(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() );
			if (price != Long.parseLong(jifeng.getCost())/10) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, jifeng.getOrder_id(), remoteIp, commonCheck.getPushType());
		} catch(Exception e){
			LoggerUtil.error(CPChargeCallBackService.class,"deal jifengCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_PARAM_VALIDATE;
		}
	}
	
	/**
	 * 8868
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer juGameCharging(JuGameChargingFormBean form, String remoteIp){
		
		try {
			//验证订单状态
			if(!"S".equals(form.getData().getOrderStatus()) ){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getData().getCallbackInfo(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String juGameCpKey = null;
			String juGameCpID = null;
			if ("0".equals(juGameCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if ("0".equals(juGameCpID = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_CPID))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.juGameChecker(form.getData(),juGameCpID, juGameCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() );
			if (price != Double.parseDouble(form.getData().getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getData().getOrderId(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal juGameCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 乐游
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer leyouCharging(LeyouChargingFormBean form, String remoteIp){
		
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getAttach(), remoteIp);
				int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String leyouCpKey = null;
			if ("0".equals(leyouCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.leyouChecker(form, leyouCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() );
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal leyouCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 猎宝
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer liebaoCharging(LiebaoChargingFormBean form, String remoteIp){
		
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getAttach(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String liebaoCpKey = null;
			if ("0".equals(liebaoCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.liebaoChecker(form,liebaoCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() );
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal liebaoCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 迅雷
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer xunleiCharging(XunleiChargingFormBean form, String remoteIp){
		
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getExt(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String xunleiCpKey = null;
			if ("0".equals(xunleiCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_MD5))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.xunleiChecker(form,xunleiCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() );
			long count = payment.getChargeAmount();
			if (price != Double.parseDouble(form.getMoney()) || count != Double.parseDouble(form.getGold())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal xunleiCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 虫虫
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer ccCharging(CCChargingFormBean form, String remoteIp){
		
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getPartnerTransactionNo(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String ccCpKey = null;
			if ("0".equals(ccCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.ccChecker(form,ccCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() );
			if (price != Double.parseDouble(form.getOrderPrice()) ) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getTransactionNo(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal ccCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * icc
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer iccCharging(ICCChargingFormBean form, String remoteIp){
		
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOrderidClient(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证签名
			String sign = form.getSign();
			String iccCpKey = null;
			if ("0".equals(iccCpKey = CacheUtil.getKey(payment.getGameId(), payment.getCpId(),Constant.CONFIG_ENCRYPTION_RSA))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!RSASignatureChecker.iccChecker(form,iccCpKey, sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() );
			if (price != Double.parseDouble(form.getChargeMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderidClient(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal iccCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 快用
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer kyCharging(KYChargingFormBean form, String remoteIp){
		RSAEncrypt rsaEncrypt = new RSAEncrypt();
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getDealseq(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//解密
			String kyCpKey = null;
			if ("0".equals(kyCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_RSA))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			rsaEncrypt.loadPublicKey(kyCpKey);
			String data = new String(rsaEncrypt.decrypt(rsaEncrypt.getPublicKey(), Base64.decode(form.getNotify_data())));
			String[] array = data.split("&");
			for(String str : array ){
				String[] bodyArray = str.split("=",2);
				switch(bodyArray[0]){
				case "dealseq":
					if(!form.getDealseq().equals(bodyArray[1])){
						return Constant.ERROR_PARAM_VALIDATE;
					}
					break;
				case "fee":
					form.setFee(bodyArray[1]);
					break;
				case "payresult":
					form.setPayresult(bodyArray[1]);
					break;
				}
			}

			ValidateService.valid(form);
			//判断订单是否为成功订单
			if(!"0".equals(form.getPayresult())){
				return Constant.ERROR_FAIL_ORDER; 
			}
			//验签
			String sign = form.getSign();
			
			if (!RSASignatureChecker.kyCheck(form, sign,kyCpKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getFee())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal kyCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 内涵段子
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer neihanCharging(NeihanChargingFormBean form, String remoteIp){
		
		try {
			//验证订单状态(0：交易成功，可以退款   3：交易成功，并且不可退款)
			if((!"0".equals(form.getTrade_status()))&&(!"3".equals(form.getTrade_status()))){
				return Constant.ERROR_FAIL_ORDER; 
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOut_trade_no(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			String sign = form.getTt_sign();
			String neihanCpKey = null;
			if ("0".equals(neihanCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_RSA))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!RSASignatureChecker.neihanCheck(form, sign, neihanCpKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() * 100);
			if (price != Double.parseDouble(form.getTotal_fee())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getTrade_no(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal neihanCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 三星
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer samsungCharging(SamsungChargingFormBean form, SamsungChargingFormBean.OrderInfo orderInfoBean, String remoteIp){
		
		try {
			//验证订单状态
			if(!"0".equals(orderInfoBean.getResult())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), orderInfoBean.getCporderid(), remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			String sign = form.getSign();
			String samsungCpKey = null;
			if ("0".equals(samsungCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5WITHRSA))) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5WithRSAChecker.samsungCheck(form, sign, samsungCpKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != Double.parseDouble(orderInfoBean.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, orderInfoBean.getTransid(), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal samsungCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 靠谱
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public ResultBean kaopuCharging(KaopuChargingFormBean form, String remoteIp){
		String kaopuCpKey = "";
		try {
			List<Object> gameIdAndCpId = CacheUtil.getGameId(form.getGamename(),form.getCpName());
			if(gameIdAndCpId==null){
				return null;
			}
			int gameId = Integer.parseInt(gameIdAndCpId.get(0).toString());
			int cpId = Integer.parseInt(gameIdAndCpId.get(1).toString());
			//Integer gameId = CacheUtil.getGameId(form.getGameName(), form.getCpName()); 
			String sign = form.getSign();
			kaopuCpKey = CacheUtil.getKey(gameId,cpId, Constant.CONFIG_ENCRYPTION_MD5);
			if ("0".equals(kaopuCpKey)) {
				return new ResultBean(Constant.ERROR_CHARGE_SIGN, null);
			}
			if (!MD5SignatureChecker.kaopuChecker(form,kaopuCpKey, sign)) {
				return new ResultBean(Constant.ERROR_SIGN, kaopuCpKey);
			}
			//验证订单状态
			if(!"1".equals(form.getStatus())){
				return new ResultBean(Constant.ERROR_FAIL_ORDER, kaopuCpKey);
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getYwordernum() ,remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return new ResultBean(commonResult, kaopuCpKey);
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney() *100);
			if (price != Double.parseDouble(form.getAmount())) {
				return new ResultBean(Constant.ERROR_CHARGE_MONEY, kaopuCpKey);
			}
			return new ResultBean(remoteCall(payment, form.getKpordernum(), remoteIp, commonCheck.getPushType()), kaopuCpKey);
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal kaopuCharging param error:"+form+",error info:" + t.toString());
			return new ResultBean(Constant.ERROR_SYSTEM, kaopuCpKey);
		}
	}
	/**
	 * 37充值信息回调
	 * 
	 * @param TSChargingFormBean
	 *            form
	 * @param String
	 *            remoteIp
	 * 
	 * @return Integer
	 */
	public Integer stCharging(TSChargingFormBean form, String remoteIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getObject(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			String sign = form.getHash();
			String stCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (stCpKey == null || stCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.stChecker(form,stCpKey,sign)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			long price = (long) (payment.getChargeMoney());
			if (price != Long.parseLong(form.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getChange_id()), remoteIp, commonCheck.getPushType());
		} catch (Exception t) {
			LoggerUtil.error(CPChargeCallBackService.class, "deal stCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 坚果充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer jianGuoCharging(JianGuoReqBean form, String remoteIp){
		try{
			//验证订单状态
			if(!"2".equals(form.getOrder_status())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getAttach(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			String jianGuoCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (jianGuoCpKey == null || jianGuoCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.jianGuoChecker(form,jianGuoCpKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getOrder_id()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal jianGuoCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 57K充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer fiveSevenKCharging(FiveSevenKChargingFormBean form, String remoteIp){
		try{
			//验证订单状态
			if(!"2".equals(form.getOrder_status())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCp_order_id(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			String fiveSevenKCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (fiveSevenKCpKey == null || fiveSevenKCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.fiveSevenChecker(form,fiveSevenKCpKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getProduct_price())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getOrder_id()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal fiveSevenKCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 泡椒IOS充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer paojiaoIosCharging(PaoJiaoIOSFormBean form, String remoteIp){
		try{
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCpOrderNo(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			String paojiaoIosKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (paojiaoIosKey == null || paojiaoIosKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.paojiaoIosChecker(form, paojiaoIosKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getPrice())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getOrderNo()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal paojiaoIosCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 泡椒Android充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer paojiaoAndroidCharging(PaoJiaoAndroidFormBean form, String remoteIp){
		try{
			//验证订单状态
			if(!"5".equals(form.getStatus())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getExt(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			String paojiaoAndroidKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (paojiaoAndroidKey == null || paojiaoAndroidKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.paojiaoAndroidChecker(form, paojiaoAndroidKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getPrice())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			//查询沙盒网关，如果匹配了，则直接返回沙盒测试错误码
			return remoteCall(payment, String.valueOf(form.getOrderNo()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal paojiaoAndroidCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 浩动IOS充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer haodongIosCharging(HaodongIOSFormBean form,String remoteIp){
		try{
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCode(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			String haodongIosKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (haodongIosKey == null || haodongIosKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.haodongIosChecker(form, haodongIosKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getOrderno()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal haodongIosCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 浩动Android-青冥双剑-充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer haodongAndroidQMSJCharging(HaodongAndroidQMSJFormBean form,String remoteIp){
		try{
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCode(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			String haodongAndroidQMSJKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (haodongAndroidQMSJKey == null || haodongAndroidQMSJKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.haodongAndroidQMSJChecker(form, haodongAndroidQMSJKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getOrderno()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal haodongAndroidQMSJCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 浩动Android-蜀山剑雨-充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer haodongAndroidSSJYCharging(HaodongAndroidSSJYFormBean form,String remoteIp){
		try{
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCode(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			String haodongAndroidSSJYKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (haodongAndroidSSJYKey == null || haodongAndroidSSJYKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.haodongAndroidSSJYChecker(form, haodongAndroidSSJYKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getOrderno()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal haodongAndroidSSJYCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 浩动Android充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer haodongAndroidCharging(HaodongAndroidFormBean form,String remoteIp){
		try{
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCodeNo(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			if (!MD5SignatureChecker.haodongAndroidChecker(form)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getTradeNo()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal haodongAndroidCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * eagleHaodong充值信息回调
	 * @param form
	 * @param remoteIP
	 * @return
	 */
	public int eagleHaodongCharging(EagleHaodongFormBean form, String remoteIP) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean check = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getExtension(), remoteIP);
			int commonCheck = commonCheck(check);
			if(commonCheck != 1) {
				return commonCheck;
			}
			PrePaymentPOJO payment = check.getPayment();
			//判断金额
			if(payment.getChargeMoney()*100 != Double.parseDouble(form.getMoney())){
				return Constant.ERROR_CHARGE_MONEY;
			}
			//查询MD5值
			String md5key = CacheUtil.getKey(payment.getGameId(), payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (md5key == null || md5key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验签
			if(!MD5SignatureChecker.eagleHaodongChecker(form, md5key)){
				return Constant.ERROR_SIGN;
			}
			return remoteCall(payment, String.valueOf(form.getOrderID()), remoteIP, check.getPushType());
		}catch(Exception e){
			e.printStackTrace();
			LoggerUtil.error(CPChargeCallBackService.class, "deal eagleHaodongCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * CGame充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer cgameCharging(CGameFormBean form,String remoteIp){
		try{
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOutorderid(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			String cgameCpKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (cgameCpKey == null || cgameCpKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			if (!MD5SignatureChecker.cgameChecker(form,cgameCpKey)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney())*100;
			if (price != Double.parseDouble(form.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getOrderid()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal cgameCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	
	/**
	 * 游戏猫充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer youximaoCharging(YouximaoChargingFormBean form,String remoteIp){
		try{
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCodeNo(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			if (!MD5SignatureChecker.youximaoChecker(form)) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = payment.getChargeMoney();
			if (price != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getTradeNo()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal youximaoCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}	
	
	/**
	 * 乱码导致补单
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer qingyuanIOSRepairCharging(QingYuanIOSChargingFormBean form, String remoteIp){
		try{
			//验证订单状态
			if(!"5".equals(form.getStatus())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOrderid(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
//			//验签
//			String qingyuanIOSKey = CacheUtil.getKey(payment.getGameId(), Constant.CONFIG_ENCRYPTION_RSA);
//			if (qingyuanIOSKey == null || qingyuanIOSKey.equals("0")) {
//				return Constant.ERROR_CHARGE_SIGN;
//			}
//			//form+qingyuanIOSKey=?sign
//			if (!RSASignatureChecker.qingyuanIOSKeyChecker(form, qingyuanIOSKey, form.getSign())) {
//				return Constant.ERROR_SIGN;
//			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getPrice())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getTransid()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal qingyuanIOSRepairCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	
	/**
	 * 清源游戏IOS充值信息回调
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer qingyuanIOSCharging(QingYuanIOSChargingFormBean form, String remoteIp){
		try{
			//验证订单状态
			if(!"5".equals(form.getStatus())){
				return Constant.ERROR_FAIL_ORDER;
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOrderid(),remoteIp);
			int commonResult = commonCheck(commonCheck);
			if(commonResult != Constant.SUCCESS){
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验签
			String qingyuanIOSKey = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_RSA);
			if (qingyuanIOSKey == null || qingyuanIOSKey.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//form+qingyuanIOSKey=?sign
			if (!RSASignatureChecker.qingyuanIOSKeyChecker(form, qingyuanIOSKey, form.getSign())) {
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			double price = (double) (payment.getChargeMoney());
			if (price != Double.parseDouble(form.getPrice())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, String.valueOf(form.getTransid()), remoteIp, commonCheck.getPushType());
		}catch(Exception t){
			LoggerUtil.error(CPChargeCallBackService.class, "deal qingyuanIOSCharging param error:"+form+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	
	/**
	 * apple充值信息回调
	 * 由于 apple预支付 然后 apple回调的，模式下，SDK那边处理订单号，与苹果凭证的匹配，会有回调时匹配不上或者丢掉预支付订单号的情况，SDK对于这种情况，无法处理，
	 * 所以，服务器，不以SDK的预支付订单号和产品信息为准，而是，通过凭证的信息以及基础信息到预支表中匹配对应预支付。
		注：
		SDK是将预支付成功后的预支付订单缓存到游戏缓存中，如果用户关掉游戏，预支付订单缓存即会消失，并且，如果每次点击充值，则会把缓存中的信息更新，
		故，当用户充值成功后，如果苹果的凭证延迟给过来，此过程中，用户关掉过游戏，则预支付订单则就不存在。
		         当用户充值成功后，如果苹果的凭证延迟给过来，此过程中，用户再次点击充值，则预支付订单则就会刷新，此时凭证的产品与预支付订单中的产品则可能不一致。
	 * @param form
	 * @param remoteIp
	 * @return
	 */
	public Integer appleCharging(AppleChargingFormBean form, String remoteIp) {
		long begin = System.currentTimeMillis();
		boolean sandPay = false;
		StringBuffer buffer = new StringBuffer();
		buffer.append("method=").append("appleCharging").append(",param:").append(form.toString()).append(",remoteIp=").append(remoteIp).append(",");
		try {
			if(form.getPayMentId() != null && !"".equals(form.getPayMentId())){
				//查出此预支付是否支付成功
				int payMentIsSuccess = queryServerDao.queryPayMentIsSuccess(form.getPayMentId());
				if(payMentIsSuccess > 0){//说明已经入库，order表有数据，则不需要重复回调，即使状态为0
					buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.SUCCESS);
					log.info(buffer.toString());
					return Constant.SUCCESS;
				}
			}
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getPayMentId(),remoteIp,form.getCpId(),form.getCpGameId(),form.getCpName());
			//带着要验证的参数去数据库验证
			int commonResult = appleCheck(commonCheck);

			if(commonResult != Constant.SUCCESS){
				buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(commonResult);
				log.info(buffer.toString());
				return commonResult;
			}
			//验证是否是需要沙河凭证验证URL的用户或网关
			boolean isAppleTest = Common.isAppleTest(form.getGatewayID(),form.getUserName(),form.getCpGameId(),form.getCpId());
			String verifyUrl = null;   //apple验证地址
			
			if(isAppleTest){
				sandPay = true;
				//如果是获取沙河地址，获取沙河地址
				verifyUrl = CacheUtil.getKey(form.getCpGameId(),form.getCpId(), Constant.CONFIG_APPLE_VERIFY_URL_TEST);
				if(verifyUrl == null || verifyUrl.equals("0")){
					buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_APPLE_VERIFY_URL_TEST);
					log.info(buffer.toString());
					return Constant.ERROR_APPLE_VERIFY_URL_TEST;
				}
	
			}else{ 
				//如果不是，获取apple凭证验证的URL，并验证小额充值次数验证问题
				verifyUrl = CacheUtil.getKey(form.getCpGameId(),form.getCpId(), Constant.CONFIG_APPLE_VERIFY_URL);
				if(verifyUrl == null || verifyUrl.equals("0")){
					buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_APPLE_VERIFY_URL);
					log.info(buffer.toString());
					return Constant.ERROR_APPLE_VERIFY_URL;
				}
			}
			//使用凭证去apple验证，验证凭证
			String appleResult = HttpUtils.httpPostApple(verifyUrl, "{\"receipt-data\" : \""+form.getTransactionReceipt()+"\"}","apple");			
			try{
				if("v6.0".equals(form.getVersion())){
					AppleResVSixBean appResBean = JsonUtil.convertJsonToBean(appleResult, AppleResVSixBean.class);
					if(appResBean.getStatus() != 0){
						buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_APPLE_VERIFY);
						log.info(buffer.toString());
						return Constant.ERROR_APPLE_VERIFY;
					}
					//匹配对应预支付
					Map<String, Object> preCharge = queryServerDao.matchPayMentInfo(form.getUserName(), form.getCpId(), form.getCpGameId(), form.getGatewayID(), appResBean.getReceipt().getProduct_id());
					if (!preCharge.get("result").equals(Constant.SUCCESS)) {
						buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_MATCH_PAYMENT);
						log.info(buffer.toString());
						return Constant.ERROR_MATCH_PAYMENT;
					}
					PrePaymentPOJO payment = (PrePaymentPOJO)preCharge.get("payment");
					if(payment == null){
						buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_MATCH_PAYMENT);
						log.info(buffer.toString());
						return Constant.ERROR_MATCH_PAYMENT;
					}
					if(sandPay) {
						//标记沙盒订单，预支付test_state字段改成9，能走到此处，说明该订单确实是笔沙盒订单，此时再进行标记
						int markResult = chargeServerDao.markSandCharge(payment.getPaymentId(), Constant.APPLE_SANDBOX_STATE);
						if (markResult < 1) {
							//能走到此处已经表示该笔订单用户付款了，只是标记沙盒订单时出错，不做直接返回，打印日志供开发人员查问题
							buffer.append("time=").append((System.currentTimeMillis() - begin)).append(",result=").append(Constant.ERROR_MATCH_PAYMENT);
							log.info(buffer.toString());
						}
					}
					//获取玩家真实账号，并覆盖
					int temp = getRealPassportName(payment);
					if(temp<0){ return temp; }
					return remoteCallForAppleForSix(payment, remoteIp,appResBean, commonCheck.getPushType());
				}else if("v7.0".equals(form.getVersion())){
					AppleResVSevenBean appResOneBean = JsonUtil.convertJsonToBean(appleResult, AppleResVSevenBean.class);
					if(appResOneBean.getStatus() != 0){
						buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_APPLE_VERIFY);
						log.info(buffer.toString());
						return Constant.ERROR_APPLE_VERIFY;
					}
					//找到未完成的订单
					int order = -1;
					int orderResult = order;
					long flag = 1000000000000L;
					for(InApp inapp : appResOneBean.getReceipt().getIn_app()){
						order ++;
						if(Math.abs(Long.valueOf(appResOneBean.getReceipt().getReceipt_creation_date_ms()) - Long.valueOf(inapp.getPurchase_date_ms())) <flag){
							orderResult = order;
							flag = Math.abs(Long.valueOf(inapp.getPurchase_date_ms())-Long.valueOf(appResOneBean.getReceipt().getReceipt_creation_date_ms()));
						}
					}
					if(orderResult == -1){
						buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_APPLE_ORDER);
						log.info(buffer.toString());
						return Constant.ERROR_APPLE_ORDER;
					}
					//匹配对应预支付
					Map<String, Object> preCharge = queryServerDao.matchPayMentInfo(form.getUserName(), form.getCpId(), form.getCpGameId(), form.getGatewayID(), appResOneBean.getReceipt().getIn_app().get(orderResult).getProduct_id());
					if (!preCharge.get("result").equals(Constant.SUCCESS)) {
						buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_MATCH_PAYMENT);
						log.info(buffer.toString());
						return Constant.ERROR_MATCH_PAYMENT;
					}
					PrePaymentPOJO payment = (PrePaymentPOJO)preCharge.get("payment");
					if(payment == null){
						buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_MATCH_PAYMENT);
						log.info(buffer.toString());
						return Constant.ERROR_MATCH_PAYMENT;
					}
					if(sandPay) {
						//标记沙盒订单，预支付test_state字段改成9，能走到此处，说明该订单确实是笔沙盒订单，此时再进行标记
						int markResult = chargeServerDao.markSandCharge(payment.getPaymentId(), Constant.APPLE_SANDBOX_STATE);
						if (markResult < 1) {
							//能走到此处已经表示该笔订单用户付款了，只是标记沙盒订单时出错，不做直接返回，打印日志供开发人员查问题
							buffer.append("time=").append((System.currentTimeMillis() - begin)).append(",result=").append(Constant.ERROR_MATCH_PAYMENT);
							log.info(buffer.toString());
						}
					}
					//获取玩家真实账号，并覆盖
					int temp = getRealPassportName(payment);
					if(temp<0){ return temp; }
					int ret = remoteCallForAppleForSeven(payment, remoteIp,appResOneBean,orderResult, commonCheck.getPushType());
					buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(ret);
					log.info(buffer.toString());
					return ret;
				}else{
					buffer.append("time=").append((System.currentTimeMillis()-begin)).append(",result=").append(Constant.ERROR_APPLE_VERSION);
					log.info(buffer.toString());
					return Constant.ERROR_APPLE_VERSION;
				}
				
			} catch (Exception e) {
				log.error( "deal appleCharging param error:"+form+",time="+(System.currentTimeMillis()-begin)+",error info:" + e.toString());
				return Constant.ERROR_APPLE_VERIFY;
			}
			
		} catch (Exception t) {
			log.error("deal appleCharging param error:"+form+",time="+(System.currentTimeMillis()-begin)+",error info:" + t.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 草花回调接口业务层
	 * @param form
	 * @param requestIp
	 * @return
	 */
	public int caohuaCharging(CaoHuaChargingFormBean form, String requestIp) {
		try {
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOrderno_cp(), requestIp);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验签
			if(! MD5SignatureChecker.caohuaChecker(form, key)){
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			if(payment.getChargeMoney()*100 != Double.parseDouble(form.getPay_amt())){
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderno(), requestIp, commonCheck.getPushType());
		}catch (Exception e){
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), "deal caohuaCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 7K业务层
	 * @param form
	 * @param requestIp
	 * @return
	 */
	public int sevenKCharging(SevenKChargingFormBean form, String requestIp) {
		try{
			//进行通用检查，并返回预支付订单信息
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getPayorderid(), requestIp);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验签
			if(! MD5SignatureChecker.sevenKChecker(form, key)){
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			if(payment.getChargeMoney() != Double.parseDouble(form.getMoney())){
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getPaycid(), requestIp, commonCheck.getPushType());
		}catch (Exception e){
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), "deal sevenKCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 松鼠回调业务层
	 * @param form
	 * @param requestIp
	 * @return
	 */
	public int songshuCharging(SongshuFormBean form, String requestIp) {
		try{
			if("0".equals(form.getState())){
				return Constant.ERROR_FAIL_ORDER;
			}
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCpOrderID(), requestIp);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验签
			if(! MD5SignatureChecker.songshuChecker(form, key)){
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			if(payment.getChargeMoney() != Double.parseDouble(form.getMoney())){
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrderID(), requestIp, commonCheck.getPushType());
		}catch (Exception e){
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), "deal songshuCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}
	/**
	 * 魅族聚合回调业务层
	 * @param form
	 * @param trueIP
	 * @return
	 */
	public int meizuJuheCharging(MeizuJuHeForBean form, String trueIP) {
		try{
			if(!"1".equals(form.getStatus())){
				return Constant.ERROR_FAIL_ORDER;
			}
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCp_order_id(), trueIP);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验签
			if(! MD5SignatureChecker.meizuJuheChecker(form, key)){
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			if(payment.getChargeMoney() != Double.parseDouble(form.getTotal_price())){
				return Constant.ERROR_CHARGE_MONEY;
			}
			return remoteCall(payment, form.getOrder_id(), trueIP, commonCheck.getPushType());
		}catch (Exception e){
			LoggerUtil.error(this.getClass(), "deal meizuJuheCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 一点-回调业务层
	 * @param form
	 * @param requestIp
	 * @return
	 */
	public int yidianCharging(YiDianChargingFormBean form, String requestIp) {
		try{
			if(!"0".equals(form.getCode())){
				return Constant.ERROR_FAIL_ORDER;
			}
			
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getOut_trade_no(), requestIp);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//验证appID
			String appId = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_APPID);
			if (appId == null || appId.equals("0")) {
				return Constant.ERROR_APP_ID;
			}
			if(!appId.equals(form.getApp_id())){
				return Constant.ERROR_CALLBACK_APPID;
			}
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验签
			if(!MD5SignatureChecker.yidianChecker(form, key)){
				return Constant.ERROR_SIGN;
			}
			// 验证支付金额是否一致
			if(payment.getChargeMoney()*100 != Double.parseDouble(form.getMoney())){
				return Constant.ERROR_CHARGE_MONEY;
			}
			//获取partner_id
			String partnerId = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_CPID);
			if (partnerId == null || partnerId.equals("0")) {
				return Constant.ERROR_CP_ID;
			}
			//到渠道查询该订单
			String checkOrderURL = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_CHECKORDERURL);
			if(StringUtils.isEmpty(checkOrderURL) || key.equals("0")){
				return Constant.ERROR_CALLBACK_URL;
			}
			String checkOrderSign = MD5SignatureChecker.createYidianCheckerOrderSign(payment,appId, key,partnerId).toUpperCase();
			String checkOrderParam = "partner_id="+partnerId+"&app_id="+appId+"&out_trade_no="+payment.getPaymentId()+"&sign="+checkOrderSign;
			String checkOrderResult = HttpUtils.httpGet(checkOrderURL,checkOrderParam,"yidian");
			YiDianCheckOrderResBean checkOrderResultJson = JsonUtil.convertJsonToBean(checkOrderResult, YiDianCheckOrderResBean.class);
			if(checkOrderResultJson.getCode() != 0){
				return Constant.ERROR_FAIL_ORDER;
			}
			//回调订单数据库处理
			return remoteCall(payment, form.getInvoice_no(), requestIp, commonCheck.getPushType());
		}catch (Exception e){
			e.printStackTrace();
			LoggerUtil.error(this.getClass(), "deal yidianCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * UC单机版，充值回调
	 * @param form
	 * @param trueIP
	 * @return
	 */
	public int ucStandAloneCharging(UCStandAloneFormBean form, String trueIP) {
		try{
			if(!"S".equals(form.getData().getOrderStatus())) {
				return Constant.ERROR_FAIL_ORDER;
			}
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getData().getOrderId(), trueIP);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//金额校验
			if(Double.parseDouble(form.getData().getAmount()) != payment.getChargeMoney()) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验签
			if(!MD5SignatureChecker.ucStandAloneChecker(form, key)){
				return Constant.ERROR_SIGN;
			}
			//订单入库
			return remoteCall(payment, form.getData().getTradeId(), trueIP, commonCheck.getPushType());
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "deal ucStandAloneCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 爱奇艺聚合回调业务层
	 * @param form
	 * @param trueIP
	 * @return
	 */
	public int aiqiyiJuheCharging(AiqiyiJuheFormBean form, String trueIP) {
		try {
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCp_order_no(), trueIP);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//金额校验
			if(Double.parseDouble(form.getMoney()) != payment.getChargeMoney() * 100) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验证签名
			if(!MD5SignatureChecker.aiqiyiJuheChecker(form, key)) {
				return Constant.ERROR_SIGN;
			}
			//订单入库
			return remoteCall(payment, form.getUnion_order_id(), trueIP, commonCheck.getPushType());
		}catch (Exception e){
			LoggerUtil.error(this.getClass(), "deal aiqiyiJuheCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 9377渠道回调业务层
	 * @param form
	 * @param trueIP
	 * @return
	 */
	public int ntssCharging(NTSSChargingFormBean form, String trueIP) {
		try{
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getExtra_info(), trueIP);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//金额校验
			if(Double.parseDouble(form.getMoney()) != payment.getChargeMoney()) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验证签名
			if(!MD5SignatureChecker.ntssChecker(form, key)) {
				return Constant.ERROR_SIGN;
			}
			//订单入库
			return remoteCall(payment, form.getOrder_id(), trueIP, commonCheck.getPushType());
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "deal ntssCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 新版本游戏猫充值回调
	 * @param request
	 * @param response
	 * @param form
	 */
	public int youximao2Charging(YouximaoChargingFormBean2 form, String trueIP) {
		try{
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCodeNo(), trueIP);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//金额校验
			if(Double.parseDouble(form.getAmount()) != payment.getChargeMoney()) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验证签名
			if(!MD5SignatureChecker.youximaoChecker2(form, key)) {
				return Constant.ERROR_SIGN;
			}
			//订单入库
			return remoteCall(payment, form.getTradeNo(), trueIP, commonCheck.getPushType());
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "deal ntssCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 蝶恋渠道回调
	 * @param form
	 * @param trueIP
	 * @return
	 */
	public int dielianCharging(DieLianChargingFormBean form, String trueIP) {
		try{
			if(!"1".equals(form.getSt())) {
				return Constant.ERROR_FAIL_ORDER;
			}
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCbi(), trueIP);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//金额校验
			if(payment.getChargeMoney()*100 != Double.parseDouble(form.getFee())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验证签名
			if(!MD5SignatureChecker.dielianChecker(form, key)) {
				return Constant.ERROR_SIGN;
			}
			return remoteCall(payment, String.valueOf(form.getTcd()), trueIP, commonCheck.getPushType());
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "deal dielianCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 大唐剑侠回调业务层
	 * @param data
	 * @param trueIP
	 * @return
	 */
	public int huaShengCharging(HuaShengChargingFormBean form, String trueIP) {
		try{
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getExtension(), trueIP);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//金额校验
			if(payment.getChargeMoney()*100 != Double.parseDouble(form.getMoney())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验证签名
			if(!MD5SignatureChecker.huaShengChecker(form, key)) {
				return Constant.ERROR_SIGN;
			}
			return remoteCall(payment, form.getOrderID(), trueIP, commonCheck.getPushType());
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "deal huaShengCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 极速渠道回调业务层
	 * @param form
	 * @param trueIP
	 * @return
	 */
	public int jisuCharging(JisuFormBean form, String trueIP) {
		try{
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCp_order(), trueIP);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//金额校验
			if(payment.getChargeMoney()*100 != Double.parseDouble(form.getAmount())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验证签名
			if(!MD5SignatureChecker.jisuChecker(form, key)) {
				return Constant.ERROR_SIGN;
			}
			return remoteCall(payment, form.getOrder(), trueIP, commonCheck.getPushType());
		}catch (Exception e){
			LoggerUtil.error(this.getClass(), "deal jisuCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 一点自己的渠道，回调业务层
	 * @param form
	 * @param trueIP
	 * @return
	 */
	public int yidianSelfCharging(YidianselfFormBean form, String trueIP) {
		try{
			if(!"2".equals(form.getOrder_status())) {
				return Constant.ERROR_FAIL_ORDER;
			}
			CommonCheckBean commonCheck = new CommonCheckBean(form.getGameName(), form.getCpName(), form.getCp_order_id(), trueIP);
			int commonResult = commonCheck(commonCheck);
			if (commonResult != Constant.SUCCESS) {
				return commonResult;
			}
			PrePaymentPOJO payment = commonCheck.getPayment();  //预支付订单信息
			//金额校验
			if(payment.getChargeMoney() != Double.parseDouble(form.getProduct_price())) {
				return Constant.ERROR_CHARGE_MONEY;
			}
			//获取签名秘钥
			String key = CacheUtil.getKey(payment.getGameId(),payment.getCpId(), Constant.CONFIG_ENCRYPTION_MD5);
			if (key == null || key.equals("0")) {
				return Constant.ERROR_CHARGE_SIGN;
			}
			//验证签名
			if(!MD5SignatureChecker.yidianSelfChecker(form, key)) {
				return Constant.ERROR_SIGN;
			}
			return remoteCall(payment, form.getOrder_id(), trueIP, commonCheck.getPushType());
		}catch (Exception e) {
			LoggerUtil.error(this.getClass(), "deal yidianSelfCharging param error:"+form+",error info:" + e.toString());
			return Constant.ERROR_SYSTEM;
		}
	}

	/**
	 * 渠道通用验证
	 * @param gameName	游戏Code
	 * @param cpName	渠道Code
	 * @param ordeID    预支付ID
	 * @param payment   查询出的订单信息  （返回）
	 * @param ratio  	充值比例	          （返回）
	 * @return
	 * @throws Exception
	 */
	public int commonCheck(CommonCheckBean bean) throws Exception{
		//获取渠道状态
		Integer cpState = CacheUtil.getCPStateByCpName(bean.getCpName());
		//Constant里配置了很多配置信息常量
		// 验证渠道
		if (cpState == Constant.STATE_NULL) {
			return Constant.ERROR_REQUEST_CP;
		}
		//验证渠道状态是否正常
		if (cpState == Constant.STATE_STOP_COOPERATE) {
			return Constant.ERROR_REQUEST_CP_STATE;
		}
		//获取渠道游戏配置状态
		Integer cpGameState = CacheUtil.vaildCpGameByName(bean.getGameName(),bean.getCpName());
		//验证渠道游戏
		if(cpGameState == Constant.STATE_NULL){
			return Constant.ERROR_REQUEST_CPGAME;
		}
		//验证渠道游戏是否生效
		if(cpGameState == Constant.STATE_NOT_EFFECTIVE){
			return Constant.ERROR_REQUEST_CPGAME_STATE;
		}
		//验证预支付订单号是否已经支付成功
		Integer successPayCount = queryServerDao.queryPayMentIsSuccess(bean.getOrdeID());
		if(successPayCount > 0){
			return Constant.ERROR_ORDER_DUPLICATE;
		}
		//查询预支付信息
		Map<String, Object> preCharge = queryServerDao.getPayMentInfo(bean.getOrdeID());
		// 验证与支付订单是否存在
		if (!preCharge.get("result").equals(Constant.SUCCESS)) {
			return Constant.ERROR_ORDER_EXIST;
		}
		//将查处的Payment实例封装到bean里
		bean.setPayment((PrePaymentPOJO) preCharge.get("payment"));
		//检验渠道IP白名单
		String serverIps = CacheUtil.getKey(bean.getPayment().getGameId(), bean.getPayment().getCpId(),Constant.CONFIG_CP_SERVER_IP);
		if (serverIps == null || serverIps.equals("0")) {
			return Constant.ERROR_CHARGE_SERVER_IP;
		}
		//服务器验证，验证渠道IP白名单，有一个不满足，则不匹配
		if(!serverIps.contains(bean.getRemoteIp()+";") && !"*".equals(serverIps) && !serverIps.contains(bean.getRemoteIp().subSequence(0, bean.getRemoteIp().lastIndexOf(".")+1)+"*;")){
			return Constant.ERROR_SERVER_IP;
		}
		// 验证游戏是否开通充值
		if (!CacheUtil.getKey(bean.getPayment().getGameId(),bean.getPayment().getCpId(), Constant.CONFIG_IS_OPEN_CHARGE).equals("true")
				|| CacheUtil.getKey(bean.getPayment().getGameId(),bean.getPayment().getCpId(), Constant.CONFIG_IS_OPEN_CHARGE).equals("0")) {
			return Constant.ERROR_CHARGE_STATUS;
		}
		
		//判断是否使用新的活动充值系统，也就是决定使用哪种推送方式
		if (CacheUtil.getKey(bean.getPayment().getGameId(),bean.getPayment().getCpId(), Constant.CONFIG_IS_NEW_ACT).equals("true")) {
			bean.setPushType(Constant.MQ_PUSH); //充值解耦
		}else{
			bean.setPushType(Constant.DB_PUSH);
		}
		//是否检查角色信息，如果不检查，角色ID设置为0
//		
//		if(!"true".equals(CacheUtil.getKey(bean.getPayment().getGameId(),bean.getPayment().getCpId(), Constant.CONFIG_IS_CHECK_ROLE))){
//			//
//			if(bean.getPayment().getCpId() != 59)     
//				bean.getPayment().setRoleId(0);
//		}
		return Constant.SUCCESS;
	}
	/**
	 * apple验证
	 * @param gameName	游戏Code
	 * @param cpName	渠道Code
	 * @param ordeID    预支付ID
	 * @param payment   查询出的订单信息  （返回）
	 * @param ratio  	充值比例	          （返回）
	 * @return
	 * @throws Exception
	 */
	public int appleCheck(CommonCheckBean bean) throws Exception{
		//获取渠道状态
		Integer cpState = CacheUtil.getCPState(bean.getCpName(),bean.getCpId());
		// 验证渠道
		if (cpState == Constant.STATE_NULL) {
			return Constant.ERROR_REQUEST_CP;
		}
		//验证渠道状态是否正常
		if (cpState == Constant.STATE_STOP_COOPERATE) {
			return Constant.ERROR_REQUEST_CP_STATE;
		}
		//获取渠道游戏配置状态
		Integer cpGameState = CacheUtil.vaildCpGameById(bean.getCpGameId(), bean.getCpId());
		//验证渠道游戏
		if(cpGameState == Constant.STATE_NULL){
			return Constant.ERROR_REQUEST_CPGAME;
		}
		//验证渠道游戏是否生效
		if(cpGameState == Constant.STATE_NOT_EFFECTIVE){
			return Constant.ERROR_REQUEST_CPGAME_STATE;
		}
		// 验证游戏是否开通充值
		if (!CacheUtil.getKey(bean.getCpGameId(),bean.getCpId(), Constant.CONFIG_IS_OPEN_CHARGE).equals("true")
				|| CacheUtil.getKey(bean.getCpGameId(), bean.getCpId(),Constant.CONFIG_IS_OPEN_CHARGE).equals("0")) {
			return Constant.ERROR_CHARGE_STATUS;
		}
		//判断是否使用信息的活动充值系统，也就是决定使用哪种推送方式
		if (!CacheUtil.getKey(bean.getCpGameId(),bean.getCpId(), Constant.CONFIG_IS_NEW_ACT).equals("true")
				|| CacheUtil.getKey(bean.getCpGameId(),bean.getCpId(), Constant.CONFIG_IS_NEW_ACT).equals("0")) {
			bean.setPushType(Constant.DB_PUSH);
		}else{
			bean.setPushType(Constant.MQ_PUSH);
		}
		//是否检查角色信息，如果不检查，角色ID设置为0
		if(!"true".equals(CacheUtil.getKey(bean.getCpGameId(),bean.getCpId(), Constant.CONFIG_IS_CHECK_ROLE))){
			//bean.getPayment().setRoleId(0);
		}
		return Constant.SUCCESS;
	}
	
	/**
	 * 回调订单数据库处理
	 * @param payment
	 * @param orderId 渠道订单ＩＤ
	 * @param remoteIp
	 * @return
	 * @throws ParseException
	 * @throws UnknownHostException
	 */
	private int remoteCall(PrePaymentPOJO payment,String orderId,String remoteIp,int pushType) throws ParseException, UnknownHostException{
	    ChargingPOJO pojo = new ChargingPOJO();
		pojo.setAttachCode(payment.getAttachCode());
		pojo.setChargeAmount(payment.getChargeAmount());
		pojo.setChargeDetailId(Common.ChargeDetailIdGenerator());
		pojo.setChargeMoney((float) payment.getChargeMoney());
		pojo.setChargeTime(DateUtil.getCurrentDateInMiType());
		pojo.setExpandInfo(payment.getExpandInfo());
		pojo.setGameId(payment.getGameId());
		pojo.setGatewayId(payment.getGatewayId());
		pojo.setCpId(payment.getCpId());
		pojo.setOrderId(String.valueOf(orderId));
		pojo.setPaymentId(payment.getPaymentId());
		pojo.setPlatformName(payment.getPlatformName());
		pojo.setRequestIP(remoteIp);
		pojo.setServerIP(InetAddress.getLocalHost().getHostAddress());
		pojo.setUnionCode(String.valueOf(payment.getCpId()));
		pojo.setUserName(payment.getUserName());
		//存入log_cp_payment_order
		Integer resultCode = chargeServerDao.charging(pojo);
		//如果执行成功，进行信息推送
		if(resultCode == Constant.SUCCESS){
			PushOrderInfoPOJO pushPojo = new PushOrderInfoPOJO();
			pushPojo.setChargeDetailId(pojo.getChargeDetailId());
			pushPojo.setClientIp(remoteIp);
			pushPojo.setGameId(payment.getGameId());
			pushPojo.setServerIP(InetAddress.getLocalHost().getHostAddress());
			pushPojo.setUnionOrderId(orderId);
			//推送物品
			pushOrder(pushType,pushPojo,pojo,payment);
			
		}
		return resultCode;
   }
	
	/**
	 * apple订单数据库处理
	 * @param payment
	 * @param orderId 渠道订单ＩＤ
	 * @param remoteIp
	 * @return
	 * @throws ParseException
	 * @throws UnknownHostException
	 */
	private int remoteCallForAppleForSix(PrePaymentPOJO payment,String remoteIp,AppleResVSixBean appRes,int pushType) throws ParseException, UnknownHostException{
	    ChargingPOJO pojo = new ChargingPOJO();
		pojo.setAttachCode(payment.getAttachCode());
		pojo.setChargeAmount(payment.getChargeAmount());
		pojo.setChargeDetailId(Common.ChargeDetailIdGenerator());
		pojo.setChargeMoney((float) payment.getChargeMoney());
		pojo.setChargeTime(DateUtil.getCurrentDateInMiType());
		pojo.setExpandInfo(payment.getExpandInfo());
		pojo.setGameId(payment.getGameId());
		pojo.setGatewayId(payment.getGatewayId());
		pojo.setCpId(payment.getCpId());
		pojo.setOrderId(appRes.getReceipt().getTransaction_id());
		pojo.setPaymentId(payment.getPaymentId());
		pojo.setPlatformName(payment.getPlatformName());
		pojo.setRequestIP(remoteIp);
		pojo.setServerIP(InetAddress.getLocalHost().getHostAddress());
		pojo.setUnionCode(String.valueOf(payment.getCpId()));
		pojo.setUserName(payment.getUserName());
		
		AppstoreChargePOJO appPojo = Common.getAppstoreChargePOJO(appRes, pojo);
		//存入log_cp_payment_order，log_cp_payment_order
		Integer resultCode = chargeServerDao.chargingForApple(pojo, appPojo );
		//如果执行成功，进行信息推送
		if(resultCode == Constant.SUCCESS){
			PushOrderInfoPOJO pushPojo = new PushOrderInfoPOJO();
			pushPojo.setChargeDetailId(pojo.getChargeDetailId());
			pushPojo.setClientIp(remoteIp);
			pushPojo.setGameId(payment.getGameId());
			pushPojo.setServerIP(InetAddress.getLocalHost().getHostAddress());
			pushPojo.setUnionOrderId(appPojo.getTransactionId());
			//推送物品
			pushOrder(pushType,pushPojo,pojo,payment);
		}
		
		
		return resultCode;
   }
	/**
	 * apple订单数据库处理
	 * @param payment
	 * @param orderId 渠道订单ＩＤ
	 * @param remoteIp
	 * @return
	 * @throws ParseException
	 * @throws UnknownHostException
	 */
	private int remoteCallForAppleForSeven(PrePaymentPOJO payment,String remoteIp,AppleResVSevenBean appRes,int order,int pushType) throws ParseException, UnknownHostException{
	    ChargingPOJO pojo = new ChargingPOJO();
		pojo.setAttachCode(payment.getAttachCode());
		pojo.setChargeAmount(payment.getChargeAmount());
		pojo.setChargeDetailId(Common.ChargeDetailIdGenerator());
		pojo.setChargeMoney((float) payment.getChargeMoney());
		pojo.setChargeTime(DateUtil.getCurrentDateInMiType());
		pojo.setExpandInfo(payment.getExpandInfo());
		pojo.setGameId(payment.getGameId());
		pojo.setGatewayId(payment.getGatewayId());
		pojo.setCpId(payment.getCpId());
		pojo.setOrderId(appRes.getReceipt().getIn_app().get(order).getTransaction_id());
		pojo.setPaymentId(payment.getPaymentId());
		pojo.setPlatformName(payment.getPlatformName());
		pojo.setRequestIP(remoteIp);
		pojo.setServerIP(InetAddress.getLocalHost().getHostAddress());
		pojo.setUnionCode(String.valueOf(payment.getCpId()));
		pojo.setUserName(payment.getUserName());
		
		AppstoreChargePOJO appPojo = Common.getAppstoreChargePOJO(appRes, pojo, order);
		//存入log_cp_payment_order，log_cp_appstore_charge
		Integer resultCode = chargeServerDao.chargingForApple(pojo, appPojo );
		//判断订单是否充值成功(预支付状态为1)
		int state = chargeServerDao.getOrderStatus(payment.getPaymentId());
		if(state == Constant.CHARGE_SUCCESS
				&& resultCode == Constant.SUCCESS){
			PushOrderInfoPOJO pushPojo = new PushOrderInfoPOJO();
			pushPojo.setChargeDetailId(pojo.getChargeDetailId());
			pushPojo.setClientIp(remoteIp);
			pushPojo.setGameId(payment.getGameId());
			pushPojo.setServerIP(InetAddress.getLocalHost().getHostAddress());
			pushPojo.setUnionOrderId(appPojo.getTransactionId());
			//推送物品
			pushOrder(pushType,pushPojo,pojo,payment);
			
			return Constant.SUCCESS;
		}else if(resultCode != Constant.SUCCESS){
			return resultCode;
		}else{
			return Constant.ERROR_ORDER_NO_PAY;
		}
   }
	/**
	 * 推送物品
	 * @param pushType
	 * @param pushPojo
	 * @param pojo  (带IP)
	 * @param payment
	 * @return
	 */
	public int pushOrder(int  pushType,PushOrderInfoPOJO pushPojo,ChargingPOJO pojo,PrePaymentPOJO payment){
		int result = Constant.SUCCESS;
		//推送消息
		switch(pushType){
				case Constant.DB_PUSH:
					ThreadPoolUtil.pool.submit(new PushInfo(pushPojo, chargeServerDao, queryServerDao));
					break;
			case Constant.MQ_PUSH:
				PushOrderWithMQInfoPOJO pushOrderWithMQInfoPOJO = new PushOrderWithMQInfoPOJO(pojo,Constant.DEFAULT_DISCOUNT,payment);	
				//检查是否符合发送条件
				result = chargeServerDao.pushMQChargeInfoCheck(pushPojo);
				if(result != Constant.SUCCESS){
					return result;
				}
				ThreadPoolUtil.pool.submit(new PushInfoWithMQ(pushOrderWithMQInfoPOJO,chargeInvokeRabbitMQ,chargeDao));
				break;
		}
		return result;
	}

	/**
	 * 此方法解决用户绑定正式账号时，未重新登录游戏导致充值带着试玩账号，会推送ertaing查不到用户（对于未绑定和绑定之后有重登的，此方法不会更改任何内容）
	 * 如查出的内容为空，说明用户传入的是正式账号
	 * 查出内容不为空，（1、当和预支付表的账号不等说明，该用户的充值还是带着试玩账号  2、如果相等，）
	 *
	 * select passport_name from sys_passport where passport_id = (
	 select passport_id from sys_passport_fast_reg where passport_name = ?)
	 * @param userName
	 * @return
	 */
	public int getRealPassportName(PrePaymentPOJO pojo){
		//1、把userName当成试玩账号，查出正式账号表对应的正式账号
		int updateRes = 0;
		String realName = queryServerDao.getRealName(pojo.getUserName());
		if(realName != null && !"".equals(realName)) {//如为空说名是正式账号，直接跳过
			if (!realName.equals(pojo.getUserName())) {//说明用户绑定新账号未退游戏就充值
				//1、更新pojo的userName
				pojo.setUserName(realName);
				//2、表更新该笔预支付记录的userName
				if((updateRes=chargeServerDao.updateUserName(pojo.getPaymentId(), realName))<0) {
					 return Constant.ERROR_UPDATE_PRE_USERNAME;
				}
			} else {//说明允许试玩账号充值，此处暂不做处理，后续有新需求可在此处扩展

			}
		}
		return updateRes;
	}


	public int fillOrder(String unionOrderId){
		Map<String, Object> pushInfoResult = queryServerDao.getPushInfo();
		if((Integer)pushInfoResult.get("result")!=Constant.SUCCESS){
			return -1;
		}
		
		@SuppressWarnings("unchecked")
		List<PushOrderInfoPOJO> pushList = (List<PushOrderInfoPOJO>) pushInfoResult.get("pushOrderInfoList");
		
		for(PushOrderInfoPOJO pojo : pushList){
			if (unionOrderId.equals(pojo.getUnionOrderId()) == false)
				continue;
			
			//获取预支付信息
			Map<String, Object> preInfoMap = queryServerDao.getPreInfoByChargeDetailId(pojo.getUnionOrderId());
			if((Integer)preInfoMap.get("result")!=Constant.SUCCESS){
				LoggerUtil.info(CPChargeCallBackService.class, "repair-PushInfo_param:"+pojo+"_getPreInfoByChargeDetailId:"+(Integer)preInfoMap.get("result"));
				return -2;
			}
			PrePaymentPOJO preInfo = (PrePaymentPOJO)preInfoMap.get("payment");
			//设置IP
			pojo.setClientIp(preInfo.getRequestIp());
			pojo.setServerIP(preInfo.getServerIp());
		
			int result = Constant.SUCCESS;
			PushOrderWithMQInfoPOJO pushOrderWithMQInfoPOJO = new PushOrderWithMQInfoPOJO(Constant.DEFAULT_DISCOUNT,preInfo,pojo);	
			//检查是否符合发送条件
			result = chargeServerDao.pushMQChargeInfoCheck(pojo);
			if(result != Constant.SUCCESS){
				LoggerUtil.info(CPChargeCallBackService.class, "repair-MQ-PushInfo_param:"+pojo+"pushInfoReturn:"+result);
				return -3;
			}
			try {
				chargeInvokeRabbitMQ.chargePushInvokeMQ(JsonUtil.convertBeanToJson(pushOrderWithMQInfoPOJO));
				return 1;
			} catch (IOException e) {
				LoggerUtil.error(CPChargeCallBackService.class, "repair-MQ-Error-PushOrderWithMQInfoPOJO_param:"+pushOrderWithMQInfoPOJO+"push_result:"+result+"_error:"+e.toString());
			} catch (Exception e) {
				LoggerUtil.error(CPChargeCallBackService.class, "repair-MQ-Error-PushOrderWithMQInfoPOJO_param:"+pushOrderWithMQInfoPOJO+"push_result:"+result+"_error:"+e.toString());
			}
			return -4;
		}
		return -5;
	}
	
	public int fillOrder2(String chargeDetailId, String unionOrderId, int pushType){
		//获取预支付信息
		Map<String, Object> preInfoMap = queryServerDao.getPreInfoByChargeDetailId(unionOrderId);
		if((Integer)preInfoMap.get("result")!=Constant.SUCCESS){
			LoggerUtil.info(CPChargeCallBackService.class, "repair-PushInfo_param:"+unionOrderId+"_getPreInfoByChargeDetailId:"+(Integer)preInfoMap.get("result"));
			return -2;
		}
		PrePaymentPOJO preInfo = (PrePaymentPOJO)preInfoMap.get("payment");
		
		PushOrderInfoPOJO pushPojo = new PushOrderInfoPOJO();
		pushPojo.setChargeDetailId(Long.parseLong(chargeDetailId));
		pushPojo.setClientIp("127.0.0.1");
		pushPojo.setGameId(preInfo.getGameId());
		try{
		   pushPojo.setServerIP(InetAddress.getLocalHost().getHostAddress());
		}
		catch(Exception e){
			pushPojo.setServerIP("127.0.0.1");
		}
		pushPojo.setUnionOrderId(unionOrderId);
	
		int result = Constant.SUCCESS;			
		PushOrderWithMQInfoPOJO pushOrderWithMQInfoPOJO = new PushOrderWithMQInfoPOJO(Constant.DEFAULT_DISCOUNT,preInfo,pushPojo);	
		//检查是否符合发送条件
		result = chargeServerDao.pushMQChargeInfoCheck(pushPojo);
		if(result != Constant.SUCCESS){
			LoggerUtil.info(CPChargeCallBackService.class, "repair-MQ-PushInfo_param:"+pushPojo+"pushResult:"+result);
			return -3;
		}
		try {
			if (pushType == 0){
				ThreadPoolUtil.pool.submit(new PushInfo(pushPojo, chargeServerDao,queryServerDao));
			}
			else{
			    chargeInvokeRabbitMQ.chargePushInvokeMQ(JsonUtil.convertBeanToJson(pushOrderWithMQInfoPOJO));
			}
			return 1;
		} catch (IOException e) {
			LoggerUtil.error(CPChargeCallBackService.class, "repair-MQ-Error-PushOrderWithMQInfoPOJO_param:"+pushOrderWithMQInfoPOJO+"push_reuslt:"+result+"_error:"+e.toString());
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackService.class, "repair-MQ-Error-PushOrderWithMQInfoPOJO_param:"+pushOrderWithMQInfoPOJO+"push_reuslt:"+result+"_error:"+e.toString());
		}
		return -4;
	
	}	
	
	public String getDESKey(String gameName,String cpName){
		try {
			//int gameId = CacheUtil.getGameId(gameName, cpName);
			List<Object> gameIdAndCpId = CacheUtil.getGameId(gameName, cpName);
			if(gameIdAndCpId==null){
				return null;
			}
			int gameId = Integer.parseInt(gameIdAndCpId.get(0).toString());
			int cpId = Integer.parseInt(gameIdAndCpId.get(1).toString());
			if (gameId == 0) {
				return null;
			}
			String publicKey = CacheUtil.getKey(gameId, cpId,Constant.CONFIG_ENCRYPTION_DES);
			if (publicKey == null) {
				return null;
			}
			return publicKey;
		} catch (Exception e) {
			LoggerUtil.error(CPChargeCallBackService.class, e.toString());
			return null;
		}
	}

	//补单
	public int repairApple(String paymentId,String unionId, int pushType) {
		try {
			Map<String, Object> preCharge = queryServerDao.getPayMentInfo(paymentId);
			if (!preCharge.get("result").equals(Constant.SUCCESS)) {
				return Constant.ERROR_ORDER_EXIST;
			}
			//将查处的Payment实例封装到bean里
			PrePaymentPOJO bean = (PrePaymentPOJO) preCharge.get("payment");
			return remoteCall(bean, unionId, "113.208.129.8", pushType);

		} catch (Exception e) {
			e.printStackTrace();
			return Constant.ERROR_PARAM_VALIDATE;
		}
	}

}
