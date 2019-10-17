package com.huangniuniu.movie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.mapper.ActorMapper;
import com.huangniuniu.movie.pojo.Actor;
import com.huangniuniu.movie.pojo.Movie;
import com.huangniuniu.movie.service.ActorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {
    @Autowired
    private ActorMapper actorMapper;

    /**
     * 添加演员
     * @param actor
     */
    @Transactional
    public void insertActor(Actor actor){
        actorMapper.insertSelective(actor);
    }

    /**
     *删除演员
     * @param id
     */
    @Transactional
    public void deleteActor(Long id){
        actorMapper.deleteByPrimaryKey(id);
    }

    /**
     *更新演员信息
     * @param actor
     */
    @Transactional
    public void updateActor(Actor actor){
        actorMapper.updateByPrimaryKeySelective(actor);
    }

    /**
     *分页获得所有演员信息
     * @return
     */
    public PageResult<Actor> getAllActor(Integer page, Integer rows){
        PageHelper.startPage(page, rows);
        List<Actor> actors = actorMapper.selectAll();
        PageInfo<Actor> pageInfo=new PageInfo<>(actors);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     *分页根据条件查询演员（名字模糊匹配、编号相等）
     * @param actor
     * @return
     */
    public PageResult<Actor> getActorByCondition(Integer page, Integer rows,Actor actor){
        Example example = new Example(Actor.class);
        Example.Criteria criteria=example.createCriteria();
        if(!StringUtils.isBlank(actor.getActorName())){
            criteria.andLike("actorName","%"+actor.getActorName()+"%");
        }
        if(actor.getId()!=null){
            criteria.andEqualTo("id",actor.getId());
        }
        PageHelper.startPage(page, rows);
        List<Actor> actors = actorMapper.selectByExample(example);
        PageInfo<Actor> pageInfo=new PageInfo<>(actors);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     *分页根据电影id查询所有演员
     * @param mid
     * @return
     */
    public PageResult<Actor> getActorByMovieid(Integer page, Integer rows,Long mid){
        PageHelper.startPage(page, rows);
        List<Actor> actorByMovieid = actorMapper.getActorByMovieid(mid);
        PageInfo<Actor> pageInfo=new PageInfo<>(actorByMovieid);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }



}
