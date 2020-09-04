package com.lrowy.pojo.bookmark;

import java.util.Date;

public class Bookmark {
    private int bookmarkId;
    private int bookmarkCategoryId;
    private String description;
    private String title;
    private String url;
    private String favicon;
    private int visits;
    private int delFlag;
    private int hiddenFlag;
    private Date createDate;

    public Bookmark() {

    }

    public Bookmark(String url) {
        this.url = url;
        this.createDate = new Date();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
