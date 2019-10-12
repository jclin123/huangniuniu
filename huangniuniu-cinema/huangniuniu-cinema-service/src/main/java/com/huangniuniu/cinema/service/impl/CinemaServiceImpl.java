package com.huangniuniu.cinema.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.cinema.mapper.CinemaMapper;
import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.cinema.service.CinemaService;
import com.huangniuniu.common.pojo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private CinemaMapper cinemaMapper;

    @Override
    @Transactional
    public void insertCinema(Cinema cinema) {
        cinemaMapper.insertSelective(cinema);
    }

    @Override
    @Transactional
    public void deleteCinema(Long cid) {
        cinemaMapper.deleteByPrimaryKey(cid);
    }

    @Override
    @Transactional
    public void updateCinema(Cinema cinema) {
        cinemaMapper.updateByPrimaryKeySelective(cinema);
    }

    @Override
    public Cinema getCinemaByCinemaid(Long cinemaid) {
        Cinema cinema = cinemaMapper.selectByPrimaryKey(cinemaid);
        return cinema;
    }

    @Override
    public List<Cinema> getAllCinema() {
        List<Cinema> cinemas = cinemaMapper.selectAll();
        return cinemas;
    }

    @Override
    public PageResult<Cinema> getCinemaByCondition(Cinema cinema,Integer pageNumber,Integer pageSize) {
        PageHelper.startPage(pageNumber,pageSize);

        Example example = new Example(Cinema.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isBlank(cinema.getCinemaName())){
            criteria.andLike("cinemaName","%"+cinema.getCinemaName()+"%");
        }
        if(!StringUtils.isBlank(cinema.getCinemaAddress())){
            criteria.andLike("cinemaAddress","%"+cinema.getCinemaAddress()+"%");
        }
        List<Cinema> cinemas = cinemaMapper.selectByExample(example);

        PageInfo<Cinema> pageInfo = new PageInfo<>(cinemas);

        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public PageResult<Cinema> getAllCinemasByPage(Integer pageNumber, Integer rows, Long cid) {

        //条件查询
        Example example = new Example(Cinema.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cityid",cid);

        PageHelper.startPage(pageNumber,rows);

        List<Cinema> list = cinemaMapper.selectByExample(example);
        //封装pageresult
        PageInfo<Cinema> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();//获得总条数
        List<Cinema> cinemaList = pageInfo.getList();//获得分页记录

        return new PageResult<>(total,cinemaList);
    }

    @Override
    public PageResult<Cinema> querylistPage(Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        List<Cinema> list = cinemaMapper.selectAll();
        PageInfo<Cinema> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }
}
