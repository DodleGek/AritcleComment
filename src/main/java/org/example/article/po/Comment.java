package org.example.article.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Document(collection = "comment") // 如果省略，将默认使用类名小写映射集合
@CompoundIndex(def = "{'userid':1,'nickname':-1}") // 创建复合索引，userid是升序，nickname是降序
public class Comment implements Serializable {

    @Id
    private String id; // 主键ID

    @Field("content")
    private String content; // 评论内容，对应mongodb的字段名，如果一致，则无需该注解

    private Date publishtime; // 评论发布日期

    @Indexed
    private String userid; // 发布人ID，添加一个单字段索引

    private String nickname; // 发布人昵称

    private LocalDateTime createdatetime; // 评论创建的日期和时间

    private Integer likenum; // 评论的点赞数

    private Integer replynum; // 评论的回复数

    private String state; // 评论的状态

    private String parentid; // 父评论ID

    private String articleid; // 文章ID

    // getter和setter方法
    // toString方法
}