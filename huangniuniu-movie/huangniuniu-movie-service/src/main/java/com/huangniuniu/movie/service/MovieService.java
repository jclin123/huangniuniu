package com.huangniuniu.movie.service;

import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.pojo.Movie;
import com.huangniuniu.movie.pojo.MovieDetail;
import org.springframework.transaction.annotation.Transactional;

public interface MovieService {
    /**
     * 获取电影列表，超过下架时间三个月不展出
     * @return
     */
    PageResult<Movie> getAllMovie(Integer page, Integer rows);
    /**
     * 根据电影id查询电影
     * @param mid
     * @return
     */

    Movie getMovieByMovieid(Long mid);
    /**
     * 新增电影
     * @param movie
     */
    void insertMovie(Movie movie);
    /**
     * 根据id删除电影
     * @param id
     */
    void deleteMovie(Long id);
    /**
     * 根据条件查询电影（名称、类型、地区模糊匹配，评分相等）
     * @param movie
     * @return
     */
    PageResult<Movie> getMovieByCondition(Integer page, Integer rows,Movie movie);
    /**
     * 根据城市id查询电影
     * ishot为true为热映
     * ishot为false为即将上映
     * 已下架不展出
     * @param cid
     * @param ishot
     * @return
     */
    PageResult<Movie> getMovieByCityid(Integer page, Integer rows,Long cid,Boolean ishot);
    /**
     * 根据电影id查询封装电影详情数据
     * @param movieid
     * @return
     */
    MovieDetail getMovieDetail(Long movieid);
}
