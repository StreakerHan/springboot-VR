package com.streaker.dao;

import com.streaker.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章接口类
 *
 * @author StreakerHan
 * @version 2018/10/16 13:39:42
 **/
@Mapper
public interface ArticleDao {

    /**
     * 添加文章接口
     */
    int addArticle(Article article);

    /**
     * 通过id查找文章
     */
    Article getArticleById(@Param("aid") Integer aid);

    /**
     * 通过id删除文章
     */
    int deleteArticleById(@Param("aid") Integer aid);

    /**
     * 获取文章列表
     */
    List<Article> getArticleList();

    /**
     * 获取最新的4条文章
     */
    List<Article> getFormArticle();

    /**
     * 获取总数量
     */
    int selectCount();
}
