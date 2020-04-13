package com.lrowy.dao;

import com.lrowy.pojo.captcha.Captcha;

import java.util.List;

public interface CaptchaDao {
    public int saveCaptcha(Captcha captcha);
    public Captcha findCaptchaByTarget(String target);
    public List<Captcha> findCaptchaByIpToday(String ip);
    public List<Captcha> findCaptchaByTargetToday(String target);
}