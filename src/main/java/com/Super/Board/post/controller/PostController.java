package com.Super.Board.post.controller;

import com.Super.Board.post.dto.PostDTO;
import com.Super.Board.user.repository.UserRepository;
import com.Super.Board.post.entity.PostEntity;
import com.Super.Board.user.repository.entity.User;
import com.Super.Board.user.repository.entity.userDetails.CustomUserDetails;
import com.Super.Board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")//공통 주소
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;


    // 셍성
    @PostMapping
    public ResponseEntity postPost(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody PostDTO postDto){

        String email = customUserDetails.getUsername();
        User user = userRepository.findById(customUserDetails.getUserId()).orElseThrow(() -> new EntityNotFoundException("해당하는 사용자를 찾을 수 없습니다."));

        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setContents(postDto.getContents());
        postEntity.setAuthor(email);
        postEntity.setUser(user);

        PostEntity response = postService.createPost(postEntity, email);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 게시글 전체조회
    @GetMapping
    public ResponseEntity getPosts(){
        List<PostEntity> response = postService.findPosts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //검색
    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> getPostsByPostWriter(@RequestParam(name = "author") String author) {
        System.out.println("author = " + author);

        List<PostDTO> postsByPostWriter = postService.getPostsByPostWriter(author);

        // 결과가 비어 있는 경우 404 Not Found 응답을 반환
        if (postsByPostWriter.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 결과가 있는 경우 200 OK 응답을 반환
        return new ResponseEntity<>(postsByPostWriter, HttpStatus.OK);
    }



    // 게시글 수정
    //post_id? or postId
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable long postId, @RequestBody PostDTO updatedPostDTO) {

        // 게시글 ID를 사용하여 해당 게시글을 업데이트
        System.out.println("postId = " + postId);
             
        String author = customUserDetails.getUsername();
        System.out.println(author);

// 기존 작성 글의 이메일
        PostDTO existingPost = postService.getPostById(postId);

        if (existingPost != null) {
            // 로그인한 유저와 기존 작성 글의 이메일 비교
            if (author.equals(existingPost.getAuthor())) {
                PostDTO updatedPost = postService.updatePost(postId, updatedPostDTO);

                if (updatedPost != null) {
                    return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 수정 중 오류가 발생했습니다.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자가 달라서 수정할 권한이 없습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정할 권한이 없습니다.");
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{post-id}")
    public String deletePost(@PathVariable("post-id") @Positive long postId){
        postService.deletePost(postId);

        return "success post deleted";
    }

}
