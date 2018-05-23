package com.linekong.union.charge.consume.util.sign;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.iharder.Base64;

import org.apache.commons.lang.CharEncoding;

import sun.misc.BASE64Decoder;

import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;


import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.web.formbean.ICCChargingFormBean;
import com.linekong.union.charge.consume.web.formbean.JRTTChargingFormBean;
import com.linekong.union.charge.consume.web.formbean.JinLiChargingFormBean;
import com.linekong.union.charge.consume.web.formbean.KYChargingFormBean;
import com.linekong.union.charge.consume.web.formbean.NeihanChargingFormBean;
import com.linekong.union.charge.consume.web.formbean.OppoChargingFormBean;
import com.linekong.union.charge.consume.web.formbean.PreChargeFormBean;
import com.linekong.union.charge.consume.web.formbean.QingYuanIOSChargingFormBean;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.JinliExpandInfo;


public final class RSASignatureChecker {

	private static final BASE64Decoder BASE64DECODER = new BASE64Decoder();
	private static final String DEFAULT_RSA_ALGORITHM="SHA1WithRSA";
	private static final String DEFAULT_CHARSET = "utf-8";
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	private static final String SIGN_ALGORITHMS256 = "SHA256WithRSA";

   
	
   public static boolean wandoujiaChecker(String jsonContent,String publicKey,String sign){
	   return doCheck(jsonContent,sign,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);
   }
	
   public static boolean huaweiChecker(String postContent,String publicKey, String sign, String signType) {
       if(postContent == null){
    	   return false;
       } 
	   String[] parameters = postContent.split("&");
       Map<String,String> sortMap  = new HashMap<String,String>();
	   for(String parameter:parameters){
		   if(parameter.startsWith("sign")){
    		   continue;
		   }
    	   String[] keyAndValue = parameter.split("=");
    	   if(keyAndValue[0].equals("signType")||keyAndValue[0].equals("sign"))
    		   continue;
    	   if(keyAndValue.length == 1 ){
    		   sortMap.put(keyAndValue[0], "");
    	   }else if(keyAndValue.length == 2) {
    		   sortMap.put(keyAndValue[0], keyAndValue[1].equals("null")? "":keyAndValue[1]);
    	   }else{
    		   return false;
    	   }
       }
	   String[] sortParams = new String[sortMap.size()];
	   sortMap.keySet().toArray(sortParams);
	   Arrays.sort(sortParams);
	   StringBuilder sb = new StringBuilder();
	   for(String para:sortParams){
		   sb.append(para).append("=").append(sortMap.get(para)).append("&");
	   }
	   String content = sb.toString();
	   if(signType.equalsIgnoreCase("RSA256")){
		   return doCheck(content.substring(0,content.length()-1),sign,publicKey,SIGN_ALGORITHMS256,DEFAULT_CHARSET);
	   }
	   return doCheck(content.substring(0,content.length()-1),sign,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);
	}

   
   public static boolean lenovoChecker(String json,String publicKey,String sign){
	   return doCheck(json,sign,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);
   }
   
