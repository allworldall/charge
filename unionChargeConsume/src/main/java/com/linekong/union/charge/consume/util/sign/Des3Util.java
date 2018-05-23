package com.linekong.union.charge.consume.util.sign;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class Des3Util {
   
	private static final String DES_ALGORITHM = "DESede";
	private static final String DEFAULT_CHARSET = "utf-8";
	
	/**
	 * DES3解密，返回数值为UTF-8字符 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws UnsupportedEncodingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * 
	 * */
	public static String decrypt(String src,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, IOException {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		SecretKey deskey = new SecretKeySpec(key.getBytes(), DES_ALGORITHM);
		Cipher c1 = Cipher.getInstance(DES_ALGORITHM);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		BASE64Decoder decoder = new BASE64Decoder();
		return new String(c1.doFinal(decoder.decodeBuffer(src)),DEFAULT_CHARSET);	
	}
	
	/**
	 * DES3加密，返回数值为Base64字符串
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws UnsupportedEncodingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * */
	public static String encrypt(String src,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		 SecretKey deskey = new SecretKeySpec(key.getBytes(), DES_ALGORITHM); 
         Cipher c1 = Cipher.getInstance(DES_ALGORITHM);
         c1.init(Cipher.ENCRYPT_MODE,deskey );
         BASE64Encoder encoder = new BASE64Encoder();
         return encoder.encode(c1.doFinal(src.getBytes(DEFAULT_CHARSET))) ;
	}
}
