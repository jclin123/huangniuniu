package com.huangniuniu.city.controller;

import com.huangniuniu.city.pojo.City;
import com.huangniuniu.city.service.CityService;
import com.huangniuniu.common.pojo.PageResult;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * 获取所有城市
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<City>> getAllCity(){

        List<City> cities = cityService.getAllCity();
        if(CollectionUtils.isEmpty(cities)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }

    /**
     * 根据条件获取所有城市并分页
     * @return
     */
    @GetMapping("listtopage")
    public ResponseEntity<PageResult<City>> getAllCity(@RequestParam(value = "pn",defaultValue = "1") Integer pn,@RequestParam(value = "pagesize",defaultValue = "10") Integer pagesize){
        //List<City> cities = cityService.getAllCity();
        PageResult<City> cityPageResult = cityService.getAllCityToPage(pn,pagesize);
        if(cityPageResult==null || CollectionUtils.isEmpty(cityPageResult.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cityPageResult);
    }

    /**
     * 根据条件查询所有城市，返回符合条件的城市
     * @param city
     * @return
     */
    @GetMapping("conditionlist")
    public ResponseEntity<List<City>> getCityBycon(City city){
        List<City> cities = cityService.getCityByPre(city);
        if(CollectionUtils.isEmpty(cities)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }

    /**
     * 根据条件查询所有城市，返回符合条件的城市
     * @param city
     * @return
     */
    @GetMapping("conditionlist")
    public ResponseEntity<PageResult<City>> getCityBycon(City city,@RequestParam(value = "pn",defaultValue = "1")Integer pn,@RequestParam(value = "pagesize",defaultValue = "10")Integer pagesize){
        PageResult<City> cities = cityService.getCityByPreToPage(city,pn,pagesize);
        if(cities == null || CollectionUtils.isEmpty(cities.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }

    /**
     * 添加新城市
     * @param city
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> insertCity(City city){
        cityService.insertCity(city);
        return ResponseEntity.noContent().build();
    }

    /**
     * 更新城市信息
     * @param city
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCity(City city){
        cityService.updateCity(city);
        return ResponseEntity.noContent().build();
    }

    /**
     * 根据ID删除城市
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id){
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
