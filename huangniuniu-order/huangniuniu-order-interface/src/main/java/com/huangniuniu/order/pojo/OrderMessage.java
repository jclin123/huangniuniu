package com.huangniuniu.order.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class OrderMessage extends UserOrder{
    private String roomName;
    private Date showDate;
    private String cinemaName;
    private String movieName;
    private Float totalPrice;

}
