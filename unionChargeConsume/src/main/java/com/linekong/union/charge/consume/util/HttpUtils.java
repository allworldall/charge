package com.linekong.union.charge.consume.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.linekong.union.charge.consume.util.log.LoggerUtil;


public class HttpUtils {

	private static Logger log = Logger.getLogger("payInfo");

	public static String httpPost(String url, Map<String, String> params) {
		URL u = null;
		StringBuffer buffer = new StringBuffer();
		HttpURLConnection conn = null;
		StringBuilder log = new StringBuilder();
		log.append("POST,url:"+url);
		StringBuffer sb = new StringBuffer();
		//拼接请求参数
		if (!params.isEmpty()) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
		}
		sb.substring(0, sb.length() - 1);
		log.append(",param:"+sb.toString());
		// 发送数据
		try {
			u = new URL(url);
			//打开和URL的连接
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			//获取HttpURLConnection对象的输出流
			OutputStreamWriter osw = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
			log.append(",result:");
			// 读取数据
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
			br.close();
			log.append(buffer.toString());
			LoggerUtil.info(HttpUtils.class, log.toString());
		} catch (Exception e) {
			LoggerUtil.error(HttpUtils.class, "POST,url:"+url+",error:"+e.toString()+",param:"+sb.toString());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return buffer.toString();
	}
	
	public static String httpPost1(String url, String body,String channelName) {
		URL u = null;
		HttpURLConnection conn = null;
		 StringBuilder sb = new StringBuilder();
         sb.append("POST_"+channelName+":");
         sb.append(",url:"+url);
         sb.append(",param:"+body);
         StringBuffer buffer = new StringBuffer();
		// 发送数据，给苹果
		try {
			u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			OutputStreamWriter osw = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			osw.write(body);
			osw.flush();
			osw.close();
			// 读取数据
			sb.append(",result:");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				buffer.append(temp);
			}
			br.close();
			LoggerUtil.info(HttpUtils.class, sb.toString());
			
		} catch (Exception e) {
			LoggerUtil.error(HttpUtils.class, "POST,url:"+url+",error:"+e.toString()+",param:"+sb.toString());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return buffer.toString();
	}
	public static String httpPostApple(String url, String body,String channelName) {
		long begin = System.currentTimeMillis();
		URL u = null;
		HttpURLConnection conn = null;
		 StringBuilder sb = new StringBuilder();
         sb.append("requestApple");
         sb.append(",url:"+url);
         sb.append(",param:"+body);
         StringBuffer buffer = new StringBuffer();
		// 发送数据，给苹果
		try {
			u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			OutputStreamWriter osw = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			osw.write(body);
			osw.flush();
			osw.close();
			// 读取数据
			sb.append(",result:");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				buffer.append(temp);
			}
			br.close();
			sb.append(",time=").append(System.currentTimeMillis() - begin);
			log.info( sb.toString());
			
		} catch (Exception e) {
			sb.append(",time=").append(System.currentTimeMillis() - begin);
			log.error(sb.toString()+" error info:"+e.toString());
			buffer = new StringBuffer();
			buffer.append("{\"status\":"+Constant.HTTP_EXCEPION+"}");
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return buffer.toString();
	}
	public static String httpGet(String url, String param,String channelName) throws Exception {
            String result = "";
            String urlNameString = url + "?"+ param;
            //String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，  
            // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection  
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();  
            // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到服务器  
            connection.connect();  
            // 取得输入流，并使用Reader读取  
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码  
            StringBuilder sb = new StringBuilder();
            sb.append("GET_"+channelName);
            sb.append(",url:"+url);
            sb.append(",param:"+param);
            String lines;  
            while ((lines = reader.readLine()) != null) {  
                // lines = new String(lines.getBytes(), "utf-8"); 
            	result = lines;
            }  
            reader.close();  
            // 断开连接  
            connection.disconnect();  
            sb.append(",result:"+result);
            LoggerUtil.info(HttpUtils.class, sb.toString());
            return result;
    }

	public static String httpGetParamMap(String url, Map<String, Object> map,String channelName) throws Exception {
		String result = "";
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		String param = "";
		try {
			//解析map
			StringBuffer paramMap = new StringBuffer();
			Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
			while(iterator.hasNext()) {
				Entry<String, Object> next = iterator.next();
				paramMap.append(next.getKey()).append("=").append(next.getValue().toString()).append("&");
			}
			param = paramMap.toString().substring(0, paramMap.toString().length()-1);
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			connection = (HttpURLConnection) realUrl.openConnection();
			connection.connect();
			// 取得输入流，并使用Reader读取
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
			StringBuilder sb = new StringBuilder();
			sb.append("GET_" + channelName);
			sb.append(",url:" + url);
			sb.append(",param:" + param);
			StringBuffer sbuf = new StringBuffer();
			String lines;
			while ((lines = reader.readLine()) != null) {
				sbuf.append(lines);
			}
			result = sbuf.toString();
			sb.append(",result:" + result);
			LoggerUtil.info(HttpUtils.class, sb.toString());
		}catch (Exception e) {
			LoggerUtil.error(HttpUtils.class, "channel name="+channelName + ",http error,error info:" + e.toString() + ",url="+url + ",param="+param);
		}finally {
			if(reader != null) {
				reader.close();
			}
			if(connection != null) {
				connection.disconnect();
			}
		}
		return result;
	}


}
