package com.huangniuniu.comment.controller;

import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.comment.service.CommentService;
import com.huangniuniu.common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    public CommentService commentService;

    /**
     * 查询所有评论
     * @return
     */
    @GetMapping("Commentlist")
    public ResponseEntity<List<Comment>> getAllComment(){
        List<Comment> comments = commentService.getAllComment();
        if(CollectionUtils.isEmpty(comments)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }

    /**
     * 根据条件查询所有评论并分页
     * @return
     */
    @GetMapping("Commentlisttopage")
    public ResponseEntity<PageResult<Comment>> getAllComment(@RequestParam(value = "pn",defaultValue = "1") Integer pn,@RequestParam(value = "pagesize",defaultValue = "10") Integer pagesize){
        PageResult<Comment> pageResult = commentService.getAllCommentToPage(pn,pagesize);
        if(pageResult == null || CollectionUtils.isEmpty(pageResult.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据条件查询评论，返回所有符合评论
     * @param comment
     * @return
     */
    @GetMapping("Comments")
    public ResponseEntity<List<Comment>> getAllComment(Comment comment){
        List<Comment> comments = commentService.getCommentByCondition(comment);
        if(CollectionUtils.isEmpty(comments)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }

    /**
     * 根据条件查询评论，返回所有符合评论并分页
     * @param comment
     * @return
     */
    @GetMapping("Commentstopage")
    public ResponseEntity<PageResult<Comment>> getAllComment(Comment comment,@RequestParam(value = "pn",defaultValue = "1") Integer pn,@RequestParam(value = "pagesize",defaultValue = "10") Integer pagesize){
        PageResult<Comment> comments = commentService.getCommentByConditionToPage(comment,pn,pagesize);
        if(comment == null || CollectionUtils.isEmpty(comments.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> insertComment(Comment comment){
        commentService.insertComment(comment);
        return ResponseEntity.noContent().build();
    }

    /**
     * 更新评论
     * @param comment
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateComment(Comment comment){
        commentService.updateComment(comment);
        return ResponseEntity.noContent().build();
    }

    /**
     * 根据id删除评论
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 根据movieid查询评论，返回所有符合评论并分页
     * @param movieid
     * @param pn
     * @param pagesize
     * @return
     */
    @GetMapping("bymovieid/{movieid}")
    public ResponseEntity<PageResult<Comment>>  getCommentsBymovie(@PathVariable("movieid")Long movieid,
                                                                   @RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                                                   @RequestParam(value = "pagesize",defaultValue = "10")Integer pagesize){
        PageResult<Comment> comments = commentService.getCommentsBymovie(movieid,pn,pagesize);
        if(comments == null || CollectionUtils.isEmpty(comments.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }

    /**
     * 根据movieid获得电影平均评分
     * @param movieid
     * @return 返回电影平均评分
     */
    @GetMapping("moviescore/{movieid}")
    public ResponseEntity<Float> getMovieScoreByMovieId(@PathVariable("movieid") Long movieid){
        Float score = commentService.getMovieScoreByMovieId(movieid);
        if(score == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(score);
    }
}
