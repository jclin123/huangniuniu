package com.huangniuniu.cinema.api;

import com.huangniuniu.cinema.pojo.Skedule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface SkeduleApi {

    /**
     * 根据排场id查询该排场
     * @param sid
     * @return
     */
    @GetMapping("skedule/{sid}")
    Skedule getSkeduleBySkeduleid(@PathVariable("sid")Long sid);

    /**
     * 条件查询排场信息
     * @param skedule
     * @return
     */
//    @GetMapping(value = "skedule/conditionlsit",consumes="application/json")
//   List<Skedule> getSkeduleByCondition(@RequestBody Skedule skedule);

    @RequestMapping(value="skedule/conditionlsit",method = RequestMethod.POST)
    List<Skedule> getSkeduleByCondition(@RequestBody Skedule skedule);



}
