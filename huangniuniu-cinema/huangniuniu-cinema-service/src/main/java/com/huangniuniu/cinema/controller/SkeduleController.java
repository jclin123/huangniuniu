package com.huangniuniu.cinema.controller;

import com.huangniuniu.cinema.pojo.CinemaDetail;
import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.cinema.service.SkeduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("skedule")
public class SkeduleController {

    @Autowired
    private SkeduleService skeduleService;

    /**
     * 根据电影院和电影信息，添加排场信息
     * @param skedule
     * @param cinema_movie
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addSkedule(Skedule skedule, Cinema_movie cinema_movie){
        skeduleService.addSkedule(skedule,cinema_movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 查询所有排场信息
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Skedule>> getAllSkedule(){
        List<Skedule> list = skeduleService.getAllSkedule();
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据排场id查询该排场
     * @param sid
     * @return
     */
    @GetMapping("{sid}")
    public ResponseEntity<Skedule> getSkeduleBySkeduleid(@PathVariable("sid")Long sid){
        Skedule skedule = skeduleService.getSkeduleBySkeduleid(sid);
        if(skedule == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skedule);
    }

    /**
     * 删除
     * @param sid
     * @return
     */
    @DeleteMapping("{sid}")
    public ResponseEntity<Void> deleteSkeduleBysid(@PathVariable("sid")Long sid){
        skeduleService.deleteSkeduleBysid(sid);
        return ResponseEntity.noContent().build();
    }

    /**
     * 修改排场信息
     * @param skedule
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateSkedule(Skedule skedule){
        skeduleService.updateSkedule(skedule);
        return ResponseEntity.noContent().build();
    }

    /**
     * 条件查询排场信息
     * @param skedule
     * @return
     */
    @PostMapping("conditionlsit")
    public ResponseEntity<List<Skedule>> getSkeduleByCondition(@RequestBody Skedule skedule){
        System.out.println("=======getSkeduleByCondition=========");
        List<Skedule> list = skeduleService.getSkeduleByCondition(skedule);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("cinemaDetail/{cid}")
    public ResponseEntity<CinemaDetail> getCinemaDetailBycinemaId(@PathVariable("cid")Long cid){
        CinemaDetail detail = skeduleService.getCinemaDetailBycinemaId(cid);
        if(detail == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detail);
    }

    /**
     * 当页面加载后发出，根据电影院id查询出该电影院信息以及该电影院的电影信息
     * @param cinemaid
     * @return
     */
    @GetMapping("cinemaMovieListBycid/{cinemaid}")
    public ResponseEntity<Map<String,Object>> selectCinemaAndMovieListByCinemaId(@PathVariable("cinemaid")Long cinemaid){
        Map<String, Object> map = skeduleService.selectCinemaAndMovieListByCinemaId(cinemaid);
        if(CollectionUtils.isEmpty(map)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(map);
    }

}
