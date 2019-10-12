package com.huangniuniu.cinema.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.huangniuniu.cinema.pojo.Skedule;
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
    @RequestMapping(value="skedule/conditionlsit",method = RequestMethod.POST)
    List<Skedule> getSkeduleByCondition(@RequestBody Skedule skedule);


    /**
     * 根据排场id和购买该排场的电影票数量，修改排场的电影票数量
     * @param skeduleid
     * @param number
     * @return
     */
    @GetMapping("buyticket/{skeduleid}")
    public Void buyTicketBySkeduleId(@PathVariable("skeduleid")Long skeduleid, @RequestParam("number")Integer number);
}
