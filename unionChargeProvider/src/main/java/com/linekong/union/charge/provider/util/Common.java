package com.linekong.union.charge.provider.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Common {

    /**
     * 获取本机IP
     */
    public static String getLocalIp() {
        String localIp = "127.0.0.1";
        try {
            InetAddress address = InetAddress.getLocalHost();// 获取的是本地的IP地址
            localIp = address.getHostAddress();// 192.168.0.121

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return localIp;
    }

    public static void main(String[] args) {
        System.out.println(getLocalIp());
    }
}
