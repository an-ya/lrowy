package com.lrowy.service;

import com.lrowy.pojo.common.http.HttpResult;

import com.lrowy.utils.UrlUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HttpAPIService {
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig intraNetConfig;
    @Autowired
    private RequestConfig extraNetConfig;

    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doGet(String url, boolean extraNetFlag) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        if (extraNetFlag) {
            httpGet.setConfig(extraNetConfig);
        } else {
            httpGet.setConfig(intraNetConfig);
        }

        // 设置Header
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");
        httpGet.setHeader("Referer", UrlUtil.getBaseUrl(url)); // 解决根据referer反爬虫

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        if (response.getStatusLine().getStatusCode() == 403) {
            return doGet(url, extraNetFlag, url);
        } else {
            return new HttpResult(response.getStatusLine().getStatusCode(), response.getEntity(), "UTF-8");
        }
    }

    private HttpResult doGet(String url, boolean extraNetFlag, String referUrl) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        if (extraNetFlag) {
            httpGet.setConfig(extraNetConfig);
        } else {
            httpGet.setConfig(intraNetConfig);
        }

        // 设置Header
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36");
        httpGet.setHeader("Referer", referUrl); // 解决根据referer反爬虫

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        return new HttpResult(response.getStatusLine().getStatusCode(), response.getEntity(), "UTF-8");
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doGet(String url, Map<String, Object> map, boolean extraNetFlag) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString(), extraNetFlag);
    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(intraNetConfig);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), response.getEntity(), "UTF-8");
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, null);
    }
}
