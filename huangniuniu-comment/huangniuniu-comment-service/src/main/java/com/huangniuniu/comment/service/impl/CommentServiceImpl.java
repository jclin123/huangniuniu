package com.huangniuniu.comment.service.impl;

import com.huangniuniu.auth.pojo.UserInfo;
import com.huangniuniu.auth.utils.JwtUtils;
import com.huangniuniu.comment.client.MovieClient;
import com.huangniuniu.comment.config.JwtProperties;
import com.huangniuniu.comment.interceptor.LoginInterceptor;
import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.comment.mapper.CommentMapper;
import com.huangniuniu.comment.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    public CommentMapper commentMapper;

    @Autowired
    public MovieClient movieClient;

    @Autowired
    public JwtProperties jwtProperties;

    @Override
    public List<Comment> getAllComment() {
        List<Comment> comments = commentMapper.selectAll();
        return comments;
    }

    @Override
    public List<Comment> getCommentByCondition(Comment comment) {
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        if(comment.getScore()!=null){
            criteria.andEqualTo("score",comment.getScore());
        }
        if(!StringUtils.isBlank(comment.getCommentWords())){
            criteria.andLike("commentWords","%"+comment.getCommentWords()+"%");
        }
        if(comment.getCommentTime()!=null){
            criteria.andEqualTo("commentTime",comment.getCommentTime());
        }
        if(comment.getMovieid()!=null){
            criteria.andEqualTo("movieid",comment.getMovieid());
        }
        if(comment.getUserid()!=null){
            criteria.andEqualTo("userid",comment.getUserid());
        }
        if(!StringUtils.isBlank(comment.getMovieName())){
            criteria.andLike("movieName","%"+comment.getMovieName()+"%");
        }
        if(!StringUtils.isBlank(comment.getNickname())){
            criteria.andLike("nickname","%"+comment.getNickname()+"%");
        }
        List<Comment> comments = commentMapper.selectByExample(example);
        return comments;
    }

    @Override
    public void insertComment(Comment comment) {
        comment.setMovieName(movieClient.getMovieByMovieid(comment.getMovieid()).getMovieName());
        UserInfo userInfo = LoginInterceptor.getuserInfo();
        comment.setNickname(userInfo.getUsername());
        commentMapper.insertSelective(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateComment(Comment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }
}
