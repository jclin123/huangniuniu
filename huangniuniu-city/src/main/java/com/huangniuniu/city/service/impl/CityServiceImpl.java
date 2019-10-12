package com.huangniuniu.city.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.city.mapper.CityMapper;
import com.huangniuniu.common.pojo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huangniuniu.city.pojo.City;
import com.huangniuniu.city.service.CityService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    public CityMapper cityMapper;

    @Override
    public List<City> getAllCity() {

        List<City> citys = cityMapper.selectAll();
        return citys;
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
            criteria.andLike("perLetter","%"+city.getPreLetter()+"%");
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
            criteria.andLike("perLetter","%"+city.getPreLetter()+"%");
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
}
