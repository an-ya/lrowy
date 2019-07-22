package com.lrowy.pojo;

public class Label {
    private int label_id;
    private String labelName;
    private String synopsis;
    private int articleNumber;

    public Label() {

    }

    public Label(String labelName, String synopsis, int articleNumber) {
        this.labelName = labelName;
        this.synopsis = synopsis;
        this.articleNumber = articleNumber;
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(int articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getLabel_id() {
        return label_id;
    }

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }
}
