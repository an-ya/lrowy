package com.lrowy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lrowy.dao.CaptchaDao;
import com.lrowy.pojo.captcha.Captcha;
import com.lrowy.pojo.common.http.HttpResult;

import com.lrowy.service.CommentService;
import com.lrowy.service.HttpAPIService;
import com.lrowy.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LrowyApplicationTests {
    @Resource
    private HttpAPIService httpAPIService;
    @Resource
    private CommentService commentService;
    @Autowired
    private CaptchaDao captchaDao;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testHttpClient() {
        try {
            HttpResult hr = httpAPIService.doGet("https://common.cnblogs.com/favicon.ico", false);
            System.out.println(hr.getCode());
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void testComment() {
        System.out.println(commentService.getCommentByIssue(1, "Article"));
    }

    @Test
    public void testCaptcha() {
        Captcha captcha = captchaDao.findCaptchaByTarget("95989@gmail.com");
        try {
            String content = JsonUtil.generate(captcha);
            System.out.println(content);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}