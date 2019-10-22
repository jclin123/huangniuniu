package com.huangniuniu.city.controller;

import com.huangniuniu.city.pojo.City;
import com.huangniuniu.city.service.CityService;
import com.huangniuniu.common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * 获取所有城市
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<Map<Character, List<City>>> getAllCity(){

        Map<Character, List<City>> cities = cityService.getAllCity();
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
    public ResponseEntity<PageResult<City>> getAllCity(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        //List<City> cities = cityService.getAllCity();
        PageResult<City> cityPageResult = cityService.getAllCityToPage(pageNumber,pageSize);
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
    @GetMapping("conditionlisttopage")
    public ResponseEntity<PageResult<City>> getCityBycon(City city,@RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber,@RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        PageResult<City> cities = cityService.getCityByPreToPage(city,pageNumber,pageSize);
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

    /**
     * 根据城市id查询城市
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<City> getCityById(@PathVariable("id")Long id){
        City city = cityService.getCityById(id);
        if(city == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(city);
    }
}
