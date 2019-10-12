package com.huangniuniu.movie.service;

import com.huangniuniu.movie.pojo.ActorMovie;
import org.springframework.transaction.annotation.Transactional;

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
}
