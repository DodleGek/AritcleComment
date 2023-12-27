package org.example.article.dao;

import org.example.article.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    // 继承MongoRepository接口，MongoRepository是Spring Data MongoDB库提供的一个接口，用于操作MongoDB数据库
    // Comment是实体类型，String是Comment实体的ID类型

    // 根据父评论ID查询评论，并返回分页结果
    // parentid是父评论的ID
    // pageable是一个接口，封装了分页信息，包括页码和每页的记录数
    Page<Comment> findByParentid(String parentid, Pageable pageable);

    // 根据文章ID查询评论
    List<Comment> findByArticleid(String articleId);
}