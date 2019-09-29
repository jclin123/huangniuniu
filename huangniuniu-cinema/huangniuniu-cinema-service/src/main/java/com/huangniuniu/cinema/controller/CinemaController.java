package com.huangniuniu.cinema.controller;

import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.cinema.service.CinemaService;
import com.huangniuniu.common.pojo.PageReult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    /**
     * 添加电影院
     * @param cinema
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> insertCinema(@Valid Cinema cinema){
        cinemaService.insertCinema(cinema);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据电影院id删除电影院
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable("id")Long id){
        cinemaService.deleteCinema(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 根据电影院id查询电影院
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Cinema> getCinemaByCinemaid(@PathVariable("id")Long id){
        Cinema cinema = cinemaService.getCinemaByCinemaid(id);
        if(cinema == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cinema);
    }

    /**
     * 修改电影院信息
     * @param cinema
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCinema(@Valid Cinema cinema){
        cinemaService.updateCinema(cinema);
        return ResponseEntity.noContent().build();
    }

    /**
     * 查询所有电影院，获得电影院列表
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Cinema>> getAllCinema(){
        List<Cinema> list = cinemaService.getAllCinema();
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 条件查询所有电影院，获得条件查询后的电影院列表
     * @param cinema
     * @return
     */
    @GetMapping("listCondition")
    public ResponseEntity<List<Cinema>> getCinemaByCondition(Cinema cinema){
        List<Cinema> list = cinemaService.getCinemaByCondition(cinema);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据城市id，分显示电影院列表
     * @param pageNumber
     * @param rows
     * @param cityid
     * @return
     */
    @GetMapping("pagelist")
    public ResponseEntity<PageReult<Cinema>> getAllCinemasByPage(@RequestParam(name = "pageNumber",defaultValue = "1")Integer pageNumber,
                                                                 @RequestParam(name = "rows",defaultValue = "6")Integer rows,
                                                                 @RequestParam("cityid")Long cityid){
        PageReult<Cinema> list = cinemaService.getAllCinemasByPage(pageNumber, rows, cityid);
        if(list == null || CollectionUtils.isEmpty(list.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }
}
