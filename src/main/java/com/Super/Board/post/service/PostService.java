package com.Super.Board.post.service;


import com.Super.Board.post.dto.PostDTO;
import com.Super.Board.post.entity.PostEntity;
import com.Super.Board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 생성
    public PostEntity createPost(PostEntity postEntity, String email){
        return postRepository.save(postEntity);
    }


    // 단일 게시글 조회
    public PostEntity findPost(long postId){
        return findVerifiedPost(postId);
    }

    public List<PostEntity> findPosts(){
        return postRepository.findAll();
    }

    public List<PostDTO> getPostsByPostWriter(String author) {
        System.out.println("author = " + author);
        List<PostEntity> postEntities = postRepository.findByAuthorContaining(author);

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
    // 게시글 삭제
    public void deletePost(long postId) {
        PostEntity findPost = findVerifiedPost(postId);

        postRepository.delete(findPost);
    }

    // 게시물 있나 검증
    private PostEntity findVerifiedPost(long postId){
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);
        PostEntity findPost = optionalPostEntity.orElseThrow(() ->
                new NoSuchElementException());
        return findPost;
    }
}
