package com.lrowy;

import com.lrowy.pojo.bookmark.Bookmark;
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
        System.out.println(fs.getFaviconUrl(new Bookmark("https://blog.csdn.net/qq_37385726/article/details/82020214")));
    }
}