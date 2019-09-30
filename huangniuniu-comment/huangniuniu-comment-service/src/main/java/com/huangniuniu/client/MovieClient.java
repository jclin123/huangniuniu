package com.huangniuniu.client;

import com.huangniuniu.movie.api.MovieApi;
import com.huangniuniu.movie.pojo.Movie;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("movie-service")
public interface MovieClient extends MovieApi {
}
