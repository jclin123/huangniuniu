package com.huangniuniu.cinema.service;

import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.common.pojo.PageReult;

import java.util.List;

public interface CinemaService {

    /**
     * 添加电影院
     * @param cinema
     */
    void insertCinema(Cinema cinema);

    /**
     * 根据电影院id删除电影院
     * @param cid
     */
    void deleteCinema(Long cid);

    /**
     * 修改电影院信息
     * @param cinema
     */
    void updateCinema(Cinema cinema);

    /**
     * 根据电影院id查询该电影院信息
     * @param cinemaid
     * @return
     */
    Cinema getCinemaByCinemaid(Long cinemaid);

    /**
     * 获得所有电影院信息
     * @return
     */
    List<Cinema> getAllCinema();

    /**
     * 根据查询条件查询电影院信息
     * @param cinema
     * @return
     */
    List<Cinema> getCinemaByCondition(Cinema cinema);

    /**
     * 根据电影院id查询该电影院的上映电影
     * @param cid
     * @return
     */
//    List<Movie> getMoviesByCinemaId(Long cid);

    /**
     * 根据城市id，分显示电影院列表
     * @param pageNumber
     * @param rows
     * @param cid
     * @return
     */
    PageReult<Cinema> getAllCinemasByPage(Integer pageNumber, Integer rows, Long cid);


}
