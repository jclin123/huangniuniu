package com.huangniuniu.city.service.impl;

import com.huangniuniu.city.mapper.CityMapper;
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
