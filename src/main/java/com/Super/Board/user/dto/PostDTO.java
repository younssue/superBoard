package com.Super.Board.user.dto;

import com.Super.Board.user.repository.entity.PostEntity;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDTO {

    private Long postId;
    // private String author; //작성자
    private String author;
    private String title; //제목
    private String contents; // 내용
    private LocalDateTime createdAt; // 작성 시간
    private LocalDateTime updatedAt; // 수정 시간


    //entity -> dto

    public static PostDTO toPostDTO(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(postEntity.getPostId());
        postDTO.setAuthor(postEntity.getAuthor());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setContents(postEntity.getContents());
        postDTO.setCreatedAt(postEntity.getCreatedAt());
        postDTO.setUpdatedAt(postEntity.getUpdatedAt());

        return postDTO;
    }




}
