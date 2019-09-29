package com.huangniuniu.movie.mapper;

import com.huangniuniu.movie.pojo.Actor;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ActorMapper extends Mapper<Actor> {
    @Select("SELECT * from actor WHERE id IN(SELECT actorid FROM actor_movie WHERE movieid=#{mid})")
    List<Actor> getActorByMovieid(Long mid);
}
