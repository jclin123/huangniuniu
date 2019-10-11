package com.huangniuniu.cinema.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.cinema.mapper.CinemaMapper;
import com.huangniuniu.cinema.mapper.CinemaMovieMapper;
import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.cinema.service.CinemaMovieService;
import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.pojo.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CinemaMovieServiceImpl implements CinemaMovieService {

    @Autowired
    private CinemaMovieMapper cinemaMovieMapper;
    @Autowired
    private CinemaMapper cinemaMapper;

    @Override
    public void chooseCinemaMovie(Cinema_movie cinema_movie) {
        cinemaMovieMapper.insertSelective(cinema_movie);
    }

    @Override
    public Map<String,Object> getMoviesByCinemaId(Long cinemaid) {
        //查询该电影院下的所有电影id，id降序
        Example example = new Example(Cinema_movie.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cinemaid",cinemaid);
        //降序
        //example.setOrderByClause("desc");
        example.orderBy("id").desc();

        List<Cinema_movie> cinema_movies = cinemaMovieMapper.selectByExample(example);//两个id加一个主键

        //根据电影id得到电影名称，封装成有电影名称的cinema_movie对象列表返回
        cinema_movies.forEach(cinema_movie->{
            String movieName = cinemaMovieMapper.selectMovieNameByCinemaMovie(cinema_movie.getMovieid());
            cinema_movie.setMovieName(movieName);
        });

        //查询电影院名称
        Cinema cinema = cinemaMapper.selectByPrimaryKey(cinemaid);
        String cinemaName = cinema.getCinemaName();
        Map<String,Object> map = new HashMap<>();
        map.put("cinemaName",cinemaName);
        map.put("items",cinema_movies);

        return map;
    }

    @Override
    public void deleteCinemaMovie(Long id) {
        cinemaMovieMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Cinema_movie selectCinemaMovieBycmid(Long id) {
        Cinema_movie cinema_movie = cinemaMovieMapper.selectByPrimaryKey(id);
        //获得电影名称
        String movieName = cinemaMovieMapper.selectMovieNameByCinemaMovie(cinema_movie.getMovieid());
        //获得电影院名称
        Cinema cinemaName = cinemaMapper.selectByPrimaryKey(cinema_movie.getCinemaid());
        //封装cinema_movie
        Cinema_movie cinema_movie2 = new Cinema_movie();
        cinema_movie2.setCinemaid(cinema_movie.getCinemaid());
        cinema_movie2.setMovieid(cinema_movie.getMovieid());
        cinema_movie2.setMovieName(movieName);
        cinema_movie2.setCinemaName(cinemaName.getCinemaName());

        return cinema_movie2;
    }

    @Override
    public List<Cinema> selectCinemaByCityIdAndMovieId(Long cityid, Long movieid) {

        List<Cinema> cinemaList = cinemaMovieMapper.selectCinemaByCityIdAndMovieId(cityid, movieid);
        return cinemaList;
    }

    @Override
    public PageResult<Movie> getMoviesPageByCinemaId(Long cid, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        //查询该电影院下的所有电影
        Example example = new Example(Cinema_movie.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cinemaid",cid);
        example.orderBy("movieid").desc();

        List<Cinema_movie> cinema_movies = cinemaMovieMapper.selectByExample(example);//两个id加一个主键

        //根据电影id得到电影名称，封装成有电影名称的cinema_movie对象列表返回
        cinema_movies.forEach(cinema_movie->{
            String movieName = cinemaMovieMapper.selectMovieNameByCinemaMovie(cinema_movie.getMovieid());
            cinema_movie.setMovieName(movieName);
        });

        PageInfo<Cinema_movie> pageInfo = new PageInfo<>(cinema_movies);
        //封装pageresult
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

}
