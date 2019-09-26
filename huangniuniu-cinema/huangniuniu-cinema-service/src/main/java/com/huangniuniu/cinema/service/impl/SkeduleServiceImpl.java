package com.huangniuniu.cinema.service.impl;

import com.huangniuniu.cinema.mapper.SkeduleMapper;
import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.cinema.service.SkeduleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SkeduleServiceImpl implements SkeduleService {

    @Autowired
    private SkeduleMapper skeduleMapper;

    @Override
    @Transactional
    public void addSkedule(Skedule skedule, Cinema_movie cinema_movie) {
        //初始化排场信息
        skedule.setId(null);
        skedule.setTicketsSold(0);
        skedule.setCinemaid(cinema_movie.getCinemaid());
        skedule.setMovieid(cinema_movie.getMovieid());
        skedule.setCinemaName(cinema_movie.getCinemaName());
        skedule.setMovieName(cinema_movie.getMovieName());
        //添加排场信息
        skeduleMapper.insertSelective(skedule);
    }

    @Override
    public List<Skedule> getAllSkedule() {
        List<Skedule> skedules = skeduleMapper.selectAll();
        return skedules;
    }

    @Override
    public Skedule getSkeduleBySkeduleid(Long sid) {
        Skedule skedule = skeduleMapper.selectByPrimaryKey(sid);
        return skedule;
    }

    @Override
    public void deleteSkeduleBysid(Long sid) {
        skeduleMapper.deleteByPrimaryKey(sid);
    }

    @Override
    public void updateSkedule(Skedule skedule) {
        skeduleMapper.updateByPrimaryKeySelective(skedule);
    }

    @Override
    public List<Skedule> getSkeduleByCondition(Skedule skedule) {

        Example example = new Example(Skedule.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isBlank(skedule.getCinemaName())){
            criteria.andLike("cinemaName","%"+skedule.getCinemaName()+"%");
        }
        if(!StringUtils.isBlank(skedule.getMovieName())){
            criteria.andLike("movieName","%"+skedule.getMovieName()+"%");
        }
        List<Skedule> list = skeduleMapper.selectByExample(example);
        return list;
    }

}
