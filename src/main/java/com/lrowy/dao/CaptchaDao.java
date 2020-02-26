package com.lrowy.dao;

import com.lrowy.pojo.captcha.Captcha;

public interface CaptchaDao {
    public int saveCaptcha(Captcha captcha);
    public Captcha findCaptcha(int captchaId);
}