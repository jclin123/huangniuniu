package com.huangniuniu.cinema.controller;

import com.huangniuniu.cinema.pojo.Cinema;
import com.huangniuniu.cinema.pojo.Cinema_movie;
import com.huangniuniu.cinema.service.CinemaMovieService;
import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.pojo.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cinemaMovie")
public class CinemaMovieController {

    @Autowired
    private CinemaMovieService cinemaMovieService;

    /**
     * 两者的id的关系，先把两个id封装给CinemaMovie，即添加电影
     * @param cinema_movie
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> chooseCinemaMovie(Cinema_movie cinema_movie){
        cinemaMovieService.chooseCinemaMovie(cinema_movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 用户前台
     * 点击电影院列表中某个电影院的查看所有电影
     * 根据电影院id查询该电影院的(正在/即将）上映电影
     * @param cid
     * @return
     */
    @GetMapping("moviesByCid/{cid}")
    public ResponseEntity<Map<String,Object>> getMoviesByCinemaId(@PathVariable("cid")Long cid){
        Map<String,Object> movies = cinemaMovieService.getMoviesByCinemaId(cid);
        if(CollectionUtils.isEmpty(movies)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movies);
    }

    /**
     * 管理后台
     * 根据电影院id分页查询该电影院的电影
     * @param cid
     * @param pageNumber
     * @param pageSize
     * @return
     */
   @GetMapping("moviesPage/{cid}")
   public ResponseEntity<PageResult<Movie>> getMoviesPageByCinemaId(@PathVariable("cid")Long cid,
                                                                    @RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber,
                                                                    @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
       PageResult<Movie> list = this.cinemaMovieService.getMoviesPageByCinemaId(cid,pageNumber,pageSize);

       if(list == null || CollectionUtils.isEmpty(list.getItems())){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(list);
   }

    /**
     * 根据cinema_movie的id删除该电影院的该电影
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCinemaMovie(@PathVariable("id")Long id){
        cinemaMovieService.deleteCinemaMovie(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 点击添加排场时，需要的返回数据
     * 根据cinema_movie的id查询cinema_movie(包括电影和电影院名称)
     * @param id
     * @return
     */
    @GetMapping("cnameAndname/{id}")
    public ResponseEntity<Cinema_movie> selectCinemaMovieBycmid(@PathVariable("id")Long id){
        Cinema_movie cinema_movie = cinemaMovieService.selectCinemaMovieBycmid(id);
        if(cinema_movie == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cinema_movie);
    }

    /**
     * 根据城市id和电影id查询该城市下有这个电影的电影院
     * @param cityId
     * @param mid
     * @return
     */
    @GetMapping("citymovie/{cityId}/{mid}")
    public ResponseEntity<List<Cinema>> selectCinemaByCityIdAndMovieId(@PathVariable("cityId")Long cityId,
                                                                       @PathVariable("mid")Long mid){
        List<Cinema> cinemaList = cinemaMovieService.selectCinemaByCityIdAndMovieId(cityId, mid);
        if(CollectionUtils.isEmpty(cinemaList)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cinemaList);
    }


}
