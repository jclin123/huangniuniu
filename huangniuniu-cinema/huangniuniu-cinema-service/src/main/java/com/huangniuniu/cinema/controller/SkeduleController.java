package com.huangniuniu.cinema.controller;

import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.cinema.service.SkeduleService;
import com.huangniuniu.common.pojo.PageResult;
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
     * 分页查询排场信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("listPage")
    public ResponseEntity<PageResult<Skedule>> getAllSkeduleByPage(@RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber,
                                                                   @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
       PageResult<Skedule> list = this.skeduleService.getAllSkeduleByPage(pageNumber,pageSize);
        if(list == null || CollectionUtils.isEmpty(list.getItems())){
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
     * 条件查询排场信息并且分页
     * @param skedule
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("conditionlsitpage")
    public ResponseEntity<PageResult<Skedule>> getSkeduleByConditionpage(Skedule skedule,
                                                                     @RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber,
                                                                     @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageResult<Skedule> list = skeduleService.getSkeduleByConditionpage(skedule,pageNumber,pageSize);
        if(list == null || CollectionUtils.isEmpty(list.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 不分页条件查询所有排场
     * @param skedule
     * @return
     */
    @PostMapping("conditionlist")
    public ResponseEntity<List<Skedule>> getSkeduleByCondition(Skedule skedule){
        List<Skedule> list = skeduleService.getSkeduleByCondition(skedule);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }


    /**
     * 当页面加载后发出，根据电影院id查询出该电影院信息以及该电影院的电影信息
     * @param cinemaid
     * @return
     */
    @GetMapping("cinemaMovieList/{cinemaid}")
    public ResponseEntity<Map<String,Object>> selectCinemaAndMovieListByCinemaId(@PathVariable("cinemaid")Long cinemaid){
        Map<String, Object> map = skeduleService.selectCinemaAndMovieListByCinemaId(cinemaid);
        if(CollectionUtils.isEmpty(map)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(map);
    }

    /**
     * 根据电影院id和电影id查询排场（排场时间>当前时间的排场时间列表）
     * @param cinemaid
     * @param movieid
     * @return
     */
    @GetMapping("skeduleTimeList/{cinemaid}/{movieid}")
    public ResponseEntity<List<String>> selectSkeduleTimeListByCinemaIdAndMovieId(@PathVariable("cinemaid")Long cinemaid,
                                                                                  @PathVariable("movieid")Long movieid){
        List<String> list = skeduleService.selectSkeduleTimeListByCinemaIdAndMovieId(cinemaid, movieid);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据选择的电影院、电影、以及该电影选择的时间(MM-dd)查询排场信息
     * @param cinemaid
     * @param movieid
     * @param skeduletime
     * @return
     */
    @GetMapping("skeduleList/{cinemaid}/{movieid}")
    public ResponseEntity<List<Skedule>> selectSkeduleListByCinemaIdAndMovieIdAndSkeduleTime(@PathVariable("cinemaid")Long cinemaid,
                                                                                             @PathVariable("movieid")Long movieid,
                                                                                             @RequestParam("skeduletime")String skeduletime){
        List<Skedule> skeduleList = skeduleService.selectSkeduleListByCinemaIdAndMovieIdAndSkeduleTime(cinemaid, movieid, skeduletime);
        if(CollectionUtils.isEmpty(skeduleList)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skeduleList);
    }

    /**
     * 根据排场id和购买该排场的电影票数量，修改排场的电影票数量
     * @param skeduleid
     * @param number
     * @return
     */
    @GetMapping("buyticket/{skeduleid}")
    public ResponseEntity<Void> buyTicketBySkeduleId(@PathVariable("skeduleid")Long skeduleid,
                                                     @RequestParam("number")Integer number){
        skeduleService.buyTicketBySkeduleId(skeduleid,number);
        return ResponseEntity.noContent().build();
    }


}
