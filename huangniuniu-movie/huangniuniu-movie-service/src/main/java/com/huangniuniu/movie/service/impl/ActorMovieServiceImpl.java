package com.huangniuniu.movie.service.impl;

import com.huangniuniu.movie.mapper.ActorMovieMapper;
import com.huangniuniu.movie.pojo.ActorMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        ActorMovie actorMovie1 = actorMovieMapper.selectOne(actorMovie);
        actorMovieMapper.deleteByPrimaryKey(actorMovie1.getId());
    }

}
