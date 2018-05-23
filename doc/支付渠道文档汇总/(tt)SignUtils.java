package com.linekong.union.charge.consume.util.tt;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * 签名工具类
 * 
 * @author TT
 *
 */

public class SignUtils {

	public static String sign(String data, String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update((data + key).getBytes("UTF-8"));
		return Base64.encodeBase64String(md5.digest());
	}


}
