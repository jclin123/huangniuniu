package com.huangniuniu.city.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.city.mapper.CityMapper;
import com.huangniuniu.city.pojo.City;
import com.huangniuniu.city.service.CityService;
import com.huangniuniu.common.pojo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    public CityMapper cityMapper;

    @Override
    public Map<Character, List<City>> getAllCity() {

        Example example = new Example(City.class);
        //example.orderBy("preLetter").asc();
        List<City> citys = cityMapper.selectByExample(example);
        Map<Character, List<City>> map = citys.stream().collect(Collectors.groupingBy(City::getPreLetter));
        TreeMap<Character, List<City>> treeMap = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, TreeMap::new));
        return  treeMap;
    }

    @Override
    public PageResult<City> getAllCityToPage(Integer pn, Integer pagesize) {
        PageHelper.startPage(pn,pagesize);
        List<City> cities = cityMapper.selectAll();
        PageInfo pageInfo = new PageInfo(cities,pagesize);
        PageResult<City> pageResult = new PageResult<City>();
        pageResult.setItems(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @Override
    public List<City> getCityByPre(City city) {
        Example example = new Example(City.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isBlank(city.getCityName())){
            criteria.andLike("cityName","%"+city.getCityName()+"%");
        }
        if(city.getPreLetter()!=null){
            criteria.andLike("preLetter","%"+city.getPreLetter()+"%");
        }
        List<City> cities = cityMapper.selectByExample(example);
        return cities;
    }

    @Override
    public PageResult<City> getCityByPreToPage(City city, Integer pn, Integer pagesize) {
        PageHelper.startPage(pn,pagesize);
        Example example = new Example(City.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isBlank(city.getCityName())){
            criteria.andLike("cityName","%"+city.getCityName()+"%");
        }
        if(city.getPreLetter()!=null){
            criteria.andLike("preLetter","%"+city.getPreLetter()+"%");
        }
        List<City> cities = cityMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(cities,pagesize);
        PageResult<City> pageResult = new PageResult<City>();
        pageResult.setItems(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @Override
    public void insertCity(City city) {
        cityMapper.insertSelective(city);
    }

    @Override
    public void deleteCity(Long id) {
        cityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateCity(City city) {
        cityMapper.updateByPrimaryKeySelective(city);
    }

    @Override
    public City getCityById(Long id) {
        City city = cityMapper.selectByPrimaryKey(id);
        return city;
    }

    @Override
    public City getCityByCityName(String cityName) {
        Example example = new Example(City.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("cityName",cityName);
        List<City> cities = this.cityMapper.selectByExample(example);
        return cities.get(0);
    }
}
