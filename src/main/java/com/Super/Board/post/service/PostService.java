package com.Super.Board.post.service;


import com.Super.Board.post.dto.PostDTO;
import com.Super.Board.post.entity.PostEntity;
import com.Super.Board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostDTO> getPostsByPostWriter(String author) {
        System.out.println("author = " + author);
        List<PostEntity> postEntities = postRepository.findByPostWriterContaining(author);

        // 결과가 존재하는지 확인
        if (!postEntities.isEmpty()) {
            List<PostDTO> postDTOList = new ArrayList<>();
            for (PostEntity postEntity : postEntities) {
                postDTOList.add(PostDTO.toPostDTO(postEntity));
            }
            return postDTOList;
        } else {
            // 결과가 없는 경우 RuntimeException 예외를 던짐
            throw new RuntimeException("해당 작성자에게서 글을 찾을 수 없습니다.");
        }
    }


    public PostDTO getPostById(Long postId) {
        // 게시글을 데이터베이스에서 조회
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        if (optionalPostEntity.isPresent()) {
            // 조회한 게시글이 존재하는 경우
            PostEntity postEntity = optionalPostEntity.get();
            return PostDTO.toPostDTO(postEntity);
        } else {
            // 조회한 게시글이 없는 경우
            return null;
        }
    }

    @Transactional
    public PostDTO updatePost(Long postId, PostDTO updatedPostDTO) {
        // 게시글을 데이터베이스에서 조회
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        if (optionalPostEntity.isPresent()) {
            // 조회한 게시글이 존재하는 경우
            PostEntity existingPostEntity = optionalPostEntity.get();

            // 게시글 내용을 업데이트


            existingPostEntity.setTitle(updatedPostDTO.getTitle());
            existingPostEntity.setContents(updatedPostDTO.getContents());


            // 데이터베이스에 업데이트된 게시글 저장
            existingPostEntity = postRepository.save(existingPostEntity);

            // 업데이트된 게시글을 PostDTO로 변환하여 반환
            return PostDTO.toPostDTO(existingPostEntity);
        } else {
            // 조회한 게시글이 없는 경우
            return null;
        }
    }
}
