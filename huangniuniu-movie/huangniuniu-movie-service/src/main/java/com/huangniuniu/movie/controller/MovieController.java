package com.huangniuniu.movie.controller;

import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.pojo.Movie;
import com.huangniuniu.movie.pojo.MovieDetail;
import com.huangniuniu.movie.service.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MovieController {
    @Autowired
    private MovieServiceImpl movieService;

    /**
     * 分页查询电影信息，已下架三个月不展出
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<Movie>> getAllMovie(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(value = "rows", defaultValue = "10") Integer rows) {
        PageResult<Movie> allMovie = movieService.getAllMovie(page, rows);
        if (allMovie == null || CollectionUtils.isEmpty(allMovie.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allMovie);

    }


    /**
     * 根据电影id查询电影
     *
     * @param mid
     * @return
     */
    @GetMapping("{mid}")
    public ResponseEntity<Movie> getMovieByMovieid(@PathVariable("mid") Long mid) {
        Movie movie = this.movieService.getMovieByMovieid(mid);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * 添加电影
     *
     * @param movie
     * @return
     */
    @PostMapping("insert")
    public ResponseEntity<Void> insertMovie(@Valid Movie movie) {
        this.movieService.insertMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除电影
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id) {
        this.movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 分页根据条件查询电影（名称、类型、地区模糊匹配，评分相等）
     *
     * @param movie
     * @return
     */
    @GetMapping("getMovieByCondition")
    public ResponseEntity<PageResult<Movie>> getMovieByCondition(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                 @RequestParam(value = "rows", defaultValue = "10") Integer rows,
                                                                 Movie movie) {
        PageResult<Movie> movieByCondition = movieService.getMovieByCondition(page, rows, movie);
        if (movieByCondition == null || CollectionUtils.isEmpty(movieByCondition.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieByCondition);
    }

    /**
     * 分页根据城市id查询电影
     * ishot为true为热映
     * ishot为false为即将上映
     * 已下架不展出
     *
     * @param cid
     * @param ishot
     * @return
     */
    @GetMapping("getMovieByCityid")
    public ResponseEntity<PageResult<Movie>> getMovieByCityid(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(value = "rows", defaultValue = "10") Integer rows,
                                                              @RequestParam("cid") Long cid,
                                                              @RequestParam("ishot") Boolean ishot) {
        PageResult<Movie> movieByCityid = movieService.getMovieByCityid(page, rows, cid, ishot);
        if (movieByCityid == null || CollectionUtils.isEmpty(movieByCityid.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieByCityid);

    }

    /**
     * 根据电影id查询封装电影详情数据
     *
     * @param movieid
     * @return
     */
    @GetMapping("getMovieDetail/{movieid}")
    public ResponseEntity<MovieDetail> getMovieDetail(@PathVariable("movieid") Long movieid,
                                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                      @RequestParam(value = "rows", defaultValue = "10") Integer rows) {
        MovieDetail movieDetail = movieService.getMovieDetail(page, rows, movieid);
        if (movieDetail == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(movieDetail);
    }


}
