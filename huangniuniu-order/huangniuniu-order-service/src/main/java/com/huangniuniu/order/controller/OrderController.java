package com.huangniuniu.order.controller;

import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.order.pojo.Notice;
import com.huangniuniu.order.pojo.OrderMessage;
import com.huangniuniu.order.pojo.UserOrder;
import com.huangniuniu.order.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    /**
     * 分页获取全部订单信息
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult<OrderMessage>> getAllOrderMessage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                       @RequestParam(value = "rows",defaultValue = "10") Integer rows){
        PageResult<OrderMessage> allOrderMessage = orderService.getAllOrderMessage(page,rows);
        if(allOrderMessage==null||CollectionUtils.isEmpty(allOrderMessage.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allOrderMessage);

    }

    /**
     * 分页根据用户id查询该用户订单详细信息
     * @param userid
     * @return
     */
    @GetMapping("selectOrderMessageByUserid/{userid}")
    public ResponseEntity<PageResult<OrderMessage>> selectOrderMessageByUserid(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                               @RequestParam(value = "rows",defaultValue = "10") Integer rows,
                                                                               @PathVariable("userid") Long userid){
        PageResult<OrderMessage> allOrderMessage = orderService.selectOrderMessageByUserid(page,rows,userid);
        if(allOrderMessage==null||CollectionUtils.isEmpty(allOrderMessage.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allOrderMessage);

    }

    /**
     * 购买行为产生订单
     * @param userOrder
     * @return
     */
    @PostMapping("insert")
    public ResponseEntity<Void> insertUserOrderMessage(UserOrder userOrder){
        orderService.insertUserOrderMessage(userOrder);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 给用户发送购票成功通知
     * @param orderid
     * @return
     */
    @GetMapping("sendNotice/{orderid}")
    public ResponseEntity<Notice> sendNotice(@PathVariable("orderid") Long orderid){
        Notice notice = orderService.sendNotice(orderid);
        if(notice==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notice);

    }

    /**
     * 分页根据条件查询订单信息（电影、影院名称模糊匹配，购票数量相等）
     * @param orderMessage
     * @return
     */
    @GetMapping("getOrderMessageByCondition")
    public ResponseEntity<PageResult<OrderMessage>> getOrderMessageByCondition(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                               @RequestParam(value = "rows",defaultValue = "10") Integer rows,
                                                                               OrderMessage orderMessage){
        PageResult<OrderMessage> orderMessageByCondition = orderService.getOrderMessageByCondition(page,rows,orderMessage);
        if(orderMessageByCondition==null||CollectionUtils.isEmpty(orderMessageByCondition.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderMessageByCondition);

    }


}
