package com.Super.Board.controller;

import com.Super.Board.dto.CommentDTO;
import com.Super.Board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<String> save(@RequestBody CommentDTO commentDTO) {
        return commentService.save(commentDTO);
    }

    // 댓글 수정
    @PutMapping("/comments/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Map<String, String> map) {
        /**
         * map(key, value)
         * 여기서 key 값은 content, password
         */
        String content = map.get("content");
        String password = map.get("password");
        return commentService.update(id, content, password);
    }

    // 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> findAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, @RequestBody Map<String, String> map) {
        String password = map.get("password");
        return commentService.delete(id, password);
    }

}
