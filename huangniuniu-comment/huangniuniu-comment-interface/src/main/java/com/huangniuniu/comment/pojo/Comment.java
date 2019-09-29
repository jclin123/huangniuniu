package com.huangniuniu.comment.pojo;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;
    private String commentWords;
    private Data commentTime;
    private Long userid;
    private Long movieid;
    @Transient
    private String movieName;
    @Transient
    private String nickname;
}
