package com.lrowy.pojo.captcha;

import java.util.Date;

public class Captcha {
    private int captchaId;
    private int sendId;
    private int timeout;
    private String sendMode;
    private String sendTarget;
    private String ip;
    private String code;
    private Date createDate;

    public Captcha() {
        this.timeout = 3600;
        this.createDate = new Date();
    }

    public boolean isExpired() {
        return (new Date().getTime() - this.createDate.getTime()) / 1000 > this.timeout;
    }

    public boolean inOneMinute() {
        return (new Date().getTime() - this.createDate.getTime()) / 1000 < 60;
    }

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

    public String getSendTarget() {
        return sendTarget;
    }

    public void setSendTarget(String sendTarget) {
        this.sendTarget = sendTarget;
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
