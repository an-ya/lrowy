package com.lrowy.controller;

import com.lrowy.dao.ArticleDao;
import com.lrowy.pojo.article.Article;
import com.lrowy.pojo.article.ArticleCategory;
import com.lrowy.pojo.common.enums.SystemConstant;
import com.lrowy.pojo.common.response.BaseResponse;
import com.lrowy.pojo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController extends BaseController {
    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("/admin/writer")
    public String writer(Model model) {
        User user = isLogin() ? getUser() : null;
        model.addAttribute("user", user);
        List<ArticleCategory> articleCategoryList = articleDao.findAllArticleCategory();
        model.addAttribute("articleCategoryList", articleCategoryList);
        return "/admin/writer";
    }

    @RequestMapping("/admin/article/add")
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

    @RequestMapping("/admin/article/init")
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

    @RequestMapping("/admin/article/update/content")
    @ResponseBody
    public BaseResponse<Article> articleUpdateContent(@ModelAttribute Article article) {
        articleDao.updateArticleContent(article);
        BaseResponse<Article> br = new BaseResponse<>();
        br.setResult(articleDao.findArticle(article.getArticleId()));
        return br;
    }
}
