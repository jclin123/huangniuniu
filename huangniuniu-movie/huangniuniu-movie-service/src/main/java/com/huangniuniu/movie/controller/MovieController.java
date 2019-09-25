package com.huangniuniu.movie.controller;

import com.huangniuniu.movie.pojo.Movie;
import com.huangniuniu.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("list")
    public ResponseEntity<List<Movie>> getAllMovie(){
        List<Movie> allMovie = this.movieService.getAllMovie();
        if(CollectionUtils.isEmpty(allMovie)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allMovie);

    }

    @GetMapping("getById/{mid}")
    public ResponseEntity<Movie> getMovieByMovieid(@PathVariable("mid") Integer mid){
        Movie movieByMovieid = this.movieService.getMovieByMovieid(mid);
        if(StringUtils.isEmpty(movieByMovieid)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieByMovieid);
    }

    @PostMapping("insert")
    public ResponseEntity<Void> insertMovie(@RequestBody() Movie movie){
        this.movieService.insertMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("delete")
    public ResponseEntity<Void> deleteMovie(@RequestBody() Movie movie){

        this.movieService.deleteMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("getByCondition")
    public ResponseEntity<List<Movie>> getMovieByCondition(@RequestBody() Movie movie){
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//        movie.setSoldOutTime(new Date(df.format(movie.getReleaseTime())));
//        movie.setReleaseTime(new Date(df.format(movie.getSoldOutTime())));
        List<Movie> movieByCondition = this.movieService.getMovieByCondition(movie);
        if(CollectionUtils.isEmpty(movieByCondition)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieByCondition);
    }








}
