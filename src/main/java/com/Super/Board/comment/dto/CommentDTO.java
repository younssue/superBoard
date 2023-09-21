package com.Super.Board.comment.dto;

import com.Super.Board.comment.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private String author;
    private String content;
    private Long postId;
    private LocalDateTime createdAt;

    private Long userId;

    public static CommentDTO listDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setPostId(commentEntity.getPostEntity().getPostId());
        commentDTO.setContent(commentEntity.getContent());
        commentDTO.setAuthor(commentEntity.getAuthor());
        commentDTO.setCreatedAt(commentEntity.getCreatedAt());
        commentDTO.setUserId(commentEntity.getUser().getUserId());
        return commentDTO;
    }
}
