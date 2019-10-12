package com.huangniuniu.comment.mapper;

import com.huangniuniu.comment.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface CommentMapper extends Mapper<Comment> {

    /**
     * 根据评论的电影id求电影平均评分
     * @param movieid
     * @return
     */
    @Select("SELECT AVG(score) as score from `comment` GROUP BY movieid HAVING movieid=#{movieid}")
    Float getMovieScoreByMovieId(@Param("movieid") Long movieid);
}
