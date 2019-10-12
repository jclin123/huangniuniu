package com.huangniuniu.comment.api;

import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.common.pojo.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CommentApi {


    /**
     * 根据movieid查询评论，返回所有符合评论并分页
     * @param movieid
     * @param pn
     * @param pagesize
     * @return
     */
    @GetMapping("bymovieid/{movieid}")
    PageResult<Comment>  getCommentsBymovie(@PathVariable("movieid")Long movieid,
                                                   @RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                                   @RequestParam(value = "pagesize",defaultValue = "10")Integer pagesize);

    /**
     * 根据movieid获得电影平均评分
     * @param movieid
     * @return 返回电影平均评分
     */
    @GetMapping("moviescore/{movieid}")
    Float getMovieScoreByMovieId(@PathVariable("movieid") Long movieid);
}