   public static boolean JRTTCheckcer(JRTTChargingFormBean form,String publicKey,String sign){
	   Map<String,String> sortMap = new HashMap<String,String>();
	   @SuppressWarnings("unused")
	   String temp;
	   sortMap.put("notify_id", form.getNotify_id());
	   sortMap.put("notify_type", form.getNotify_type());
	   sortMap.put("notify_time", form.getNotify_time());
	   sortMap.put("trade_status", form.getTrade_status());
	   sortMap.put("way", form.getWay());
	   sortMap.put("client_id", form.getClient_id());
	   sortMap.put("out_trade_no", form.getOut_trade_no());
	   temp = form.getTrade_no() != null ? sortMap.put("trade_no", form.getTrade_no()):null;  
	   temp = form.getPay_time() != null ? sortMap.put("pay_time", form.getPay_time()):null;
	   temp = form.getTotal_fee()!= null? sortMap.put("total_fee", String.valueOf(form.getTotal_fee())):null;
	   sortMap.put("buyer_id", form.getBuyer_id());
	   String[] sortParams = new String[sortMap.size()];
	   sortMap.keySet().toArray(sortParams);
	   Arrays.sort(sortParams);
	   StringBuilder sb = new StringBuilder();
	   for(String para:sortParams){
	         sb.append(para).append("=").append(sortMap.get(para)).append("&");
	   }
	   String content = sb.toString();
	   return doCheck(content.substring(0,content.length()-1),sign,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);
   }
	
  
   public static boolean jinlinChecker(JinLiChargingFormBean form,String publicKey,String sign){
		StringBuilder sb = new StringBuilder();
		sb.append("api_key=").append(form.getApi_key());
		sb.append("&close_time=").append(form.getClose_time());
		sb.append("&create_time=").append(form.getCreate_time());
		sb.append("&deal_price=").append(form.getDeal_price());
		sb.append("&out_order_no=").append(form.getOut_order_no());
		sb.append("&pay_channel=").append(form.getPay_channel());
		sb.append("&submit_time=").append(form.getSubmit_time());
		sb.append("&user_id=").append(form.getUser_id());
		return doCheck(sb.toString(),sign,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);
	}
	
   public static String createJinliSign(JinliExpandInfo info, PreChargeFormBean form,String paymentId,String date, String apiKey, String privateKey) throws Exception{
	   StringBuilder sb = new StringBuilder();
	   sb.append(apiKey);
	   sb.append(form.getChargeMoney());
	   sb.append(info.getPayType());
	   sb.append(paymentId);
	   sb.append(form.getProductName());
	   sb.append(date);
	   sb.append(form.getChargeMoney());
	   sb.append(info.getJl_uid());
	   return sign(sb.toString(),privateKey);
   }
	
	public static boolean oppoCheck(OppoChargingFormBean bean, String signature, String publicKey) {
		StringBuilder sb = new StringBuilder();
		sb.append("notifyId=").append(bean.getNotifyId());
		sb.append("&partnerOrder=").append(bean.getPartnerOrder());
		sb.append("&productName=").append(bean.getProductName());
		sb.append("&productDesc=").append(bean.getProductDesc());
		sb.append("&price=").append(bean.getPrice());
		sb.append("&count=").append(bean.getCount());
		sb.append("&attach=").append(bean.getAttach());
		return doCheck(sb.toString(),signature,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);
	}
	
