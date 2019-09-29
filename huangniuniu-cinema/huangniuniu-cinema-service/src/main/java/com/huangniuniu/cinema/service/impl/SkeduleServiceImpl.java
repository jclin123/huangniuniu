package com.huangniuniu.cinema.service.impl;

import com.huangniuniu.cinema.mapper.CinemaMapper;
import com.huangniuniu.cinema.mapper.CinemaMovieMapper;
import com.huangniuniu.cinema.mapper.SkeduleMapper;
import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.cinema.pojo.CinemaDetail;
import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.cinema.service.SkeduleService;
import com.huangniuniu.movie.api.MovieApi;
import com.huangniuniu.movie.pojo.Movie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

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
    private MovieApi movieApi;

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

    @Override
    public CinemaDetail getCinemaDetailBycinemaId(Long cinemaid) {
       /* //查询电影院信息
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
                Movie movie = movieApi.getMovieByMovieid(cinema_movie.getMovieid());
                if(movie.getSoldOutTime().getTime()>date.getTime()){
                    movieList.add(movie);
                }
            });
        }

        //2、(遍历电影列表，从而获得该电影的全部排场)，条件：排场时间>当前时间，筛选出未上映的排场,按时间升序

        Map<Movie,List<Skedule>> movieListMap = new HashMap<>();
            movieList.forEach(movie -> {
                Example example2 = new Example(Skedule.class);
                Example.Criteria criteria2 = example2.createCriteria();
                criteria2.andGreaterThan("showDate",date);
                criteria2.andEqualTo("movieid",movie.getId());
                example2.orderBy("showDate").asc();//排场时间升序
                List<Skedule> skedules = skeduleMapper.selectByExample(example2);
                movieListMap.put(movie,skedules);
            });
        //3、给全部排场按照时间分组,给movieListMap遍历，如果前后时间不一样

*/
        return null;
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
                Movie movie = movieApi.getMovieByMovieid(cinema_movie.getMovieid());
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

}
