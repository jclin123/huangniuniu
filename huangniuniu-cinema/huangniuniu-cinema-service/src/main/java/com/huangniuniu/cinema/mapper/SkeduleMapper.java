package com.huangniuniu.cinema.mapper;

import com.huangniuniu.cinema.pojo.Skedule;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkeduleMapper extends Mapper<Skedule> {

    @Select("select SUBSTRING(show_date,1,10) date from skedule WHERE SUBSTRING(show_date,1,10)>=SUBSTRING(SYSDATE(),1,10) AND cinemaid=#{cinemaid} AND movieid=#{movieid} group by SUBSTRING(show_date,1,10)")
    List<String> selectSkeduleTimeListByCinemaIdAndMovieId(@Param("cinemaid")Long cinemaid, @Param("movieid")Long movieid);
}
