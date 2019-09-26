package com.huangniuniu.movie.service;

import com.huangniuniu.movie.mapper.MovieMapper;
import com.huangniuniu.movie.pojo.Movie;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class MovieService {
    @Autowired
    private MovieMapper movieMapper;

    public List<Movie> getAllMovie(){
        Example example = new Example(Movie.class);
        Example.Criteria criteria = example.createCriteria();
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH,-3);
        Date time = instance.getTime();
        criteria.andGreaterThan("soldOutTime",time);
        return this.movieMapper.selectByExample(example);
    }

    public Movie getMovieByMovieid(Integer mid){
        return this.movieMapper.selectByPrimaryKey(mid);
    }
    public void insertMovie(Movie movie){
       this.movieMapper.insertSelective(movie);

    }
    public void deleteMovie(Long id){
        this.movieMapper.deleteByPrimaryKey(id);

    }

    /**
     * 根据条件查询电影（名称、类型、地区模糊匹配，评分相等）
     * @param movie
     * @return
     */
    public List<Movie> getMovieByCondition(Movie movie){
        Example example = new Example(Movie.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isBlank(movie.getMovieName())){
            criteria.andLike("movieName","%"+movie.getMovieName()+"%");
        }
        if(!StringUtils.isBlank(movie.getMovieType())){
            criteria.andLike("movieType","%"+movie.getMovieType()+"%");
        }
        if(!StringUtils.isBlank(movie.getLocation())){
            criteria.andEqualTo("location",movie.getLocation());
        }
        if(movie.getScore()!=null){
            criteria.andEqualTo("score",movie.getScore());
        }
        List<Movie> movies = movieMapper.selectByExample(example);
        return movies;

    }

    public List<Movie> getMovieByCityid(Long cid,Boolean ishot){
            List<Movie> movieByCityids = movieMapper.getMovieByCityid(cid, ishot);
        List<Movie> movies=new ArrayList<Movie>();
                movieByCityids.forEach(movieByCityid->{
                    Example example = new Example(Movie.class);
                    Example.Criteria criteria = example.createCriteria();
                    Date date = new Date();
                    if(movieByCityid.getId()!=null){
                        criteria.andEqualTo("id",movieByCityid.getId());
                    }
                    if(ishot){
                        if(movieByCityid.getSoldOutTime()!=null){
                            criteria.andNotBetween("releaseTime",date,movieByCityid.getSoldOutTime());
                            criteria.andGreaterThan("soldOutTime",date);
                        }
                    }else {
                        criteria.andGreaterThan("releaseTime",date);
                    }
                    Movie movie = movieMapper.selectOneByExample(example);
                    if(!org.springframework.util.StringUtils.isEmpty(movie)){
                        movies.add(movie);
                    }
                });
                return movies;
    }

}