package com.Super.Board.comment.controller;

import com.Super.Board.comment.dto.CommentDTO;
import com.Super.Board.comment.entity.CommentEntity;
import com.Super.Board.user.entity.User;
import com.Super.Board.user.entity.userDetails.CustomUserDetails;
import com.Super.Board.comment.service.CommentService;
import com.Super.Board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> findAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

//    @PostMapping
//    public ResponseEntity<String> save(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody CommentDTO commentDTO) {
//
//        String email = customUserDetails.getUsername();
//        return commentService.save(email,commentDTO);
//    }

    @PostMapping
    public ResponseEntity<String> save(@AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody CommentDTO commentDTO) {
        String email = customUserDetails.getUsername();
        User user = userRepository.findById(customUserDetails.getUserId()).orElseThrow(() -> new EntityNotFoundException("해당하는 사용자를 찾을 수 없습니다."));
        commentDTO.setAuthor(email);
        commentDTO.setUserId(user.getUserId());
        return commentService.save(commentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id, @RequestBody CommentDTO commentDTO){

        String email = customUserDetails.getUsername();
        Optional<CommentEntity> optionalComment = commentService.updateComment(email,id, commentDTO);

        if (optionalComment.isPresent()) {
            CommentEntity updatedComment = optionalComment.get();
            CommentDTO updatedCommentDTO = CommentDTO.listDTO(updatedComment);

            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("댓글 수정에 실패하였습니다.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long id){

        String email = customUserDetails.getUsername();

        boolean deleted = commentService.deleteComment(email,id);
        if(deleted) return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        else return ResponseEntity.badRequest().body("댓글 삭제에 실패하였습니다.");
    }
}
