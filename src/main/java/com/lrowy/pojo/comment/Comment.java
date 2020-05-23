package com.lrowy.pojo.comment;

import java.util.Date;

public class Comment {
    private int commentId;
    private int issueId;
    private String issueType;
    private int userId;
    private String name;
    private String avatarVersion;
    private int pUserId;
    private String pName;
    private String pAvatarVersion;
    private Date createDate;
    private String content;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarVersion() {
        return avatarVersion;
    }

    public void setAvatarVersion(String avatarVersion) {
        this.avatarVersion = avatarVersion;
    }

    public int getpUserId() {
        return pUserId;
    }

    public void setpUserId(int pUserId) {
        this.pUserId = pUserId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpAvatarVersion() {
        return pAvatarVersion;
    }

    public void setpAvatarVersion(String pAvatarVersion) {
        this.pAvatarVersion = pAvatarVersion;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
