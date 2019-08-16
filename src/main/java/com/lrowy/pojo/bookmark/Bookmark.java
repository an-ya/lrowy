package com.lrowy.pojo.bookmark;

import java.util.Date;

public class Bookmark {
    private int bookmarkId;
    private int bookmarkCategoryId;
    private String description;
    private String title;
    private String state;
    private String url;
    private String baseUrl;
    private int visits;
    private int delFlag;
    private int hiddenFlag;
    private int isAccessible;
    private int shortcutFlag;
    private int extraNetFlag;
    private Date createDate;
    private Favicon favicon;

    public Bookmark() {

    }

    public Bookmark(String url) {
        this.url = url;
        this.createDate = new Date();
        this.isAccessible = 1;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getIsAccessible() {
        return isAccessible;
    }

    public void setIsAccessible(int isAccessible) {
        this.isAccessible = isAccessible;
    }

    public int getShortcutFlag() {
        return shortcutFlag;
    }

    public void setShortcutFlag(int shortcutFlag) {
        this.shortcutFlag = shortcutFlag;
    }

    public int getExtraNetFlag() {
        return extraNetFlag;
    }

    public void setExtraNetFlag(int extraNetFlag) {
        this.extraNetFlag = extraNetFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Favicon getFavicon() {
        return favicon;
    }

    public void setFavicon(Favicon favicon) {
        this.favicon = favicon;
    }
}
