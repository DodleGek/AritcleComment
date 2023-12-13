package org.example.article.controller;

import org.example.article.po.Comment;
import org.example.article.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class Controller {
    private final CommentService commentService;

    public Controller(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("findall")
    public List<Comment> testFindAll(){
        return commentService.findCommentList();
    }

    @RequestMapping("insert")
    public void testSaveComment(String articleid,String content){
        Comment comment = new Comment();
        comment.setArticleid(articleid);
        comment.setContent(content);
        comment.setCreatedatetime(LocalDateTime.now());
        comment.setUserid("1003");
        comment.setNickname("测试用户");
        comment.setState("0");
        comment.setLikenum(0);
        commentService.saveComment(comment);
    }

    @RequestMapping("find")
    public String testFindCommentListPageByParentid(String parentid,int page,int size){
        Page<Comment> pageResponse = commentService.findCommentListBy(parentid, page, size);
        return "总记："+pageResponse.getTotalElements()+"当前"+pageResponse.getContent();
    }
}