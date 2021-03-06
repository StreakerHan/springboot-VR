package com.streaker.service.impl;

import com.streaker.dao.ArticleDao;
import com.streaker.entity.Article;
import com.streaker.entity.ResponseBo;
import com.streaker.service.ArticleService;
import com.streaker.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章逻辑接口实现类
 *
 * @author StreakerHan
 * @version 2018/10/16 15:29:11
 **/
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private RedisUtils redisUtils;

    @CachePut(value = "article",key = "#title")
    @Override
    public void addArticle(String title, String email, String username, String cdesc, String content, Integer uid, String cdate,String status) {
        Article article = new Article();
        article.setTitle(title);
        article.setCdesc(cdesc);
        article.setContent(content);
        article.setUsername(username);
        article.setUid(uid);
        article.setCdate(cdate);
        article.setEmail(email);
        articleDao.addArticle(article);
    }

    @Override
    public Article getArticleById(Integer aid) {
        return articleDao.getArticleById(aid);
    }

    @Override
    public void deleteArticleById(Integer aid) {
        if(null == aid) {
            ResponseBo.error("删除失败！");
        }
        //删除redis缓存 (key)
        redisUtils.del(aid.toString());
       articleDao.deleteArticleById(aid);
    }

    @Override
    public List<Article> getArticleList() {
        List<Article> list = articleDao.getArticleList();
        return list;
    }

    /**
     * 添加首页展示缓存
     * @return
     */
    @Cacheable(cacheNames = "articleList")
    @Override
    public List<Article> getFormArticle() {
        List<Article> list = articleDao.getFormArticle();
        return list;
    }

    @Override
    public int selectCount() {
        int count = articleDao.selectCount();
        return count;
    }

    @Override
    public int updateArticle(Article article) {
        //在redis中添加缓存
        redisUtils.set(article.getAid().toString(),article.getStatus());
        return articleDao.updateArticle(article);
    }
}
