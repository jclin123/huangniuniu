package com.huangniuniu.cinema.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name = "skedule")
public class Skedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private Float price;
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date showDate;
    private Integer ticketsLeft;
    private Integer ticketsSold;
    private Long movieid;
    private Long cinemaid;
    private String movieName;
    private String cinemaName;
}
