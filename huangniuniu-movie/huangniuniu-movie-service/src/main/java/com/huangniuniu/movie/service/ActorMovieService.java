package com.huangniuniu.movie.service;

import com.huangniuniu.movie.mapper.ActorMovieMapper;
import com.huangniuniu.movie.pojo.ActorMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActorMovieService {
    @Autowired
    private ActorMovieMapper actorMovieMapper;


    @Transactional
    public Boolean addActorAndMovie(ActorMovie actorMovie ){
        ActorMovie actorMovie1 = actorMovieMapper.selectOne(actorMovie);
        if(actorMovie1==null){
            actorMovieMapper.insertSelective(actorMovie);
            return true;
        }
        return false;


    }
    public void deleteActorAndMovie(ActorMovie actorMovie){
        ActorMovie actorMovie1 = actorMovieMapper.selectOne(actorMovie);
        actorMovieMapper.deleteByPrimaryKey(actorMovie1.getId());

    }

}
