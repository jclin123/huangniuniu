package com.huangniuniu.movie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.common.pojo.PageResult;
import com.huangniuniu.movie.client.CommentClient;
import com.huangniuniu.movie.mapper.ActorMapper;
import com.huangniuniu.movie.mapper.MovieMapper;
import com.huangniuniu.movie.pojo.Actor;
import com.huangniuniu.movie.pojo.Movie;
import com.huangniuniu.movie.pojo.MovieDetail;
import com.huangniuniu.movie.service.MovieService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private ActorMapper actorMapper;
    @Autowired
    private CommentClient commentClient;

    /**
     * 获取电影列表，超过下架时间三个月不展出
     * @return
     */
    public PageResult<Movie> getAllMovie(Integer page, Integer rows){
        Example example = new Example(Movie.class);
        Example.Criteria criteria = example.createCriteria();
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH,-3);
        Date time = instance.getTime();
        criteria.andGreaterThan("soldOutTime",time);
        List<Movie> movies = movieMapper.selectByExample(example);
        PageHelper.startPage(page, rows);
        PageInfo<Movie> pageInfo=new PageInfo<>(movies);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 分页查询电影信息，已下架三个月不展出
     * @param page
     * @param rows
     * @return
     */
//    public PageResult<Movie> getAllMovieByPage(Integer page, Integer rows){
//        List<Movie> allMovie = this.getAllMovie();
//        List<Movie> movies = movieMapper.selectByExample(allMovie);
//        PageHelper.startPage(page, rows);
//        PageInfo<Movie> pageInfo=new PageInfo<>(movies);
//        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
//
//    }



    /**
     * 根据电影id查询电影
     * @param mid
     * @return
     */

    public Movie getMovieByMovieid(Long mid){
        return this.movieMapper.selectByPrimaryKey(mid);
    }


    /**
     * 新增电影
     * @param movie
     */
    @Transactional
    public void insertMovie(Movie movie){
       this.movieMapper.insertSelective(movie);

    }

    /**
     * 根据id删除电影
     * @param id
     */
    @Transactional
    public void deleteMovie(Long id){
        this.movieMapper.deleteByPrimaryKey(id);

    }

    /**
     * 根据电影id修改电影信息
     * @param movie
     */
    @Transactional
    public void updateMovie(Movie movie){
        this.movieMapper.updateByPrimaryKeySelective(movie);
    }

    /**
     * 根据条件查询电影（名称、类型、地区模糊匹配，评分相等）
     * @param movie
     * @return
     */
    public PageResult<Movie> getMovieByCondition(Integer page, Integer rows,Movie movie){
        Example example = new Example(Movie.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isBlank(movie.getMovieName())){
            criteria.andLike("movieName","%"+movie.getMovieName()+"%");
        }
        if(!StringUtils.isBlank(movie.getMovieType())){
            criteria.andLike("movieType","%"+movie.getMovieType()+"%");
        }
        if(!StringUtils.isBlank(movie.getLocation())){
            criteria.andEqualTo("location",movie.getLocation());
        }
        if(movie.getScore()!=null){
            criteria.andEqualTo("score",movie.getScore());
        }
        List<Movie> movies = movieMapper.selectByExample(example);
        PageHelper.startPage(page, rows);
        PageInfo<Movie> pageInfo=new PageInfo<>(movies);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());

    }

    /**
     * 根据城市id查询电影
     * ishot为true为热映
     * ishot为false为即将上映
     * 已下架不展出
     * @param cid
     * @param ishot
     * @return
     */
    public PageResult<Movie> getMovieByCityid(Integer page, Integer rows,Long cid,Boolean ishot){
            List<Movie> movieByCityids = movieMapper.getMovieByCityid(cid);
        List<Movie> movies=new ArrayList<Movie>();
                movieByCityids.forEach(movieByCityid->{
                    Example example = new Example(Movie.class);
                    Example.Criteria criteria = example.createCriteria();
                    Date date = new Date();
                    if(movieByCityid.getId()!=null){
                        criteria.andEqualTo("id",movieByCityid.getId());
                    }
                    if(ishot){
                        if(movieByCityid.getSoldOutTime()!=null){
                            criteria.andNotBetween("releaseTime",date,movieByCityid.getSoldOutTime());
                            criteria.andGreaterThan("soldOutTime",date);
                        }
                    }else {
                        criteria.andGreaterThan("releaseTime",date);
                    }
                    Movie movie = movieMapper.selectOneByExample(example);
                    if(!org.springframework.util.StringUtils.isEmpty(movie)){
                        movies.add(movie);
                    }
                });
        PageHelper.startPage(page, rows);
        PageInfo<Movie> pageInfo=new PageInfo<>(movies);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 根据电影id查询封装电影详情数据
     * @param movieid
     * @return
     */
    public MovieDetail getMovieDetail(Long movieid){
        MovieDetail movieDetail = new MovieDetail();
        Movie movie = movieMapper.selectByPrimaryKey(movieid);
        List<Actor> actorByMovieid = actorMapper.getActorByMovieid(movieid);
        Comment comment = new Comment();
        comment.setMovieid(movieid);
        List<Comment> allComment = commentClient.getAllComment(comment);
        if(movie!=null){
            movieDetail.setId(movie.getId());
            movieDetail.setMovieName(movie.getMovieName());
            movieDetail.setMovieType(movie.getMovieType());
            movieDetail.setDiscription(movie.getDiscription());
            movieDetail.setLocation(movie.getLocation());
            movieDetail.setMoviePicture(movie.getMoviePicture());
            movieDetail.setReleaseTime(movie.getReleaseTime());
            movieDetail.setSoldOutTime(movie.getSoldOutTime());
            movieDetail.setPrevideo(movie.getPrevideo());
            movieDetail.setStagePhotos(movie.getStagePhotos());
            movieDetail.setScore(movie.getScore());
        }
        if(actorByMovieid!=null){
            movieDetail.setActorList(actorByMovieid);
        }
        if(allComment!=null){
            movieDetail.setCommentList(allComment);
        }
        return movieDetail;

    }

}