package com.huangniuniu.order.service;

import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.common.utils.IdWorker;
import com.huangniuniu.order.client.SkeduleClient;
import com.huangniuniu.order.mapper.OrderMapper;
import com.huangniuniu.order.pojo.Notice;
import com.huangniuniu.order.pojo.OrderMessage;
import com.huangniuniu.order.pojo.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SkeduleClient skeduleClient;
    @Autowired
    private IdWorker idWorker;


    public List<OrderMessage> getAllOrderMessage(){
        List<UserOrder> userOrders = orderMapper.selectAll();
        List<OrderMessage> orderMessageList=new ArrayList<OrderMessage>();
        if(userOrders!=null){
            userOrders.forEach(userOrder->{
                System.out.println(userOrder.toString());
                OrderMessage orderMessage = new OrderMessage();
                Long skeduleid = userOrder.getSkeduleid();
                System.out.println(skeduleid);
                Skedule skeduleBySkeduleid = skeduleClient.getSkeduleBySkeduleid(skeduleid);
                if(skeduleBySkeduleid!=null){
                    orderMessage.setId(userOrder.getId());
                    orderMessage.setNickname(userOrder.getNickname());
                    orderMessage.setOrderNum(userOrder.getOrderNum());
                    orderMessage.setOrderTime(userOrder.getOrderTime());
                    orderMessage.setSkeduleid(userOrder.getSkeduleid());
                    orderMessage.setUserid(userOrder.getUserid());
                    orderMessage.setCinemaName(skeduleBySkeduleid.getCinemaName());
                    orderMessage.setMovieName(skeduleBySkeduleid.getMovieName());
                    orderMessage.setRoomName(skeduleBySkeduleid.getRoomName());
                    orderMessage.setShowDate(skeduleBySkeduleid.getShowDate());
                    orderMessage.setTotalPrice(userOrder.getOrderNum()*skeduleBySkeduleid.getPrice());
                    orderMessageList.add(orderMessage);
                }

            });
        }
        return orderMessageList;

    }

    public List<OrderMessage> selectOrderMessageByUserid(Long userid){
        List<UserOrder> userOrders=this.selectUserOrderByUserid(userid);
        List<OrderMessage> orderMessageList=new ArrayList<OrderMessage>();
        if(userOrders!=null){
            userOrders.forEach(userOrder -> {
                Long skeduleid = userOrder.getSkeduleid();
                Skedule skeduleBySkeduleid = skeduleClient.getSkeduleBySkeduleid(skeduleid);
                if(skeduleBySkeduleid!=null){
                    OrderMessage orderMessage = new OrderMessage();

                    orderMessage.setId(userOrder.getId());
                    orderMessage.setNickname(userOrder.getNickname());
                    orderMessage.setOrderNum(userOrder.getOrderNum());
                    orderMessage.setOrderTime(userOrder.getOrderTime());
                    orderMessage.setSkeduleid(userOrder.getSkeduleid());
                    orderMessage.setUserid(userOrder.getUserid());
                    orderMessage.setCinemaName(skeduleBySkeduleid.getCinemaName());
                    orderMessage.setMovieName(skeduleBySkeduleid.getMovieName());
                    orderMessage.setRoomName(skeduleBySkeduleid.getRoomName());
                    orderMessage.setShowDate(skeduleBySkeduleid.getShowDate());
                    orderMessage.setTotalPrice(userOrder.getOrderNum()*skeduleBySkeduleid.getPrice());
                    orderMessageList.add(orderMessage);
                }
            });
        }
        return orderMessageList;

    }

    public List<UserOrder> selectUserOrderByUserid(Long userid){
        Example example = new Example(UserOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid",userid);
        List<UserOrder> userOrders = orderMapper.selectByExample(example);
        return userOrders;

    }

    public void insertUserOrderMessage(UserOrder userOrder){
        long orderId = idWorker.nextId();
        userOrder.setId(orderId);
        userOrder.setOrderTime(new Date());
        orderMapper.insertSelective(userOrder);

    }

    public Notice sendNotice(Long orderid){
        Notice notice = new Notice();
        UserOrder userOrder = orderMapper.selectByPrimaryKey(orderid);
        Skedule skeduleBySkeduleid = skeduleClient.getSkeduleBySkeduleid(userOrder.getSkeduleid());
        notice.setPrice(skeduleBySkeduleid.getPrice());
        notice.setCinemaName(skeduleBySkeduleid.getCinemaName());
        notice.setId(userOrder.getId());
        notice.setMovieName(skeduleBySkeduleid.getMovieName());
        notice.setNickname(userOrder.getNickname());
        notice.setOrderNum(userOrder.getOrderNum());
        notice.setOrderTime(userOrder.getOrderTime());
        notice.setRoomName(skeduleBySkeduleid.getRoomName());
        notice.setShowDate(skeduleBySkeduleid.getShowDate());
        notice.setSkeduleid(userOrder.getSkeduleid());
        notice.setUserid(userOrder.getUserid());
        notice.setTotalPrice(userOrder.getOrderNum()*skeduleBySkeduleid.getPrice());
        return notice;
    }

    public List<Skedule> test(OrderMessage orderMessage){
        String cinemaName = orderMessage.getCinemaName();
        String movieName = orderMessage.getMovieName();
        System.out.println(cinemaName);
        System.out.println(movieName);
        System.out.println(orderMessage.toString());
        Skedule skedule = new Skedule();
        skedule.setCinemaName(cinemaName);
        skedule.setMovieName(movieName);
        System.out.println(skedule.toString());
        List<Skedule> skeduleByCondition = skeduleClient.getSkeduleByCondition(skedule);
        skeduleByCondition.forEach(skedule1 -> {
            System.out.println(skedule1.toString());
        });
        return skeduleByCondition;

    }



    public List<OrderMessage> getOrderMessageByCondition(OrderMessage orderMessage){
        String cinemaName = orderMessage.getCinemaName();
        String movieName = orderMessage.getMovieName();
        System.out.println(cinemaName);
        System.out.println(movieName);
       System.out.println(orderMessage.toString());
        Skedule skedule = new Skedule();
        skedule.setCinemaName(cinemaName);
        skedule.setMovieName(movieName);
        System.out.println(skedule.toString());
        List<Skedule> skeduleByCondition = skeduleClient.getSkeduleByCondition(skedule);
        skeduleByCondition.forEach(skedule1 -> {
            System.out.println(skedule1.toString());
        });

        List<OrderMessage> orderMessageList=new ArrayList<OrderMessage>();
        if(skeduleByCondition!=null){
            skeduleByCondition.forEach(skedule1 -> {
                Long id = skedule1.getId();
                Example example = new Example(UserOrder.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("skeduleid",id);
                if(orderMessage.getOrderNum()!=null){
                    criteria.andEqualTo("orderNum",orderMessage.getOrderNum());

                }
                List<UserOrder> userOrders = orderMapper.selectByExample(example);
                userOrders.forEach(userOrder -> {
                    OrderMessage orderMessage1 = new OrderMessage();
                    orderMessage1.setId(userOrder.getId());
                    orderMessage1.setNickname(userOrder.getNickname());
                    orderMessage1.setOrderNum(userOrder.getOrderNum());
                    orderMessage1.setOrderTime(userOrder.getOrderTime());
                    orderMessage1.setUserid(userOrder.getUserid());
                    orderMessage1.setSkeduleid(userOrder.getSkeduleid());
                    orderMessage1.setCinemaName(skedule1.getCinemaName());
                    orderMessage1.setMovieName(skedule1.getMovieName());
                    orderMessage1.setRoomName(skedule1.getRoomName());
                    orderMessage1.setShowDate(skedule1.getShowDate());
                    orderMessage1.setTotalPrice(userOrder.getOrderNum()*skedule1.getPrice());
                    orderMessageList.add(orderMessage1);
                });
            });
        }
        return orderMessageList;

    }



}
