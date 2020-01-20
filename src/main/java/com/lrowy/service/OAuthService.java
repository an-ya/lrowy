package com.lrowy.service;

import com.lrowy.pojo.common.http.HttpResult;
import com.lrowy.utils.UrlUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {
    @Resource
    private HttpAPIService httpAPIService;

    private String getToken(String code) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", "2aa8575f003ba2c492cc");
        params.put("client_secret", "804de68eb1ec684657e685e8cf706a9f2b20a222");
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
