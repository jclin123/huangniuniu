package com.huangniuniu.cinema.service;

import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.cinema.pojo.Skedule;

import java.util.List;

public interface SkeduleService {
    /**
     * void addSkedule(Skedule skedule);//添加排场
     * void deleteSkedule(Skedule skedule);
     * void updateSkedule(Skedule skedule);
     * Cinemas getSkeduleBySkeduleid(Long skeduleid);//根据排场id查询该排场
     * List<Skedule> getAllSkedule();//获取全部排场信息
     * List<Skedule> getSkeduleByCondition(Skedule skedule);//根据条件查询排场信息
     * 前台：
     * CinemaDetail  selectCinemaDetail(Long cinemaid);//根据影院id查询该影院的所有电影的中每一部电影的所有排场
     */

    /**
     * 根据电影院和电影信息，添加排场信息
     * @param skedule
     * @param cinema_movie
     */
    void addSkedule(Skedule skedule, Cinema_movie cinema_movie);

    /**
     * 查询所有排场信息
     * @return
     */
    List<Skedule> getAllSkedule();

    /**
     * 根据排场id查询该排场
     * @param sid
     * @return
     */
    Skedule getSkeduleBySkeduleid(Long sid);

    /**
     * 根据排场id删除排场
     * @param sid
     */
    void deleteSkeduleBysid(Long sid);

    /**
     * 修改排场信息
     * @param skedule
     */
    void updateSkedule(Skedule skedule);

    /**
     * 根据条件查询排场信息
     * @param skedule
     * @return
     */
    List<Skedule> getSkeduleByCondition(Skedule skedule);
}
