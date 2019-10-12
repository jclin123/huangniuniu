package com.huangniuniu.comment.api;

import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.common.pojo.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CommentApi {


    /**
     * 根据movieid查询评论，返回所有符合评论并分页
     * @param movieid
     * @return
     */
    @GetMapping("bymovieid/{movieid}")
    List<Comment> getCommentsBymovie(@PathVariable("movieid")Long movieid);

    /**
     * 根据movieid获得电影平均评分
     * @param movieid
     * @return 返回电影平均评分
     */
    @GetMapping("moviescore/{movieid}")
    Float getMovieScoreByMovieId(@PathVariable("movieid") Long movieid);
}
