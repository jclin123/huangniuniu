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

    /**
     * 根据movieid查询评论，返回所有符合评论并分页
     * @param movieid
     * @return
     */
    List<Comment> getCommentsBymovie(Long movieid);

    /**
     * 根据movieid获得电影平均评分
     * @param movieid
     * @return 返回电影平均评分
     */
    Float getMovieScoreByMovieId(Long movieid);

    /**
     * 根据commentid获得某一个评论
     * @param commentid
     * @return
     */
    Comment getCommentByid(Long commentid);

    /**
     * 根据电影id分页查询评论
     * @param mid
     * @param pageSize
     * @param pageNumber
     * @return
     */
    PageResult<Comment> getcommentsPageBymid(Long mid, Integer pageNumber, Integer pageSize);

    /**
     * 根据排场id和用户id查询该评论是不是存在，存在则不能再评论
     * @param userid
     * @param skeduleid
     * @return
     */
    Boolean getCommentByUseridAndSkeduleid(Long userid, Long skeduleid);
}
