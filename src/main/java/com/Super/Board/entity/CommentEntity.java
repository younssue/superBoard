package com.Super.Board.entity;

import com.Super.Board.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String content;

    @Column
    private String password;

    @Column
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private BoardEntity boardEntity;

    public static CommentEntity saveEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setPassword(commentDTO.getPassword());
        commentEntity.setAuthor(commentDTO.getAuthor());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }

}
