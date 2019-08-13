package com.lrowy.pojo.bookmark;

import java.util.Date;

public class Bookmark {
    private int bookmarkId;
    private int bookmarkCategoryId;
    private String description;
    private String title;
    private String faviconUrl;
    private String faviconBlurUrl;
    private String faviconOriginalUrl;
    private String state;
    private String url;
    private String baseUrl;
    private int visits;
    private int delFlag;
    private int hiddenFlag;
    private int isAccessible;
    private int shortcutFlag;
    private Date createDate;

    public Bookmark() { }

    public Bookmark(String url) {
        this.url = url;
        this.createDate = new Date();
        this.isAccessible = 1;
    }

    public Bookmark(Bookmark bookmark) {
        this.faviconUrl = bookmark.getFaviconUrl();
        this.faviconBlurUrl = bookmark.getFaviconBlurUrl();
        this.faviconOriginalUrl = bookmark.getFaviconOriginalUrl();
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public int getBookmarkCategoryId() {
        return bookmarkCategoryId;
    }

    public void setBookmarkCategoryId(int bookmarkCategoryId) {
        this.bookmarkCategoryId = bookmarkCategoryId;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
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

    public String getFaviconBlurUrl() {
        return faviconBlurUrl;
    }

    public void setFaviconBlurUrl(String faviconBlurUrl) {
        this.faviconBlurUrl = faviconBlurUrl;
    }

    public void setFaviconOriginalUrl(String faviconOriginalUrl) {
        this.faviconOriginalUrl = faviconOriginalUrl;
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
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
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
        return isAccessible;
    }

    public void setAccessible(int isAccessible) {
        this.isAccessible = isAccessible;
    }

    public int getShortcutFlag() {
        return shortcutFlag;
    }

    public void setShortcutFlag(int shortcutFlag) {
        this.shortcutFlag = shortcutFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
