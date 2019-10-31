package com.huangniuniu.cinema.listener;

import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.cinema.service.SkeduleService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
public class SkeduleListener {

    @Autowired
    private SkeduleService skeduleService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "huangniuniu.skedule.queue", durable = "true"),
            exchange = @Exchange(value = "huangniuniu.skedule.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"skedule.number"}))
    public void updateMovieScore(Map<String,Object> msg) {
        if(CollectionUtils.isEmpty(msg)){
            return ;
        }
        Long id = (Long) msg.get("skeduleid");
        Integer number = (Integer) msg.get("number");
        //查询该排场信息
        Skedule skedule = this.skeduleService.getSkeduleBySkeduleid(id);
        skedule.setTicketsLeft(skedule.getTicketsLeft()-number);
        skedule.setTicketsSold(skedule.getTicketsSold()+number);

        //修改该排场信息，即票数
        this.skeduleService.updateSkedule(skedule);

    }
}
