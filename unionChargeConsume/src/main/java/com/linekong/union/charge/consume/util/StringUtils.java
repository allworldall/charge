package com.linekong.union.charge.consume.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

import com.sun.mail.util.BASE64EncoderStream;
import com.thoughtworks.xstream.core.util.Base64Encoder;
/**
 * 字符串工具类
 */
public class StringUtils {
	/**
     * 将字符串有某种编码转变成另一种编码
     * @param string 编码的字符串
     * @param originCharset 原始编码格式
     * @param targetCharset 目标编码格式
     * @return String 编码后的字符串
     */
    public static String encodeString(String string,Charset originCharset,Charset targetCharset){
        return string=new String(string.getBytes(originCharset),targetCharset);
    }
     
    /**
     * URL编码
     * @param string 编码字符串
     * @param charset 编码格式
     * @return String
     */
    @SuppressWarnings("deprecation")
    public static String encodeUrl(String string,String charset){
        if(null!=charset&&!charset.isEmpty()){
            try {
                return URLEncoder.encode(string,charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return URLEncoder.encode(string);
    }
     
    /**
     * URL编码
     * @param string 解码字符串
     * @param charset 解码格式
     * @return String
     */
    @SuppressWarnings("deprecation")
    public static String decodeUrl(String string,String charset){
        if(null!=charset&&!charset.isEmpty()){
            try {
                return URLDecoder.decode(string,charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return URLDecoder.decode(string);
    }
    /**
     * 判断字符串是否是空的
     * 方法摘自commons.lang
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
     
     /**
     * <p>判断字符串是否是""," ",null,注意和isEmpty的区别</p>
     * 方法摘自commons.lang
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 带空格的字符串，将空格变成加号
     * @param string
     * @return
     */
    public static String blank2Plus(String string){
		String b = string.trim();
		String[] arr = b.split("\\s+");
		String str = "";
		for(int i=0;i<arr.length;i++){
			if(i==arr.length-1){
				str += arr[i];
				break;
			}
			str += arr[i].trim()+"+";
		}
		return str;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
		String content = "liYHOdvUdCjny0dVue-1125-2017103012";
	    //byte[] base = Base64.encodeBase64(content.getBytes());
	   // String encode =null;
	    
		String	encode = URLEncoder.encode(content,"utf-8");
		//	System.out.println(new String(base, "utf-8"));
		
	    System.out.println(encode);
    }
}
