package com.goode.loader.util;

import java.net.InetAddress;

/**
 * @author HX0013522
 * @date 2019-05-14 19:14:14
 */
public class StringUtil {

    /**
     * 获取本地服务器名称
     *
     * @return
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
