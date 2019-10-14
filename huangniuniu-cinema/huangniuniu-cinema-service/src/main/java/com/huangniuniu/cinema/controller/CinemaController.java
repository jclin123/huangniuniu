package com.huangniuniu.cinema.controller;

import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.cinema.service.CinemaService;
import com.huangniuniu.common.pojo.PageResult;
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
    @GetMapping("id/{id}")
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
     * 分页查询电影院信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("listPage")
    public ResponseEntity<PageResult<Cinema>> querylistPage(@RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber,
                                                            @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageResult<Cinema> list = this.cinemaService.querylistPage(pageNumber,pageSize);
        if(list == null || CollectionUtils.isEmpty(list.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 条件分页查询所有电影院，获得条件查询后的电影院列表
     * @param cinema
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("listConditionPage")
    public ResponseEntity<PageResult<Cinema>> getCinemaByCondition(Cinema cinema,
                                                             @RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber,
                                                             @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageResult<Cinema> list = cinemaService.getCinemaByCondition(cinema,pageNumber,pageSize);
        if(list == null || CollectionUtils.isEmpty(list.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据城市id，分显示电影院列表
     * @param pageNumber
     * @param pageSize
     * @param cityid
     * @return
     */
    @GetMapping("pagelist")
    public ResponseEntity<PageResult<Cinema>> getAllCinemasByPage(@RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber,
                                                                 @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                                                 @RequestParam("cityid")Long cityid){
        PageResult<Cinema> list = cinemaService.getAllCinemasByPage(pageNumber, pageSize, cityid);
        if(list == null || CollectionUtils.isEmpty(list.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }
}
