package com.lrowy.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class UrlUtil {
    private final static Set<String> PublicSuffixSet = new HashSet<String>(Arrays.asList(new String("com|org|net|gov|edu|co|tv|mobi|info|asia|xxx|onion|cn|com.cn|edu.cn|gov.cn|net.cn|org.cn|jp|kr|tw|com.hk|hk|com.hk|org.hk|se|com.se|org.se").split("\\|")));
    private static Pattern IP_PATTERN = Pattern.compile("(\\d{1,3}\\.){3}(\\d{1,3})");

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

    public static String getRescFullPath(String BaseUrl, String href) throws MalformedURLException {
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

    public static String getDomainName(String url) throws MalformedURLException {
        URL u = new URL(url);
        return u.getHost();
    }

    public static String getTopDomainName(String url) throws MalformedURLException {
        URL u = new URL(url);
        String host = u.getHost();
        if (host.endsWith(".")){
            host = host.substring(0, host.length() - 1);
        }
        if (IP_PATTERN.matcher(host).matches()){
            return host;
        }
        int index = 0;
        String candidate = host;
        for (; index >= 0;) {
            index = candidate.indexOf('.');
            String subCandidate = candidate.substring(index + 1);
            if (PublicSuffixSet.contains(subCandidate)) {
                return candidate;
            }
            candidate = subCandidate;
        }
        return candidate;
    }
}
