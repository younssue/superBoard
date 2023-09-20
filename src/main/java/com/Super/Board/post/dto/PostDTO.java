package com.Super.Board.post.dto;

import com.Super.Board.post.entity.PostEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDTO {

    private Long postId;
    // private String author; //작성자
    private String author = "user@email.com";
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
