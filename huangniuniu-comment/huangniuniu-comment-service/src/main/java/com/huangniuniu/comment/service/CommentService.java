package com.huangniuniu.comment.service;

import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.common.pojo.PageResult;

import java.util.List;

public interface CommentService {
    /**
     * 查询所有评论
     * @return
     */
    List<Comment> getAllComment();

    /**
     * 根据条件查询所有评论并分页
     * @return
     */
    PageResult<Comment> getAllCommentToPage(Integer pn,Integer pagesize);


    /**
     * 根据条件查询评论，返回所有符合评论
     * @param comment
     * @return
     */
    List<Comment> getCommentByCondition(Comment comment);

    /**
     * 根据条件查询评论，返回所有符合评论并分页
     * @param comment
     * @return
     */
    PageResult<Comment> getCommentByConditionToPage(Comment comment,Integer pn,Integer pagesize);

    /**
     * 添加评论
     * @param comment
     */
    void insertComment(Comment comment);

    /**
     * 删除评论
     * @param id
     */
    void deleteComment(Long id);

    /**
     * 更新评论
     * @param comment
     */
    void updateComment(Comment comment);
}
