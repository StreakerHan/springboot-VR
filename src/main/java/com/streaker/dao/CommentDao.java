package com.streaker.dao;

import com.streaker.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户意见接口的定义
 *
 * @author StreakerHan
 * @version 2018/9/6 11:25:29
 **/
@Mapper
public interface CommentDao {

    /**
     * 用户提交意见接口
     */
    int addComment(Comment comment);

    /**
     *通过主键id删除意见接口
     */
    int deleteComment(@Param("cid") Integer cid);

    /**
     * 查看意见列表接口
     */
    List<Comment> getCommentList();


    /**
     * 查看最近提交的意见接口
     */
    List<Comment> getCommentRecently();
}
