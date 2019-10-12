package com.huangniuniu.movie.client;

import com.huangniuniu.comment.api.CommentApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("comment-service")
public interface CommentClient extends CommentApi {
}
