package com.lrowy.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class UrlUtil {
    public static Boolean isUrl(String url) {
        url = url.toLowerCase();
        String regex = "^((https|http)://)"  // 协议
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IPv4地址
                + "|" // 允许IP或域名
                + "([0-9a-z_!~*'()-]+\\.)*" // 主机名.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // 一级域名
                + "(:[0-9]{1,5})?" // 端口号，最大为65535，5位数
                + "((/?)|" // 斜杆
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return url.matches(regex);
    }

    public static Map<String, Object> parse(String paramsString) {
        Map<String, Object> result = new HashMap<>();
        if (paramsString != null && !paramsString.trim().equals("")) {
            String[] params = paramsString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                result.put(keyValue[0], keyValue[1]);
            }
        }
        return result;
    }

    public static String getBaseUrl(String url) throws MalformedURLException {
        URL u = new URL(url);
        StringBuilder BaseUrl = new StringBuilder();
        BaseUrl.append(u.getProtocol());
        BaseUrl.append("://");
        BaseUrl.append(u.getHost());
        if (u.getPort() > 0) {
            BaseUrl.append(":");
            BaseUrl.append(u.getPort());
        }
        return BaseUrl.toString();
    }

    public static String getFullPath(String BaseUrl, String href) throws MalformedURLException {
        URL url = new URL(BaseUrl);
        StringBuilder fullUrl = new StringBuilder();
        if (!Pattern.matches("(http://|https://).*", href)) {
            if (Pattern.matches("//.*", href)) {
                fullUrl.append(url.getProtocol());
                fullUrl.append(":");
            } else if (Pattern.matches("/.*", href)) {
                fullUrl.append(BaseUrl);
            } else {
                fullUrl.append(BaseUrl);
                fullUrl.append("/");
            }
        }
        fullUrl.append(href);
        return fullUrl.toString();
    }
}
