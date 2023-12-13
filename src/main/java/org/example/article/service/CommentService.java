package org.example.article.service;

import org.example.article.dao.CommentRepository;
import org.example.article.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    //注入dao
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * 保存一个评论
     *
     * @param comment 要保存的评论内容
     */
    public void saveComment(Comment comment) {
        //如果需要自定义主键，可以在这里指定主键，否则会自动生成主键
        //设置一些默认初始值
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 更新评论
     *
     * @param comment 要更新的评论内容
     */
    public void updateComment(Comment comment) {
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 根据id删除评论
     *
     * @param id 要删除的评论的id
     */
    public void deleteCommentById(String id) {
        //调用dao
        commentRepository.deleteById(id);
    }

    /**
     * 查询所有评论
     *
     * @return List<Comment>
     */
    public List<Comment> findCommentList() {
        //调用dao
        return commentRepository.findAll();
    }

    /**
     * 根据id查询评论
     *
     * @param id 要查询的评论的id
     * @return Comment
     */
    public Comment findCommentById(String id) {
        //调用dao
        return commentRepository.findById(id).orElse(null);
    }

    public Page<Comment> findCommentListBy(String parentid, int page, int size) {
        //调用dao
        return commentRepository.findByParentid(parentid, PageRequest.of(page - 1, size));
    }

}