package com.huangniuniu.cinema.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Table(name = "cinema")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "电影院名称不能为空")
    private String cinemaName;

    @NotBlank(message = "电影院地址不能为空")
    private String cinemaAddress;

    private Long cityid;
}
