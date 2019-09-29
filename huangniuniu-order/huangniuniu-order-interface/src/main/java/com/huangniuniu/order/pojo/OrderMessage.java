package com.huangniuniu.order.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderMessage extends UserOrder{
    private String roomName;
    private Date showDate;
    private String cinemaName;
    private String movieName;

}
