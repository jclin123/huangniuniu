package com.huangniuniu.cinema.service;

import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.common.pojo.PageResult;

import java.util.List;
import java.util.Map;

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
     * 当页面加载后发出，根据电影院id查询出该电影院信息以及该电影院的电影信息
     * @param cinemaid
     * @return
     */
    Map<String,Object> selectCinemaAndMovieListByCinemaId(Long cinemaid);

    /**
     * 在电影院选择电影后，显示排场时间(MM-dd)
     * @param cinemaid
     * @param movieid
     * @return
     */
    List<String> selectSkeduleTimeListByCinemaIdAndMovieId(Long cinemaid,Long movieid);

    /**
     * 根据选择的电影院、电影、以及该电影选择的时间(MM-dd)查询排场信息
     * @param cinemaid
     * @param movieid
     * @param Skeduletime
     * @return
     */
    List<Skedule> selectSkeduleListByCinemaIdAndMovieIdAndSkeduleTime(Long cinemaid, Long movieid, String Skeduletime);

    /**
     * 根据排场id和购买该排场的电影票数量，修改排场的电影票数量
     * @param skeduleId
     * @param number
     */
    void buyTicketBySkeduleId(Long skeduleId, Integer number);

    /**
     * 分页查询排场信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResult<Skedule> getAllSkeduleByPage(Integer pageNumber, Integer pageSize);

    /**
     * 条件查询排场信息并且分页
     * @param skedule
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResult<Skedule> getSkeduleByConditionpage(Skedule skedule, Integer pageNumber, Integer pageSize);

    /**
     * 不分页条件查询所有排场
     * @param skedule
     * @return
     */
    List<Skedule> getSkeduleByCondition(Skedule skedule);
}
