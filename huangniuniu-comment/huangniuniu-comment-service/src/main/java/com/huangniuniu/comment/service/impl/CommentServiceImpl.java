package com.huangniuniu.comment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.comment.client.MovieClient;
import com.huangniuniu.comment.mapper.CommentMapper;
import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.comment.service.CommentService;
import com.huangniuniu.common.pojo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    public CommentMapper commentMapper;

    @Autowired
    public MovieClient movieClient;

    @Autowired
    private AmqpTemplate amqpTemplate;


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
        example.orderBy("commentTime").desc();
        List<Comment> comments = commentMapper.selectByExample(example);
        PageInfo<Comment> pageInfo = new PageInfo(comments,pagesize);
        PageResult<Comment> pageResult = new PageResult<Comment>();
        pageResult.setItems(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return  pageResult;
    }

    @Override
    public void insertComment(Comment comment) {
        //comment.setMovieName(movieClient.getMovieByMovieid(comment.getMovieid()).getMovieName());
        //UserInfo userInfo = LoginInterceptor.getuserInfo();
        //comment.setUserid(userInfo.getId());
        //comment.setNickname(userInfo.getUsername());
        Date date = new Date();
        comment.setCommentTime(date);
        commentMapper.insertSelective(comment);
        //查询该评论的电影平均得分
        Float score = this.getMovieScoreByMovieId(comment.getMovieid());
        Map<String,Object> msg = new HashMap<>();
        msg.put("movieid",comment.getMovieid());
        msg.put("score",score);
        amqpTemplate.convertAndSend("huangniuniu.movie.exchange","movie.score",msg);
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
        example.orderBy("commentTime").desc();
        List<Comment> comments = commentMapper.selectByExample(example);

        return  comments;
    }

    @Override
    public Float getMovieScoreByMovieId(Long movieid) {
        Float score = commentMapper.getMovieScoreByMovieId(movieid);
        return score;
    }

    @Override
    public Comment getCommentByid(Long commentid) {
        Comment comment = commentMapper.selectByPrimaryKey(commentid);
        return  comment;
    }

    @Override
    public PageResult<Comment> getcommentsPageBymid(Long mid, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        if(mid!=null){
            criteria.andEqualTo("movieid",mid);
        }
        example.orderBy("commentTime").desc();
        List<Comment> comments = commentMapper.selectByExample(example);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        PageResult<Comment> pageResult = new PageResult<Comment>();
        pageResult.setItems(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return  pageResult;
    }

    @Override
    public Boolean getCommentByUseridAndSkeduleid(Long userid, Long skeduleid) {
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userid",userid);
        criteria.andEqualTo("skeduleid",skeduleid);
        List<Comment> comments = this.commentMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(comments)){
            return false;
        }
        return true;
    }
}
