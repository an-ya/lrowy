package com.lrowy.pojo.captcha;

import java.util.Date;

public class Captcha {
    private int captchaId;
    private int sendId;
    private String sendMode;
    private String ip;
    private String code;
    private Date createDate;

    public int getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(int captchaId) {
        this.captchaId = captchaId;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public String getSendMode() {
        return sendMode;
    }

    public void setSendMode(String sendMode) {
        this.sendMode = sendMode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
