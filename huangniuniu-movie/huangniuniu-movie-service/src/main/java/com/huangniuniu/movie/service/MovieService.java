package com.huangniuniu.movie.service;

import com.huangniuniu.movie.mapper.MovieMapper;
import com.huangniuniu.movie.pojo.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class MovieService {
    @Autowired
    private MovieMapper movieMapper;

    public List<Movie> getAllMovie(){
        Movie movie = new Movie();
        return this.movieMapper.select(movie);
    }

    public Movie getMovieByMovieid(Integer mid){
        return this.movieMapper.selectByPrimaryKey(mid);
    }
    public void insertMovie(Movie movie){
       this.movieMapper.insertSelective(movie);

    }
    public void deleteMovie(Movie movie){
        this.movieMapper.delete(movie);

    }
    public List<Movie> getMovieByCondition(Movie movie){
        Example example = new Example(Movie.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("releaseTime",movie.getReleaseTime());
        return this.movieMapper.select(movie);
    }






}
