package com.Super.Board.comment.entity;

import com.Super.Board.comment.dto.CommentDTO;
import com.Super.Board.user.repository.entity.BaseTimeEntity;
import com.Super.Board.post.entity.PostEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    public static CommentEntity saveEntity(CommentDTO commentDTO, PostEntity postEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setAuthor(commentDTO.getAuthor());
        commentEntity.setPostEntity(postEntity);
        return commentEntity;
    }
}
