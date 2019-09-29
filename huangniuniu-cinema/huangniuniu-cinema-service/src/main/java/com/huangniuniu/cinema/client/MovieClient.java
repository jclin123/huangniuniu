package com.huangniuniu.cinema.client;

import com.huangniuniu.movie.api.MovieApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("movie-service")
public interface MovieClient extends MovieApi {
}
