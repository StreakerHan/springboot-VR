package com.streaker.service;

import com.streaker.entity.Article;

import java.util.List;

/**
 * 文章逻辑接口类
 *
 * @author StreakerHan
 * @version 2018/10/16 15:24:58
 **/
public interface ArticleService {
    /**
     * 添加文章接口
     */
    void addArticle(String title, String email, String username, String cdesc, String content,
                    Integer uid, String cdate, String status);

    /**
     * 通过id查找文章
     */
    Article getArticleById( Integer aid);

    /**
     * 通过id删除文章
     */
    void deleteArticleById( Integer aid);

    /**
     * 获取文章列表
     */
    List<Article> getArticleList();

    /**
     * 获取最新的4条文章
     */
    List<Article> getFormArticle();

    /**
     * 获取数量
     */
    int selectCount();

    /**
     * 更新文章
     */
    int updateArticle(Article article);
}
