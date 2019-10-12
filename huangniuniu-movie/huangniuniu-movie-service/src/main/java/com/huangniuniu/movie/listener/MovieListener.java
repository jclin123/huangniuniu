package com.huangniuniu.movie.listener;

import com.huangniuniu.movie.pojo.Movie;
import com.huangniuniu.movie.service.MovieService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
public class MovieListener {

    @Autowired
    private MovieService movieService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "huangniuniu.movie.queue", durable = "true"),
            exchange = @Exchange(value = "huangniuniu.movie.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"movie.score"}))
    public void updateMovieScore(Map<String,Object> msg) {
        if(CollectionUtils.isEmpty(msg)){
            return ;
        }
       Float score = (Float) msg.get("score");
       Long id = (Long) msg.get("movieid");
        Movie movie = new Movie();
        movie.setId(id);
        movie.setScore(score);
        this.movieService.updateMovie(movie);
    }
}
