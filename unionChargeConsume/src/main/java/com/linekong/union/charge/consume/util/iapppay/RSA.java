package com.linekong.union.charge.consume.util.iapppay;


import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSA 
{
	private static final String  SIGN_ALGORITHMS = "MD5WithRSA";
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param ali_public_key  爱贝公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	public static String str;
	public static boolean verify(String content, String sign, String iapp_pub_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(iapp_pub_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			return signature.verify( Base64.decode(sign) );
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String sign(String content, String privateKey, String input_charset)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory          keyf        = KeyFactory.getInstance("RSA");
        	PrivateKey          priKey 		= keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );
            byte[] signed = signature.sign();
            return Base64.encode(signed);
          
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }

	public static void main(String[] args) {
		System.out.println(sign("{\"appid\":\"500137718\",\"waresid\":1,\"cporderid\":\"2018022718030552\n" +
				"48\",\"price\":30.0,\"currency\":\"RMB\",\"appuserid\":\"26269446\",\"cpprivateinfo\":\"201802271803055248\",\"notifyurl\":\"http://unionpay.8864.com/unionChargeConsume/samsungCharge/cqzj2zj/samsung\"}",
				"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIypzrlUDud5un1dkszr7TzGojyEWmCkeMEaHWeF8jbB9DWrMak/wMnPepSIJWvS5+sGbMVN/7uo8UvmIPoxt0I316PgcHkSFBz9ticsFbeZwpyoul6YFohX7ppdJX5w6PNTNPkAKZdtXpb1oeGMHS8vls5GFcpyKyNbAxRnUzvHAgMBAAECgYBHGm8M75i5GadTR8G1JG0/k4je3mhxAbKoqfLDKcpOM6ymlGr+CuQBH4fvHQMNhgcFnrHM6qiiy7ttil6RGiBv7CwTzBAWDwkPdJ+3aTOmQkhHe8vkHIUgCNf7GT4j/sgYO01DEpmYlde2mD5sO+GpnCF0iOSxHsl6MxBnI+QFgQJBAOwGfLEXgH6jkbyrgjPNg3tZm3fNuUH8aBTKLo0qNIG/Wh7RMZE6B0mYdM6DEPEd89jnrL1S76V+BDE/G+M8588CQQCYkUravyT4VodwZxjgdRb0kZt24SXbz5jESSyPblvTAJ5u5EFYVcpBchWqU8K9YriK9fj2LK0mHGQ+ZL/qr3KJAkBG2Muk/MD9QmwEjhbPJelJpWQdWgs522ICm43NodWQ/Lc4eOLADQMS/EhTlNQJDEyd1yzqx3JX4rOvdJoSY73RAkBh5wJY4sXPkn8xEwd0qc6EPJe9rncD2fCXLLmqhM/p1ADW+iQwkkTKwHHEriFONHZlvbf4CO1HgnqVS0OYTuO5AkEA05P0xSJvxZ0E2vHjeiJVPvFYIfPI6j3BTd7NEU01RKEjOwhQWQRMNj7ARsSuqLvSfwiIk2vrDKImlMYZo1Bopg==","utf-8"));
	}
	
	public static String md5s(String plainText) {
		String buff = null;
		  try {
		   MessageDigest md = MessageDigest.getInstance("MD5");
		   md.update(plainText.getBytes());
		   byte b[] = md.digest();
		   int i;

		   StringBuffer buf = new StringBuffer("");
		   for (int offset = 0; offset < b.length; offset++) {
		    i = b[offset];
		    if (i < 0)
		     i += 256;
		    if (i < 16)
		     buf.append("0");
		    buf.append(Integer.toHexString(i));
		   }
		   buff =buf.toString();
		    Base64.encode(buff.getBytes());
		    System.out.println("base64:"+ Base64.encode(buff.getBytes()));
		   str = buf.toString();
		   System.out.println("result: " + buf.toString());// 32位的加密
		   System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
		  } catch (NoSuchAlgorithmException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();

		  }
		return Base64.encode(buff.getBytes());
	
	
	
	
  }
	
}
