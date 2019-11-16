package com.lrowy.dao;

import com.lrowy.pojo.article.Article;
import com.lrowy.pojo.article.ArticleCategory;
import com.lrowy.pojo.article.ArticleTag;

import java.util.List;

public interface ArticleDao {
    public int saveArticle(Article article);
    public Article findArticle(int articleId);
    public List<Article> findArticleByTags(int[] tags, int length, int offset, int pageSize);
    public int findArticleNumByTags(int[] tags, int length);
    public List<Article> findAllArticle();
    public int updateArticleInfo(Article article);
    public int updateArticleContent(Article article);
    public int deleteArticle(int articleId);
    public int deleteTagWithArticle(int articleId, int articleTagId);
    public int addTagWithArticle(int articleId, int[] tags);
    public int saveArticleCategory(ArticleCategory articleCategory);
    public List<ArticleCategory> findAllArticleCategory();
    public int deleteArticleCategory(int articleCategoryId);
    public List<ArticleTag> findAllArticleTag();
}
