package com.lrowy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lrowy.dao.CommentDao;
import com.lrowy.pojo.comment.Comment;
import com.lrowy.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public String getCommentByIssue(int issueId, String issueType) {
        List<Comment> comments = commentDao.findCommentByIssue(issueId, issueType);
        try {
            return JsonUtil.generate(comments);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
