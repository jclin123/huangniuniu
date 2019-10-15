package com.huangniuniu.cinema.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "skedule")
public class Skedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private Float price;
    private Date showDate;
    private Integer ticketsLeft;
    private Integer ticketsSold;
    private Long movieid;
    private Long cinemaid;
    private String movieName;
    private String cinemaName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getShowDate() {
        return showDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Integer getTicketsLeft() {
        return ticketsLeft;
    }

    public void setTicketsLeft(Integer ticketsLeft) {
        this.ticketsLeft = ticketsLeft;
    }

    public Integer getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(Integer ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public Long getMovieid() {
        return movieid;
    }

    public void setMovieid(Long movieid) {
        this.movieid = movieid;
    }

    public Long getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Long cinemaid) {
        this.cinemaid = cinemaid;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