	public static boolean kyCheck(KYChargingFormBean bean, String signature, String publicKey){
		StringBuilder sb = new StringBuilder();
		sb.append("dealseq=").append(bean.getDealseq());
		sb.append("&notify_data=").append(bean.getNotify_data());
		sb.append("&orderid=").append(bean.getOrderid());
		sb.append("&subject=").append(bean.getSubject());
		sb.append("&uid=").append(bean.getUid());
		sb.append("&v=").append(bean.getV());
		return doCheck(sb.toString(),signature,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);
	}
	public static boolean neihanCheck(NeihanChargingFormBean bean, String signature, String publicKey){
		StringBuilder sb = new StringBuilder();
		sb.append("buyer_id=").append(bean.getBuyer_id());
		sb.append("&client_id=").append(bean.getClient_id());
		sb.append("&notify_id=").append(bean.getNotify_id());
		sb.append("&notify_time=").append(bean.getNotify_time());
		sb.append("&notify_type=").append(bean.getNotify_type());
		sb.append("&out_trade_no=").append(bean.getOut_trade_no());
		sb.append("&pay_time=").append(bean.getPay_time());
		sb.append("&total_fee=").append(bean.getTotal_fee());
		sb.append("&trade_no=").append(bean.getTrade_no());
		sb.append("&trade_status=").append(bean.getTrade_status());
		sb.append("&way=").append(bean.getWay());
		return doCheck(sb.toString(),signature,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);	
	}
	
	
	public static boolean iccChecker(ICCChargingFormBean bean, String publicKey, String signature) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("game_user_id=").append(bean.getUserName()).append("&");
		sb = sb.append("out_trade_no=").append(bean.getOrderidClient()).append("&");
		sb = sb.append("service=").append("2193006").append("&");
		sb = sb.append("total_fee=").append(""+Long.valueOf(bean.getChargeMoney())*100).append("&");
		sb = sb.append("trade_no=").append("").append("&");
		sb = sb.append("completed=").append("").append("");
		return doCheck(sb.toString(),signature,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);
	}
	
	public static boolean qingyuanIOSKeyChecker(QingYuanIOSChargingFormBean bean, String publicKey, String signature) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb = sb.append("appid=").append(bean.getAppid()).append("&");
		sb = sb.append("attach=").append(bean.getAttach()).append("&");
		sb = sb.append("channel=").append(bean.getChannel()).append("&");
		sb = sb.append("createat=").append(bean.getCreateat()).append("&");
		sb = sb.append("orderid=").append(bean.getOrderid()).append("&");
		sb = sb.append("ordername=").append(bean.getOrdername()).append("&");
		sb = sb.append("payat=").append(bean.getPayat()).append("&");
		sb = sb.append("price=").append(bean.getPrice()).append("&");
		sb = sb.append("sandbox=").append(bean.getSandbox()).append("&");
		sb = sb.append("startat=").append(bean.getStartat()).append("&");
		sb = sb.append("status=").append(bean.getStatus()).append("&");
		sb = sb.append("transid=").append(bean.getTransid()).append("&");
		sb = sb.append("userid=").append(bean.getUserid()).append("&");
		sb = sb.append("username=").append(bean.getUsername());
		return doCheck(sb.toString(),signature,publicKey,DEFAULT_RSA_ALGORITHM,DEFAULT_CHARSET);
	}
	
	private static boolean doCheck(String content, String signature, String publicKey,String alogrithm,String charset) {
		LoggerUtil.info(RSASignatureChecker.class, "content:"+content+"\n--signature:"+signature+"\n--publicKey:"+publicKey+"\n--alogrithm:"+alogrithm+"\n--charset:"+charset);
		System.out.println("content:"+content+"\n--signature:"+signature+"\n--publicKey:"+publicKey+"\n--alogrithm:"+alogrithm+"\n--charset:"+charset);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = BASE64DECODER.decodeBuffer(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature sign = java.security.Signature.getInstance(alogrithm);
			sign.initVerify(pubKey);
			sign.update(content.getBytes(charset));
			boolean bverify = sign.verify(BASE64DECODER.decodeBuffer(signature));
			return bverify;
		} catch (NoSuchAlgorithmException e) {
			LoggerUtil.error(RSASignatureChecker.class, e.getMessage());
			return false;
		} catch (IOException e) {
			LoggerUtil.error(RSASignatureChecker.class, e.getMessage());
			return false;
		} catch (InvalidKeySpecException e) {
			LoggerUtil.error(RSASignatureChecker.class, e.getMessage());
			return false;
		} catch (InvalidKeyException e) {
			LoggerUtil.error(RSASignatureChecker.class, e.getMessage());
			return false;
		} catch (SignatureException e) {
			LoggerUtil.error(RSASignatureChecker.class, e.getMessage());
			return false;
		}
	}
	/**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param encode
	 *            字符集编码
	 * @return 签名值
	 * @throws Exception 
	 */
	public static String sign(String content, String privateKey) throws Exception {
		String charset = CharEncoding.UTF_8;
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);

		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
		signature.initSign(priKey);
		signature.update(content.getBytes(charset));
		byte[] signed = signature.sign();
		return Base64.encodeBytes(signed);
	}


	/**
	 * 华为预支付加密
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String signHuawei(String content, String privateKey) throws Exception {
		String charset = CharEncoding.UTF_8;
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
		KeyFactory keyf = KeyFactory.getInstance("RSA");
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);

		java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSA");
		signature.initSign(priKey);
		signature.update(content.getBytes(charset));
		byte[] signed = signature.sign();
		return Base64.encodeBytes(signed);
	}
//

}
