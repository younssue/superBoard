package com.Super.Board.comment.controller;

import com.Super.Board.comment.dto.CommentDTO;
import com.Super.Board.comment.entity.CommentEntity;
import com.Super.Board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> findAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody CommentDTO commentDTO) {
        return commentService.save(commentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        Optional<CommentEntity> optionalComment = commentService.updateComment(id, commentDTO);

        if (optionalComment.isPresent()) {
            CommentEntity updatedComment = optionalComment.get();
            CommentDTO updatedCommentDTO = CommentDTO.listDTO(updatedComment);

            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("댓글 수정에 실패하였습니다.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        boolean deleted = commentService.deleteComment(id);
        if(deleted) return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        else return ResponseEntity.badRequest().body("댓글 삭제에 실패하였습니다.");
    }
}
