package com.lrowy.service;

import com.lrowy.pojo.common.captcha.Captcha;
import com.lrowy.pojo.common.http.HttpResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleReCaptchaVerifyService {
    @Value("${google.reCaptcha.secret}")
    private String secret;
    @Resource
    private HttpAPIService httpAPIService;

    public String verify(String token, Captcha captcha) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("secret", secret);
        params.put("response", token);
        params.put("remoteip", captcha.getIp());
        HttpResult hr = httpAPIService.doPost("https://www.google.com/recaptcha/api/siteverify", params);
        return hr.getEntityString();
    }
}
