package com.huangniuniu.cinema.pojo;

import com.huangniuniu.movie.pojo.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CinemaDetail extends Cinema{

    Map<Movie,Map<Object, List<Skedule>>> cinemaDetail;
}
