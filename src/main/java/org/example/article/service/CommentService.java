package org.example.article.service;

import org.example.article.dao.CommentRepository;
import org.example.article.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    // 通过Spring的依赖注入机制，自动创建并注入CommentRepository和MongoTemplate实例
    private final CommentRepository commentRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CommentService(CommentRepository commentRepository, MongoTemplate mongoTemplate) {
        this.commentRepository = commentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 保存评论
     *
     * @param comment 要保存的评论对象
     */
    public void saveComment(Comment comment) {
        // 如果Comment对象的ID已存在，则更新记录；否则，创建新记录
        commentRepository.save(comment);
    }

    /**
     * 更新评论
     *
     * @param comment 要更新的评论对象
     */
    public void updateComment(Comment comment) {
        // 保存或更新评论
        commentRepository.save(comment);
    }

    /**
     * 根据ID删除评论
     *
     * @param id 要删除的评论的ID
     */
    public void deleteCommentById(String id) {
        // 删除指定ID的评论
        commentRepository.deleteById(id);
    }

    /**
     * 查询所有评论
     *
     * @return 返回所有评论的列表
     */
    public List<Comment> findCommentList() {
        // 查询所有评论
        return commentRepository.findAll();
    }

    /**
     * 根据ID查询评论
     *
     * @param id 要查询的评论的ID
     * @return 返回查询到的评论，如果没有找到则返回null
     */
    public Comment findCommentById(String id) {
        // 查询指定ID的评论
        return commentRepository.findById(id).orElse(null);
    }

    /**
     * 根据父评论的ID查询子评论，并进行分页处理
     *
     * @param parentid 父评论的ID
     * @param page 当前页码
     * @param size 每页显示的评论数量
     * @return 返回一个Page对象，包含查询到的子评论列表和分页信息
     */
    public Page<Comment> findCommentListBy(String parentid, int page, int size) {
        // 根据父评论的ID查询子评论，并进行分页处理
        return commentRepository.findByParentid(parentid, PageRequest.of(page - 1, size));
    }

    /**
     * 对指定评论的点赞数进行+1操作
     * @param id 评论的ID
     */
    public void updateCommentLikenum(String id) {
        // 构建查询条件
        Query query = Query.query(Criteria.where("_id").is(id));
        // 构建更新条件，使用$inc操作符，表示要增加的字段
        Update update = new Update();
        update.inc("likenum");
        // 更新第一个匹配的文档
        mongoTemplate.updateFirst(query, update, Comment.class);
    }
}