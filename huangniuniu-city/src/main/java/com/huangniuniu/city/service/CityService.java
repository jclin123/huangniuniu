package com.huangniuniu.city.service;

import com.huangniuniu.city.pojo.City;
import com.huangniuniu.common.pojo.PageResult;

import java.util.List;

public interface CityService {

    /**
     * 查询所有城市
     * @return
     */
    List<City> getAllCity();

    /**
     * 根据条件查询所有城市并分页
     */
    PageResult<City> getAllCityToPage(Integer pn,Integer pagesize);

    /**
     * 根据条件查询城市
     * @param city
     * @return
     */
    List<City> getCityByPre(City city);

    /**
     * 根据条件查询城市并分页
     * @param city
     * @return
     */
    PageResult<City> getCityByPreToPage(City city,Integer pn,Integer pagesize);

    /**
     * 添加城市
     * @param city
     */
    void insertCity(City city);

    /**
     * 删除城市
     * @param id
     */
    void deleteCity(Long id);

    /**
     * 更新城市
     * @param city
     */
    void updateCity(City city);

    /**
     * 根据城市id查询城市
     * @param id
     * @return
     */
    City getCityById(Long id);
}
