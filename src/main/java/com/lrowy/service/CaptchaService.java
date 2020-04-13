package com.lrowy.service;

import com.lrowy.dao.CaptchaDao;
import com.lrowy.pojo.captcha.Captcha;
import com.lrowy.pojo.captcha.ReCaptchaResponse;
import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Random;

@Service
public class CaptchaService {
    @Value("${captcha.ipDayLimit}")
    private Integer ipDayLimit;
    @Value("${captcha.emailDayLimit}")
    private Integer emailDayLimit;
    @Autowired
    private CaptchaDao captchaDao;
    @Autowired
    private ReCaptchaService reCaptchaService;
    @Autowired
    private EmailService emailService;

    private String getCaptcha() {
        String sources = "0123456789";
        Random rand = new Random();
        StringBuilder flag = new StringBuilder();
        for (int j = 0; j < 6; j++) {
            flag.append(sources.charAt(rand.nextInt(9)));
        }
        return flag.toString();
    }

    private String createRandom(boolean numberFlag, int length) {
        StringBuilder retStr;
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = new StringBuilder();
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) count++;
                retStr.append(strTable.charAt(intR));
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr.toString();
    }

    public BaseResponse<Captcha> sendCaptcha(String email, String ip, String token) {
        BaseResponse<Captcha> br = new BaseResponse<>();

        Captcha captcha = captchaDao.findCaptchaByTarget(email);
        if (captcha != null && captcha.inOneMinute()) {
            br.setInfo(SystemConstant.CAPTCHA_EMAIL_MINUTE_LIMIT);
            return br;
        }

        List<Captcha> ipCaptchaToday = captchaDao.findCaptchaByIpToday(ip);
        List<Captcha> targetCaptchaToday = captchaDao.findCaptchaByTargetToday(email);
        if (ipCaptchaToday.size() >= ipDayLimit) {
            br.setInfo(SystemConstant.CAPTCHA_IP_DAY_LIMIT);
        } else if (targetCaptchaToday.size() >= emailDayLimit) {
            br.setInfo(SystemConstant.CAPTCHA_EMAIL_DAY_LIMIT);
        } else {
            try {
                String content = reCaptchaService.verify(token, ip);
                ReCaptchaResponse reCaptchaResponse = reCaptchaService.deserialize(content);
                if (reCaptchaResponse.getScore() >= 0.5) {
                    captcha = new Captcha();
                    captcha.setIp(ip);
                    captcha.setCode(getCaptcha());
                    captcha.setSendMode("Email");
                    captcha.setSendTarget(email);
                    Context context = new Context();
                    context.setVariable("title", "From www.Lrowy.com To" + email);
                    context.setVariable("content", captcha.getCode());
                    try {
                        int id = emailService.sendTemplateMail(email, "我的激活码", "/email", context);
                        captcha.setSendId(id);
                        captchaDao.saveCaptcha(captcha);
                        br.setResult(captcha);
                    } catch (MessagingException e) {
                        br.setInfo(SystemConstant.EMAIL_SEND_ERROR);
                        e.printStackTrace();
                    }
                } else {
                    br.setInfo(SystemConstant.RECAPTCHA_AUTHENTICATION_FAIL);
                }
            } catch (Exception e) {
                br.setInfo(SystemConstant.HTTPCLIENT_ERROR);
                e.printStackTrace();
            }
        }
        return br;
    }
}
