package com.huangniuniu.comment.controller;

import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.comment.service.CommentService;
import com.huangniuniu.common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * 查询所有评论并分页
     * @return
     */
    @GetMapping("Commentlisttopage")
    public ResponseEntity<PageResult<Comment>> getAllComment(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        PageResult<Comment> pageResult = commentService.getAllCommentToPage(pageNumber,pageSize);
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
    public ResponseEntity<PageResult<Comment>> getAllComment(Comment comment,@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        PageResult<Comment> comments = commentService.getCommentByConditionToPage(comment,pageNumber,pageSize);
        if(comments == null || CollectionUtils.isEmpty(comments.getItems())){
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
     * 根据movieid查询评论，返回所有符合评论
     * @param movieid
     * @return
     */
    @GetMapping("bymovieid/{movieid}")
    public ResponseEntity<List<Comment>>  getCommentsBymovie(@PathVariable("movieid")Long movieid){
        List<Comment> comments = commentService.getCommentsBymovie(movieid);
//        if(comments == null || CollectionUtils.isEmpty(comments)){
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok(comments);
    }

    /**
     * 根据电影id分页查询评论
     * @param mid
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @GetMapping("commentspage/{mid}")
    public ResponseEntity<PageResult<Comment>> getcommentsPageBymid(@PathVariable("mid")Long mid,
                                                                    @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                                                    @RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber){
        PageResult<Comment> comments = this.commentService.getcommentsPageBymid(mid,pageNumber,pageSize);
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

    @GetMapping("comment/{commentid}")
    public  ResponseEntity<Comment> getCommentByCommentid(@PathVariable("commentid") Long commentid){
        Comment comment = commentService.getCommentByid(commentid);
        if (comment == null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(comment);
    }

    /**
     * 根据排场id和用户id查询该评论是不是存在，存在则不能再评论
     * @param userid
     * @param skeduleid
     * @return
     */
    @GetMapping("getcomment/{userid}/{skeduleid}")
    public ResponseEntity<Boolean> getCommentByUseridAndSkeduleid(@PathVariable("userid")Long userid,
                                                                  @PathVariable("skeduleid")Long skeduleid){
        Boolean bool = this.commentService.getCommentByUseridAndSkeduleid(userid,skeduleid);
        if(!bool){
            return ResponseEntity.badRequest().build();//参数问题，响应400
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bool);

    }
}
