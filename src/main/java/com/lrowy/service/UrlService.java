package com.lrowy.service;

import com.lrowy.pojo.common.http.HttpResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

@Service
public class UrlService {
    @Resource
    private HttpAPIService httpAPIService;

    public String getFaviconUrl(String urlString) {
        HttpResult hr;
        try {
            URL url = new URL(urlString);
            StringBuilder BaseUrl = new StringBuilder();
            BaseUrl.append(url.getProtocol());
            BaseUrl.append("://");
            BaseUrl.append(url.getHost());
            if (url.getPort() > 0) {
                BaseUrl.append(":");
                BaseUrl.append(url.getPort());
            }

            StringBuilder faviconUrl = new StringBuilder();
            try {
                hr = httpAPIService.doGet(BaseUrl.toString());
                Document document = Jsoup.parse(hr.getBody());
                Elements head = document.head().children();
                String rel;
                for (Element e : head) {
                    rel = e.attr("rel");
                    if (e.tagName().equals("link") && (rel.equalsIgnoreCase("shortcut icon") || rel.equalsIgnoreCase("icon"))) {
                        String href = e.attr("href");
                        if (!Pattern.matches("(http://|https://).*", href)) {
                            if (Pattern.matches("//.*", href)) {
                                faviconUrl.append(url.getProtocol());
                                faviconUrl.append(":");
                            } else if (Pattern.matches("/.*", href)) {
                                faviconUrl.append(BaseUrl);
                            } else {
                                faviconUrl.append(BaseUrl);
                                faviconUrl.append("/");
                            }
                        }
                        faviconUrl.append(href);
                        break;
                    }
                }
                return faviconUrl.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
