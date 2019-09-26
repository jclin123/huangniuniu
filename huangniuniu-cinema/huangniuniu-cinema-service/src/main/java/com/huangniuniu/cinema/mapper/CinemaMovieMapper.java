package com.huangniuniu.cinema.mapper;

import com.huangniuniu.cinema.pojo.Cinema_movie;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface CinemaMovieMapper extends Mapper<Cinema_movie> {

    /**
     * 根据cinema_movie的movieid与movie表查询movieName
     * @param mid
     * @return
     */
    @Select("SELECT DISTINCT movie_name FROM cinema_movie a INNER JOIN movie b ON a.movieid=b.id WHERE b.id=#{mid}")
    String selectMovieNameByCinemaMovie(@Param("mid") Long mid);
}
