package com.linekong.union.charge.consume.util.sign;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.web.formbean.YoukuChargingFormBean;

public class HmacMD5SignatureChecker {

	private static final String DEFAULT_SHA1_ALGORITHM = "HmacMD5";
	private static final String DEFAULT_CHARSET = "UTF-8";

	public static boolean youkuChecker(YoukuChargingFormBean bean,String callbackUrl,String cpKey,String sign){
		StringBuilder sb = new StringBuilder();
		sb.append(callbackUrl);
		sb.append("?apporderID=");
		sb.append(bean.getApporderID());
		sb.append("&price=");
		sb.append(bean.getPrice());
		sb.append("&uid=");
		sb.append(bean.getUid());
		return doCheck(sb.toString(),sign,cpKey,DEFAULT_SHA1_ALGORITHM,DEFAULT_CHARSET);
	}
	
	public static boolean doCheck(String content, String signature, String md5Key, String alogrithm, String charset) {
		try {
			SecretKeySpec key = new SecretKeySpec((md5Key).getBytes(charset), alogrithm);
			Mac mac = Mac.getInstance(alogrithm);
			mac.init(key);
			byte[] bytes = mac.doFinal(content.getBytes(charset));
			String result = "";
			for(byte b : bytes){
			   if(Integer.toHexString(0xFF & b).length()==1){
				   result += "0"+Integer.toHexString(0xFF & b);
			   } else{
				   result += Integer.toHexString(0xFF & b);
			   }
			 }
			return Arrays.equals(result.getBytes(charset), signature.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.error(HmacSHA1SignatureChecker.class, e.getMessage());
			return false;
		} catch (InvalidKeyException e) {
			LoggerUtil.error(HmacSHA1SignatureChecker.class, e.getMessage());
			return false;
		} catch (NoSuchAlgorithmException e) {
			LoggerUtil.error(HmacSHA1SignatureChecker.class, e.getMessage());
			return false;
		}
	}
}
