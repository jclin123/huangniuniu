package com.huangniuniu.cinema.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "cinema_movie")
public class Cinema_movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cinemaid;
    private Long movieid;
    @Transient
    private String cinemaName;
    @Transient
    private String movieName;
}
