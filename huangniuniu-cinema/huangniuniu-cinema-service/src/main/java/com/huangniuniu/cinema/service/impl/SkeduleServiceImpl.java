package com.huangniuniu.cinema.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.cinema.client.MovieClient;
import com.huangniuniu.cinema.mapper.CinemaMapper;
import com.huangniuniu.cinema.mapper.CinemaMovieMapper;
import com.huangniuniu.cinema.mapper.SkeduleMapper;
import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.cinema.service.SkeduleService;
import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.pojo.Movie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SkeduleServiceImpl implements SkeduleService {

    @Autowired
    private SkeduleMapper skeduleMapper;
    @Autowired
    private CinemaMapper cinemaMapper;
    @Autowired
    private CinemaMovieMapper cinemaMovieMapper;
    @Autowired
    private MovieClient movieClient;

    @Override
    @Transactional
    public void addSkedule(Skedule skedule) {
        //初始化排场信息
        skedule.setId(null);
        skedule.setTicketsSold(0);
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
    public PageResult<Skedule> getSkeduleByConditionpage(Skedule skedule, Integer pageNumber, Integer pageSize) {

        PageHelper.startPage(pageNumber,pageSize);

        Example example = new Example(Skedule.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("cinemaName").asc();
        example.orderBy("movieName").asc();
        example.orderBy("roomName").asc();
        example.orderBy("showDate").desc();
        if(!StringUtils.isBlank(skedule.getCinemaName())){
            criteria.andLike("cinemaName","%"+skedule.getCinemaName()+"%");
        }
        if(!StringUtils.isBlank(skedule.getMovieName())){
            criteria.andLike("movieName","%"+skedule.getMovieName()+"%");
        }
        if(!StringUtils.isBlank(skedule.getRoomName())){
            criteria.andLike("roomName","%"+skedule.getRoomName()+"%");
        }
        List<Skedule> list = skeduleMapper.selectByExample(example);
        PageInfo<Skedule> pageInfo = new PageInfo<>(list);

        //封装分页
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());

    }

    @Override
    public List<Skedule> getSkeduleByCondition(Skedule skedule) {
        Example example = new Example(Skedule.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("cinemaName").asc();
        example.orderBy("movieName").asc();
        example.orderBy("showDate").desc();
        if(!StringUtils.isBlank(skedule.getCinemaName())){
            criteria.andLike("cinemaName","%"+skedule.getCinemaName()+"%");
        }
        if(!StringUtils.isBlank(skedule.getMovieName())){
            criteria.andLike("movieName","%"+skedule.getMovieName()+"%");
        }
        List<Skedule> list = skeduleMapper.selectByExample(example);
        return list;
    }


    @Override
    public Map<String, Object> selectCinemaAndMovieListByCinemaId(Long cinemaid) {
        //查询电影院信息
        Cinema cinema = cinemaMapper.selectByPrimaryKey(cinemaid);
        //1、获得该电影院未下架的电影列表，
        //获得该电影院的中间表信息
        Example example = new Example(Cinema_movie.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cinemaid",cinemaid);
        List<Cinema_movie> cinema_movies = cinemaMovieMapper.selectByExample(example);

        //获得该电影院的电影列表，条件：下架时间>当前时间,筛选出来的全部都是上映或者即将上映的电影列表
        List<Movie> movieList = new ArrayList<>();
        Date date = new Date();
        if(!CollectionUtils.isEmpty(cinema_movies)){
            cinema_movies.forEach(cinema_movie -> {
                Movie movie = movieClient.getMovieByMovieid(cinema_movie.getMovieid());
                if(movie.getSoldOutTime().getTime()>date.getTime()){
                    movieList.add(movie);
                }
            });
        }
        Map<String,Object> map = new HashMap<>();
        map.put("cinema",cinema);
        map.put("movieList",movieList);
        return map;

    }

    @Override
    public List<String> selectSkeduleTimeListByCinemaIdAndMovieId(Long cinemaid, Long movieid) {
        //得到排场时间列表
        List<String> strings = skeduleMapper.selectSkeduleTimeListByCinemaIdAndMovieId(cinemaid, movieid);
        return strings;
    }

    @Override
    public List<Skedule> selectSkeduleListByCinemaIdAndMovieIdAndSkeduleTime(Long cinemaid, Long movieid, String Skeduletime) {

        //给Skeduletime设置最大值和最小值
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String SkeduletimeMin = Skeduletime+" 00:00:00";
        String SkeduletimeMax = Skeduletime+" 23:59:59";

        //转换
        try {
            Example example = new Example(Skedule.class);
            Example.Criteria criteria = example.createCriteria();

            //获得当前时间
            Date date = new Date();
            Date mindate = format.parse(SkeduletimeMin);
            Date maxdate = format.parse(SkeduletimeMax);
            criteria.andBetween("showDate",mindate,maxdate);//排场时间在选择的时间的当天时间内
            criteria.andGreaterThan("showDate",date);//超过当前时间的排场
            criteria.andEqualTo("cinemaid",cinemaid);
            criteria.andEqualTo("movieid",movieid);
            List<Skedule> skeduleList = skeduleMapper.selectByExample(example);
            return skeduleList;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void buyTicketBySkeduleId(Long skeduleId, Integer number) {
        Skedule skedule = skeduleMapper.selectByPrimaryKey(skeduleId);
        //修改电影院数量
        Integer ticketsLeft = skedule.getTicketsLeft();
        Integer ticketsSold = skedule.getTicketsSold();
        if(ticketsLeft >= number) {
            skedule.setTicketsSold(ticketsSold + number);
            skedule.setTicketsLeft(ticketsLeft - number);
        }
        skeduleMapper.updateByPrimaryKey(skedule);
    }

    @Override
    public PageResult<Skedule> getAllSkeduleByPage(Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        Example example = new Example(Skedule.class);
        example.orderBy("cinemaName").asc();
        example.orderBy("movieName").asc();
        example.orderBy("showDate").desc();
        List<Skedule> list = this.skeduleMapper.selectByExample(example);

        PageInfo<Skedule> pageInfo = new PageInfo<>(list);
        //封装分页信息
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

}
