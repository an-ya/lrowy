package com.lrowy.pojo.comment;

import java.util.Date;
import java.util.List;

public class Comment {
    private int commentId;
    private int issueId;
    private String issueType;
    private int userId;
    private String name;
    private String avatar;
    private String avatarVersion;
    private int pId;
    private int pUserId;
    private String pName;
    private String pAvatar;
    private String pAvatarVersion;
    private Date createDate;
    private String content;
    private int level;
    private boolean fold;
    private List<Comment> child;

    public Comment() {

    }

    public void initAvatar() {
        if (this.userId != 0) this.avatar = "/upload/avatar/" + this.userId + ".png?v=" + this.avatarVersion;
        if (this.pUserId != 0) this.pAvatar = "/upload/avatar/" + this.pUserId + ".png?v=" + this.pAvatarVersion;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarVersion() {
        return avatarVersion;
    }

    public void setAvatarVersion(String avatarVersion) {
        this.avatarVersion = avatarVersion;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
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

    public String getpAvatar() {
        return pAvatar;
    }

    public void setpAvatar(String pAvatar) {
        this.pAvatar = pAvatar;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isFold() {
        return fold;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    public List<Comment> getChild() {
        return child;
    }

    public void setChild(List<Comment> child) {
        this.child = child;
    }
}
