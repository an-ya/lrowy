package com.lrowy.service;

import com.lrowy.pojo.captcha.ReCaptchaResponse;
import com.lrowy.pojo.common.http.HttpResult;
import com.lrowy.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReCaptchaService {
    @Value("${google.reCaptcha.secret}")
    private String secret;
    @Resource
    private HttpAPIService httpAPIService;

    ReCaptchaResponse deserialize(String content) throws IOException {
        return JsonUtil.parse(content, ReCaptchaResponse.class);
    }

    public String verify(String token, String ip) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("secret", secret);
        params.put("response", token);
        params.put("remoteip", ip);
        HttpResult hr = httpAPIService.doPost("https://www.recaptcha.net/recaptcha/api/siteverify", params);
        return hr.getEntityString();
    }
}
