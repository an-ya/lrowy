package com.lrowy;

import com.lrowy.service.FaviconService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LrowyApplicationTests {
    @Resource
    private FaviconService fs;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testHttpClient() {
        System.out.println(fs.getFaviconUrl("https://blog.csdn.net/liuchuanhong1/article/details/68194036"));
    }
}