package com.huangniuniu.movie.pojo;


import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "电影名不能为空")
    private String movieName;
    private String movieType;
    private String location;
    private String discription;
    private String moviePicture;
    private Integer runningTime;
    private Date releaseTime;
    private String stagePhotos;
    private String prevideo;
    private Float score;
    private Date soldOutTime;



}
