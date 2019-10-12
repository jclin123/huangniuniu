package com.huangniuniu.movie.mapper;

import com.huangniuniu.movie.pojo.Movie;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MovieMapper  extends Mapper<Movie> {

    @Select("SELECT * FROM movie WHERE id IN(SELECT movieid FROM cinema_movie WHERE cinemaid IN(SELECT id FROM cinema WHERE cityid=#{cid}))")
    public List<Movie> getMovieByCityid(@Param("cid") Long cid);

}
