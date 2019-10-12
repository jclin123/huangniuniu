package com.huangniuniu.comment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.auth.pojo.UserInfo;
import com.huangniuniu.auth.utils.JwtUtils;
import com.huangniuniu.comment.client.MovieClient;
import com.huangniuniu.comment.interceptor.LoginInterceptor;
import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.comment.mapper.CommentMapper;
import com.huangniuniu.comment.service.CommentService;
import com.huangniuniu.common.pojo.PageResult;
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


    @Override
    public List<Comment> getAllComment() {
        List<Comment> comments = commentMapper.selectAll();
        return comments;
    }

    @Override
    public PageResult<Comment> getAllCommentToPage(Integer pn,Integer pagesize) {
        PageHelper.startPage(pn,pagesize);
        List<Comment> comments = commentMapper.selectAll();
        PageInfo pageInfo = new PageInfo(comments,pagesize);
        PageResult<Comment> pageResult = new PageResult<Comment>();
        pageResult.setItems(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
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
    public PageResult<Comment> getCommentByConditionToPage(Comment comment, Integer pn, Integer pagesize) {
        PageHelper.startPage(pn,pagesize);
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        if(comment.getScore()!=null){
            criteria.andEqualTo("score",comment.getScore());
        }
        if(!StringUtils.isBlank(comment.getCommentWords())){
            criteria.andLike("commentWords","%"+comment.getCommentWords()+"%");
        }
        if(!StringUtils.isBlank(comment.getMovieName())){
            criteria.andLike("movieName","%"+comment.getMovieName()+"%");
        }
        if(!StringUtils.isBlank(comment.getNickname())){
            criteria.andLike("nickname","%"+comment.getNickname()+"%");
        }
        List<Comment> comments = commentMapper.selectByExample(example);
        PageInfo<Comment> pageInfo = new PageInfo(comments,pagesize);
        PageResult<Comment> pageResult = new PageResult<Comment>();
        pageResult.setItems(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return  pageResult;
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

    @Override
    public List<Comment> getCommentsBymovie(Long movieid) {

        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        if(movieid!=null){
            criteria.andEqualTo("movieid",movieid);
        }
        List<Comment> comments = commentMapper.selectByExample(example);
        return  comments;
    }

    @Override
    public Float getMovieScoreByMovieId(Long movieid) {
        Float score = commentMapper.getMovieScoreByMovieId(movieid);
        return score;
    }
}
