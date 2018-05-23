package com.linekong.union.charge.consume.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.linekong.union.charge.consume.web.formbean.HuaWeiChargingFormBean;
import com.linekong.union.charge.consume.web.formbean.OppoChargingFormBean;

public class UrlToObject {
	
	/**
	 * 华为url请求参数转换为form对象
	 */
	public static HuaWeiChargingFormBean huaweiUrlToObject(String url){
		Map<String,Object> map = new HashMap<String,Object>();
		String [] arr = url.split("&");
		for(int i = 0;i < arr.length;i++){
			String array [] = arr[i].split("=");
			map.put(array[0], array[1]);
		}
		HuaWeiChargingFormBean form = new HuaWeiChargingFormBean();
		Field[] fields = form.getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){  
        	Field f = fields[i];
        	f.setAccessible(true);
        	try {
        		String type = f.getType().toString();
        		if(type.endsWith("String")){
        			f.set(form, String.valueOf(map.get(f.getName())));
        		}
        		
        		if(type.endsWith("int") || type.endsWith("Integer")){
        			f.set(form, Integer.parseInt(String.valueOf(map.get(f.getName())).replaceAll("\n", "")));
        		}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        } 
        return form;
		
	}
	/**
	 * 华为url请求参数转换为form对象
	 */
	public static OppoChargingFormBean oppoUrlToObject(String url){
		Map<String,Object> map = new HashMap<String,Object>();
		String [] arr = url.split("&");
		for(int i = 0;i < arr.length;i++){
			String array [] = arr[i].split("=");
			map.put(array[0], array[1]);
		}
		OppoChargingFormBean form = new OppoChargingFormBean();
		Field[] fields = form.getClass().getDeclaredFields();
        for(int i=0;i<fields.length;i++){  
        	Field f = fields[i];
        	f.setAccessible(true);
        	try {
        		String type = f.getType().toString();
        		if(type.endsWith("String")){
        			f.set(form, String.valueOf(map.get(f.getName())));
        		}
        		if(type.endsWith("int") || type.endsWith("Integer")){
        			f.set(form, Integer.parseInt(String.valueOf(map.get(f.getName())).replaceAll("\n", "")));
        		}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        } 
        return form;
	}
//	public static void main(String[] args) {
//		String url = "result=0&userName=900086000023008738&productName=60元宝&payType=4\n" +
//				"&amount=6.00&orderId=A510247366201612120817102955&notifyTime=1481501847416" + 
//				"&requestId=201612120817102955&extReserved=953022:190086000021196878:953022:android:2014274120:60" + 
//				"&sign=Rz4EWZ8oGlRzFZ7FjpiNLYejaL9i141QDveq7+Wbdy3A9SnJIHzU5Q841lWo2Crr72nI2df+praMxjdGO8WxFQ==";
//		HuaWeiChargingFormBean a = huaweiUrlToObject(url);
//		System.out.println(a.getSign());
//	}

}
