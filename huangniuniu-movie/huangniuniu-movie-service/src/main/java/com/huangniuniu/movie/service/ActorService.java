package com.huangniuniu.movie.service;

import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.pojo.Actor;
import org.springframework.transaction.annotation.Transactional;

public interface ActorService {
    /**
     * 添加演员
     * @param actor
     */
    void insertActor(Actor actor);
    /**
     *删除演员
     * @param id
     */
    void deleteActor(Long id);
    /**
     *更新演员信息
     * @param actor
     */
    void updateActor(Actor actor);
    /**
     *获得所有演员信息
     * @return
     */
    PageResult<Actor> getAllActor(Integer page, Integer rows);
    /**
     *根据条件查询演员（名字模糊匹配、编号相等）
     * @param actor
     * @return
     */
    PageResult<Actor> getActorByCondition(Integer page, Integer rows,Actor actor);
    /**
     *根据电影id查询所有演员
     * @param mid
     * @return
     */
    PageResult<Actor> getActorByMovieid(Integer page, Integer rows,Long mid);
}
