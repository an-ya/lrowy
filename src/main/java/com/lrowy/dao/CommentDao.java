package com.lrowy.dao;

import com.lrowy.pojo.comment.Comment;

import java.util.List;

public interface CommentDao {
    public int saveComment(Comment comment);
    public List<Comment> findCommentByIssue(int issueId, String issueType);
}
