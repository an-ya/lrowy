package com.lrowy.pojo;

import java.util.Date;

public class Article {
    private int article_id;
    private int creator_id;
    private String title;
    private String synopsis;
    private Date createDate;
    private int visits;
    private String labelName;
    private String folder;
    private String templet;

    public Article() {

    }

    public Article(int creator_id, String title, String synopsis, Date createDate, int visits, String labelName, String folder, String templet) {
        this.creator_id = creator_id;
        this.title = title;
        this.synopsis = synopsis;
        this.createDate = createDate;
        this.visits = visits;
        this.labelName = labelName;
        this.folder = folder;
        this.templet = templet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return createDate;
    }

    public void setDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getTemplet() {
        return templet;
    }

    public void setTemplet(String templet) {
        this.templet = templet;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }
}
