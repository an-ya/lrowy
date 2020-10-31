package com.lrowy.service;

import com.lrowy.dao.CommentDao;
import com.lrowy.pojo.comment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    private List<Comment> dealData(List<Comment> comments) {
        int level = 1;
        Map<Integer, Comment> map = new HashMap<>();
        Comment root = new Comment();
        root.setLevel(-1);
        root.setChild(new ArrayList<>());
        map.put(0, root);
        for (Comment c : comments) {
            c.initAvatar();
            map.put(c.getCommentId(), c);
        }
        for (Comment c : comments) {
            int id = c.getpId();
            Comment parent;
            while (c.getpId() != 0) {
                c.setLevel(c.getLevel() + 1);
                parent = map.get(c.getpId());
                c.setpId(parent.getpId());
            }
            c.setpId(id);
        }
        for (Comment c : comments) {
            if (c.getLevel() >= level) {
                Comment parent = map.get(c.getpId());
                while (parent != null && parent.getLevel() >= level) {
                    c.setFold(true);
                    c.setpId(parent.getpId());
                    parent = map.get(c.getpId());
                }
            }
        }
        for (Comment c : comments) {
            Comment parent = map.get(c.getpId());
            if (parent != null) {
                if (parent.getChild() == null) parent.setChild(new ArrayList<>());
                parent.getChild().add(c);
            }
        }
        return root.getChild();
    }

    public List<Comment> getCommentByIssue(int id, String type) {
        List<Comment> comments = commentDao.findCommentByIssue(id, type);
        return dealData(comments);
    }
}
