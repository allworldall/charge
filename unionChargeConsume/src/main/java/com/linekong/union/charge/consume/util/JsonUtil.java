package com.linekong.union.charge.consume.util;

import com.google.gson.Gson;
import com.linekong.union.charge.consume.web.jsonbean.reqbean.BaiduReqBean;

public final class JsonUtil {
          
   /**
    * Object 转换为json
    * @param Object obj
    * 				obj对象
    * @return String json
    */
   public static String convertBeanToJson(Object obj){
	   return obj == null ? null : new Gson().toJson(obj); 
   }
   /**
    * gson进行json转换对象
    * @param String json
    * 				json格式数据
    * @param Class  clazz
    * 				需要转换的对象
    */
   public static <T> T convertJsonToBean(String json,Class<? extends T> clazz){
	   Gson gson = new Gson();
	   return  json == null ? null : gson.fromJson(json, clazz); 
   }
   
   
   public static void main(String[] args) {
	String str = 
			"{\"MerchandiseName\":\"980元宝\",\"OrderMoney\":\"98.00\",\"StartDateTime\":\"2016-12-12 09:53:03\",\"BankDateTime\":\"2016-12-12 09:54:11\",\"OrderStatus\":1,\"StatusMsg\":\"成功\",\"ExtInfo\":\"115143:2893343502:115143:android:2015849848:980\",\"VoucherMoney\":0,\"UID\":\"2893343502\"}";
	BaiduReqBean jsonbean = JsonUtil.convertJsonToBean(str, BaiduReqBean.class);
	System.out.println(jsonbean.getMerchandiseName());
   }
}
