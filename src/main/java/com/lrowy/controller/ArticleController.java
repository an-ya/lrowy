package com.lrowy.controller;

import com.lrowy.dao.ArticleDao;
import com.lrowy.pojo.User;

import com.lrowy.pojo.article.Article;
import com.lrowy.pojo.article.ArticleCategory;
import com.lrowy.pojo.article.ArticleTag;
import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.common.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class ArticleController extends BaseController {
    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("/article/{id}")
    public String writer(Model model, @PathVariable(name="id") int id) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);
        Article article = articleDao.findArticle(id);
        if (article == null) {
            model.addAttribute("msg", "未找到编号为" + id + "的文章");
            return "/error";
        } else {
            model.addAttribute("article", article);
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
    public BaseResponse<List<Article>> articleCet(@RequestParam(value = "tags[]", required = false) int[] tags) {
        if (tags == null) tags = new int[0];
        List<Article> articles = articleDao.findArticleByTags(tags, tags.length);
        BaseResponse<List<Article>> br = new BaseResponse<>();
        br.setResult(articles);
        return br;
    }

    @RequestMapping("/article/add")
    @ResponseBody
    public BaseResponse<Article> articleCategoryAdd(@ModelAttribute Article article) {
        articleDao.saveArticle(article);
        BaseResponse<Article> br = new BaseResponse<>();
        br.setResult(article);
        return br;
    }

    @RequestMapping("/article/update")
    @ResponseBody
    public BaseResponse<Article> articleCategoryUpdate(@ModelAttribute Article article) {
        articleDao.updateArticle(article);
        BaseResponse<Article> br = new BaseResponse<>();
        br.setResult(article);
        return br;
    }

    @RequestMapping("/article/tag/delete")
    @ResponseBody
    public BaseResponse<String> deleteTagWithArticle(int articleId, int articleTagId) {
        articleDao.deleteTagWithArticle(articleId, articleTagId);
        return new BaseResponse<>();
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
}
