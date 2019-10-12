package com.huangniuniu.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.cinema.pojo.Skedule;
import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.common.utils.IdWorker;
import com.huangniuniu.movie.pojo.Movie;
import com.huangniuniu.order.client.SkeduleClient;
import com.huangniuniu.order.mapper.OrderMapper;
import com.huangniuniu.order.pojo.Notice;
import com.huangniuniu.order.pojo.OrderMessage;
import com.huangniuniu.order.pojo.UserOrder;
import com.huangniuniu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SkeduleClient skeduleClient;
    @Autowired
    private IdWorker idWorker;

    /**
     *获取全部订单信息
     * @return
     */
    public PageResult<OrderMessage> getAllOrderMessage(Integer page, Integer rows){
        List<UserOrder> userOrders = orderMapper.selectAll();
        List<OrderMessage> orderMessageList=new ArrayList<OrderMessage>();
        if(userOrders!=null){
            userOrders.forEach(userOrder->{
                OrderMessage orderMessage = new OrderMessage();
//                Long skeduleid = userOrder.getSkeduleid();
                Skedule skeduleBySkeduleid = skeduleClient.getSkeduleBySkeduleid(userOrder.getSkeduleid());
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
        PageHelper.startPage(page, rows);
        PageInfo<OrderMessage> pageInfo=new PageInfo<>(orderMessageList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());

    }

    /**
     *根据用户id查询该用户订单详细信息
     * @param userid
     * @return
     */
    public PageResult<OrderMessage> selectOrderMessageByUserid(Integer page, Integer rows,Long userid){
        List<UserOrder> userOrders=this.selectUserOrderByUserid(userid);
        List<OrderMessage> orderMessageList=new ArrayList<OrderMessage>();
        if(userOrders!=null){
            userOrders.forEach(userOrder -> {
//                Long skeduleid = userOrder.getSkeduleid();
                Skedule skeduleBySkeduleid = skeduleClient.getSkeduleBySkeduleid(userOrder.getSkeduleid());
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
        PageHelper.startPage(page, rows);
        PageInfo<OrderMessage> pageInfo=new PageInfo<>(orderMessageList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());

    }

    /**
     *根据用户id查询该用户订单
     * @param userid
     * @return
     */
    public List<UserOrder> selectUserOrderByUserid(Long userid){
        Example example = new Example(UserOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid",userid);
        List<UserOrder> userOrders = orderMapper.selectByExample(example);
        return userOrders;
    }

    /**
     *购买行为产生订单
     * @param userOrder
     */
    @Transactional
    public void insertUserOrderMessage(UserOrder userOrder){
        long orderId = idWorker.nextId();
        userOrder.setId(orderId);
        userOrder.setOrderTime(new Date());
        orderMapper.insertSelective(userOrder);
    }

    /**
     *给用户发送购票成功通知
     * @param orderid
     * @return
     */
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


//    public List<Skedule> test(OrderMessage orderMessage){
//        String cinemaName = orderMessage.getCinemaName();
//        String movieName = orderMessage.getMovieName();
//        System.out.println(cinemaName);
//        System.out.println(movieName);
//        System.out.println(orderMessage.toString());
//        Skedule skedule = new Skedule();
//        skedule.setCinemaName(cinemaName);
//        skedule.setMovieName(movieName);
//        System.out.println(skedule.toString());
//        List<Skedule> skeduleByCondition = skeduleClient.getSkeduleByCondition(skedule);
//        skeduleByCondition.forEach(skedule1 -> {
//            System.out.println(skedule1.toString());
//        });
//        return skeduleByCondition;
//
//    }


    /**
     *根据条件查询订单信息（电影、影院名称模糊匹配，购票数量相等）
     * @param orderMessage
     * @return
     */
    public PageResult<OrderMessage> getOrderMessageByCondition(Integer page, Integer rows,OrderMessage orderMessage){
//        String cinemaName = orderMessage.getCinemaName();
//        String movieName = orderMessage.getMovieName();
        Skedule skedule = new Skedule();
        skedule.setCinemaName(orderMessage.getCinemaName());
        skedule.setMovieName(orderMessage.getMovieName());
        List<Skedule> skeduleByCondition = skeduleClient.getSkeduleByCondition(skedule);
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
        PageHelper.startPage(page, rows);
        PageInfo<OrderMessage> pageInfo=new PageInfo<>(orderMessageList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());

    }
}