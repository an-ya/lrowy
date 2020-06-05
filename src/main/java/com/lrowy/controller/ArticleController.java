package com.lrowy.controller;

import com.lrowy.dao.ArticleDao;
import com.lrowy.dao.CommentDao;
import com.lrowy.pojo.comment.Comment;
import com.lrowy.pojo.user.User;

import com.lrowy.pojo.article.Article;
import com.lrowy.pojo.article.ArticleCategory;
import com.lrowy.pojo.article.ArticleTag;
import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.common.response.BasePagingResponse;
import com.lrowy.pojo.common.response.BaseResponse;
import com.lrowy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ArticleController extends BaseController {
    @Value("${github.oauth.clientId}")
    private String clientId;

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/article/{id}")
    public String writer(Model model, @PathVariable(name="id") int id) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);
        model.addAttribute("clientId", clientId);
        Article article = articleDao.findArticle(id);
        if (article == null) {
            model.addAttribute("msg", "未找到编号为" + id + "的文章");
            return "/error";
        } else {
            model.addAttribute("article", article);
            List<Comment> comments = commentService.getCommentByIssue(id, "Article");
            model.addAttribute("comments", comments);
            return "/article/templates/normal";
        }
    }

    @RequestMapping("/writer")
    public String writer(Model model) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);
        List<ArticleCategory> articleCategoryList = articleDao.findAllArticleCategory();
        model.addAttribute("articleCategoryList", articleCategoryList);
        List<ArticleTag> articleTags = articleDao.findAllArticleTag();
        model.addAttribute("tags", articleTags);
        List<Article> articles = articleDao.findAllArticle();
        model.addAttribute("articles", articles);
        return "/article/writer/normal";
    }

    @RequestMapping("/article/init")
    @ResponseBody
    public BaseResponse<Article> articleInit(int articleId) {
        Article article = articleDao.findArticle(articleId);
        BaseResponse<Article> br = new BaseResponse<>();
        if (article == null) {
            br.setInfo(SystemConstant.PARAMS_ERROR);
            br.setMsg(br.getMsg() + ":未找到指定编号的文章。");
        } else {
            br.setResult(article);
        }
        return br;
    }

    @RequestMapping("/article/get")
    @ResponseBody
    public BasePagingResponse<List<Article>> articleCet(@RequestParam(value = "tags[]", required = false) int[] tags, Integer pageSize, Integer pageNo) {
        if (tags == null) tags = new int[0];
        BasePagingResponse<List<Article>> page = new BasePagingResponse<>();
        if (pageSize != null) {
            page.setPageSize(pageSize);
        }
        if (pageNo != null) {
            page.setPageNo(pageNo);
        }
        List<Article> articles = articleDao.findArticleByTags(tags, tags.length, (page.getPageNo()-1)*page.getPageSize(), page.getPageSize());
        int count = articleDao.findArticleNumByTags(tags, tags.length);
        page.setTotalCount(count);
        page.setResult(articles);
        return page;
    }

    @RequestMapping("/article/add")
    @ResponseBody
    public BaseResponse<Article> articleAdd(@ModelAttribute Article article) {
        Date date = new Date();
        article.setCreateDate(date);
        article.setUpdateDate(date);
        articleDao.saveArticle(article);
        BaseResponse<Article> br = new BaseResponse<>();
        br.setResult(article);
        return br;
    }

    @RequestMapping("/article/update/info")
    @ResponseBody
    public BaseResponse<Article> articleUpdateInfo(@ModelAttribute Article article) {
        articleDao.updateArticleInfo(article);
        BaseResponse<Article> br = new BaseResponse<>();
        br.setResult(articleDao.findArticle(article.getArticleId()));
        br.setResult(article);
        return br;
    }

    @RequestMapping("/article/update/content")
    @ResponseBody
    public BaseResponse<Article> articleUpdateContent(@ModelAttribute Article article) {
        articleDao.updateArticleContent(article);
        BaseResponse<Article> br = new BaseResponse<>();
        br.setResult(articleDao.findArticle(article.getArticleId()));
        return br;
    }

    @RequestMapping("/article/tag/delete")
    @ResponseBody
    public BaseResponse<String> deleteTagWithArticle(int articleId, int articleTagId) {
        articleDao.deleteTagWithArticle(articleId, articleTagId);
        return new BaseResponse<>();
    }

    @RequestMapping("/article/tag/add")
    @ResponseBody
    public BaseResponse<Article> addTagWithArticle(int articleId, @RequestParam(value = "tags[]", required = false) int[] tags) {
        if (tags != null) {
            articleDao.addTagWithArticle(articleId, tags);
        }
        Article article = articleDao.findArticle(articleId);
        BaseResponse<Article> br = new BaseResponse<>();
        br.setResult(article);
        return br;
    }

    @RequestMapping("/articleCategory/add")
    @ResponseBody
    public BaseResponse<ArticleCategory> articleCategoryAdd(@ModelAttribute ArticleCategory articleCategory) {
        articleDao.saveArticleCategory(articleCategory);
        BaseResponse<ArticleCategory> br = new BaseResponse<>();
        br.setResult(articleCategory);
        return br;
    }

    @RequestMapping("/articleTag/get")
    @ResponseBody
    public BaseResponse<List<ArticleTag>> articleTagGet() {
        List<ArticleTag> articleTags = articleDao.findAllArticleTag();
        BaseResponse<List<ArticleTag>> br = new BaseResponse<>();
        br.setResult(articleTags);
        return br;
    }

    @RequestMapping("/comment/add")
    @ResponseBody
    public BaseResponse<String> commentAdd(@ModelAttribute Comment comment) {
        BaseResponse<String> br = new BaseResponse<>();
        if (!isLogin()) {
            br.setInfo(SystemConstant.USER_NOT_LOGIN);
        } else {
            User user = getUser();
            comment.setUserId(user.getUserId());
            comment.setIssueType("Article");
            comment.setCreateDate(new Date());
            commentDao.saveComment(comment);
        }
        return br;
    }
}
