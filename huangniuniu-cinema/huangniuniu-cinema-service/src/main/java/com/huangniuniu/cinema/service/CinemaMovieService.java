package com.huangniuniu.cinema.service;

import com.huangniuniu.cinema.pojo.Cinema_movie;

import java.util.Map;

public interface CinemaMovieService {

    /**
     * 两者的id的关系，先把两个id封装给CinemaMovie，即添加电影
     * @param cinema_movie
     * @return
     */
    void chooseCinemaMovie(Cinema_movie cinema_movie);

    /**
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


}
