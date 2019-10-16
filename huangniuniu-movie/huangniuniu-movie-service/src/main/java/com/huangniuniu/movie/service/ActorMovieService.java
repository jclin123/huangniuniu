package com.huangniuniu.movie.service;

import com.huangniuniu.movie.pojo.ActorMovie;

public interface ActorMovieService {
    /**
     * 在给电影加演员时添加至中间表
     * @param actorMovie
     * @return
     */
    Boolean addActorAndMovie(ActorMovie actorMovie);
    /**
     * 给电影删除指定演员，删除中间表
     * @param actorMovie
     */
    void deleteActorAndMovie(ActorMovie actorMovie);

    /**
     * 根据演员id和电影id确定中间表的一条记录
     * @param actorMovie
     * @return
     */
    ActorMovie getActorMovieBymidAndAid(ActorMovie actorMovie);

    /**
     * 根据中间表主键删除
     * @param id
     */
    void deleteactorMovieById(Long id);

}
