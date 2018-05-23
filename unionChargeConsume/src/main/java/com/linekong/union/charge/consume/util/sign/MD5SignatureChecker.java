package com.linekong.union.charge.consume.util.sign;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.linekong.union.charge.consume.web.formbean.*;
import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lenovo.pay.sign.Base64;
import com.linekong.union.charge.consume.rabbitmq.pojo.PushOrderWithMQInfoPOJO;
import com.linekong.union.charge.consume.util.Common;
import com.linekong.union.charge.consume.util.JsonUtil;
import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.JianGuoReqBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.PreChargeMeizuReqBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.BaiduResBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.KaopuResBean;
import com.linekong.union.charge.consume.web.jsonbean.resbean.PreChargeVivoResBean;
import com.linekong.union.charge.rpc.pojo.PrePaymentPOJO;

public final class MD5SignatureChecker {

	private static final String DEFAULT_CHARSET = "utf-8";
	
	public static String getCharset() {
		return DEFAULT_CHARSET ;
	}

	public static boolean stChecker(TSChargingFormBean form,String cpKey,String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb.append(form.getUsername());
		sb.append("|");
		sb.append(form.getChange_id());
		sb.append("|");
		sb.append(form.getMoney());
		sb.append("|");
		LoggerUtil.info(MD5SignatureChecker.class, "st initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	
	
	public static boolean souGouChecker(SouGouChargingFormBean form,String cpKey,String sign) throws UnsupportedEncodingException{
		Map<String, String> sortMap = new HashMap<String, String>();
		@SuppressWarnings("unused")
		String temp = null;
		temp = form.getAmount1()!= null ?sortMap.put("amount1", URLEncoder.encode(form.getAmount1(),"utf-8")):sortMap.put("amount1","");
		temp = form.getAmount2()!= null ?sortMap.put("amount2",URLEncoder.encode( form.getAmount2(),"utf-8")):sortMap.put("amount2","");
		temp = form.getAppdata()!= null ?sortMap.put("appdata", URLEncoder.encode(form.getAppdata(),"utf-8")):sortMap.put("appdata","");
		temp = form.getDate()!= null ?sortMap.put("date", URLEncoder.encode(form.getDate(),"utf-8")):sortMap.put("date","");
		temp = form.getGid()!= null ?sortMap.put("gid", URLEncoder.encode(form.getGid(),"utf-8")):sortMap.put("gid","");
		temp = form.getOid()!= null ?sortMap.put("oid", URLEncoder.encode(form.getOid(),"utf-8")):sortMap.put("oid","");
		temp = form.getRole()!= null ?sortMap.put("role", URLEncoder.encode(form.getRole(),"utf-8")):sortMap.put("role","");
		temp = form.getSid()!= null ?sortMap.put("sid", URLEncoder.encode(form.getSid(),"utf-8")):sortMap.put("sid","");
		temp = form.getTime()!= null ?sortMap.put("time", URLEncoder.encode(form.getTime(),"utf-8")):sortMap.put("time","");
		temp = form.getUid()!= null ?sortMap.put("uid", URLEncoder.encode(form.getUid(),"utf-8")):sortMap.put("uid","");
		temp = form.getRealAmount()!= null ?sortMap.put("realAmount", URLEncoder.encode(form.getRealAmount(),"utf-8")):sortMap.put("realAmount","");
		String[] sortParams = new String[sortMap.size()];
		sortMap.keySet().toArray(sortParams);
		Arrays.sort(sortParams);
		StringBuilder sb = new StringBuilder();
		for(String param:sortParams){
			sb.append(param).append("=").append(sortMap.get(param)).append("&");
		}
		LoggerUtil.info(MD5SignatureChecker.class, "sougou initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	
	public static boolean leTvChecker(LeTvChargingFormBean form,String requestURL,String productStr,String cpKey,String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = form.getAppKey() != null ? sb.append("appKey=").append(form.getAppKey()) : sb;
		sb = form.getCurrencyCode() != null ? sb.append("currencyCode=").append(form.getCurrencyCode()):sb;
		sb = form.getParams() != null ? sb.append("params=").append(form.getParams()):sb;
		sb = form.getPrice() != null ? sb.append("price=").append(form.getPrice()):sb;
		sb = productStr != null ? sb.append("products=").append(productStr):sb;
		sb = form.getPxNumber() != null ? sb.append("pxNumber=").append(form.getPxNumber()):sb;
		sb = form.getUserName() != null ? sb.append("userName=").append(form.getUserName()):sb;
		LoggerUtil.info(MD5SignatureChecker.class, "leTV initLinkString:"+sb.toString());
	    String signContent = new StringBuilder().append(requestURL).append(sb.toString()).append(cpKey).toString();	
	    return doCheck(sign, doMD5HexEncrypt(URLEncoder.encode(signContent,DEFAULT_CHARSET), DEFAULT_CHARSET));
	}
	
	public static boolean lePhoneChecker(LePhoneChargingFormBean form, String cpKey, String sign) throws UnsupportedEncodingException{
		Map<String, String> sortMap = new HashMap<String, String>();
		@SuppressWarnings("unused")
		String temp = null;
		temp =  form.getApp_id() != null ? sortMap.put("app_id",form.getApp_id()) : sortMap.put("app_id", "");		
		temp =  form.getCooperator_order_no() != null ? sortMap.put("cooperator_order_no",form.getCooperator_order_no()) : sortMap.put("cooperator_order_no", "");
		temp =  form.getExtra_info() != null ? sortMap.put("extra_info",form.getExtra_info()) : sortMap.put("extra_info", "");
		temp =  form.getLepay_order_no() != null ? sortMap.put("lepay_order_no",form.getLepay_order_no()) : sortMap.put("lepay_order_no", "");
		temp =  form.getLetv_user_id() != null ? sortMap.put("letv_user_id",form.getLetv_user_id()) : sortMap.put("letv_user_id", "");
		temp =  form.getOriginal_price() != null ? sortMap.put("original_price",form.getOriginal_price()) : sortMap.put("original_price", "");
		temp =  form.getOut_trade_no() != null ? sortMap.put("out_trade_no",form.getOut_trade_no()) : sortMap.put("out_trade_no", "");
		temp =  form.getPay_time() != null ? sortMap.put("pay_time",form.getPay_time()) : sortMap.put("pay_time", "");
		temp =  form.getPrice() != null ? sortMap.put("price",String.valueOf(form.getPrice())) : sortMap.put("price", "");
		temp =  form.getProduct_id() != null ? sortMap.put("product_id",form.getProduct_id()) : sortMap.put("product_id", "");
		temp =  form.getSign_type() != null ? sortMap.put("sign_type",form.getSign_type()) : sortMap.put("sign_type", "");
		temp =  form.getTrade_result() != null ? sortMap.put("trade_result",form.getTrade_result()) : sortMap.put("trade_result", "");
		temp =  form.getVersion() != null ? sortMap.put("version",form.getVersion()) : sortMap.put("version", "");
		String[] sortParams = new String[sortMap.size()];
		sortMap.keySet().toArray(sortParams);
		Arrays.sort(sortParams);
		StringBuilder sb = new StringBuilder();
		for(String param:sortParams){
			sb.append(param).append("=").append(sortMap.get(param)).append("&");
		}
		LoggerUtil.info(MD5SignatureChecker.class, "lePhone initLinkString:"+sb.toString());
		sb.append("key=").append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	
	public static boolean muzhiwanChecker(MuzhiwanChargingFormBean form, String cpKey, String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb.append(form.getAppkey());
		sb.append(form.getOrderID());
		sb.append(form.getProductName());
		sb.append(form.getProductDesc());
		sb.append(form.getProductID());
		sb.append(form.getMoney());
		sb.append(form.getUid());
		sb.append(form.getExtern());
		LoggerUtil.info(MD5SignatureChecker.class, "muzhiwan initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	
	public static boolean ftnnChecker(FTNNChargingFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(form.getOrderid());
		sb.append(form.getUid());
		sb.append(form.getMoney());
		sb.append(form.getGamemoney());
		sb = form.getServerid() != null ? sb.append(form.getServerid()) : sb;
		sb.append(cpKey);
		sb = form.getMark() != null ? sb.append(form.getMark()) : sb;
		sb = form.getRoleid() != null ? sb.append(form.getRoleid()) : sb;
		sb.append(form.getTime());
		LoggerUtil.info(MD5SignatureChecker.class, "fntt initLinkString:"+sb.toString());
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static boolean leVideoChecker(LeVideoChargingFormBean form, String cpKey, String sign) throws UnsupportedEncodingException {
		Map<String, String> sortMap = new HashMap<String, String>();
		@SuppressWarnings("unused")
		String temp = null;
		temp = form.getLepay_order_no() != null ? sortMap.put("lepay_order_no", form.getLepay_order_no()) : null;
		temp = form.getLetv_user_id() != null ? sortMap.put("letv_user_id", form.getLetv_user_id()) : null;
		temp = form.getMerchant_business_id() != null ? sortMap.put("merchant_business_id", form.getMerchant_business_id()) : null;
		temp = form.getMerchant_no() != null ? sortMap.put("merchant_no", form.getMerchant_no()) : null;
		temp = form.getOut_trade_no() != null ? sortMap.put("out_trade_no", form.getOut_trade_no()) : null;
		temp = form.getPay_time() != null ? sortMap.put("pay_time", form.getPay_time()) : null;
		temp = form.getPrice() != null ? sortMap.put("price", form.getPrice()) : null;
		temp = form.getProduct_desc() != null ? sortMap.put("product_desc", form.getProduct_desc()) : null;
		temp = form.getProduct_id() != null ? sortMap.put("product_id", form.getProduct_id()) : null;
		temp = form.getProduct_name() != null ? sortMap.put("product_name", form.getProduct_name()) : null;
		temp = form.getProduct_urls() != null ? sortMap.put("product_urls", form.getProduct_urls()) : null;
		temp = form.getTrade_result() != null ? sortMap.put("trade_result", form.getTrade_result()) : null;
		temp = form.getTrade_type() != null ? sortMap.put("trade_type", form.getTrade_type()) : null;
		temp = form.getVersion() != null ? sortMap.put("version", form.getVersion()) : null;
		String[] sortParams = new String[sortMap.size()];
		sortMap.keySet().toArray(sortParams);
		Arrays.sort(sortParams);
		StringBuilder sb = new StringBuilder();
		sb.append(form.getNotify_url());
		for(String param:sortParams){
			sb.append(param).append("=").append(sortMap.get(param)).append("&");
		}
		LoggerUtil.info(MD5SignatureChecker.class, "levideo initLinkString:"+sb.toString());
		sb.append("key=").append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static boolean pengyouwanChecker(PywChargingFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(cpKey);
		sb.append(form.getCp_orderid());
		sb.append(form.getCh_orderid());
		sb.append(form.getAmount());
		LoggerUtil.info(MD5SignatureChecker.class, "pengyouwan initLinkString:"+sb.toString());
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	public static boolean pengyouwanBTChecker(PywBTChargingFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(cpKey);
		sb.append(form.getCp_orderid());
		sb.append(form.getCh_orderid());
		sb.append(form.getAmount());
		LoggerUtil.info(MD5SignatureChecker.class, "pengyouwanBT initLinkString:"+sb.toString());
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	public static boolean douyuChecker(DouyuChargingFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		Map<String, String> sortMap = new HashMap<String, String>();
		@SuppressWarnings("unused")
		String temp = null;
		temp = form.getServerId() != null ? sortMap.put("serverId", form.getServerId()) : null;
		temp = form.getServerId() != null ? sortMap.put("callbackInfo", form.getCallbackInfo()) : null;
		temp = form.getServerId() != null ? sortMap.put("openId", form.getOpenId()) : null;
		temp = form.getServerId() != null ? sortMap.put("orderId", String.valueOf(form.getOrderId())) : null;
		temp = form.getServerId() != null ? sortMap.put("orderStatus", form.getOrderStatus()) : null;
		temp = form.getServerId() != null ? sortMap.put("payType", form.getPayType()) : null;
		temp = form.getServerId() != null ? sortMap.put("amount", String.valueOf(form.getAmount())) : null;
		temp = form.getServerId() != null ? sortMap.put("remark", form.getRemark()) : null;
		String[] sortParams = new String[sortMap.size()];
		sortMap.keySet().toArray(sortParams);
		Arrays.sort(sortParams);
		StringBuilder sb = new StringBuilder();
		for (String para : sortParams) {
			sb.append(para).append("=").append(sortMap.get(para));
		}
		LoggerUtil.info(MD5SignatureChecker.class, "douyu initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static boolean papaChecker(PaPaCharginFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		Map<String, String> sortMap = new HashMap<String, String>();
		sortMap.put("app_district", form.getApp_district());
		sortMap.put("app_extra1", form.getApp_extra1());
		sortMap.put("app_extra2", form.getApp_extra2());
		sortMap.put("app_key", form.getApp_key());
		sortMap.put("app_order_id", form.getApp_order_id());
		sortMap.put("app_server", form.getApp_server());
		sortMap.put("app_user_id", form.getApp_user_id());
		sortMap.put("app_user_name", form.getApp_user_name());
		sortMap.put("money_amount", form.getMoney_amount());
		sortMap.put("pa_open_order_id", form.getPa_open_order_id());
		sortMap.put("pa_open_uid", form.getPa_open_uid());
		sortMap.put("product_id", form.getProduct_id());
		sortMap.put("product_name", form.getProduct_name());
		String[] sortParams = new String[sortMap.size()];
		sortMap.keySet().toArray(sortParams);
		Arrays.sort(sortParams);
		StringBuilder sb = new StringBuilder();
		for (String para : sortParams) {
			sb.append("&").append(para).append("=").append(sortMap.get(para));
		}
		LoggerUtil.info(MD5SignatureChecker.class, "papa initLinkString:"+sb.toString());
		return doCheck(sign, doMD5HexEncrypt(new StringBuilder().append(form.getApp_key()).append(cpKey)
				.append(sb.toString().substring(1)).toString(), DEFAULT_CHARSET));
	}

	public static boolean pptvChecker(PPTVChargingFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(form.getSid());
		sb.append(form.getUsername());
		sb.append(form.getRoid());
		sb.append(form.getOid());
		sb.append(form.getAmount());
		sb.append(form.getTime());
		LoggerUtil.info(MD5SignatureChecker.class, "pptv initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static boolean ppsChecker(PPSChargingFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(form.getUser_id());
		sb.append(form.getRole_id());
		sb.append(form.getOrder_id());
		sb.append(form.getMoney());
		sb.append(form.getTime());
		LoggerUtil.info(MD5SignatureChecker.class, "pps initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static boolean ttChecker(String json, String cpKey,String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update((json + cpKey).getBytes("UTF-8"));
		return doCheck(sign,org.apache.commons.codec.binary.Base64.encodeBase64String(md5.digest()));
	}

	/**
	 * 验证签名
	 * @param form
	 * @param key
	 * @param sign
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean kuaiKanChecker(KuaikanChargingFormBean form, String key, String sign) throws Exception {
		//将form转化成Json，再讲json转成map
		String formJson = JsonUtil.convertBeanToJson(form);
		HashMap<String,String> map = JsonUtil.convertJsonToBean(formJson, HashMap.class);
		map.remove("cpName");
		map.remove("gameName");
		TreeMap< String, String> treeMap = new TreeMap<String,String>(map);
		//进行拼串,迭代的方式
		StringBuffer sb = new StringBuffer();
		Set<Entry<String,String>> entrySet = treeMap.entrySet();
		Iterator<Entry<String, String>> it = entrySet.iterator();
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if(v != null && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)){
				sb.append(k+"="+v+"&");
			}
		}
		LoggerUtil.info(MD5SignatureChecker.class, "kuaikan initLinkString:"+sb.toString());
		sb.append("key=" + key);
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] b = md5.digest(sb.toString().getBytes(DEFAULT_CHARSET));
		String str1 = net.iharder.Base64.encodeBytes(b);
		return doCheck(sign, str1);
	}





	public static boolean play800Checker(PlayChargingFormBean form,String play800CpKey,String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if(!"".equals(form.getSite())){
			sb.append(form.getSite());
		}
		if(!"".equals(form.getTime())){
			sb.append(form.getTime());
		}
		sb.append(play800CpKey);
		if(!"".equals(form.getUid())){
			sb.append(form.getUid());
		}
		if(!"".equals(form.getOrder_money())){
			sb.append(form.getOrder_money());
		}
		if(!"".equals(form.getCp_order_id())){
			sb.append(form.getCp_order_id());
		}
		LoggerUtil.info(MD5SignatureChecker.class, "play800 initLinkString:"+sb.toString());
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	public static boolean meizuChecker(MeiZuChargingFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if(form.getApp_id() != null && !"".equals(form.getApp_id())){
			sb.append("app_id=").append(form.getApp_id());
		}
		if(form.getBuy_amount() != null && !"".equals(form.getBuy_amount())){
		sb.append("&buy_amount=").append(form.getBuy_amount());
		}
		if(form.getCp_order_id() != null && !"".equals(form.getCp_order_id())){
		sb.append("&cp_order_id=").append(form.getCp_order_id());
		}
		if(form.getCreate_time() != null && !"".equals(form.getCreate_time())){
			sb.append("&create_time=").append(form.getCreate_time());
		}
		if(form.getNotify_id() != null && !"".equals(form.getNotify_id())){
			sb.append("&notify_id=").append(form.getNotify_id());
		}
		if(form.getNotify_time() != null && !"".equals(form.getNotify_time())){
			sb.append("&notify_time=").append(form.getNotify_time());
	    }
		if(form.getOrder_id() != null && !"".equals(form.getOrder_id())){
			sb.append("&order_id=").append(form.getOrder_id());
		}
		if(form.getPartner_id() != null && !"".equals(form.getPartner_id())){
			sb.append("&partner_id=").append(form.getPartner_id());
		}
		if(form.getPay_time() != null && !"".equals(form.getPay_time())){
			sb.append("&pay_time=").append(form.getPay_time());
		}
		if(form.getPay_type() != null && !"".equals(form.getPay_type())){
			sb.append("&pay_type=").append(form.getPay_type());
		}
		if(form.getProduct_id() != null && !"".equals(form.getProduct_id())){
			sb.append("&product_id=").append(form.getProduct_id());
		}
		if(form.getProduct_per_price() != null && !"".equals(form.getProduct_per_price())){
			sb.append("&product_per_price=").append(form.getProduct_per_price());
		}else {
			sb.append("&product_per_price=");
		}
		if(form.getProduct_unit() != null && !"".equals(form.getProduct_unit())){
			sb.append("&product_unit=").append(form.getProduct_unit());
		}else {
			sb.append("&product_unit=");
		}
		if(form.getTotal_price() != null && !"".equals(form.getTotal_price())){
			sb.append("&total_price=").append(form.getTotal_price());
		}
		if(form.getTrade_status() != null && !"".equals(form.getTrade_status())){
			sb.append("&trade_status=").append(form.getTrade_status());
		}
		if(form.getUid() != null && !"".equals(form.getUid())){
			sb.append("&uid=").append(form.getUid());
		}
		if(form.getUser_info() != null && !"".equals(form.getUser_info())){
			sb.append("&user_info=").append(form.getUser_info());
		}
		LoggerUtil.info(MD5SignatureChecker.class, "meizuCallBack initLinkString:"+sb.toString());
		sb.append(":").append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static String createMeizuSign(PreChargeFormBean form, PreChargeMeizuReqBean bean,String paymentId,long createTime,String appId, String cpKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		
		sb.append("app_id=").append(appId);
		sb.append("&buy_amount=").append(form.getChargeAmount());
		sb.append("&cp_order_id=").append(paymentId);
		sb.append("&create_time=").append(createTime);
		sb.append("&pay_type=").append(bean.getPay_type());
		sb.append("&product_body=").append(form.getProductDesc());
		sb.append("&product_id=").append(form.getProductId());
		sb.append("&product_per_price=").append(bean.getProduct_per_price());
		sb.append("&product_subject=").append(bean.getProduct_subject());
		sb.append("&product_unit=").append(form.getProductName());
		sb.append("&total_price=").append(form.getChargeMoney());
		sb.append("&uid=").append(form.getUserName());
		sb.append("&user_info=").append(Common.isEmptyString(form.getAttachCode()) ? "" : form.getAttachCode());
		sb.append(":").append(cpKey);
		LoggerUtil.info(MD5SignatureChecker.class, "meizu initLinkString:"+sb.toString());
		return doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET);
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		String input = "userID=178532&productID=123456&productName=蓝钻&productDesc=10蓝钻&money=100&roleID=12&roleName=落花&serverID=11111&serverName=大和服&extension=201805111257314766&notifyUrl=http://192.168.84.2:8084/unionChargeConsume/huaShengCharge/sszj/huaSheng14d8ad67db98efbb8a240e75354bb0e4";
		/*String ure = URLEncoder.encode(input, "UTF-8");*/
		System.out.println(doMD5HexEncrypt(input, DEFAULT_CHARSET));
	}
	/**
	 * 创建努比亚预支付返回的签名
	 * @param form  	       预支付信息
	 * @param paymentId	       预支付订单号
	 * @param createTime  创建时间 unix时间戳
	 * @param appId		       应用id    对应渠道参数AppID	
	 * @param cpKey		  App的秘钥，对应渠道参数AppSecret	
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createNubiaSign(PreChargeFormBean form, String paymentId,long createTime,String appId, String cpKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		
		sb.append("amount=").append(form.getChargeMoney());
		sb.append("&app_id=").append(appId);
		sb.append("&cp_order_id=").append(paymentId);
		sb.append("&data_timestamp=").append(createTime);
		sb.append("&number=").append(form.getChargeAmount());
		sb.append("&product_des=").append(form.getProductDesc());
		sb.append("&product_name=").append(form.getProductName());
		sb.append("&uid=").append(form.getExpandInfo());
		sb.append(":").append(appId).append(":").append(cpKey);
		LoggerUtil.info(MD5SignatureChecker.class, "nubiaPre initLinkString:"+sb.toString());
		return doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET);	
	}
	/**
	 * 努比亚回调验签
	 * @param form
	 * @param cpKey
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean nubiaChecker(NubiaFormBean form, String cpKey,String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		
		sb.append("amount=").append(form.getAmount());
		sb.append("&app_id=").append(form.getApp_id());
		sb.append("&data_timestamp=").append(form.getData_timestamp());
		sb.append("&number=").append(form.getNumber());
		sb.append("&order_no=").append(form.getOrder_no());
		sb.append("&pay_success=").append(form.getPay_success());
		sb.append("&product_des=").append(form.getProduct_des());
		sb.append("&product_name=").append(form.getProduct_name());
		sb.append("&uid=").append(form.getUid());
		sb.append(":").append(form.getApp_id()).append(":").append(cpKey);
		LoggerUtil.info(MD5SignatureChecker.class, "nubia initLinkString:"+sb.toString());
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	public static boolean guopanChecker(GuoPanChargingFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(form.getSerialNumber());
		sb.append(form.getMoney());
		sb.append(form.getStatus());
		sb.append(form.getT());
		LoggerUtil.info(MD5SignatureChecker.class, "guopan initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static String baiduSignGenerator(BaiduResBean jsonbean, String cpKey) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(jsonbean.getAppID());
		sb.append(jsonbean.getResultCode());
		LoggerUtil.info(MD5SignatureChecker.class, "baidu1 initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET);
	}

	public static boolean baiduChecker(BaiduChargingFormBean form, String content, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(form.getAppID());
		sb.append(form.getOrderSerial());
		sb.append(form.getCooperatorOrderSerial());
		sb.append(Base64.encode(content.getBytes()));
		LoggerUtil.info(MD5SignatureChecker.class, "baidu2 initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static boolean yiwanChecker(YiwanCharginFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(form.getServerid());
		sb.append("|");
		sb.append(form.getCustominfo());
		sb.append("|");
		sb.append(form.getOpenid());
		sb.append("|");
		sb.append(form.getOrdernum());
		sb.append("|");
		sb.append(form.getStatus());
		sb.append("|");
		sb.append(form.getPaytype());
		sb.append("|");
		sb.append(form.getAmount());
		sb.append("|");
		sb.append(form.getErrdesc());
		sb.append("|");
		sb.append(form.getPaytime());
		sb.append("|");
		LoggerUtil.info(MD5SignatureChecker.class, "yiwan initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static boolean ucChecker(UCChargingFormBean form, String cpKey, String sign)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("accountId=").append(form.getAccountId());
		sb.append("amount=").append(form.getAmount());
		sb.append("callbackInfo=").append(form.getCallbackInfo());
		if(form.getCpOrderId() != null){
			sb.append("cpOrderId=").append(form.getCpOrderId());
		}
		sb.append("creator=").append(form.getCreator());
		sb.append("failedDesc=").append(form.getFailedDesc());
		sb.append("gameId=").append(form.getGameId());
		sb.append("orderId=").append(form.getOrderId());
		sb.append("orderStatus=").append(form.getOrderStatus());
		sb.append("payWay=").append(form.getPayWay());
		LoggerUtil.info(MD5SignatureChecker.class, "uc initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	
	public static String createUCSign(PreChargeFormBean form, String notifyUrl, String paymentId, String cpKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb.append("accountId=").append(form.getUserName());
		sb.append("amount=").append(form.getChargeMoney());
		
		sb.append("callbackInfo=").append(form.getGatewayId());
		
		sb.append("cpOrderId=").append(paymentId);
		if(notifyUrl != null){
			sb.append("notifyUrl=").append(notifyUrl);
		}
		//有的老游戏，需要这个值验签
		if(form.getExpandInfo() != null && !"".equals(form.getExpandInfo())){
			sb.append("serverId=").append(form.getExpandInfo());
		}
		LoggerUtil.info(MD5SignatureChecker.class, "ucPre initLinkString:"+sb.toString());
		sb.append(cpKey);
		return doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET);
	}

	public static boolean dangleChecker(DangLeChargingFormBean bean, String signature, String cpKey)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("order=").append(bean.getOrder());
		sb.append("&money=").append(bean.getMoney());
		sb.append("&mid=").append(bean.getMid());
		sb.append("&time=").append(bean.getTime());
		sb.append("&result=").append(bean.getResult());
		sb.append("&ext=").append(bean.getExt());
		LoggerUtil.info(MD5SignatureChecker.class, "dangle initLinkString:"+sb.toString());
		sb.append("&key=").append(cpKey);
		return doCheck(signature, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static boolean vivoChecker(VivoChargingFormBean bean, String cpKey, String sign) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (bean.getAppId() == null) {
			throw new Exception("Vivo appId is null");
		} else {
			sb.append("appId=").append(bean.getAppId());
		}
		sb = bean.getCpId() != null ? sb.append("&cpId=").append(bean.getCpId()) : sb;
		sb = bean.getCpOrderNumber() != null ? sb.append("&cpOrderNumber=").append(bean.getCpOrderNumber()) : sb;
		sb = bean.getExtInfo() != null ? sb.append("&extInfo=").append(bean.getExtInfo()) : sb;
		sb = bean.getOrderAmount() != null ? sb.append("&orderAmount=").append(bean.getOrderAmount()) : sb;
		sb = bean.getOrderNumber() != null ? sb.append("&orderNumber=").append(bean.getOrderNumber()) : sb;
		sb = bean.getPayTime() != null ? sb.append("&payTime=").append(bean.getPayTime()) : sb;
		sb = bean.getRespCode() != null ? sb.append("&respCode=").append(bean.getRespCode()) : sb;
		sb = bean.getRespMsg() != null ? sb.append("&respMsg=").append(bean.getRespMsg()) : sb;
		sb = bean.getTradeStatus() != null ? sb.append("&tradeStatus=").append(bean.getTradeStatus()) : sb;
		sb = bean.getTradeType() != null ? sb.append("&tradeType=").append(bean.getTradeType()) : sb;
		sb = bean.getUid() != null ? sb.append("&uid=").append(bean.getUid()) : sb;
		LoggerUtil.info(MD5SignatureChecker.class, "vivo initLinkString:"+sb.toString());
		sb.append("&").append(doMD5HexEncrypt(cpKey, DEFAULT_CHARSET).toLowerCase());
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static String createVivoSign(PreChargeFormBean bean,String appId, String cpId,String paymentId,String callBackURL,String date, String cpKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = appId != null 				   && !"".equals(appId)				   ? sb.append("appId=").append(appId) : sb;
		sb = cpId != null 				   && !"".equals(cpId) 				   ? sb.append("&cpId=").append(cpId) : sb;
		sb = paymentId != null 			   && !"".equals(paymentId) 			   ? sb.append("&cpOrderNumber=").append(paymentId) : sb;
		sb = bean.getAttachCode() != null  && !"".equals(bean.getAttachCode())  ? sb.append("&extInfo=").append(bean.getAttachCode()) : sb;
		sb = callBackURL != null 		   && !"".equals(callBackURL) 		   ? sb.append("&notifyUrl=").append(callBackURL) : sb;
		sb = bean.getChargeMoney() != null && !"".equals(bean.getChargeMoney()) ? sb.append("&orderAmount=").append(bean.getChargeMoney().intValue()*100) : sb;
		sb = bean.getProductDesc() != null && !"".equals(bean.getProductDesc()) ? sb.append("&orderDesc=").append(bean.getProductDesc()) : sb;
		sb = date != null 				   && !"".equals(date) 				   ?sb.append("&orderTime=").append(date) : sb;
		sb = bean.getProductName() != null && !"".equals(bean.getProductName()) ? sb.append("&orderTitle=").append(bean.getProductName()) : sb;
		sb = bean.getVar() != null 		   && !"".equals(bean.getVar()) 		   ? sb.append("&version=").append(bean.getVar()) : sb;
		LoggerUtil.info(MD5SignatureChecker.class, "vivoPre initLinkString:"+sb.toString());
		sb.append("&").append(doMD5HexEncrypt(cpKey, DEFAULT_CHARSET).toLowerCase());
		return doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase();
	}
	
	public static boolean vivoPreChargeCheck(PreChargeVivoResBean bean, String cpKey, String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = bean.getAccessKey() != null   && !"".equals(bean.getAccessKey())   ? sb.append("&accessKey=").append(bean.getAccessKey()) : sb;
		sb = bean.getOrderAmount() != null && !"".equals(bean.getOrderAmount()) ? sb.append("&orderAmount=").append(bean.getOrderAmount()) : sb;
		sb = bean.getOrderNumber() != null && !"".equals(bean.getOrderNumber()) ? sb.append("&orderNumber=").append(bean.getOrderNumber()) : sb;
		sb = bean.getRespCode() != null    && !"".equals(bean.getRespCode())    ? sb.append("&respCode=").append(bean.getRespCode()) : sb;
		sb = bean.getRespMsg() != null 	   && !"".equals(bean.getRespMsg()) 	   ? sb.append("&respMsg=").append(bean.getRespMsg()) : sb;
		LoggerUtil.info(MD5SignatureChecker.class, "vivoPreResp initLinkString:"+sb.toString());
		sb.append("&").append(doMD5HexEncrypt(cpKey, DEFAULT_CHARSET).toLowerCase());
		return doCheck(sign, doMD5HexEncrypt(sb.toString().substring(1, sb.toString().length()), DEFAULT_CHARSET).toLowerCase());
	}
	public static boolean jifengChecker(JifengChargingFormBean bean, String cpKey, String sign) throws Exception {
		return doCheck(sign, doMD5HexEncrypt(cpKey + bean.getTime(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean juGameChecker(JuGameChargingFormBean.JugameReqBean bean, String cpId, String juGameCpKey, String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append(cpId);
		sb = sb.append("amount=").append(bean.getAmount());
		sb = sb.append("callbackInfo=").append(bean.getCallbackInfo());
		sb = sb.append("failedDesc=").append(bean.getFailedDesc());
 		sb = sb.append("gameId=").append(bean.getGameId());
		sb = sb.append("orderId=").append(bean.getOrderId());
		sb = sb.append("orderStatus=").append(bean.getOrderStatus());
		sb = sb.append("payWay=").append(bean.getPayWay());
		sb = sb.append("roleId=").append(bean.getRoleId());
		sb = sb.append("serverId=").append(bean.getServerId());
		sb = sb.append("suid=").append(bean.getSuid());
		LoggerUtil.info(MD5SignatureChecker.class, "juGame initLinkString:"+sb.toString());
		sb = sb.append(juGameCpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean liebaoChecker(LiebaoChargingFormBean bean, String liebaoCpKey, String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("orderid=").append(bean.getOrderid());
		sb = sb.append("&username=").append(bean.getUsername());
		sb = sb.append("&gameid=").append(bean.getGameid());
 		sb = sb.append("&roleid=").append(bean.getRoleid());
		sb = sb.append("&serverid=").append(bean.getServerid());
		sb = sb.append("&paytype=").append(bean.getPaytype());
		sb = sb.append("&amount=").append(bean.getAmount());
		sb = sb.append("&paytime=").append(bean.getPaytime());
		sb = sb.append("&attach=").append(bean.getAttach());
		LoggerUtil.info(MD5SignatureChecker.class, "liebao initLinkString:"+sb.toString());
		sb = sb.append("&appkey=").append(liebaoCpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean leyouChecker(LeyouChargingFormBean bean, String leyouCpKey, String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("orderid=").append(URLEncoder.encode(bean.getOrderid(),DEFAULT_CHARSET));
		sb = sb.append("&username=").append(URLEncoder.encode(bean.getUsername(),DEFAULT_CHARSET));
		sb = sb.append("&gameid=").append(URLEncoder.encode(bean.getGameid(),DEFAULT_CHARSET));
 		sb = sb.append("&roleid=").append(URLEncoder.encode(bean.getRoleid(),DEFAULT_CHARSET));
		sb = sb.append("&serverid=").append(URLEncoder.encode(bean.getServerid(),DEFAULT_CHARSET));
		sb = sb.append("&paytype=").append(URLEncoder.encode(bean.getPaytype(),DEFAULT_CHARSET));
		sb = sb.append("&amount=").append(URLEncoder.encode(bean.getAmount(),DEFAULT_CHARSET));
		sb = sb.append("&paytime=").append(URLEncoder.encode(bean.getPaytime(),DEFAULT_CHARSET));
		sb = sb.append("&attach=").append(URLEncoder.encode(bean.getAttach(),DEFAULT_CHARSET));
		sb = sb.append("&appkey=").append(URLEncoder.encode(leyouCpKey,DEFAULT_CHARSET));
		LoggerUtil.info(MD5SignatureChecker.class, "leyou initLinkString:"+sb.toString());
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean xunleiChecker(XunleiChargingFormBean bean, String xunleiCpKey, String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append(bean.getOrderid());
		sb = sb.append(bean.getUser());
		sb = sb.append(bean.getGold());
		sb = sb.append(bean.getMoney());
		sb = sb.append(bean.getTime());
		LoggerUtil.info(MD5SignatureChecker.class, "xunlei initLinkString:"+sb.toString());
		sb = sb.append(xunleiCpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean ccChecker(CCChargingFormBean bean, String ccCpKey, String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = bean.getOrderPrice() != null ? sb.append("orderPrice=").append(bean.getOrderPrice()) : sb;
		sb = bean.getPackageId() != null ? sb.append("&packageId=").append(bean.getPackageId()) : sb;
		sb = bean.getPartnerTransactionNo() != null ? sb.append("&partnerTransactionNo=").append(bean.getPartnerTransactionNo()) : sb;
		sb = bean.getProductId() != null ? sb.append("&productId=").append(bean.getProductId()) : sb;
		sb = bean.getStatusCode() != null ? sb.append("&statusCode=").append(bean.getStatusCode()) : sb;
		sb = bean.getTransactionNo() != null ? sb.append("&transactionNo=").append(bean.getTransactionNo()) : sb;
		LoggerUtil.info(MD5SignatureChecker.class, "cc initLinkString:"+sb.toString());
		sb = sb.append("&").append(ccCpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean iccChecker(ICCChargingFormBean bean, String iccCpKey, String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("game_user_id=").append(bean.getUserName()).append("&");
		sb = sb.append("out_trade_no=").append(bean.getOrderidClient()).append("&");
		sb = sb.append("service=").append("2193006").append("&");
		sb = sb.append("total_fee=").append(""+Long.valueOf(bean.getChargeMoney())*100).append("&");
		sb = sb.append("trade_no=").append("").append("&");
		sb = sb.append("completed=").append("").append("");
		LoggerUtil.info(MD5SignatureChecker.class, "icc initLinkString:"+sb.toString());
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean kaopuChecker(KaopuChargingFormBean bean, String kaopuCpKey, String sign) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = !StringUtils.isEmpty(bean.getUsername()) ? sb.append(bean.getUsername()).append("|") : sb;
		sb = !StringUtils.isEmpty(bean.getKpordernum())  ? sb.append(bean.getKpordernum()).append("|") : sb;
		sb = !StringUtils.isEmpty(bean.getYwordernum())  ? sb.append(bean.getYwordernum()).append("|") : sb;
		sb = !StringUtils.isEmpty(bean.getStatus())  ? sb.append(bean.getStatus()).append("|") : sb;
		sb = !StringUtils.isEmpty(bean.getPaytype())  ? sb.append(bean.getPaytype()).append("|") : sb;
		sb = !StringUtils.isEmpty(bean.getAmount())  ? sb.append(bean.getAmount()).append("|") : sb;
		sb = !StringUtils.isEmpty(bean.getGameserver())  ? sb.append(bean.getGameserver()).append("|") : sb;
		sb =  sb.append(bean.getErrdesc()).append("|");
		sb = !StringUtils.isEmpty(bean.getPaytime())  ? sb.append(bean.getPaytime()).append("|") : sb;
		sb = !StringUtils.isEmpty(bean.getGamename())  ? sb.append(bean.getGamename()).append("|") : sb;
		LoggerUtil.info(MD5SignatureChecker.class, "kaopu initLinkString:"+sb.toString());
		sb =  sb.append(kaopuCpKey);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static String kaopuResutMD5(KaopuResBean bean, String kaopuCpKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = bean.getCode() != null ? sb.append(bean.getCode()).append("|") : sb;
		sb = bean.getMsg()  != null ? sb.append(bean.getMsg() ).append("|") : sb;
		LoggerUtil.info(MD5SignatureChecker.class, "kaopuRes initLinkString:"+sb.toString());
		sb =  sb.append(kaopuCpKey);
		return doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase();
	}
	
	public static boolean jianGuoChecker(JianGuoReqBean bean, String jianGuoKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("order_id=").append(bean.getOrder_id()).append("&");
		sb = sb.append("mem_id=").append(bean.getMem_id()).append("&");
		sb = sb.append("app_id=").append(bean.getApp_id()).append("&");
		sb = sb.append("money=").append(bean.getMoney()).append("&");
		sb = sb.append("order_status=").append(bean.getOrder_status()).append("&");
		sb = sb.append("paytime=").append(bean.getPaytime()).append("&");
		sb = sb.append("attach=").append(bean.getAttach()).append("&");
		LoggerUtil.info(MD5SignatureChecker.class, "jianguo initLinkString:"+sb.toString());
		sb = sb.append("app_key=").append(jianGuoKey);
		return doCheck(bean.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean fiveSevenChecker(FiveSevenKChargingFormBean bean, String fiveSevenKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("app_id=").append(bean.getApp_id()).append("&");
		sb = sb.append("cp_order_id=").append(Common.urlEncode(bean.getCp_order_id(), "utf-8")).append("&");
		sb = sb.append("mem_id=").append(bean.getMem_id()).append("&");
		sb = sb.append("order_id=").append(bean.getOrder_id()).append("&");
		sb = sb.append("order_status=").append(bean.getOrder_status()).append("&");
		sb = sb.append("pay_time=").append(bean.getPay_time()).append("&");
		sb = sb.append("product_id=").append(Common.urlEncode(bean.getProduct_id(), "utf-8")).append("&");
		sb = sb.append("product_name=").append(Common.urlEncode(bean.getProduct_name(), "utf-8")).append("&");
		sb = sb.append("product_price=").append(Common.urlEncode(bean.getProduct_price(), "utf-8")).append("&");
		LoggerUtil.info(MD5SignatureChecker.class, "fiveSeven initLinkString:"+sb.toString());
		sb = sb.append("app_key=").append(fiveSevenKey);
		return doCheck(bean.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}

	public static boolean sevenKChecker(SevenKChargingFormBean form, String key) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(form.getPayorderid());
		sb.append(form.getPaycid());
		sb.append(form.getAccount());
		sb.append(form.getSid());
		sb.append(form.getRoleid());
		sb.append(form.getGoodsid());
		sb.append(form.getMoney());
		sb.append(form.getCoins());
		sb.append(form.getTime());
		sb.append(form.getCustominfo());
		LoggerUtil.info(MD5SignatureChecker.class, "7K initLinkString:"+sb.toString());
		sb.append(key);
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}
	
	public static boolean paojiaoIosChecker(PaoJiaoIOSFormBean bean, String paojiaoIosKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("cpOrderNo=").append(bean.getCpOrderNo());
		sb = bean.getExt() == null ? sb:sb.append("ext=").append(bean.getExt());
		sb = sb.append("finishedTime=").append(bean.getFinishedTime());
		sb = bean.getGameId() == null ? sb:sb.append("gameId=").append(bean.getGameId());
		sb = sb.append("orderNo=").append(bean.getOrderNo());
		sb = bean.getPrice() == null ? sb:sb.append("price=").append(bean.getPrice());
		sb = sb.append("roleId=").append(bean.getRoleId());
		sb = sb.append("serverId=").append(bean.getServerId());
		sb = sb.append("uid=").append(bean.getUid());
		LoggerUtil.info(MD5SignatureChecker.class, "paojiao initLinkString:"+sb.toString());
		return doCheck(bean.getSign(), doMD5HexEncrypt(Common.urlEncode(sb.toString(),"UTF-8") + paojiaoIosKey, DEFAULT_CHARSET).toLowerCase());
	}

	public static boolean paojiaoAndroidChecker(PaoJiaoAndroidFormBean bean, String paojiaoAndroidKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("uid=").append(bean.getUid());
		sb = sb.append("price=").append(bean.getPrice());
		sb = sb.append("orderNo=").append(bean.getOrderNo());
		sb = sb.append("remark=").append(bean.getRemark());
		sb = sb.append("status=").append(bean.getStatus());
		sb = sb.append("subject=").append(bean.getSubject());
		sb = sb.append("gameId=").append(bean.getGameId());
		sb = sb.append("payTime=").append(bean.getPayTime());
		sb = sb.append("ext=").append(bean.getExt());
		LoggerUtil.info(MD5SignatureChecker.class, "paojiaoAndriod initLinkString:"+sb.toString());
		sb = sb.append(paojiaoAndroidKey);
		return doCheck(bean.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean haodongIosChecker(HaodongIOSFormBean bean,String haodongIosKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append(bean.getOrderno());
		sb = sb.append(bean.getAmount());
		sb = sb.append(bean.getCount());
		sb = sb.append(bean.getExt());
		sb = sb.append(bean.getNotifyurl());
		sb = sb.append(bean.getCode());
		LoggerUtil.info(MD5SignatureChecker.class, "haodongIos initLinkString:"+sb.toString());
		sb = sb.append(haodongIosKey);
		return doCheck(bean.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	public static boolean haodongAndroidQMSJChecker(HaodongAndroidQMSJFormBean bean,String haodongAndroidQMSJKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append(bean.getOrderno());
		sb = sb.append(bean.getAmount());
		sb = sb.append(bean.getCount());
		sb = sb.append(bean.getExt());
		sb = sb.append(bean.getNotifyurl());
		sb = sb.append(bean.getCode());
		LoggerUtil.info(MD5SignatureChecker.class, "haodongAndroidQMSJ initLinkString:"+sb.toString());
		sb = sb.append(haodongAndroidQMSJKey);
		return doCheck(bean.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean haodongAndroidSSJYChecker(HaodongAndroidSSJYFormBean bean,String haodongAndroidSSJYKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append(bean.getOrderno());
		sb = sb.append(bean.getAmount());
		sb = sb.append(bean.getCount());
		sb = sb.append(bean.getExt());
		sb = sb.append(bean.getNotifyurl());
		sb = sb.append(bean.getCode());
		LoggerUtil.info(MD5SignatureChecker.class, "haodongAndroidSSJY initLinkString:"+sb.toString());
		sb = sb.append(haodongAndroidSSJYKey);
		return doCheck(bean.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean haodongAndroidChecker(HaodongAndroidFormBean bean) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append(bean.getCodeNo());
		sb = sb.append(bean.getOpenId());
		sb = sb.append(bean.getTradeNo());
		sb = sb.append(bean.getAmount());
		sb = sb.append(bean.getPayWay());
		sb = sb.append(bean.getNotifyUr());
		LoggerUtil.info(MD5SignatureChecker.class, "haodongAndroid initLinkString:"+sb.toString());
		return doCheck(bean.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}

	/**
	 * eagleHaodong验签
	 * @param form
	 * @param md5key
	 * @return
	 */
	public static boolean eagleHaodongChecker(EagleHaodongFormBean form, String md5key) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("channelID=").append(form.getChannelID());
		sb.append("&currency=").append(form.getCurrency());
		sb.append("&extension=").append(form.getExtension());
		sb.append("&gameID=").append(form.getGameID());
		sb.append("&money=").append(form.getMoney());
		sb.append("&orderID=").append(form.getOrderID());
		sb.append("&productID=").append(form.getProductID());
		sb.append("&serverID=").append(form.getServerID());
		sb.append("&userID=").append(form.getUserID());
		sb.append("&").append(md5key);
		LoggerUtil.info(MD5SignatureChecker.class, "eagleHaodong initLinkString:"+sb.toString());
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}

	public static boolean cgameChecker(CGameFormBean bean, String cgameKey) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("ext1=").append(bean.getExt1()).append("&");
		sb = sb.append("ext2=").append(bean.getExt2()).append("&");
		sb = sb.append("from=").append(bean.getFrom()).append("&");
		sb = sb.append("gameid=").append(bean.getGameid()).append("&");
		sb = sb.append("money=").append(bean.getMoney()).append("&");
		sb = sb.append("orderid=").append(bean.getOrderid()).append("&");
		sb = sb.append("outorderid=").append(bean.getOutorderid()).append("&");
		sb = sb.append("role=").append(bean.getRole()).append("&");
		sb = sb.append("serverid=").append(bean.getServerid()).append("&");
		sb = sb.append("time=").append(bean.getTime()).append("&");
		sb = sb.append("userid=").append(bean.getUserid()).append("&");
		LoggerUtil.info(MD5SignatureChecker.class, "cgame initLinkString:"+sb.toString());
		sb = sb.append(cgameKey);
		return doCheck(bean.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	
	public static boolean youximaoChecker(YouximaoChargingFormBean bean) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
        sb.append(bean.getCodeNo()).append(bean.getOpenId()).append(bean.getTradeNo()).append(bean.getAmount());
        sb.append(bean.getPayWay()).append(bean.getNotifyUrl());
		LoggerUtil.info(MD5SignatureChecker.class, "youximao initLinkString:"+sb.toString());
		boolean bSuc = doCheck(bean.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
		return bSuc;
	}

	public static boolean youximaoChecker2(YouximaoChargingFormBean2 form, String key) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("amount=").append(form.getAmount());
		sb.append("&codeNo=").append(form.getCodeNo());
		if(StringUtils.isEmpty(form.getExt())) {
			sb.append("&ext=");
		}else {
			sb.append("&ext=").append(form.getExt());
		}
		sb.append("&notifyUrl=").append(form.getNotifyUrl());
		sb.append("&openId=").append(form.getOpenId());
		sb.append("&payWay=").append(form.getPayWay());
		sb.append("&tradeNo=").append(form.getTradeNo());
		LoggerUtil.info(MD5SignatureChecker.class, "youximao2 initLinkString:"+sb.toString());
		sb.append(key);
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	public static boolean phpChargingChecker(PushOrderWithMQInfoPOJO pojo,String sign,String key) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append(pojo.getGameId());
		sb = sb.append(pojo.getUserId());
		sb = sb.append(pojo.getUserName());
		sb = sb.append(pojo.getGatewayId());
		sb = sb.append(pojo.getChargeOrderCode());
		sb = sb.append(pojo.getChargeDetailId());
		LoggerUtil.info(MD5SignatureChecker.class, "php initLinkString:"+sb.toString());
		sb = sb.append(key);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
	public static String doMD5HexEncrypt(String content, String charset) throws UnsupportedEncodingException {
		return DigestUtils.md5Hex(content.getBytes(charset));
	}

	private static boolean doCheck(String origSign, String destSign) {
		return Arrays.equals(origSign.getBytes(), destSign.getBytes());
	}

	//360验签
	public static boolean qihuChecker(QihuChargingFormBean form, String key,
			String sign) throws UnsupportedEncodingException {
		String formJson = JsonUtil.convertBeanToJson(form);
		HashMap<String, String> map = JsonUtil.convertJsonToBean(formJson, HashMap.class);
		map.remove("cpName");
		map.remove("gameName");
		map.remove("sign_return");
		map.remove("sign");
		//进行拼串,迭代的方式
		StringBuffer sb = new StringBuffer();
		TreeMap< String, String> treeMap = new TreeMap<String,String>(map);
		Set<Entry<String,String>> set = treeMap.entrySet();
		Iterator<Entry<String, String>> it = set.iterator();
		while(it.hasNext()){
			Entry<String, String> next = it.next();
			String k = next.getKey();
			String v = next.getValue();
			if(v != null && !"".equals(v) && !v.equals("0")){
				sb.append(v).append("#");
			}
		}
		LoggerUtil.info(MD5SignatureChecker.class, "qihu initLinkString:"+sb.toString());
		sb.append(key);
		return doCheck(sign, doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	//草花验签
	public static boolean caohuaChecker(CaoHuaChargingFormBean form, String key) throws Exception {
		String fromJson = JsonUtil.convertBeanToJson(form);
		Map<String, String> map = JsonUtil.convertJsonToBean(fromJson, HashMap.class);
		map.remove("gameName");
		map.remove("cpName");
		map.remove("sign");
		TreeMap<String, String> treeMap = new TreeMap<String, String>(map);
		Set<Entry<String, String>> entries = treeMap.entrySet();
		Iterator<Entry<String, String>> it = entries.iterator();
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			sb.append(k + "=" + v +"&");
		}
		String str = sb.substring(0,sb.lastIndexOf("&"))+key;

		LoggerUtil.info(MD5SignatureChecker.class, "caohua initLinkString:"+ sb.toString());
		return doCheck(form.getSign(), doMD5HexEncrypt(str, DEFAULT_CHARSET).toUpperCase());
	}

	//松鼠回调验签
	public static boolean songshuChecker(SongshuFormBean form, String key) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("orderID=").append(form.getOrderID())
				.append("cpOrderID=").append(form.getCpOrderID())
				.append("userID=").append(form.getUserID())
				.append("appID=").append(form.getAppID())
				.append("serverID=").append(form.getServerID())
				.append("money=").append(form.getMoney())
				.append("currency=").append(form.getCurrency())
				.append("extension=").append(form.getExtension())
				.append("appKey=").append(key);
		LoggerUtil.info(MD5SignatureChecker.class, "songshu initLinkString:"+ sb.toString());
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));

	}
	/**
	 * 一点渠道-掌宜付聚合支付平台 回调验签
	 * @param form
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean yidianChecker(YiDianChargingFormBean form, String key) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("app_id=").append(form.getApp_id());
		if (!StringUtils.isEmpty(form.getCode())) {
			sb.append("&code=").append(form.getCode());
		}
		if (!StringUtils.isEmpty(form.getInvoice_no())) {
			sb.append("&invoice_no=").append(form.getInvoice_no());
		}
		if (!StringUtils.isEmpty(form.getMoney())) {
			sb.append("&money=").append(form.getMoney());
		}
		if (!StringUtils.isEmpty(form.getOut_trade_no())) {
			sb.append("&out_trade_no=").append(form.getOut_trade_no());
		}
		if (!StringUtils.isEmpty(form.getPay_way())) {
			sb.append("&pay_way=").append(form.getPay_way());
		}
		if (!StringUtils.isEmpty(form.getQn())) {
			sb.append("&qn=").append(form.getQn());
		}
		if (!StringUtils.isEmpty(form.getUp_invoice_no())) {
			sb.append("&up_invoice_no=").append(form.getUp_invoice_no());
		}
		sb.append("&key=").append(key);
		LoggerUtil.info(MD5SignatureChecker.class, "yidian initLinkString:" + sb.toString());
		return doCheck(form.getSign().toLowerCase(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	//魅族聚合回调验签
    public static boolean meizuJuheChecker(MeizuJuHeForBean form, String key) throws UnsupportedEncodingException {
		String fromJson = JsonUtil.convertBeanToJson(form);
		Map<String, String> map = JsonUtil.convertJsonToBean(fromJson, HashMap.class);
		map.remove("gameName");
		map.remove("cpName");
		map.remove("sign");
		TreeMap<String, String> treeMap = new TreeMap<String, String>(map);
		Set<Entry<String, String>> entries = treeMap.entrySet();
		Iterator<Entry<String, String>> it = entries.iterator();
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()){
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			sb.append(k + "=" + v +"&");
		}
		LoggerUtil.info(MD5SignatureChecker.class, "meizuJuhe initLinkString:"+ sb.toString());
		String str = sb.substring(0,sb.lastIndexOf("&")) + ":" + key;
		return doCheck(form.getSign(), doMD5HexEncrypt(str, DEFAULT_CHARSET));
    }


	/**
	 * 一点渠道-交易查询签名生成
	 * @param payment
	 * @param appId
	 * @param key
	 * @param partnerId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createYidianCheckerOrderSign(PrePaymentPOJO payment,String appId, String key,String partnerId) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("app_id=").append(appId)
		.append("&out_trade_no=").append(payment.getPaymentId())
		.append("&partner_id=").append(partnerId)
		.append("&key=").append(key);
		LoggerUtil.info(MD5SignatureChecker.class, "yidian initLinkString:"+ sb.toString());
		return doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET);

	}
	/**
	 * 一点渠道-支付请求签名生成
	 * @param payment
	 * @param appId
	 * @param key
	 * @param partnerId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createYidianCreateOrderSign(PreChargeFormBean payment,String paymentId,String appId, String key, String partnerId) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		//转为分单位
		Double money = (payment.getChargeMoney()*100);
		sb.append("app_id=").append(appId)
		.append("&money=").append(money.intValue())
		.append("&out_trade_no=").append(paymentId)
		.append("&partner_id=").append(partnerId)
		.append("&subject=").append(URLEncoder.encode(payment.getProductName(),DEFAULT_CHARSET))
		.append("&wap_type=").append(payment.getExpandInfo())
		.append("&key=").append(key);
		LoggerUtil.info(MD5SignatureChecker.class, "yidian initLinkString:"+ sb.toString());
		return doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET);
	}

	/**
	 * uc单机版回调验签
	 * @param form
	 * @param key
	 * @return
	 */
	public static boolean ucStandAloneChecker(UCStandAloneFormBean form, String key) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("amount=").append(form.getData().getAmount());
		if(!StringUtils.isEmpty(form.getData().getAttachInfo())){
			sb.append("attachInfo=").append(form.getData().getAttachInfo());
		}
		sb.append("failedDesc=").append(form.getData().getFailedDesc());
		sb.append("gameId=").append(form.getData().getGameId());
		sb.append("orderId=").append(form.getData().getOrderId());
		sb.append("orderStatus=").append(form.getData().getOrderStatus());
		sb.append("payType=").append(form.getData().getPayType());
		sb.append("tradeId=").append(form.getData().getTradeId());
		sb.append("tradeTime=").append(form.getData().getTradeTime());
		LoggerUtil.info(MD5SignatureChecker.class, "ucStandAlone initLinkString:" + sb.toString());
		sb.append(key);
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	/**
	 * 爱奇艺聚合回调验签
	 * @param form
	 * @param key
	 * @return
	 */
    public static boolean aiqiyiJuheChecker(AiqiyiJuheFormBean form, String secreKkey) throws UnsupportedEncodingException {
    	//将请求参数转成map
		HashMap<String, String> map = JsonUtil.convertJsonToBean(JsonUtil.convertBeanToJson(form), HashMap.class);
		map.remove("cpName");
		map.remove("gameName");
		TreeMap< String, String> sortMap = new TreeMap<String,String>(map);
		sortMap.put("sign_key", secreKkey);
		StringBuilder sb = new StringBuilder();
		String val = null;
		for(String key : sortMap.keySet()){
			if("sign".equals(key)){
				continue;
			}
			val = sortMap.get(key);
			if(StringUtils.isEmpty(val)){//排查空
				continue;
			}
			sb.append(key).append("=").append(val).append("&");
		}
		LoggerUtil.info(MD5SignatureChecker.class, "aiqiyiJuheChecker initLinkString:" + sb.toString());
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.substring(0, sb.length() - 1).toString(), DEFAULT_CHARSET));
    }

	/**
	 * 9377回调验签
	 * @param form
	 * @param key
	 * @return
	 */
    public static boolean ntssChecker(NTSSChargingFormBean form, String key) throws UnsupportedEncodingException {
    	StringBuffer sb = new StringBuffer();
    	sb.append(form.getOrder_id()).append(form.getUsername()).append(form.getServer_id()).append(form.getMoney()).append(form.getExtra_info())
				.append(form.getTime());
		LoggerUtil.info(MD5SignatureChecker.class, "ntssChecker initLinkString:" + sb.toString());
		sb.append(key);
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	/**
	 *蝶恋回调加密验签
	 * @param form
	 * @param key
	 * @return
	 */
	public static boolean dielianChecker(DieLianChargingFormBean form, String key) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append("app=").append(form.getApp());
		if(StringUtils.isEmpty(form.getCbi())){
			sb.append("&cbi=");
		}else {
			sb.append("&cbi=").append(form.getCbi());
		}
		sb.append("&ct=").append(form.getCt());
		sb.append("&fee=").append(form.getFee());
		sb.append("&pt=").append(form.getPt());
		sb.append("&sdk=").append(form.getSdk());
		sb.append("&ssid=").append(form.getSsid());
		sb.append("&st=").append(form.getSt());
		sb.append("&tcd=").append(form.getTcd());
		sb.append("&uid=").append(form.getUid());
		sb.append("&ver=").append(form.getVer());
		LoggerUtil.info(MD5SignatureChecker.class, "dielianChecker initLinkString:" + sb.toString());
		sb.append(key);
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET));
	}

	/**
	 * 花生互娱下单加密
	 * @param map
	 * @param appkey
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createhuaShengSign(HashMap<String, Object> map, String appkey) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append("userID=").append(map.get("userID")).append("&")
				.append("productID=").append(map.get("productID")).append("&")
				.append("productName=").append(URLDecoder.decode(map.get("productName").toString(),"UTF-8")).append("&")
				.append("productDesc=").append(URLDecoder.decode(map.get("productDesc").toString(),"UTF-8")).append("&")
				.append("money=").append(map.get("money")).append("&")
				.append("roleID=").append(map.get("roleID")).append("&")
				.append("roleName=").append(URLDecoder.decode(map.get("roleName").toString(),"UTF-8")).append("&")
				.append("serverID=").append(map.get("serverID")).append("&")
				.append("serverName=").append(URLDecoder.decode(map.get("serverName").toString(),"UTF-8")).append("&")
				.append("extension=").append(map.get("extension"));
		if(!StringUtils.isEmpty(String.valueOf(map.get("notifyUrl")))) {
			sb.append("&notifyUrl=").append(map.get("notifyUrl"));
		}
		sb.append(appkey);
		LoggerUtil.info(MD5SignatureChecker.class, "daTangPreChargeSign initLinkString:" + sb.toString());
		/*String encoded = URLEncoder.encode(sb.toString(), "UTF-8");*/
		return doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase();
	}

	/**
	 * 大唐剑侠回调验签
	 * @param form
	 * @param key
	 * @return
	 */
	public static boolean huaShengChecker(HuaShengChargingFormBean form, String key) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append("channelID=").append(form.getChannelID()).append("&")
				.append("currency=").append(form.getCurrency()).append("&")
				.append("extension=").append(form.getExtension()).append("&")
				.append("gameID=").append(form.getGameID()).append("&")
				.append("money=").append(form.getMoney()).append("&")
				.append("orderID=").append(form.getOrderID()).append("&")
				.append("productID=").append(form.getProductID()).append("&")
				.append("serverID=").append(form.getServerID()).append("&")
				.append("userID=").append(form.getUserID()).append("&")
				.append(key);
		LoggerUtil.info(MD5SignatureChecker.class, "daTangChecker initLinkString:" + sb.toString());
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}

	/**
	 * 极速渠道回调验签
	 * @param form
	 * @param key
	 * @return
	 */
	public static boolean jisuChecker(JisuFormBean form, String key) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append("amount=").append(form.getAmount());
		sb.append("&cp_info=");
		if(!StringUtils.isEmpty(form.getCp_order())){
			sb.append(form.getCp_info());
		}
		sb.append("&cp_order=").append(form.getCp_order());
		sb.append("&order=").append(form.getOrder());
		sb.append("&time=");
		if(!StringUtils.isEmpty(form.getTime())) {
			sb.append(form.getTime());
		}
		sb.append("&uid=").append(form.getUid());
		sb.append("&key=").append(key);
		LoggerUtil.info(MD5SignatureChecker.class, "jisuChecker initLinkString:" + sb.toString());
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}

	/**
	 * 一点自己渠道的回调验签
	 * @param form
	 * @param key
	 * @return
	 */
	public static boolean yidianSelfChecker(YidianselfFormBean form, String key) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append("app_id=").append(form.getApp_id());
		sb.append("&cp_order_id=").append(form.getCp_order_id());
		sb.append("&mem_id=").append(form.getMem_id());
		sb.append("&order_id=").append(form.getOrder_id());
		sb.append("&order_status=").append(form.getOrder_status());
		sb.append("&pay_time=").append(form.getPay_time());
		sb.append("&product_id=").append(form.getProduct_id());
		sb.append("&product_name=").append(form.getProduct_name());
		sb.append("&product_price=").append(form.getProduct_price());
		sb.append("&app_key=").append(key);
		LoggerUtil.info(MD5SignatureChecker.class, "yidianSelfChecker initLinkString:" + sb.toString());
		return doCheck(form.getSign(), doMD5HexEncrypt(sb.toString(), DEFAULT_CHARSET).toLowerCase());
	}
}
