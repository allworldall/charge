package com.linekong.union.charge.consume.util.sign;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.web.formbean.XiaoMiChargingFormBean;

public class HmacSHA1SignatureChecker {
	private static final String DEFAULT_SHA1_ALGORITHM = "HmacSHA1";
	private static final String DEFAULT_CHARSET = "UTF-8";

	public static boolean xiaomiChecker(XiaoMiChargingFormBean form, String cpKey, String sign) {
		Map<String, String> sortMap = new HashMap<String, String>();
		@SuppressWarnings("unused")
		String temp = null;
		temp = form.getAppId() != null ? sortMap.put("appId", form.getAppId()) : null;
		temp = form.getCpOrderId() != null ? sortMap.put("cpOrderId", form.getCpOrderId()) : null;
		temp = form.getCpUserInfo() != null ? sortMap.put("cpUserInfo", form.getCpUserInfo()) : null;
		if(form.getOrderConsumeType() != null && !form.getOrderConsumeType().equals("")){
			temp = form.getOrderConsumeType() != null? sortMap.put("orderConsumeType", form.getOrderConsumeType()) :null;
		}
		temp = form.getOrderId() != null ? sortMap.put("orderId", form.getOrderId()) : null;
		temp = form.getOrderStatus() != null ? sortMap.put("orderStatus", form.getOrderStatus()) : null;
		if(form.getPartnerGiftConsume() != null && !form.getPartnerGiftConsume().equals("")){
			temp = form.getPartnerGiftConsume() != null ? sortMap.put("partnerGiftConsume", form.getPartnerGiftConsume()) :null;
		}
		temp = form.getPayFee() != null ? sortMap.put("payFee", form.getPayFee()) : null;
		temp = form.getPayTime() != null ? sortMap.put("payTime", form.getPayTime()) : null;
		temp = form.getProductCode() != null ? sortMap.put("productCode", form.getProductCode()) : null;
		temp = form.getProductCount() != null ? sortMap.put("productCount", form.getProductCount()) : null;
		temp = form.getProductName() != null ? sortMap.put("productName", form.getProductName()) : null;
		temp = form.getUid() != null ? sortMap.put("uid", form.getUid()) : null;
		String[] sortParams = new String[sortMap.size()];
		sortMap.keySet().toArray(sortParams);
		Arrays.sort(sortParams);
		StringBuilder sb = new StringBuilder();
		for (String para : sortParams) {
			sb.append(para).append("=").append(sortMap.get(para)).append("&");
		}
		String content = sb.toString();
		return doCheck(content.substring(0, content.length() - 1), sign, cpKey, DEFAULT_SHA1_ALGORITHM,
				DEFAULT_CHARSET);

	}

	
	public static boolean doCheck(String content, String signature, String sha1Key, String alogrithm, String charset) {
		try {
			SecretKeySpec key = new SecretKeySpec((sha1Key).getBytes(charset), alogrithm);
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
		} catch (NoSuchAlgorithmException e) {
			LoggerUtil.error(HmacSHA1SignatureChecker.class, e.getMessage());
			return false;
		} catch (InvalidKeyException e) {
			LoggerUtil.error(HmacSHA1SignatureChecker.class, e.getMessage());
			return false;
		}
	}
}
