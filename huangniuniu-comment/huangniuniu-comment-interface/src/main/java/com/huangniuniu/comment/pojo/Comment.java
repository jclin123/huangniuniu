package com.huangniuniu.comment.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;
    private String commentWords;
    private Date commentTime;
    private Long userid;
    private Long movieid;
    private String movieName;
    private String nickname;
}
