package com.lrowy.dao;

import com.lrowy.pojo.article.Article;
import com.lrowy.pojo.article.ArticleCategory;

import java.util.List;

public interface ArticleDao {
    public int saveArticle(Article article);
    public Article findArticle(int articleId);
    public int updateArticle(Article article);
    public int deleteArticle(int articleId);
    public int saveArticleCategory(ArticleCategory articleCategory);
    public List<ArticleCategory> findAllArticleCategory();
    public int deleteArticleCategory(int articleCategoryId);
}
