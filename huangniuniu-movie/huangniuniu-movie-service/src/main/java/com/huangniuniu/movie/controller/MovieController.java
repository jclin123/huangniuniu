package com.huangniuniu.movie.controller;

import com.huangniuniu.movie.pojo.Movie;
import com.huangniuniu.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;

    /**
     * 查询电影信息
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Movie>> getAllMovie(){
        List<Movie> allMovie = this.movieService.getAllMovie();
        if(CollectionUtils.isEmpty(allMovie)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allMovie);

    }

    /**
     * 根据电影id查询电影
     * @param mid
     * @return
     */
    @GetMapping("{mid}")
        public ResponseEntity<Movie> getMovieByMovieid(@PathVariable("mid") Long mid){
        Movie movie = this.movieService.getMovieByMovieid(mid);
        if(movie == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    @PostMapping("insert")
    public ResponseEntity<Void> insertMovie(@Valid Movie movie){
        this.movieService.insertMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id){
        this.movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("getByCondition")
    public ResponseEntity<List<Movie>> getMovieByCondition(Movie movie){

        List<Movie> movieByCondition = this.movieService.getMovieByCondition(movie);
        if(CollectionUtils.isEmpty(movieByCondition)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieByCondition);
    }

    @GetMapping("getMovieByCityid")
    public ResponseEntity<List<Movie>> getMovieByCityid(@RequestParam("cid") Long cid,@RequestParam("ishot") Boolean ishot){
        List<Movie> movieByCityid = movieService.getMovieByCityid(cid, ishot);

        if(CollectionUtils.isEmpty(movieByCityid)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieByCityid);

    }



}
