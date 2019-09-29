package com.huangniuniu.city.controller;

import com.huangniuniu.city.pojo.City;
import com.huangniuniu.city.service.CityService;
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
