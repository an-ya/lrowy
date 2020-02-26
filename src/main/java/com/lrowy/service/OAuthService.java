package com.lrowy.service;

import com.lrowy.pojo.common.http.HttpResult;
import com.lrowy.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {
    @Value("${github.oauth.clientId}")
    private String clientId;
    @Value("${github.oauth.clientSecret}")
    private String clientSecret;
    @Resource
    private HttpAPIService httpAPIService;

    private String getToken(String code) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", code);
        HttpResult hr = httpAPIService.doPost("https://github.com/login/oauth/access_token", params);
        return hr.getEntityString();
    }

    public String getUserInfo(String code) {
        try {
            String paramsString = getToken(code);
            Map<String, Object> params = UrlUtil.parse(paramsString);
            System.out.println(params);
            HttpResult hr = httpAPIService.doGet("https://api.github.com/user", params, false);
            System.out.println(hr.getEntityString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
