package com.linekong.union.charge.consume.util.sign;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

import com.linekong.union.charge.consume.util.log.LoggerUtil;
import com.linekong.union.charge.consume.web.formbean.SinaChargingFormBean;

public class SHA1SignatureChecker {

	private static final String DEFAULT_CHARSET = "UTF-8";
	
	public static boolean sinaChecker(SinaChargingFormBean form,String appkey,String signature){
		StringBuilder sb = new StringBuilder();
		sb.append("actual_amount").append("|").append(form.getActual_amount()).append("|");
		sb.append("amount").append("|").append(form.getAmount()).append("|");
		sb.append("order_id").append("|").append(form.getOrder_id()).append("|");
		sb.append("order_uid").append("|").append(form.getOrder_uid()).append("|");
		sb.append("pt").append("|").append(form.getPt()).append("|");
		sb.append("source").append("|").append(form.getSource()).append("|");
	    sb.append(appkey);
		try {
			return doCheck(signature,doSHA1(sb.toString(),DEFAULT_CHARSET),DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.error(SHA1SignatureChecker.class, e.getMessage());
			return false;
		}
	}
	
	
	@SuppressWarnings("deprecation")
	private static String doSHA1(String content,String charset) throws UnsupportedEncodingException {
		return DigestUtils.shaHex(content.getBytes(charset));
	}
	
	private static boolean doCheck(String OriginSign,String DesctSign,String charset) throws UnsupportedEncodingException{
	    return Arrays.equals(OriginSign.getBytes(charset),DesctSign.getBytes(charset));
	}
}
