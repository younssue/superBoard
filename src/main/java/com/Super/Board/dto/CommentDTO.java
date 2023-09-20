package com.Super.Board.dto;

import com.Super.Board.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private Long postId;
    private String content;
    private String password;
    private String author;
    private LocalDateTime createdAt;

    public static CommentDTO listDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setPostId(commentEntity.getBoardEntity().getId());
        commentDTO.setContent(commentEntity.getContent());
        commentDTO.setPassword(commentEntity.getPassword());
        commentDTO.setAuthor(commentEntity.getAuthor());
        commentDTO.setCreatedAt(commentEntity.getCreatedAt());
        return commentDTO;
    }

}
