package org.example.article.controller;

import org.example.article.po.Comment;
import org.example.article.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class Controller {
    private final CommentService commentService;

    public Controller(CommentService commentService) {
        this.commentService = commentService;
    }


    @RequestMapping(value = "like", method = RequestMethod.POST)
    @ResponseBody
    public String likeComment(@RequestParam String id) {
        commentService.incrementCommentLikenum(id);
        return "点赞成功";
    }

    @RequestMapping(value = "unlike", method = RequestMethod.POST)
    @ResponseBody
    public String unlikeComment(@RequestParam String id) {
        commentService.decrementCommentLikenum(id);
        return "取消点赞成功";
    }


    @RequestMapping(value = "reply", method = RequestMethod.POST) // 映射到POST请求的/reply路径
    @ResponseBody // 表示该方法的返回结果直接写入 HTTP 响应体中
    public String replyToComment(@RequestParam String parentid, @RequestParam String content) {
        Comment comment = new Comment(); // 创建一个新的评论对象
        comment.setArticleid("articleid"); // 设置评论所属的文章ID，这里需要替换为实际的文章ID
        comment.setContent(content); // 设置评论的内容
        comment.setCreatedatetime(LocalDateTime.now()); // 设置评论的创建时间为当前时间
        comment.setUserid("1003"); // 设置发表评论的用户ID，这里需要替换为实际的用户ID
        comment.setNickname("测试用户"); // 设置发表评论的用户昵称，这里需要替换为实际的用户昵称
        comment.setState("0"); // 设置评论的状态为"0"
        comment.setLikenum(0); // 设置评论的点赞数为0
        comment.setParentid(parentid); // 设置评论的父评论ID，即该评论是回复哪个评论的
        commentService.saveComment(comment); // 调用服务层的saveComment方法保存评论
        return "回复成功"; // 返回"回复成功"字符串
    }

    @PostMapping("comment")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        commentService.saveComment(comment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteComment(@PathVariable String id) {
        commentService.deleteCommentById(id);
        return "删除成功";
    }

    @PutMapping("/{id}")
    public String updateComment(@PathVariable String id, @RequestBody Comment comment) {
        comment.setId(id);
        commentService.updateComment(comment);
        return "更新成功";
    }

    @GetMapping("/articles/{articleId}/comments")
    public List<Comment> findCommentList(@PathVariable String articleId) {
        return commentService.findCommentList(articleId);
    }

    @GetMapping("/parent/{parentid}")
    public Page<Comment> findCommentListByParentid(@PathVariable String parentid,
                                                   @RequestParam int page,
                                                   @RequestParam int size) {
        return commentService.findCommentListByParentid(parentid, page, size);
    }

    @PutMapping("/like/{id}")
    public String incrementCommentLikenum(@PathVariable String id) {
        commentService.incrementCommentLikenum(id);
        return "点赞成功";
    }

    @PutMapping("/unlike/{id}")
    public String decrementCommentLikenum(@PathVariable String id) {
        commentService.decrementCommentLikenum(id);
        return "取消点赞成功";
    }
}