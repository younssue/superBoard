package com.Super.Board.comment.entity;

import com.Super.Board.comment.dto.CommentDTO;
import com.Super.Board.user.entity.BaseTimeEntity;
import com.Super.Board.post.entity.PostEntity;
import com.Super.Board.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "comment")
public class CommentEntity extends BaseTimeEntity {

    @Id
    @Column(name = "comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnore
    private User user;

    public static CommentEntity saveEntity(CommentDTO commentDTO, PostEntity postEntity, User user) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setAuthor(commentDTO.getAuthor());
        commentEntity.setPostEntity(postEntity);
        commentEntity.setUser(user);
        return commentEntity;
    }
}
