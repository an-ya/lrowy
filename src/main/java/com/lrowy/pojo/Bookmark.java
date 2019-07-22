package com.lrowy.pojo;

import java.util.Date;

public class Bookmark {
    private int bookmark_id;
    private String desc;
    private String title;
    private String faviconUrl;
    private String faviconOriginalUrl;
    private String keyword;
    private String state;
    private String url;
    private String BaseUrl;
    private int visits;
    private int delFlag;
    private int hiddenFlag;
    private int accessible;
    private int faviconFlag;
    private Date createDate;

    public Bookmark(String url) {
        this.url = url;
        this.createDate = new Date();
        this.accessible = 1;
    }

    public int getBookmark_id() {
        return bookmark_id;
    }

    public void setBookmark_id(int bookmark_id) {
        this.bookmark_id = bookmark_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFaviconUrl() {
        return faviconUrl;
    }

    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    public String getFaviconOriginalUrl() {
        return faviconOriginalUrl;
    }

    public void setFaviconOriginalUrl(String faviconOriginalUrl) {
        this.faviconOriginalUrl = faviconOriginalUrl;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBaseUrl() {
        return BaseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        BaseUrl = baseUrl;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getHiddenFlag() {
        return hiddenFlag;
    }

    public void setHiddenFlag(int hiddenFlag) {
        this.hiddenFlag = hiddenFlag;
    }

    public int getAccessible() {
        return accessible;
    }

    public void setAccessible(int accessible) {
        this.accessible = accessible;
    }

    public int getFaviconFlag() {
        return faviconFlag;
    }

    public void setFaviconFlag(int faviconFlag) {
        this.faviconFlag = faviconFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
