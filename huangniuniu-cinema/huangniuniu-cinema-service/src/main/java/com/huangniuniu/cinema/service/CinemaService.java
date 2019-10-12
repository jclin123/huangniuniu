package com.huangniuniu.cinema.service;

import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.common.pojo.PageResult;

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
     * 条件分页查询所有电影院，获得条件查询后的电影院列表
     * @param cinema
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResult<Cinema> getCinemaByCondition(Cinema cinema,Integer pageNumber,Integer pageSize);


    /**
     * 根据城市id，分显示电影院列表
     * @param pageNumber
     * @param rows
     * @param cid
     * @return
     */
    PageResult<Cinema> getAllCinemasByPage(Integer pageNumber, Integer rows, Long cid);

    /**
     * 分页查询电影院信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResult<Cinema> querylistPage(Integer pageNumber, Integer pageSize);
}
