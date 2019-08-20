package com.lrowy;

import com.lrowy.pojo.common.http.HttpResult;

import com.lrowy.service.HttpAPIService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LrowyApplicationTests {
    @Resource
    private HttpAPIService httpAPIService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testHttpClient() {
        try {
            HttpResult hr = httpAPIService.doGet("https://common.cnblogs.com/favicon.ico", false);
            System.out.println(hr.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}