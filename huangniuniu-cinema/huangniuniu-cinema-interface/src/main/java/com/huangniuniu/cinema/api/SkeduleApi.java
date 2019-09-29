package com.huangniuniu.cinema.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface SkeduleApi {

    /**
     * 根据排场id和购买该排场的电影票数量，修改排场的电影票数量
     * @param skeduleid
     * @param number
     * @return
     */
    @GetMapping("buyticket/{skeduleid}")
    public Void buyTicketBySkeduleId(@PathVariable("skeduleid")Long skeduleid, @RequestParam("number")Integer number);
}
