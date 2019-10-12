package com.huangniuniu.movie.controller;


import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.pojo.Actor;
import com.huangniuniu.movie.service.ActorService;
import com.huangniuniu.movie.service.impl.ActorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActorController {
    @Autowired
    private ActorService actorService;

    /**
     * 添加演员
     * @param actor
     * @return
     */
    @PostMapping("insertActor")
    public ResponseEntity<Void> insertActor(Actor actor){
        actorService.insertActor(actor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除演员
     * @param id
     * @return
     */
    @DeleteMapping("deleteActor/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable("id") Long id){
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 更新演员信息
     * @param actor
     * @return
     */
    @PutMapping("updateActor")
    public ResponseEntity<Void> updateActor(Actor actor){
        actorService.updateActor(actor);
        return ResponseEntity.noContent().build();
    }

    /**
     * 获得所有演员信息
     * @return
     */
    @GetMapping("listActor")
    public ResponseEntity<PageResult<Actor>> getAllActor(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                         @RequestParam(value = "rows",defaultValue = "10") Integer rows){
        PageResult<Actor> allActor = actorService.getAllActor(page, rows);
        if(allActor==null||CollectionUtils.isEmpty(allActor.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allActor);

    }

    /**
     * 根据条件查询演员（名字模糊匹配、编号相等）
     * @param actor
     * @return
     */
    @GetMapping("getActorByCondition")
    public ResponseEntity<PageResult<Actor>> getActorByCondition(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                 @RequestParam(value = "rows",defaultValue = "10") Integer rows,
                                                                 Actor actor){
        PageResult<Actor> actorByCondition = actorService.getActorByCondition(page, rows,actor);
        if(actorByCondition==null||CollectionUtils.isEmpty(actorByCondition.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actorByCondition);
    }

    /**
     * 根据电影id查询所有演员
     * @param mid
     * @return
     */
    @GetMapping("getActorByMovieid/{mid}")
    public ResponseEntity<PageResult<Actor>> getActorByMovieid(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                               @RequestParam(value = "rows",defaultValue = "10") Integer rows,
                                                               @PathVariable("mid") Long mid){
        PageResult<Actor> actorByMovieid = actorService.getActorByMovieid(page, rows,mid);
        if(actorByMovieid==null||CollectionUtils.isEmpty(actorByMovieid.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actorByMovieid);
    }

}
