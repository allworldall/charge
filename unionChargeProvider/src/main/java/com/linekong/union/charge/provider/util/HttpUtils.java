package com.linekong.union.charge.provider.util;

import com.linekong.union.charge.provider.util.log.LoggerUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpUtils {
    public static String httpPost(String url, Map<String, String> params) {
        URL u = null;
        HttpURLConnection conn = null;
        StringBuffer buffer = null;
        StringBuilder log = new StringBuilder();
        log.append("post_url:" + url);

        StringBuffer sb = new StringBuffer();
        if (!params.isEmpty()) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
        }
        sb.substring(0, sb.length() - 1);
        log.append(",param:" + sb.toString());
        // 发送数据
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
            osw.write(sb.toString());
            osw.flush();
            osw.close();
            //读取数据
            buffer = new StringBuffer();
            log.append(",result:");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                log.append(temp);
                buffer.append(temp);
            }

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
}
