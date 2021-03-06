package com.huangniuniu.movie.controller;

import com.huangniuniu.movie.pojo.ActorMovie;
import com.huangniuniu.movie.service.impl.ActorMovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActorMovieController {
    @Autowired
    private ActorMovieServiceImpl actorMovieService;

    /**
     * 在给电影加演员时添加至中间表
     * @param actorMovie
     * @return
     */
    @PostMapping("addActorAndMovie")
    public ResponseEntity<Boolean> addActorAndMovie(ActorMovie actorMovie){
        if(actorMovieService.addActorAndMovie(actorMovie)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();

    }

    /**
     * 给电影删除指定演员，删除中间表
     * @param actorMovie
     * @return
     */
    @DeleteMapping("deleteActorAndMovie")
    public ResponseEntity<Void> deleteActorAndMovie(ActorMovie actorMovie){
        actorMovieService.deleteActorAndMovie(actorMovie);
        return ResponseEntity.noContent().build();

    }

    /**
     * 根据aid、mid查询中间表
     * @param actorMovie
     * @return
     */
    @GetMapping("getactorMovie")
    public ResponseEntity<ActorMovie> getActorMovieBymidAndAid(ActorMovie actorMovie){
        ActorMovie movie = this.actorMovieService.getActorMovieBymidAndAid(actorMovie);
        if(movie == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * 删除中间表
     * @param id
     * @return
     */
    @DeleteMapping("deleteOne/{id}")
    public ResponseEntity<Void> deleteactorMovieById(@PathVariable("id")Long id){
        actorMovieService.deleteactorMovieById(id);
        return ResponseEntity.noContent().build();
    }

}
