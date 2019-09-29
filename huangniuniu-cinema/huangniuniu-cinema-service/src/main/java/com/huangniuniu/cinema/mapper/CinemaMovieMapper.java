package com.huangniuniu.cinema.mapper;

import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.cinema.pojo.Cinema_movie;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CinemaMovieMapper extends Mapper<Cinema_movie> {

    /**
     * 根据cinema_movie的movieid与movie表查询movieName
     * @param mid
     * @return
     */
    @Select("SELECT DISTINCT movie_name FROM cinema_movie a INNER JOIN movie b ON a.movieid=b.id WHERE b.id=#{mid}")
    String selectMovieNameByCinemaMovie(@Param("mid") Long mid);

    /**
     * 根据城市id和电影id查询该城市下有这个电影的电影院,当有驼峰命名时，取别名要去与pojo字段名称一样才能赋值给pojo
     * @param cityid
     * @param mid
     * @return
     */
    @Select("SELECT c.id,c.cinema_name cinemaName,c.cinema_address cinemaAddress,c.cityid FROM cinema c INNER JOIN cinema_movie cm ON c.id= cm.cinemaid WHERE c.cityid=#{cityid} AND cm.movieid=#{mid}")
    List<Cinema> selectCinemaByCityIdAndMovieId(@Param("cityid") Long cityid, @Param("mid") Long mid);
}
