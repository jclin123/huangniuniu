package com.huangniuniu.service.impl;

import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.mapper.CommentMapper;
import com.huangniuniu.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    public CommentMapper commentMapper;

    @Override
    public List<Comment> getAllComment() {
        List<Comment> comments = commentMapper.selectAll();
        return comments;
    }

    @Override
    public List<Comment> getCommentByMovie(Long id) {
        return null;
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
        List<Comment> comments = commentMapper.selectByExample(example);
        return comments;
    }

    @Override
    public void insertComment(Comment comment) {
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
