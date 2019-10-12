package com.huangniuniu.movie.pojo;

import com.huangniuniu.comment.pojo.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class MovieDetail extends Movie{
    List<Actor> actorList;
    List<Comment> commentList;
}
