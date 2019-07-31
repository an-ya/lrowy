package com.lrowy.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class UrlUtil {
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
}
