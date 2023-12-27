package org.example.article.controller;

import org.example.article.po.Comment;
import org.example.article.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    private final CommentService commentService;

    // 通过构造器注入CommentService
    public Controller(CommentService commentService) {
        this.commentService = commentService;
    }

    // 处理POST请求，创建新的评论
    @PostMapping("/articles/{articleId}/comments/{id}")
    public ResponseEntity<Comment> createComment(@PathVariable String articleId, @RequestBody String content, @PathVariable String id) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setArticleid(articleId); // 设置评论的文章ID
        comment.setContent(content); // 设置评论的内容
        commentService.saveComment(comment); // 保存评论
        return new ResponseEntity<>(comment, HttpStatus.CREATED); // 返回创建的评论和HTTP状态码201
    }

    // 处理DELETE请求，删除指定ID的评论
    @DeleteMapping("/comments/{id}")
    @ResponseBody
    public String deleteComment(@PathVariable String id) {
        commentService.deleteCommentById(id); // 删除评论
        return "删除成功"; // 返回删除成功的消息
    }

    // 处理PUT请求，更新指定ID的评论
    @PutMapping("/comments/{id}")
    public String updateComment(@PathVariable String id, @RequestBody String content) {
        Comment comment = commentService.findCommentById(id); // 查找评论
        if (comment != null) {
            comment.setContent(content); // 更新评论的内容
            commentService.updateComment(comment); // 更新评论
            return "更新成功"; // 返回更新成功的消息
        } else {
            return "评论不存在"; // 如果评论不存在，返回错误消息
        }
    }

    // 处理GET请求，获取指定文章ID的所有评论
    @GetMapping("/articles/{articleId}/comments")
    public List<Comment> findCommentList(@PathVariable String articleId) {
        return commentService.findCommentList(articleId); // 返回评论列表
    }

    // 处理GET请求，获取指定父评论ID的所有子评论，并进行分页处理
    @GetMapping("/parent/{parentid}")
    public Page<Comment> findCommentListByParentid(@PathVariable String parentid,
                                                   @RequestParam int page,
                                                   @RequestParam int size) {
        return commentService.findCommentListByParentid(parentid, page, size); // 返回子评论的分页列表
    }

    // 处理PUT请求，对指定ID的评论进行点赞（点赞数+1）
    @PutMapping("/like/{id}")
    public String incrementCommentLikenum(@PathVariable String id) {
        if (commentService.findCommentById(id) == null) {
            return "评论不存在";
        } else {
            commentService.incrementCommentLikenum(id); // 点赞
            return "点赞成功"; // 返回点赞成功的消息
        }
    }

    // 处理PUT请求，对指定ID的评论取消点赞（点赞数-1）
    @PutMapping("/unlike/{id}")
    public String decrementCommentLikenum(@PathVariable String id) {
        if (commentService.findCommentById(id) == null) {
            return "评论不存在";
        } else {
            if (commentService.findCommentById(id).getLikenum() == 0) {
                return "点赞数不能为负数";
            } else {
                commentService.decrementCommentLikenum(id); // 取消点赞
                return "取消点赞成功"; // 返回取消点赞成功的消息
            }
        }
    }
}