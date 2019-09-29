package com.huangniuniu.movie.api;

import com.huangniuniu.movie.pojo.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface MovieApi {

    /**
     * 根据电影id查询电影
     * @param mid
     * @return
     */
    @GetMapping("{mid}")
    public Movie getMovieByMovieid(@PathVariable("mid") Long mid);
}
