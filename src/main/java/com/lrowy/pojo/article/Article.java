package com.lrowy.pojo.article;

import java.util.Date;

public class Article {
    private int articleId;
    private int creatorId;
    private int articleCategoryId;
    private String title;
    private String content;
    private String description;
    private String template;
    private int visits;
    private int delFlag;
    private int hiddenFlag;
    private Date createDate;
    private Date updateDate;

    public Article() {
        Date now = new Date();
        this.title = "";
        this.content = "";
        this.description = "";
        this.template = "";
        this.hiddenFlag = 1;
        this.createDate = now;
        this.updateDate = now;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(int articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
