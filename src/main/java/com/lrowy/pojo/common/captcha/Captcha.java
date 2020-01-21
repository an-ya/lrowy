package com.lrowy.pojo.common.captcha;

import java.util.Date;

public class Captcha {
    private String code;
    private String ip;
    private Date createDate;
    private int timeout = 3600;

    public Captcha () {
        this.createDate = new Date();
    }

    public boolean isExpired (Date currentDate) {
        return (currentDate.getTime() - this.createDate.getTime()) / 1000 > this.timeout;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
