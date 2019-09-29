package com.huangniuniu.movie.controller;


import com.huangniuniu.movie.pojo.Actor;
import com.huangniuniu.movie.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActorController {
    @Autowired
    private ActorService actorService;


    @PostMapping("insertActor")
    public ResponseEntity<Void> insertActor(Actor actor){
        actorService.insertActor(actor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("deleteActor/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable("id") Long id){
        actorService.deleteActor(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("updateActor")
    public ResponseEntity<Void> updateActor(Actor actor){

        actorService.updateActor(actor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("listActor")
    public ResponseEntity<List<Actor>> getAllActor(){
        List<Actor> allActor = actorService.getAllActor();
        if(CollectionUtils.isEmpty(allActor)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allActor);

    }

    @GetMapping("getActorByCondition")
    public ResponseEntity<List<Actor>> getActorByCondition(Actor actor){
        List<Actor> actorByCondition = actorService.getActorByCondition(actor);
        if(CollectionUtils.isEmpty(actorByCondition)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actorByCondition);
    }

    @GetMapping("getActorByMovieid")
    public ResponseEntity<List<Actor>> getActorByMovieid(@RequestParam("mid") Long mid){
        List<Actor> actorByMovieid = actorService.getActorByMovieid(mid);
        if(CollectionUtils.isEmpty(actorByMovieid)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actorByMovieid);

    }

}
