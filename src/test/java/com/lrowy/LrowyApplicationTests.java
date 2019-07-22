package com.lrowy;

import com.lrowy.service.UrlService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LrowyApplicationTests {
    @Resource
    private UrlService urlService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testHttpClient() {
        System.out.println(urlService.getFaviconUrl("https://blog.csdn.net/liuchuanhong1/article/details/68194036"));
    }
}