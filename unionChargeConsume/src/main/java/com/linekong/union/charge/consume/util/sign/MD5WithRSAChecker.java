package com.linekong.union.charge.consume.util.sign;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;

import com.linekong.union.charge.consume.util.coolpad.Base64;
import com.linekong.union.charge.consume.util.coolpad.MD5;
import com.linekong.union.charge.consume.util.coolpad.RSAUtil;
import com.linekong.union.charge.consume.util.iapppay.RSA;
import com.linekong.union.charge.consume.web.formbean.SamsungChargingFormBean;

public class MD5WithRSAChecker {
	private static final String DEFAULT_CHARSET = "utf-8";
	
	 public static boolean kupaiChecker(String json,String publicKey,String sign){
		   try {
				String md5Str = MD5.md5Digest(json);

				String decodeBaseStr = Base64.decode(publicKey);

				String[] decodeBaseVec = decodeBaseStr.replace('+', '#').split("#");

				String privateKey = decodeBaseVec[0];
				String modkey = decodeBaseVec[1];

				String reqMd5 = RSAUtil.decrypt(sign, new BigInteger(privateKey),
						new BigInteger(modkey));

				if (md5Str.equals(reqMd5)) {
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
	   }
	 public static boolean samsungCheck(SamsungChargingFormBean bean, String signature, String publicKey) throws UnsupportedEncodingException{
		 try {
			 bean.setTransdata( URLDecoder.decode(bean.getTransdata(), "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}  
			if (RSA.verify(bean.getTransdata(), signature, publicKey, DEFAULT_CHARSET)) {
				return true;
			} else {
				return false;
			}
	}
	 public static boolean yingyonghuiChecker(String transData,String publicKey,String sign){
		   try {
			   transData = URLDecoder.decode(transData, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}  
			if (RSA.verify(transData, sign, publicKey, DEFAULT_CHARSET)) {
				return true;
			} else {
				return false;
			}
	   }
}
