package com.huangniuniu.cinema.service;

import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.pojo.Movie;

import java.util.List;
import java.util.Map;

public interface CinemaMovieService {

    /**
     * 两者的id的关系，先把两个id封装给CinemaMovie，即添加电影
     * @param cinema_movie
     * @return
     */
    void chooseCinemaMovie(Cinema_movie cinema_movie);

    /**
     * 用户前台
     * 点击电影院列表中某个电影院的查看所有电影
     * 根据电影院id查询该电影院的(正在/即将）上映电影
     * @param cinemaid
     * @return
     */
    Map<String,Object> getMoviesByCinemaId(Long cinemaid);

    /**
     * 根据cinema_movie的id删除该电影院的该电影
     * @param id
     */
    void deleteCinemaMovie(Long id);

    /**
     * 点击添加排场时，需要的返回数据
     * 根据cinema_movie的id查询cinema_movie(包括电影和电影院名称)
     * @param id
     * @return
     */
    Cinema_movie selectCinemaMovieBycmid(Long id);

    /**
     * 根据城市id和电影id查询该城市下有这个电影的电影院
     * @param cityid
     * @param movieid
     * @return
     */
    List<Cinema> selectCinemaByCityIdAndMovieId(Long cityid, Long movieid);

    /**
     * 管理后台
     * 根据电影院id分页查询该电影院的电影
     * @param cid
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResult<Movie> getMoviesPageByCinemaId(Long cid, Integer pageNumber, Integer pageSize);

    /**
     * 根据电影院id查询该电影院的电影
     * @param mid
     * @return
     */
    List<Movie> selectMovieListByCinemaId(Long mid);
}
