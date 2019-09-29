package com.huangniuniu.movie.service;

import com.huangniuniu.movie.mapper.ActorMapper;
import com.huangniuniu.movie.pojo.Actor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ActorService {
    @Autowired
    private ActorMapper actorMapper;

    @Transactional
    public void insertActor(Actor actor){
        actorMapper.insertSelective(actor);

    }

    public void deleteActor(Long id){
        actorMapper.deleteByPrimaryKey(id);
    }

    public void updateActor(Actor actor){
        actorMapper.updateByPrimaryKeySelective(actor);
    }

    public List<Actor> getAllActor(){
        return actorMapper.selectAll();
    }

    public List<Actor> getActorByCondition(Actor actor){
        Example example = new Example(Actor.class);
        Example.Criteria criteria=example.createCriteria();
        if(!StringUtils.isBlank(actor.getActorName())){
            criteria.andLike("actorName","%"+actor.getActorName()+"%");
        }
        if(actor.getId()!=null){
            criteria.andEqualTo("id",actor.getId());
        }
        return actorMapper.selectByExample(example);

    }

    public List<Actor> getActorByMovieid(Long mid){

        return actorMapper.getActorByMovieid(mid);
    }



}
