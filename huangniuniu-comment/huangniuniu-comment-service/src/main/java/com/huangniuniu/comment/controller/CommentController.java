package com.huangniuniu.comment.controller;

import com.huangniuniu.comment.pojo.Comment;
import com.huangniuniu.comment.service.CommentService;
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
    @DeleteMapping
    public ResponseEntity<Void> deleteComment(Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
