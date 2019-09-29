package com.huangniuniu.city.service;

import com.huangniuniu.city.pojo.City;

import java.util.List;

public interface CityService {

    /**
     * 查询所有城市
     * @return
     */
    List<City> getAllCity();

    /**
     * 根据条件查询城市
     * @param city
     * @return
     */
    List<City> getCityByPre(City city);

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
}
