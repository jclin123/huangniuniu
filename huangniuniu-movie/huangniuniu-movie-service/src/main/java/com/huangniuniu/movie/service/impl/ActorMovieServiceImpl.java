package com.huangniuniu.movie.service.impl;

import com.huangniuniu.movie.mapper.ActorMovieMapper;
import com.huangniuniu.movie.pojo.ActorMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ActorMovieServiceImpl {
    @Autowired
    private ActorMovieMapper actorMovieMapper;

    /**
     * 在给电影加演员时添加至中间表
     * @param actorMovie
     * @return
     */
    @Transactional
    public Boolean addActorAndMovie(ActorMovie actorMovie){
        ActorMovie actorMovie1 = actorMovieMapper.selectOne(actorMovie);
        //演员不能重复
        if(actorMovie1==null){
            actorMovieMapper.insertSelective(actorMovie);
            return true;
        }
        return false;
    }

    /**
     * 给电影删除指定演员，删除中间表
     * @param actorMovie
     */
    @Transactional
    public void deleteActorAndMovie(ActorMovie actorMovie){
        Example example = new Example(ActorMovie.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("actorid",actorMovie.getActorid());
        criteria.andEqualTo("movieid",actorMovie.getMovieid());
        List<ActorMovie> actorMovies = actorMovieMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(actorMovies)){
            actorMovieMapper.deleteByPrimaryKey(actorMovies.get(0).getId());
        }
    }

    public ActorMovie getActorMovieBymidAndAid(ActorMovie actorMovie){
        Example example = new Example(ActorMovie.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("actorid",actorMovie.getActorid());
        criteria.andEqualTo("movieid",actorMovie.getMovieid());
        List<ActorMovie> actorMovies = actorMovieMapper.selectByExample(example);
        return actorMovies.get(0);
    }

    public void deleteactorMovieById(Long id){
        actorMovieMapper.deleteByPrimaryKey(id);
    }

}
