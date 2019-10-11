package com.huangniuniu.order.controller;

import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.order.pojo.Notice;
import com.huangniuniu.order.pojo.OrderMessage;
import com.huangniuniu.order.pojo.UserOrder;
import com.huangniuniu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("list")
    public ResponseEntity<List<OrderMessage>> getAllOrderMessage(){
        List<OrderMessage> allOrderMessage = orderService.getAllOrderMessage();
        if(CollectionUtils.isEmpty(allOrderMessage)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allOrderMessage);

    }

    @GetMapping("selectOrderMessageByUserid/{userid}")
    public ResponseEntity<List<OrderMessage>> selectOrderMessageByUserid(@PathVariable("userid") Long userid){
        List<OrderMessage> allOrderMessage = orderService.selectOrderMessageByUserid(userid);
        if(CollectionUtils.isEmpty(allOrderMessage)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allOrderMessage);

    }

    @PostMapping("insert")
    public ResponseEntity<Void> insertUserOrderMessage(UserOrder userOrder){
        orderService.insertUserOrderMessage(userOrder);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("sendNotice/{orderid}")
    public ResponseEntity<Notice> sendNotice(@PathVariable("orderid") Long orderid){
        Notice notice = orderService.sendNotice(orderid);
        if(notice==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notice);

    }

    @GetMapping("getOrderMessageByCondition")
    public ResponseEntity<List<OrderMessage>> getOrderMessageByCondition(OrderMessage orderMessage){
        System.out.println(orderMessage.toString());
        List<OrderMessage> orderMessageByCondition = orderService.getOrderMessageByCondition(orderMessage);
        if(CollectionUtils.isEmpty(orderMessageByCondition)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderMessageByCondition);

    }

    @GetMapping("test")
    public ResponseEntity<List<Skedule>> test(@RequestBody OrderMessage orderMessage){
        List<Skedule> test = orderService.test(orderMessage);
        if(CollectionUtils.isEmpty(test)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(test);
    }

}
