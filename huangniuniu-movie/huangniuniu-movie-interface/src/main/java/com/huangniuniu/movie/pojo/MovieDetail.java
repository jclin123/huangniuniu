package com.huangniuniu.movie.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDetail extends Movie{
    List<Actor> actorList;
}
