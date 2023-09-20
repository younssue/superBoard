package com.Super.Board.service;

import com.Super.Board.dto.CommentDTO;
import com.Super.Board.entity.CommentEntity;
import com.Super.Board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public ResponseEntity<String> save(CommentDTO commentDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getPostId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.saveEntity(commentDTO, boardEntity);
            commentRepository.save(commentEntity);
            return ResponseEntity.status(200).body("댓글이 성공적으로 저장되었습니다.");
        } else {
            return ResponseEntity.status(200).body("댓글 저장이 실패하였습니다.");
        }
    }

    @Transactional
    public ResponseEntity<String> update(Long id, String content, String password) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
        CommentEntity getCommentEntity = optionalCommentEntity.get();
        if (getCommentEntity.getPassword().equals(password)){
            getCommentEntity.setContent(content);
            commentRepository.save(getCommentEntity);
            return ResponseEntity.status(200).body("댓글이 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.status(200).body("댓글 수정이 실패하였습니다.");
        }
    }

    public List<CommentDTO> findAll() {
        List<CommentEntity> commentEntityList = commentRepository.findAll();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            commentDTOList.add(CommentDTO.listDTO(commentEntity));
        }
        return commentDTOList;
    }

    public ResponseEntity<String> delete(Long id, String password) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
        CommentEntity commentEntity = optionalCommentEntity.get();
        if (commentEntity.getPassword().equals(password)) {
            commentRepository.deleteById(id);
            return ResponseEntity.status(200).body("댓글이 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(200).body("댓글 삭제가 실패하였습니다.");
        }
    }

}
