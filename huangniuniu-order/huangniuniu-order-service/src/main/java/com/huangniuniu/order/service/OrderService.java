package com.huangniuniu.order.service;

import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.order.pojo.Notice;
import com.huangniuniu.order.pojo.OrderMessage;
import com.huangniuniu.order.pojo.UserOrder;

import java.util.List;

public interface OrderService {
    /**
     *获取全部订单信息
     * @return
     */
    PageResult<OrderMessage> getAllOrderMessage(Integer page, Integer rows);
    /**
     *根据用户id查询该用户订单详细信息
     * @param userid
     * @return
     */
    PageResult<OrderMessage> selectOrderMessageByUserid(Integer page, Integer rows,Long userid);
    /**
     *根据用户id查询该用户订单
     * @param userid
     * @return
     */
    List<UserOrder> selectUserOrderByUserid(Long userid);
    /**
     *购买行为产生订单
     * @param userOrder
     */
    void insertUserOrderMessage(UserOrder userOrder);
    /**
     *给用户发送购票成功通知
     * @param orderid
     * @return
     */
    Notice sendNotice(Long orderid);
    /**
     *根据条件查询订单信息（电影、影院名称模糊匹配，购票数量相等）
     * @param orderMessage
     * @return
     */
    PageResult<OrderMessage> getOrderMessageByCondition(Integer page, Integer rows,OrderMessage orderMessage);
    /**
     * 删除订单
     * @param id
     */
    void deleteOrder(Long id);

}
